package com.siloenix.socket.smp.message.types;

import com.siloenix.socket.smp.message.BaseMessage;
import com.siloenix.socket.smp.message.TestMessageType;
import lombok.Getter;

@Getter
public class ByteMessage extends BaseMessage {
    private static final byte VALUE = 10;

    private final byte simple = VALUE;
    private final Byte wrapped = VALUE;

    public ByteMessage() {
        super(TestMessageType.BYTE_MESSAGE_TYPE);
    }

    public ByteMessage(byte[] body) {
        super(TestMessageType.BYTE_MESSAGE_TYPE, body);
    }
}
