package com.siloenix.socket.smp.message.types;

import com.siloenix.socket.smp.message.BaseMessage;
import com.siloenix.socket.smp.message.TestMessageType;
import lombok.Getter;

@Getter
public class IntMessage extends BaseMessage {
    private static final int VALUE = 10;

    private final int simple = VALUE;
    private final Integer wrapped = VALUE;

    public IntMessage() {
        super(TestMessageType.INT_MESSAGE_TYPE);
    }

    public IntMessage(byte[] body) {
        super(TestMessageType.INT_MESSAGE_TYPE, body);
    }
}
