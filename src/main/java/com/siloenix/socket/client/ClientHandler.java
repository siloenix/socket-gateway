package com.siloenix.socket.client;

import com.siloenix.socket.communication.MessageHandler;
import com.siloenix.socket.message.Message;
import com.siloenix.socket.message.MessageType;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.util.Queue;

public class ClientHandler extends ChannelInboundHandlerAdapter {
    private final int waitMillis = 1000;
    private final Queue<Message> queue;
    private final MessageHandler readHandler;
    private ChannelHandlerContext ctx;

    public ClientHandler(Queue<Message> queue, MessageHandler readHandler) {
        this.queue = queue;
        this.readHandler = readHandler;
    }

    @Override
    public void channelActive(ChannelHandlerContext channelHandlerContext){
        ctx = channelHandlerContext;
        sendQueueMessage();
    }

    private void sendQueueMessage() {
        Message message = queue.poll();
        if (message == null) {
            System.out.println("No message in queue");
            return;
        }
        if (message.getType() != MessageType.START) {
            try {
                System.out.println("Waiting millis: " + waitMillis);
                Thread.sleep(waitMillis);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        sendMessage(message);
    }

    public void sendMessage(Message message) {
        ctx.writeAndFlush(message);
        System.out.println("Message sent: " + message);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext channelHandlerContext, Throwable cause){
        cause.printStackTrace();
        channelHandlerContext.close();
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        Message message = (Message) msg;
        System.out.println("Channel read: " + message);
        if (readHandler != null) {
            readHandler.handle(message);
            return;
        }

        if (message.getType() == MessageType.WATER_RELEASE) {
            sendMessage(message);
            return;
        }
        if (!queue.isEmpty()) {
            sendQueueMessage();
        }
    }
}