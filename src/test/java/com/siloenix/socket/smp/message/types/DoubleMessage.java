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
public class DoubleMessage extends BaseMessage {
    private static final double VALUE = 10.5;

    private double simple = VALUE;
    private Double wrapped = VALUE;

    public DoubleMessage() {
        super(TestMessageType.DOUBLE_MESSAGE_TYPE);
    }

    public DoubleMessage(double simple, Double wrapped) {
        super(TestMessageType.DOUBLE_MESSAGE_TYPE);
        this.simple = simple;
        this.wrapped = wrapped;
    }
}
