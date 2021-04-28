package com.siloenix.socket.smp.message.types;

import com.siloenix.socket.smp.message.BaseMessage;
import com.siloenix.socket.smp.message.TestMessageType;
import lombok.Getter;

@Getter
public class LongMessage extends BaseMessage {
    private static final long VALUE = 10;

    private final long simple = VALUE;
    private final Long wrapped = VALUE;

    public LongMessage() {
        super(TestMessageType.LONG_MESSAGE_TYPE);
    }

    public LongMessage(byte[] body) {
        super(TestMessageType.LONG_MESSAGE_TYPE, body);
    }
}
