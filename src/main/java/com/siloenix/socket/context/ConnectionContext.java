package com.siloenix.socket.context;

import com.siloenix.socket.message.Message;
import com.siloenix.socket.message.types.BaseMessage;
import com.siloenix.socket.util.Utils;
import io.netty.channel.ChannelHandlerContext;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;


@Slf4j
public class ConnectionContext {
    private static final ConnectionContext CONNECTION_CONTEXT = new ConnectionContext();
    public static ConnectionContext getInstance() {
        return CONNECTION_CONTEXT;
    }

    private final Map<String, ChannelHandlerContext> connections = new ConcurrentHashMap<>();
    private final Map<String, ChannelHandlerContext> initializedConnections = new ConcurrentHashMap<>();
    private final Map<String, String> addressPerSimId = new ConcurrentHashMap<>();

    public void handleConnectionOpened(ChannelHandlerContext context) {
        connections.put(Utils.socketAddress(context), context);
    }

    public void handleConnectionClosed(ChannelHandlerContext context) {
        String address = Utils.socketAddress(context);
        String simId = addressPerSimId.get(address);

        connections.remove(address);
        if (simId != null) {
            initializedConnections.remove(simId);
        } else {
            initializedConnections.values().removeIf(c -> Utils.socketAddress(c).equals(address));
        }
        addressPerSimId.remove(address);
    }

    public void handleConnectionInitialized(ChannelHandlerContext context, BaseMessage message) {
        initializedConnections.put(message.getMachineId(), context);
        addressPerSimId.put(Utils.socketAddress(context), message.getMachineId());
    }

    public void enrichMessageWithSimId(ChannelHandlerContext context, BaseMessage message) {
        String simId = getSimId(context);
        message.setMachineId(simId);
    }

    private String getSimId(ChannelHandlerContext context) {
        String address = Utils.socketAddress(context);
        return addressPerSimId.get(address);
    }

    public boolean isConnectionInitialized(ChannelHandlerContext ctx) {
        String simId = getSimId(ctx);
        return simId != null && initializedConnections.containsKey(simId);
    }

    public ChannelHandlerContext getConnection(Message message) {
        return initializedConnections.get(message.getMachineId());
    }

    public void cleanUpConnections() {
        List<String> missingSimIds = addressPerSimId.values().stream()
                .filter(simId -> !initializedConnections.containsKey(simId))
                .collect(Collectors.toList());
        log.info("Missing simIds: {}", missingSimIds);
    }
}
