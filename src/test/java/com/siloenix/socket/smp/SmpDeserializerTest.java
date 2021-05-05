package com.siloenix.socket.smp;

import com.siloenix.socket.smp.message.TestMessageType;
import com.siloenix.socket.smp.message.types.*;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import static org.assertj.core.api.Assertions.assertThat;

/**
 *     BYTE_MESSAGE_TYPE((byte) 1, ByteMessage.class),
 *     CHAR_MESSAGE_TYPE((byte) 2, CharMessage.class),
 *     INT_MESSAGE_TYPE((byte) 3, IntMessage.class),
 *     INT_FLOAT_MESSAGE_TYPE((byte) 4, IntFloatMessage.class),
 *     INT_DOUBLE_MESSAGE_TYPE((byte) 5, IntDoubleMessage.class),
 *     LONG_MESSAGE_TYPE((byte) 6, LongMessage.class),
 *     FLOAT_MESSAGE_TYPE((byte) 7, FloatMessage.class),
 *     DOUBLE_MESSAGE_TYPE((byte) 8, DoubleMessage.class),
 *     STRING_MESSAGE_TYPE((byte) 9, StringMessage.class),
 *     BYTE_ARRAY_MESSAGE_TYPE((byte) 10, ByteArrayMessage.class),
 *     IGNORED_MESSAGE_TYPE((byte) 11, IgnoredMessage.class),
 *     MIXED_MESSAGE_TYPE((byte) 12, MixedMessage.class);
 * */
@RunWith(JUnit4.class)
public class SmpDeserializerTest {
    private final SmpDeserializer deserializer = new SmpDeserializer();
    private final SmpSerializer serializer = new SmpSerializer();

    @Before
    public void setUp() throws Exception {
        TestMessageType t = TestMessageType.BYTE_ARRAY_MESSAGE_TYPE;
    }

    @Test
    public void byteTest() {
        byte[] bytes = {1, 20, 20};
        ByteMessage message = deserializer.deserialize(bytes);

        assertThat(message)
                .isEqualTo(
                        ByteMessage.builder()
                                .simple((byte) 20)
                                .wrapped((byte) 20)
                                .build()
                );
    }

    @Test
    public void charTest() {
        byte[] bytes = {2, 0, 20, 0, 20};
        CharMessage message = deserializer.deserialize(bytes);

        assertThat(message)
                .isEqualTo(
                        CharMessage.builder()
                                .simple((char) 20)
                                .wrapped((char) 20)
                                .build()
                );
    }

    @Test
    public void intTest() {
        byte[] bytes = {3, 0, 0, 0, 20, 0, 0, 0, 20};
        IntMessage message = deserializer.deserialize(bytes);

        assertThat(message)
                .isEqualTo(
                        IntMessage.builder()
                                .simple(20)
                                .wrapped(20)
                                .build()
                );
    }

    @Test
    public void intFloatTest() {
        byte[] bytes = {4, 0, 0, 0, 125, 0, 0, 0, 125};
        IntFloatMessage message = deserializer.deserialize(bytes);

        assertThat(message)
                .isEqualTo(
                        IntFloatMessage.builder()
                                .simple(12.5F)
                                .wrapped(12.5F)
                                .build()
                );
    }

    @Test
    public void intDoubleTest() {
        byte[] bytes = {5, 0, 0, 0, 125, 0, 0, 0, 125};
        IntDoubleMessage message = deserializer.deserialize(bytes);

        assertThat(message)
                .isEqualTo(
                        IntDoubleMessage.builder()
                                .simple(12.5)
                                .wrapped(12.5)
                                .build()
                );
    }

    @Test
    public void longTest() {
        byte[] bytes = {6, 0, 0, 0, 0, 0, 0, 0, 20, 0, 0, 0, 0, 0, 0, 0, 20};
        LongMessage message = deserializer.deserialize(bytes);

        assertThat(message)
                .isEqualTo(
                        LongMessage.builder()
                                .simple(20L)
                                .wrapped(20L)
                                .build()
                );
    }

    @Test
    public void floatTest() {
        byte[] bytes = {7, 0x41, (byte) 0xa4, 0, 0, 0x41, (byte) 0xa4, 0, 0};
        FloatMessage message = deserializer.deserialize(bytes);

        assertThat(message)
                .isEqualTo(
                        FloatMessage.builder()
                                .simple(20.5F)
                                .wrapped(20.5F)
                                .build()
                );
    }

    @Test
    public void doubleTest() {
        byte[] bytes = {8, 64, 52, -128, 0, 0, 0, 0, 0, 64, 52, -128, 0, 0, 0, 0, 0};
        DoubleMessage message = deserializer.deserialize(bytes);

        assertThat(message)
                .isEqualTo(
                        DoubleMessage.builder()
                                .simple(20.5)
                                .wrapped(20.5)
                                .build()
                );
    }

    @Test
    public void stringTest() {
        byte[] bytes = {9, 'e', 'e', 's', 't'};
        StringMessage message = deserializer.deserialize(bytes);

        assertThat(message)
                .isEqualTo(
                        StringMessage.builder()
                                .simple("eest")
                                .build()
                );
    }

    @Test
    public void byteArrayTest() {
        byte[] bytes = {10, 2, 2, 3, 4};
        ByteArrayMessage message = deserializer.deserialize(bytes);

        assertThat(message)
                .isEqualTo(
                        ByteArrayMessage.builder()
                                .simple(new byte[]{2, 2, 3, 4})
                                .build()
                );
    }

    @Test
    public void ignoreTest() {
        byte[] bytes = {11};
        IgnoredMessage message = deserializer.deserialize(bytes);

        assertThat(message)
                .isEqualTo(
                        new IgnoredMessage()
                );
    }

    @Test
    public void mixedTest() {
        byte[] bytes = {
                12, // type
                20, // byte
                0, 20, // char
                0, 0, 0, 20, // int
                0, 0, 0, 125, // int float
                0, 0, 0, 125, // int double
                0, 0, 0, 0, 0, 0, 0, 20, // long
                0x41, (byte) 0xa4, 0, 0, // float
                64, 52, -128, 0, 0, 0, 0, 0, // double
                'e', 'e', 's', 't', // String
                2, 2, 3, 4, // byte array
        };
        MixedMessage message = deserializer.deserialize(bytes);

        assertThat(message)
                .isEqualTo(
                        MixedMessage.builder()
                                .byteValue((byte) 20)
                                .charValue((char) 20)
                                .intValue(20)
                                .intFloatValue(12.5F)
                                .intDoubleValue(12.5)
                                .longValue(20)
                                .floatValue(20.5F)
                                .doubleValue(20.5)
                                .stringValue("eest")
                                .byteArrayValue(new byte[]{2,2,3,4})
                                .ignored(10)
                                .build()
                );
    }
}

