package com.siloenix.socket.errors;

import lombok.ToString;

@ToString
public enum GatewayErrorCode implements ErrorCode {
    BAD_PACKET(0),
    MACHINE_OFFLINE(248),
    MACHINE_ALREADY_INITIALIZED(249),
    MACHINE_NOT_INITIALIZED(250),
    INTERNAL_SERVER_ERROR(251),
    TCP_SERVER_ERROR(252);

    public int code;

    GatewayErrorCode(int code) {
        this.code = code;
    }

    @Override
    public int code() {
        return code;
    }
}
