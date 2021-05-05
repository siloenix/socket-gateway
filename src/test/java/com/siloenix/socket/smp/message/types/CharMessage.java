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
public class CharMessage extends BaseMessage {
    private static final char VALUE = 10;

    private char simple = VALUE;
    private Character wrapped = VALUE;

    public CharMessage() {
        super(TestMessageType.CHAR_MESSAGE_TYPE);
    }

    public CharMessage(byte[] body) {
        super(TestMessageType.CHAR_MESSAGE_TYPE, body);
    }

    public CharMessage(char simple, Character wrapped) {
        super(TestMessageType.CHAR_MESSAGE_TYPE);
        this.simple = simple;
        this.wrapped = wrapped;
    }
}
