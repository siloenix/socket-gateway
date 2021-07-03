package com.siloenix.socket.message.types;

import com.siloenix.socket.message.BaseMessage;
import com.siloenix.socket.message.BaseMessageType;
import lombok.ToString;

@ToString(callSuper = true)
public class AckMessage extends BaseMessage {
    public static final AckMessage OK = new AckMessage((byte) 0);

    private byte code;

    public AckMessage() {
        super(BaseMessageType.ACK);
    }

    public AckMessage(byte code) {
        super(BaseMessageType.ACK);
        this.code = code;
    }
}
