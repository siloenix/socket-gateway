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
public class LongMessage extends BaseMessage {
    private static final long VALUE = 10;

    private long simple = VALUE;
    private Long wrapped = VALUE;

    public LongMessage() {
        super(TestMessageType.LONG_MESSAGE_TYPE);
    }

    public LongMessage(long simple, Long wrapped) {
        super(TestMessageType.LONG_MESSAGE_TYPE);
        this.simple = simple;
        this.wrapped = wrapped;
    }
}
