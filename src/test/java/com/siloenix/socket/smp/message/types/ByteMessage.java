package com.siloenix.socket.smp.message.types;

import com.siloenix.socket.smp.message.BaseMessage;
import com.siloenix.socket.smp.message.TestMessageType;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Getter
@Builder
@ToString
@EqualsAndHashCode(callSuper = false)
public class ByteMessage extends BaseMessage {
    private static final byte VALUE = 10;

    private byte simple = VALUE;
    private Byte wrapped = VALUE;

    public ByteMessage() {
        super(TestMessageType.BYTE_MESSAGE_TYPE);
    }

    public ByteMessage(byte[] body) {
        super(TestMessageType.BYTE_MESSAGE_TYPE, body);
    }

    public ByteMessage(byte simple, Byte wrapped) {
        super(TestMessageType.BYTE_MESSAGE_TYPE);
        this.simple = simple;
        this.wrapped = wrapped;
    }
}
