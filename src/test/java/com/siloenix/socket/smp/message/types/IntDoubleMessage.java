package com.siloenix.socket.smp.message.types;

import com.siloenix.socket.smp.annotations.SmpIntDouble;
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
public class IntDoubleMessage extends BaseMessage {
    private static final double VALUE = 10.5;

    @SmpIntDouble
    private double simple = VALUE;
    @SmpIntDouble
    private Double wrapped = VALUE;

    public IntDoubleMessage() {
        super(TestMessageType.INT_DOUBLE_MESSAGE_TYPE);
    }

    public IntDoubleMessage(double simple, Double wrapped) {
        super(TestMessageType.INT_DOUBLE_MESSAGE_TYPE);
        this.simple = simple;
        this.wrapped = wrapped;
    }
}
