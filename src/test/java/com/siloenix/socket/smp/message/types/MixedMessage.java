package com.siloenix.socket.smp.message.types;

import com.siloenix.socket.smp.annotations.*;
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

    public MixedMessage(
            byte byteValue,
            char charValue,
            int intValue,
            float intFloatValue,
            double intDoubleValue,
            long longValue,
            float floatValue,
            double doubleValue,
            String stringValue,
            byte[] byteArrayValue,
            int ignored
    ) {
        super(TestMessageType.MIXED_MESSAGE_TYPE);
        this.byteValue = byteValue;
        this.charValue = charValue;
        this.intValue = intValue;
        this.intFloatValue = intFloatValue;
        this.intDoubleValue = intDoubleValue;
        this.longValue = longValue;
        this.floatValue = floatValue;
        this.doubleValue = doubleValue;
        this.stringValue = stringValue;
        this.byteArrayValue = byteArrayValue;
        this.ignored = ignored;
    }
}
