package com.siloenix.socket.smp.message.types;

import com.siloenix.socket.smp.annotations.SmpByteArray;
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
public class ByteArrayMessage extends BaseMessage {
    private static final byte[] VALUE = {1,2,3,4};

    @SmpByteArray(length = 4)
    private byte[] simple = VALUE;

    public ByteArrayMessage() {
        super(TestMessageType.BYTE_ARRAY_MESSAGE_TYPE);
    }

    public ByteArrayMessage(byte[] simple) {
        super(TestMessageType.BYTE_ARRAY_MESSAGE_TYPE);
        this.simple = simple;
    }
}
