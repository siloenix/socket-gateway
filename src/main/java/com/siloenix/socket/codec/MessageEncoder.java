package com.siloenix.socket.codec;

import com.siloenix.socket.message.Message;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

public class MessageEncoder extends MessageToByteEncoder<Message> {
    @Override
    protected void encode(ChannelHandlerContext ctx, Message msg, ByteBuf out) throws Exception {
        out.writeByte(Message.SPECIAL_SYMBOL);
        out.writeBytes(msg.serialize());
    }
}
