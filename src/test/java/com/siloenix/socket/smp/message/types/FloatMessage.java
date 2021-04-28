package com.siloenix.socket.smp.message.types;

import com.siloenix.socket.smp.message.BaseMessage;
import com.siloenix.socket.smp.message.TestMessageType;
import lombok.Getter;

@Getter
public class FloatMessage extends BaseMessage {
    private static final float VALUE = 10.5F;

    private final float simple = VALUE;
    private final Float wrapped = VALUE;

    public FloatMessage() {
        super(TestMessageType.FLOAT_MESSAGE_TYPE);
    }

    public FloatMessage(byte[] body) {
        super(TestMessageType.FLOAT_MESSAGE_TYPE, body);
    }
}
