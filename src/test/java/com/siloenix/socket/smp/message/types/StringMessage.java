package com.siloenix.socket.smp.message.types;

import com.siloenix.socket.smp.annotations.SmpString;
import com.siloenix.socket.smp.message.BaseMessage;
import com.siloenix.socket.smp.message.TestMessageType;
import lombok.Getter;

@Getter
public class StringMessage extends BaseMessage {
    private static final String VALUE = "test";

    @SmpString(length = 4)
    private final String simple = VALUE;

    public StringMessage() {
        super(TestMessageType.STRING_MESSAGE_TYPE);
    }

    public StringMessage(byte[] body) {
        super(TestMessageType.STRING_MESSAGE_TYPE, body);
    }
}
