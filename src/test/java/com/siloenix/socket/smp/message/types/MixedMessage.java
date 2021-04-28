package com.siloenix.socket.smp.message.types;

import com.siloenix.socket.smp.annotations.*;
import com.siloenix.socket.smp.message.BaseMessage;
import com.siloenix.socket.smp.message.TestMessageType;
import lombok.Getter;

@Getter
public class MixedMessage extends BaseMessage {

    private byte byteValue = 10;
    private char charValue = 10;
    private int intValue = 10;
    @SmpIntFloat
    private float intFloatValue = 10.5F;
    @SmpIntDouble
    private double intDoubleValue = 10.5;
    private long longValue = 10;
    private float floatValue = 10;
    private double doubleValue = 10;
    @SmpString(length = 4)
    private String stringValue = "test";
    @SmpByteArray(length = 4)
    private byte[] byteArrayValue = new byte[]{1,2,3,4};

    @SmpIgnore
    private int ignored = 10;

    public MixedMessage() {
        super(TestMessageType.MIXED_MESSAGE_TYPE);
    }

    public MixedMessage(byte[] body) {
        super(TestMessageType.MIXED_MESSAGE_TYPE, body);
    }
}
