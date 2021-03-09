package com.siloenix.socket.message.types;

import com.siloenix.socket.errors.ErrorCode;
import com.siloenix.socket.message.Message;
import com.siloenix.socket.message.MessageType;
import lombok.ToString;

import java.nio.ByteBuffer;

@ToString(callSuper = true)
public class ErrorMessage extends Message {
    public static Integer FULL_LENGTH
            = 1 // size
            + 2 // command
            + 1 // code
            + 2; // crc

    private ErrorCode type;
    private byte code;

    public ErrorMessage(byte[] body) {
        super(MessageType.ERROR, body);
    }

    public ErrorMessage(ErrorCode type) {
        super(MessageType.ERROR);
        this.code = (byte) type.code;
        this.type = type;
    }

    @Override
    protected void deserializeData(ByteBuffer buffer) {
        this.code = buffer.get();
        this.type = ErrorCode.define(code);
    }

    @Override
    protected void serializeData(ByteBuffer buffer) {
        buffer.put(code);
    }

    public byte code() {
        return this.code;
    }

    @Override
    public Integer messageSize() {
        return FULL_LENGTH;
    }


}
