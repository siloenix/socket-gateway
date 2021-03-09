package com.siloenix.socket.message.types;

import com.siloenix.socket.message.Message;
import com.siloenix.socket.message.MessageType;
import lombok.ToString;

import java.nio.ByteBuffer;

@ToString(callSuper = true)
public class PingMessage extends Message {
    private static final Integer FULL_LENGTH
            = 1 // size
            + 2 // command
            + 2; // crc


    public PingMessage(byte[] body) {
        super(MessageType.PING, body);
    }

    public PingMessage() {
        super(MessageType.PING);
    }

    @Override
    protected void deserializeData(ByteBuffer buffer) {
    }

    @Override
    protected void serializeData(ByteBuffer buffer) {
    }

    @Override
    public Integer messageSize() {
        return FULL_LENGTH;
    }
}
