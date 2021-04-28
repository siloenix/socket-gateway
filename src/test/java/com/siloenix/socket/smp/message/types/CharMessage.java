package com.siloenix.socket.smp.message.types;

import com.siloenix.socket.smp.message.BaseMessage;
import com.siloenix.socket.smp.message.TestMessageType;
import lombok.Getter;

@Getter
public class CharMessage extends BaseMessage {
    private static final char VALUE = 10;

    private final char simple = VALUE;
    private final Character wrapped = VALUE;

    public CharMessage() {
        super(TestMessageType.CHAR_MESSAGE_TYPE);
    }

    public CharMessage(byte[] body) {
        super(TestMessageType.CHAR_MESSAGE_TYPE, body);
    }
}
