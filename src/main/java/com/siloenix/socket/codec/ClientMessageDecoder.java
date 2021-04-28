package com.siloenix.socket.codec;

import com.siloenix.socket.smp.SmpDeserializer;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import lombok.extern.slf4j.Slf4j;

import java.util.Arrays;
import java.util.List;

@Slf4j
public class ClientMessageDecoder extends ByteToMessageDecoder {
    private static final int MESSAGE_SIZE_INDEX = 0;
    private static final int CRC_BYTES_COUNT = 2;

    private MessageDeserializer deserializer = new SmpDeserializer();

        @Override
        protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
            log.info("Receiving bytes from: {}", ctx.channel().remoteAddress());
            int readableBytes = in.readableBytes();
            if (readableBytes < 1) {
                log.info("Empty buffer received -- waiting...");
                return;
            }

            // special symbol
            in.readByte();

            int messageSize = in.getByte(1);
            log.info("Current buffer size after receiving: {}", readableBytes);


//            if (readableBytes < messageSize) {
//                ByteBuf copied = Unpooled.copiedBuffer(in);
//                byte[] partialBytes = new byte[copied.readableBytes()];
//                copied.readBytes(partialBytes);
//
//                log.info("Current buffer: {}", Arrays.toString(partialBytes));
//                log.info(
//                        "Current buffer size is less then message size: {}/{} -- waiting...",
//                        readableBytes,
//                        messageSize
//                );
//                in.resetReaderIndex();
//                in.resetWriterIndex();
//                throw new Error(String.format("Message is shorter than declared message size %s < %s", readableBytes, messageSize));
//            }
//            if (readableBytes > messageSize) {
//                in.resetReaderIndex();
//                in.resetWriterIndex();
//                throw new Error(String.format("Message is longer than declared message size %s > %s", readableBytes, messageSize));
//            }
            byte[] messageBody = new byte[messageSize - CRC_BYTES_COUNT];
            in.readBytes(messageBody);
            long messageCrc = in.readChar();
            log.info("Final bytes received: {}", Arrays.toString(messageBody));
            log.info("Crc: {}", messageCrc);
            out.add(deserializer.deserialize(messageBody));
        }
}
