package com.siloenix.socket.codec;

import com.siloenix.socket.message.Message;
import com.siloenix.socket.util.Crc;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

public class MessageEncoder extends MessageToByteEncoder<Message<?, ?>> {
    private MessageSerializer serializer;

    public MessageEncoder(MessageSerializer serializer) {
        this.serializer = serializer;
    }

    @Override
    protected void encode(ChannelHandlerContext ctx, Message<?, ?> msg, ByteBuf out) throws Exception {
        out.writeByte(Message.SPECIAL_SYMBOL);
        byte[] messageBody = serializer.serialize(msg);
        out.writeBytes(messageBody);
        out.writeChar((int) Crc.calculate(messageBody));
    }
}
