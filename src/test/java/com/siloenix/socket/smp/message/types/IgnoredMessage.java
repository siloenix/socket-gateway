package com.siloenix.socket.smp.message.types;

import com.siloenix.socket.smp.annotations.SmpIgnore;
import com.siloenix.socket.smp.message.BaseMessage;
import com.siloenix.socket.smp.message.TestMessageType;
import lombok.Getter;

@Getter
public class IgnoredMessage extends BaseMessage {

    @SmpIgnore
    private final int simple = 1234;

    public IgnoredMessage() {
        super(TestMessageType.IGNORED_MESSAGE_TYPE);
    }

    public IgnoredMessage(byte[] body) {
        super(TestMessageType.IGNORED_MESSAGE_TYPE, body);
    }
}
