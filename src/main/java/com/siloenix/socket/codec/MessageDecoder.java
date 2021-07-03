package com.siloenix.socket.codec;

import com.siloenix.socket.errors.GatewayErrorCode;
import com.siloenix.socket.errors.SocketGatewayException;
import com.siloenix.socket.util.Crc;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import lombok.extern.slf4j.Slf4j;

import java.util.Arrays;
import java.util.List;

@Slf4j
public class MessageDecoder extends ByteToMessageDecoder {
    private static final int MESSAGE_SIZE_INDEX = 0;
    private static final int CRC_BYTES_COUNT = 2;
    private static final int LENGTH_BYTES_COUNT = 1;

    private final MessageDeserializer deserializer;

    public MessageDecoder(MessageDeserializer deserializer) {
        this.deserializer = deserializer;
    }

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
        log.info("Receiving bytes from: {}", ctx.channel().remoteAddress());
        int readableBytes = in.readableBytes();
        if (readableBytes < 1) {
            log.info("Empty buffer received -- waiting...");
            return;
        }
        int messageSize = Byte.toUnsignedInt(in.getByte(MESSAGE_SIZE_INDEX));
        log.info("Current buffer size after receiving: {}", readableBytes);


        if (readableBytes < messageSize) {
            ByteBuf copied = Unpooled.copiedBuffer(in);
            byte[] partialBytes = new byte[copied.readableBytes()];
            copied.readBytes(partialBytes);

            log.info("Current buffer: {}", Arrays.toString(partialBytes));
            log.info(
                    "Current buffer size is less then message size: {}/{} -- waiting...",
                    readableBytes,
                    messageSize
            );
            in.resetReaderIndex();
            in.resetWriterIndex();
            throw new SocketGatewayException(GatewayErrorCode.BAD_PACKET, String.format("Message is shorter than declared message size %s < %s", readableBytes, messageSize));
        }
        if (readableBytes > messageSize) {
            in.resetReaderIndex();
            in.resetWriterIndex();
            throw new SocketGatewayException(GatewayErrorCode.BAD_PACKET, String.format("Message is longer than declared message size %s > %s", readableBytes, messageSize));
        }

        // todo: fix crc to include length
        // skip length
        in.readBytes(LENGTH_BYTES_COUNT);

        byte[] messageBody = new byte[messageSize - CRC_BYTES_COUNT - LENGTH_BYTES_COUNT];
        in.readBytes(messageBody);
        long messageCrc = in.readChar();
        log.info("Final bytes received: {}", Arrays.toString(messageBody));
        log.info("Crc: {}", messageCrc);

        Crc.check(messageBody, messageCrc);
        out.add(deserializer.deserialize(messageBody));
    }
}
