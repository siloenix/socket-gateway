package com.siloenix.socket.server;

import com.siloenix.socket.codec.MessageDecoder;
import com.siloenix.socket.codec.MessageEncoder;
import com.siloenix.socket.communication.MessageHandler;
import com.siloenix.socket.communication.MessageSender;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.timeout.IdleStateHandler;
import lombok.extern.slf4j.Slf4j;

import java.net.InetSocketAddress;
import java.util.concurrent.TimeUnit;

@Slf4j
public class SocketServer {
    public static int IDLE_TIMEOUT = 10000;
    static {
        String timeout = System.getenv("SOCKET_READ_IDLE_TIMEOUT");
        if (timeout != null && !timeout.isEmpty()) {
            try {
                IDLE_TIMEOUT = Integer.parseInt(timeout);
                log.info("Using SOCKET_READ_IDLE_TIMEOUT -- {}", IDLE_TIMEOUT);
            } catch (NumberFormatException e) {
                log.error("Failed to set socket idle timeout from env - falling to default: {}", IDLE_TIMEOUT);
            }
        } else {
            log.warn("SOCKET_READ_IDLE_TIMEOUT not set -- using default: {}", IDLE_TIMEOUT);
        }
    }

    private EventLoopGroup group = new NioEventLoopGroup();
//    private EventExecutorGroup handlerThread = new DefaultEventExecutorGroup(20);
    private MessageHandler messageHandler;

    public SocketServer(MessageHandler messageHandler) {
        this.messageHandler = messageHandler;
    }

    public void start() {
        try {
            ServerBootstrap serverBootstrap = new ServerBootstrap();
            int port = 9999;
            serverBootstrap
                    .group(group)
                    .channel(NioServerSocketChannel.class)
                    .localAddress(new InetSocketAddress("0.0.0.0", port))
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        protected void initChannel(SocketChannel socketChannel) throws Exception {
                            socketChannel.pipeline()
                                    .addLast(
//                                            new LengthFieldBasedFrameDecoder(1024, 0, 1),
                                            new MessageEncoder(),
                                            new MessageDecoder(),
                                            new IdleStateHandler(IDLE_TIMEOUT,0,0, TimeUnit.MILLISECONDS),
                                            new ConnectionHandler(messageHandler)
                                    );
                        }
                    })
                    .childOption(ChannelOption.SO_KEEPALIVE, true);
            ChannelFuture channelFuture = serverBootstrap.bind().sync();

            log.info("socket server started on port {}", port);
            log.info("");
            log.info("");
            log.info("");
            channelFuture.channel().closeFuture().sync();

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                group.shutdownGracefully().sync();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        new SocketServer(message -> {
            System.out.println(message);
            MessageSender.send(message);
        }).start();
    }
}
