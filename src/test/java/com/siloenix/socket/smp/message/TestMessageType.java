package com.siloenix.socket.smp.message;

import com.siloenix.socket.message.Message;
import com.siloenix.socket.smp.SmpMessageType;
import com.siloenix.socket.smp.SmpTypeRegistry;
import com.siloenix.socket.smp.message.types.*;

public enum TestMessageType implements SmpMessageType {

    BYTE_MESSAGE_TYPE((byte) 1, ByteMessage.class),
    CHAR_MESSAGE_TYPE((byte) 2, CharMessage.class),
    INT_MESSAGE_TYPE((byte) 3, IntMessage.class),
    INT_FLOAT_MESSAGE_TYPE((byte) 4, IntFloatMessage.class),
    INT_DOUBLE_MESSAGE_TYPE((byte) 5, IntDoubleMessage.class),
    LONG_MESSAGE_TYPE((byte) 6, LongMessage.class),
    FLOAT_MESSAGE_TYPE((byte) 7, FloatMessage.class),
    DOUBLE_MESSAGE_TYPE((byte) 8, DoubleMessage.class),
    STRING_MESSAGE_TYPE((byte) 9, StringMessage.class),
    BYTE_ARRAY_MESSAGE_TYPE((byte) 10, ByteArrayMessage.class),
    IGNORED_MESSAGE_TYPE((byte) 11, IgnoredMessage.class),
    MIXED_MESSAGE_TYPE((byte) 12, MixedMessage.class),
    COERCED_MESSAGE_TYPE((byte) 13, CoercedMessage.class);

    static {
        SmpTypeRegistry.add(TestMessageType.values());
    }

    private final byte code;
    private final Class<? extends Message<?, ?>> type;

    TestMessageType(byte code, Class<? extends Message<?, ?>> type) {
        this.code = code;
        this.type = type;
    }

    @Override
    public byte code() {
        return code;
    }

    @Override
    public Class<? extends Message<?, ?>> type() {
        return type;
    }
}
