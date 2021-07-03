package com.siloenix.socket.communication;

import com.siloenix.socket.context.ConnectionContext;
import com.siloenix.socket.errors.GatewayErrorCode;
import com.siloenix.socket.errors.SocketGatewayException;
import com.siloenix.socket.message.BaseMessage;
import io.netty.channel.ChannelHandlerContext;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class MessageSender {
    public static void send(BaseMessage message) {
        ChannelHandlerContext connection = ConnectionContext.getInstance().getConnection(message);
        if (connection == null) {
            throw new SocketGatewayException(GatewayErrorCode.TCP_SERVER_ERROR, "No connection to machine: " + message.getMachineId());
        }
        connection.writeAndFlush(message);
        log.info("Message sent: {}", message);
    }
}
