package com.siloenix.socket.server;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.util.Arrays;

@ChannelHandler.Sharable
public class EchoConnectionHandler extends ChannelInboundHandlerAdapter {

//    @Override
//    public void channelRead(ChannelHandlerContext ctx, Object data) throws Exception {
//        Message message = (Message) data;
//        System.out.println("Server received: " + message);
//
//        byte[] serialize = message.serialize();
//        ctx.write(Unpooled.copiedBuffer(serialize));
//
////        SocketStore.contexts.forEach((context) -> {
////            System.out.println("asdf");
////            context.writeAndFlush(Unpooled.copiedBuffer("asdf", CharsetUtil.UTF_8));
////        });
//    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object data) throws Exception {
        ByteBuf buffer = (ByteBuf) data;
        byte[] bytes = new byte[buffer.readableBytes()];
        buffer.readBytes(bytes);
        System.out.println("Received bytes: " + Arrays.toString(bytes));
        System.out.println("Bytes as string: " + new String(bytes, 0, bytes.length));
        ctx.writeAndFlush(Unpooled.copiedBuffer(bytes));
    }

    @Override
    public void channelRegistered(ChannelHandlerContext ctx) throws Exception {
//        SocketStore.contexts.add(ctx);
        System.out.println("New connection " + ctx.channel().remoteAddress());
        super.channelRegistered(ctx);
    }

    @Override
    public void channelUnregistered(ChannelHandlerContext ctx) throws Exception {
//        SocketStore.contexts.remove(ctx);
        System.out.println("Connection closed " + ctx.channel().remoteAddress());
        super.channelUnregistered(ctx);
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        ctx.writeAndFlush(Unpooled.EMPTY_BUFFER);
//                .addListener(ChannelFutureListener.CLOSE);
    }



    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
}
