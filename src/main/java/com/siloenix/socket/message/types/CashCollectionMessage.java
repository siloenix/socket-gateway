package com.siloenix.socket.message.types;

import com.siloenix.socket.message.Message;
import com.siloenix.socket.message.MessageType;
import lombok.ToString;

import java.nio.ByteBuffer;

@ToString(callSuper = true)
public class CashCollectionMessage extends Message {
    public static Integer FULL_LENGTH
            = 1 // size
            + 2 // command type
            + 2; // crc

    public CashCollectionMessage(byte[] body) {
        super(MessageType.CASH_COLLECTION, body);
    }

    public CashCollectionMessage() {
        super(MessageType.CASH_COLLECTION);
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
