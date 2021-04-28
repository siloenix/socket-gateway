package com.siloenix.socket.smp;

import com.siloenix.socket.smp.message.types.*;
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
public class SmpSerializerTest {
    private final SmpSerializer serializer = new SmpSerializer();

    @Test
    public void byteTest() {
        ByteMessage message = new ByteMessage();

        byte[] bytes = serializer.serialize(message);
        assertThat(bytes).isEqualTo(new byte[]{1, 10, 10});
    }

    @Test
    public void charTest() {
        CharMessage message = new CharMessage();

        byte[] bytes = serializer.serialize(message);
        assertThat(bytes).isEqualTo(new byte[]{2, 0, 10, 0, 10});
    }

    @Test
    public void intTest() {
        IntMessage message = new IntMessage();

        byte[] bytes = serializer.serialize(message);
        assertThat(bytes).isEqualTo(new byte[]{3, 0, 0, 0, 10, 0, 0, 0, 10});
    }

    @Test
    public void intFloatTest() {
        IntFloatMessage message = new IntFloatMessage();

        byte[] bytes = serializer.serialize(message);
        assertThat(bytes).isEqualTo(new byte[]{4, 0, 0, 0, 105, 0, 0, 0, 105});
    }

    @Test
    public void intDoubleTest() {
        IntDoubleMessage message = new IntDoubleMessage();

        byte[] bytes = serializer.serialize(message);
        assertThat(bytes).isEqualTo(new byte[]{5, 0, 0, 0, 105, 0, 0, 0, 105});
    }

    @Test
    public void longTest() {
        LongMessage message = new LongMessage();

        byte[] bytes = serializer.serialize(message);
        assertThat(bytes).isEqualTo(new byte[]{6, 0, 0, 0, 0, 0, 0, 0, 10, 0, 0, 0, 0, 0, 0, 0, 10});
    }

    @Test
    public void floatTest() {
        FloatMessage message = new FloatMessage();

        byte[] bytes = serializer.serialize(message);
        assertThat(bytes).isEqualTo(new byte[]{7, 65, 40, 0, 0, 65, 40, 0, 0});
    }

    @Test
    public void doubleTest() {
        DoubleMessage message = new DoubleMessage();

        byte[] bytes = serializer.serialize(message);
        assertThat(bytes).isEqualTo(new byte[]{8, 64, 37, 0, 0, 0, 0, 0, 0, 64, 37, 0, 0, 0, 0, 0, 0});
    }

    @Test
    public void stringTest() {
        StringMessage message = new StringMessage();

        byte[] bytes = serializer.serialize(message);
        assertThat(bytes).isEqualTo(new byte[]{9, 't', 'e', 's', 't'});
    }

    @Test
    public void byteArrayTest() {
        ByteArrayMessage message = new ByteArrayMessage();

        byte[] bytes = serializer.serialize(message);
        assertThat(bytes).isEqualTo(new byte[]{10, 1, 2, 3, 4});
    }

    @Test
    public void ignoreTest() {
        IgnoredMessage message = new IgnoredMessage();

        byte[] bytes = serializer.serialize(message);
        assertThat(bytes).isEqualTo(new byte[]{11});
    }

    @Test
    public void mixedTest() {
        MixedMessage message = new MixedMessage();

        byte[] bytes = serializer.serialize(message);
        assertThat(bytes).isEqualTo(new byte[]{
                12, // type
                10, // byte
                0, 10, // char
                0, 0, 0, 10, // int
                0, 0, 0, 105, // int float
                0, 0, 0, 105, // int double
                0, 0, 0, 0, 0, 0, 0, 10, // long
                65, 32, 0, 0, // float
                64, 36, 0, 0, 0, 0, 0, 0, // double
                't', 'e', 's', 't', // String
                1, 2, 3, 4, // byte array
        });
    }

//    todo: coercing not working
//    @Test
//    public void coercedTest() {
//        CoercedMessage message = new CoercedMessage();
//
//        byte[] bytes = serializer.serialize(message);
//        assertThat(bytes).isEqualTo(new byte[]{
//                13, // type
//                10, // char -> byte
//                0, 10, // int -> char
//                0, 0, 0, 10, // long -> int
//                0, 0, 0, 10, // float -> int
//                65, 32, 0, 0, // double -> float
//        });
//    }
}

