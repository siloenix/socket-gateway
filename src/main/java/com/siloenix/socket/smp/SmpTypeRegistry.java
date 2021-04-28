package com.siloenix.socket.smp;

import com.siloenix.socket.errors.GatewayErrorCode;
import com.siloenix.socket.errors.SocketGatewayException;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class SmpTypeRegistry {
    private static final Map<Byte, SmpMessageType> registry = new ConcurrentHashMap<>();

    public static void add(SmpMessageType type) {
        registry.put(type.code(), type);
    }

    public static void add(SmpMessageType... types) {
        for (SmpMessageType type : types) {
            registry.put(type.code(), type);
        }
    }

    public static SmpMessageType resolve(byte code) {
        if (registry.isEmpty()) {
            throw new SocketGatewayException(GatewayErrorCode.TCP_SERVER_ERROR, "SmpTypeRegistry not initialized");
        }
        return registry.get(code);
    }
}
