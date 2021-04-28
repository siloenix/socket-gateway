package com.siloenix.socket.smp.message.types;

import com.siloenix.socket.smp.annotations.SmpIntDouble;
import com.siloenix.socket.smp.message.BaseMessage;
import com.siloenix.socket.smp.message.TestMessageType;
import lombok.Getter;

@Getter
public class IntDoubleMessage extends BaseMessage {
    private static final double VALUE = 10.5;

    @SmpIntDouble
    private final double simple = VALUE;
    @SmpIntDouble
    private final Double wrapped = VALUE;

    public IntDoubleMessage() {
        super(TestMessageType.INT_DOUBLE_MESSAGE_TYPE);
    }

    public IntDoubleMessage(byte[] body) {
        super(TestMessageType.INT_DOUBLE_MESSAGE_TYPE, body);
    }
}
