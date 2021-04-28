package com.siloenix.socket.smp.message.types;

import com.siloenix.socket.smp.annotations.SmpIntFloat;
import com.siloenix.socket.smp.message.BaseMessage;
import com.siloenix.socket.smp.message.TestMessageType;
import lombok.Getter;

@Getter
public class IntFloatMessage extends BaseMessage {
    private static final float VALUE = 10.5F;

    @SmpIntFloat
    private final float simple = VALUE;
    @SmpIntFloat
    private final Float wrapped = VALUE;

    public IntFloatMessage() {
        super(TestMessageType.INT_FLOAT_MESSAGE_TYPE);
    }

    public IntFloatMessage(byte[] body) {
        super(TestMessageType.INT_FLOAT_MESSAGE_TYPE, body);
    }
}
