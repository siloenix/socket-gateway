package com.siloenix.socket.smp.message.types;

import com.siloenix.socket.smp.annotations.SmpIntFloat;
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
public class IntFloatMessage extends BaseMessage {
    private static final float VALUE = 10.5F;

    @SmpIntFloat
    private float simple = VALUE;
    @SmpIntFloat
    private Float wrapped = VALUE;

    public IntFloatMessage() {
        super(TestMessageType.INT_FLOAT_MESSAGE_TYPE);
    }

    public IntFloatMessage(float simple, Float wrapped) {
        super(TestMessageType.INT_FLOAT_MESSAGE_TYPE);
        this.simple = simple;
        this.wrapped = wrapped;
    }
}
