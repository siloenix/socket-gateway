package com.siloenix.socket.codec;

import com.siloenix.socket.message.Message;
import com.siloenix.socket.util.Crc;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

public class ClientMessageEncoder extends MessageToByteEncoder<Message> {
    public static final int LENGTH_PARAM_SIZE = 1;
    public static final int CRC_PARAM_SIZE = 2;

    private MessageSerializer serializer;

    public ClientMessageEncoder(MessageSerializer serializer) {
        this.serializer = serializer;
    }

    @Override
    protected void encode(ChannelHandlerContext ctx, Message msg, ByteBuf out) throws Exception {
        byte[] messageBody = serializer.serialize(msg);
        int length = messageBody.length + LENGTH_PARAM_SIZE + CRC_PARAM_SIZE;
        out.writeByte(length);
        out.writeBytes(messageBody);
        out.writeChar((int) Crc.calculate(messageBody));
    }
}
