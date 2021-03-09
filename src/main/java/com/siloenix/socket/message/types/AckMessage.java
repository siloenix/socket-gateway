package com.siloenix.socket.message.types;

import com.siloenix.socket.message.Message;
import com.siloenix.socket.message.MessageType;
import lombok.ToString;

import java.nio.ByteBuffer;

@ToString(callSuper = true)
public class AckMessage extends Message {
    private static final Integer FULL_LENGTH
            = 1 // size
            + 2 // command
            + 1 // code
            + 2; // crc
    public static final AckMessage OK = new AckMessage((byte) 0);

    private byte code;

    public AckMessage(byte[] body) {
        super(MessageType.ACK, body);
    }

    public AckMessage(byte code) {
        super(MessageType.ACK);
        this.code = code;
    }

    @Override
    protected void deserializeData(ByteBuffer buffer) {
        this.code = buffer.get();
    }

    @Override
    protected void serializeData(ByteBuffer buffer) {
        buffer.put(this.code);
    }

    @Override
    public Integer messageSize() {
        return FULL_LENGTH;
    }
}
