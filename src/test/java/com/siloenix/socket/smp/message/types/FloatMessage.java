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
public class FloatMessage extends BaseMessage {
    private static final float VALUE = 10.5F;

    private float simple = VALUE;
    private Float wrapped = VALUE;

    public FloatMessage() {
        super(TestMessageType.FLOAT_MESSAGE_TYPE);
    }

    public FloatMessage(byte[] body) {
        super(TestMessageType.FLOAT_MESSAGE_TYPE, body);
    }

    public FloatMessage(float simple, Float wrapped) {
        super(TestMessageType.FLOAT_MESSAGE_TYPE);
        this.simple = simple;
        this.wrapped = wrapped;
    }
}
