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
public class IntMessage extends BaseMessage {
    private static final int VALUE = 10;

    private int simple = VALUE;
    private Integer wrapped = VALUE;

    public IntMessage() {
        super(TestMessageType.INT_MESSAGE_TYPE);
    }

    public IntMessage(int simple, Integer wrapped) {
        super(TestMessageType.INT_MESSAGE_TYPE);
        this.simple = simple;
        this.wrapped = wrapped;
    }
}
