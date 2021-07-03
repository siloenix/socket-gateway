package com.siloenix.socket.smp.message.types;

import com.siloenix.socket.smp.annotations.SmpString;
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
public class StringMessage extends BaseMessage {
    private static final String VALUE = "test";

    @SmpString(length = 4)
    private String simple = VALUE;

    public StringMessage() {
        super(TestMessageType.STRING_MESSAGE_TYPE);
    }

    public StringMessage(String simple) {
        super(TestMessageType.STRING_MESSAGE_TYPE);
        this.simple = simple;
    }
}
