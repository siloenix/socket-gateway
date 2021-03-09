package com.siloenix.socket.message.types;

import com.siloenix.socket.message.Message;
import com.siloenix.socket.message.MessageType;
import lombok.ToString;

import java.nio.ByteBuffer;

@ToString(callSuper = true)
public class EbatMessage extends Message {
    public EbatMessage(byte[] body) {
        super(MessageType.EBAT, body);
    }

    @Override
    protected void deserializeData(ByteBuffer buffer) {

    }

    @Override
    protected void serializeData(ByteBuffer buffer) {

    }

    @Override
    public Integer messageSize() {
        return null;
    }
}
