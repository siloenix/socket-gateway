package com.siloenix.socket.message.types;

import com.siloenix.socket.message.Message;
import com.siloenix.socket.message.MessageType;
import lombok.Builder;
import lombok.ToString;

import java.nio.ByteBuffer;

@Builder
@ToString(callSuper = true)
public class FinishMessage extends Message {
    private static final int SIM_ID_LENGTH = 32;
    public static final Integer FULL_LENGTH
            = 1 // size
            + 2 // command
            + 2; // crc

    public FinishMessage() {
        super(MessageType.FINISH);
    }

    public FinishMessage(byte[] body) {
        super(MessageType.FINISH, body);
    }

    @Override
    protected void deserializeData(ByteBuffer buffer) {
        // do nothing
    }

    @Override
    protected void serializeData(ByteBuffer buffer) {
    }

    @Override
    public Integer messageSize() {
        return FULL_LENGTH;
    }
}
