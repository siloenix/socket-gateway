package com.siloenix.socket.smp.message.types;

import com.siloenix.socket.smp.message.BaseMessage;
import com.siloenix.socket.smp.message.TestMessageType;
import lombok.Getter;

@Getter
public class DoubleMessage extends BaseMessage {
    private static final double VALUE = 10.5;

    private final double simple = VALUE;
    private final Double wrapped = VALUE;

    public DoubleMessage() {
        super(TestMessageType.DOUBLE_MESSAGE_TYPE);
    }

    public DoubleMessage(byte[] body) {
        super(TestMessageType.DOUBLE_MESSAGE_TYPE, body);
    }
}
