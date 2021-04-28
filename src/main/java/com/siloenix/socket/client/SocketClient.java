//package com.siloenix.socket.client;
//
//import com.siloenix.socket.codec.ClientMessageDecoder;
//import com.siloenix.socket.codec.ClientMessageEncoder;
//import com.siloenix.socket.communication.MessageHandler;
//import com.siloenix.socket.message.Message;
//import io.netty.bootstrap.Bootstrap;
//import io.netty.channel.ChannelFuture;
//import io.netty.channel.ChannelInitializer;
//import io.netty.channel.EventLoopGroup;
//import io.netty.channel.nio.NioEventLoopGroup;
//import io.netty.channel.socket.SocketChannel;
//import io.netty.channel.socket.nio.NioSocketChannel;
//import lombok.SneakyThrows;
//
//import java.net.InetSocketAddress;
//import java.net.SocketAddress;
//import java.util.LinkedList;
//import java.util.Queue;
//
//
//public class SocketClient {
//    public enum Mode { LOCAL, REMOTE }
//
//    private final Queue<Message> queue = new LinkedList<>();
//    private final ClientHandler clientHandler;
//
//    public SocketClient() {
//        clientHandler = new ClientHandler(queue, null);
//    }
//
//    public SocketClient(MessageHandler readHandler) {
//        clientHandler = new ClientHandler(queue, readHandler);
//    }
//
//    public SocketClient queue(Message message) {
//        queue.add(message);
//        return this;
//    }
//    public SocketClient send(Message message) {
//        clientHandler.sendMessage(message);
//        return this;
//    }
//
//    @SneakyThrows
//    public void start(Mode mode) {
//        SocketAddress remoteAddress = mode == Mode.LOCAL
//                ? new InetSocketAddress("localhost", 9999)
//                : new InetSocketAddress("socket.aquabox.siloenix.com", 9999);
//        EventLoopGroup group = new NioEventLoopGroup();
//        try{
//            Bootstrap clientBootstrap = new Bootstrap();
//
//            clientBootstrap.group(group);
//            clientBootstrap.channel(NioSocketChannel.class);
//            clientBootstrap.remoteAddress(remoteAddress);
//            clientBootstrap.handler(new ChannelInitializer<SocketChannel>() {
//                protected void initChannel(SocketChannel socketChannel) {
//                    socketChannel.pipeline().addLast(
//                            new ClientMessageEncoder(),
//                            new ClientMessageDecoder(),
//                            clientHandler
//                    );
//                }
//            });
//            ChannelFuture channelFuture = clientBootstrap.connect().sync();
//            channelFuture.channel().closeFuture().sync();
//        } finally {
//            group.shutdownGracefully().sync();
//        }
//    }
//}
