package com.siloenix.socket.communication;

import com.siloenix.socket.context.ConnectionContext;
import com.siloenix.socket.errors.ErrorCode;
import com.siloenix.socket.errors.SocketGatewayException;
import com.siloenix.socket.message.Message;
import io.netty.channel.ChannelHandlerContext;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class MessageSender {
    public static void send(Message message) {
        ChannelHandlerContext connection = ConnectionContext.getInstance().getConnection(message);
        if (connection == null) {
            throw new SocketGatewayException(ErrorCode.TCP_SERVER_ERROR, "No connection to automaton: " + message.getSimId());
        }
        connection.writeAndFlush(message);
        log.info("Message sent: {}", message);
    }
}
