package com.siloenix.socket.codec;

import com.siloenix.socket.errors.ErrorCode;
import com.siloenix.socket.errors.SocketGatewayException;
import com.siloenix.socket.message.Message;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import lombok.extern.slf4j.Slf4j;

import java.util.Arrays;
import java.util.List;

@Slf4j
public class MessageDecoder extends ByteToMessageDecoder {
        @Override
        protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
            log.info("Receiving bytes from: {}", ctx.channel().remoteAddress());
            int readableBytes = in.readableBytes();
            if (readableBytes < 1) {
                log.info("Empty buffer received -- waiting...");
                return;
            }
            int messageSize = in.getByte(0);
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
                throw new SocketGatewayException(ErrorCode.BAD_PACKET, String.format("Message is shorter than declared message size %s < %s", readableBytes, messageSize));
            }
            if (readableBytes > messageSize) {
                in.resetReaderIndex();
                in.resetWriterIndex();
                throw new SocketGatewayException(ErrorCode.BAD_PACKET, String.format("Message is longer than declared message size %s > %s", readableBytes, messageSize));
            }
            byte[] bytes = new byte[messageSize];
            in.readBytes(bytes);
            log.info("Final bytes received: {}", Arrays.toString(bytes));
            out.add(Message.deserialize(bytes));
        }
}
