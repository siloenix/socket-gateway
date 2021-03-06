package com.siloenix.socket.server;

import com.newrelic.api.agent.NewRelic;
import com.newrelic.api.agent.Trace;
import com.siloenix.socket.communication.MessageHandler;
import com.siloenix.socket.context.ConnectionContext;
import com.siloenix.socket.errors.ErrorCode;
import com.siloenix.socket.errors.SocketGatewayException;
import com.siloenix.socket.message.Message;
import com.siloenix.socket.message.MessageType;
import com.siloenix.socket.message.types.ErrorMessage;
import com.siloenix.socket.message.types.FinishMessage;
import com.siloenix.socket.Utils;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ConnectionHandler extends ChannelInboundHandlerAdapter {
    private final ConnectionContext context = ConnectionContext.getInstance();
    private final MessageHandler receiver;

    public ConnectionHandler(MessageHandler receiver) {
        this.receiver = receiver;
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object data) throws Exception {
        Message message = (Message) data;
        log.info("Message received: {}", message);

        context.cleanUpConnections();

        if (message.getType() == MessageType.START) {
            if (message.getSimId() == null) {
                throw new SocketGatewayException(
                        ErrorCode.BAD_PACKET,
                        "StartMessage does not have simId for connection: " + ctx.channel().remoteAddress()
                );
            }

            ChannelHandlerContext connection = context.getConnection(message);
            if (connection != null) {
                log.info("Connection already initialized for simId = {} with address = {}", message.getSimId(), Utils.socketAddress(connection));
                log.info("Resetting binding for simId = {}", message.getSimId());
                context.handleConnectionClosed(connection);
                connection.close();
            }
            context.handleConnectionInitialized(ctx, message);
        }
        if (!context.isConnectionInitialized(ctx)) {
            throw new SocketGatewayException(
                    ErrorCode.NOT_INITIALIZED,
                    "connection not initialized: " + ctx.channel().remoteAddress()
            );
        }
        if (message.getSimId() == null) {
            context.enrichMessageWithSimId(ctx, message);
        }
        receiver.handle(message);
    }

    @Override
    @Trace(metricName = "ChannelRegistered", dispatcher = true)
    public void channelRegistered(ChannelHandlerContext ctx) throws Exception {
        log.info("New connection: {}", ctx.channel().remoteAddress());
        context.handleConnectionOpened(ctx);
        super.channelRegistered(ctx);
    }

    @Override
    @Trace(metricName = "ChannelUnregistered", dispatcher = true)
    public void channelUnregistered(ChannelHandlerContext ctx) throws Exception {
        log.info("Connection closed: {}", ctx.channel().remoteAddress());
        if (context.isConnectionInitialized(ctx)) {
            FinishMessage message = new FinishMessage();
            context.enrichMessageWithSimId(ctx, message);
            receiver.handle(message);
        }
        context.handleConnectionClosed(ctx);
        super.channelUnregistered(ctx);
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        ctx.writeAndFlush(Unpooled.EMPTY_BUFFER);
    }

    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        if (evt instanceof IdleStateEvent) {
            IdleStateEvent e = (IdleStateEvent) evt;
            if (e.state() == IdleState.READER_IDLE) {
                handleReaderIdle(ctx);
            }
        }
    }

    @Trace(metricName = "ReaderIdle", dispatcher = true)
    private void handleReaderIdle(ChannelHandlerContext ctx) {
        log.info("READER IDLE");
        ctx.close();
    }

    @Override
    @Trace(metricName = "ExceptionCaught", dispatcher = true)
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        log.error("Error on connection: {}", ctx.channel().remoteAddress());
        log.error("Error on socket server", cause);

        SocketGatewayException rootCause = findRootCause(cause);
        NewRelic.noticeError(cause);
        if (rootCause != null) {
            log.error("Error code: {}", rootCause.getCode());
            ctx.writeAndFlush(new ErrorMessage(rootCause.getCode()));
            return;
        }

        ctx.writeAndFlush(new ErrorMessage(ErrorCode.TCP_SERVER_ERROR));
    }

    private SocketGatewayException findRootCause(Throwable cause) {
        if (cause instanceof SocketGatewayException) {
            return (SocketGatewayException) cause;
        }
        if (cause.getCause() == null) {
            return null;
        }
        return findRootCause(cause.getCause());
    }
}
