package com.siloenix.socket.message.types;

import lombok.ToString;

@ToString(callSuper = true)
public class AckMessage extends BaseMessage {
    public static final AckMessage OK = new AckMessage((byte) 0);

    private byte code;

    public AckMessage(byte[] body) {
        super(MessageType.ACK, body);
    }

    public AckMessage(byte code) {
        super(MessageType.ACK);
        this.code = code;
    }
}
