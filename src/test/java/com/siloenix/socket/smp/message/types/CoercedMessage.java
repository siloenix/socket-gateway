package com.siloenix.socket.smp.message.types;

import com.siloenix.socket.smp.annotations.SmpByte;
import com.siloenix.socket.smp.annotations.SmpChar;
import com.siloenix.socket.smp.annotations.SmpFloat;
import com.siloenix.socket.smp.annotations.SmpInteger;
import com.siloenix.socket.smp.message.BaseMessage;
import com.siloenix.socket.smp.message.TestMessageType;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@EqualsAndHashCode(callSuper = false)
public class CoercedMessage extends BaseMessage {

    @SmpByte
    private char charValue = 10;
    @SmpChar
    private int intValue = 10;
    @SmpInteger
    private long longValue = 10;
    @SmpInteger
    private float floatValue = 10.5F;
    @SmpFloat
    private double doubleValue = 10.5;

    public CoercedMessage() {
        super(TestMessageType.COERCED_MESSAGE_TYPE);
    }

    public CoercedMessage(byte[] body) {
        super(TestMessageType.COERCED_MESSAGE_TYPE, body);
    }
}
