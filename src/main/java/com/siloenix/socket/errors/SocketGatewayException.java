package com.siloenix.socket.errors;

import lombok.Getter;


public class SocketGatewayException extends RuntimeException {
    @Getter
    private final ErrorCode code;

    public SocketGatewayException(ErrorCode code) {
        this.code = code;
    }

    public SocketGatewayException(ErrorCode code, String message) {
        super(message);
        this.code = code;
    }

    public SocketGatewayException(ErrorCode code, String message, Throwable cause) {
        super(message, cause);
        this.code = code;
    }
}
