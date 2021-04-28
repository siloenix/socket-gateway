package com.siloenix.socket.smp;

import com.siloenix.socket.codec.MessageSerializer;
import com.siloenix.socket.errors.GatewayErrorCode;
import com.siloenix.socket.errors.SocketGatewayException;
import com.siloenix.socket.message.Message;
import com.siloenix.socket.smp.annotations.*;
import lombok.SneakyThrows;

import java.lang.reflect.Field;
import java.nio.ByteBuffer;

import static com.siloenix.socket.util.Utils.asBytes;
import static java.lang.reflect.Modifier.isStatic;

public class SmpSerializer implements MessageSerializer<SmpMessageType> {

    @SneakyThrows
    private void serializeField(Field field, Message<?, ?> message, ByteBuffer buffer) {
        boolean finished;
        field.setAccessible(true);

        SmpIgnore ignore = field.getDeclaredAnnotation(SmpIgnore.class);
        if (ignore != null) {
            return;
        }

        finished = serializeExplicit(field, message, buffer);
        if (finished) {
            return;
        }

        finished = serializeSpecial(field, message, buffer);
        if (finished) {
            return;
        }

        finished = serializeImplicit(field, message, buffer);
        if (finished) {
            return;
        }

        throw new SocketGatewayException(
                GatewayErrorCode.TCP_SERVER_ERROR,
                String.format(
                        "Failed to deserialize type -- %s -- Unknown type for field: '%s'",
                        message.getClass().getSimpleName(),
                        field.getName()
                ));
    }

    private boolean serializeSpecial(Field field, Message<?, ?> message, ByteBuffer buffer) throws IllegalAccessException {
        Class<?> fieldType = field.getType();
        SmpString smpString = field.getDeclaredAnnotation(SmpString.class);
        if (smpString != null) {
            String value = (String) field.get(message);
            buffer.put(asBytes(value, smpString.length()));
            return true;
        } else if (fieldType == String.class) {
            throw new SocketGatewayException(
                    GatewayErrorCode.TCP_SERVER_ERROR,
                    String.format(
                            "Failed to serialize type -- %s -- String field must have SmpString annotation: '%s'",
                            message.getClass().getSimpleName(),
                            field.getName()
                    )
            );
        }

        SmpByteArray smpByteArray = field.getDeclaredAnnotation(SmpByteArray.class);
        if (smpByteArray != null) {
            byte[] bytes = (byte[]) field.get(message);
            if (bytes.length > smpByteArray.length()) {
                throw new SocketGatewayException(
                        GatewayErrorCode.TCP_SERVER_ERROR,
                        String.format(
                                "Failed to serialize type -- %s -- ByteArray field is longer than declared length: %d > %d",
                                message.getClass().getSimpleName(),
                                bytes.length,
                                smpByteArray.length()
                        )
                );
            }
            buffer.put(bytes);
            return true;
        } else if (fieldType == byte[].class) {
            throw new SocketGatewayException(
                    GatewayErrorCode.TCP_SERVER_ERROR,
                    String.format(
                            "Failed to serialize type -- %s -- byte[] field must have SmpByteArray annotation: '%s'",
                            message.getClass().getSimpleName(),
                            field.getName()
                    )
            );
        }

        return false;
    }

    // todo: coercion is not working
    private boolean serializeExplicit(Field field, Message<?, ?> message, ByteBuffer buffer) throws IllegalAccessException {
        SmpIntFloat smpIntFloat = field.getDeclaredAnnotation(SmpIntFloat.class);
        if (smpIntFloat != null) {
            float value = (float) field.get(message);
            buffer.putInt((int) (value * Math.pow(10, smpIntFloat.precision())));
            return true;
        }

        SmpIntDouble smpIntDouble = field.getDeclaredAnnotation(SmpIntDouble.class);
        if (smpIntDouble != null) {
            double value = (double) field.get(message);
            buffer.putInt((int) (value * Math.pow(10, smpIntDouble.precision())));
            return true;
        }

        SmpByte smpByte = field.getDeclaredAnnotation(SmpByte.class);
        if (smpByte != null) {
            putByte(field, message, buffer);
            return true;
        }

        SmpChar smpChar = field.getDeclaredAnnotation(SmpChar.class);
        if (smpChar != null) {
            putChar(field, message, buffer);
            return true;
        }

        SmpInteger smpInteger = field.getDeclaredAnnotation(SmpInteger.class);
        if (smpInteger != null) {
            putInteger(field, message, buffer);
            return true;
        }

        SmpLong smpLong = field.getDeclaredAnnotation(SmpLong.class);
        if (smpLong != null) {
            putLong(field, message, buffer);
            return true;
        }

        SmpFloat smpFloat = field.getDeclaredAnnotation(SmpFloat.class);
        if (smpFloat != null) {
            putFloat(field, message, buffer);
            return true;
        }

        SmpDouble smpDouble = field.getDeclaredAnnotation(SmpDouble.class);
        if (smpDouble != null) {
            putDouble(field, message, buffer);
            return true;
        }
        return false;
    }

    private boolean serializeImplicit(Field field, Message<?, ?> message, ByteBuffer buffer) throws IllegalAccessException {
        Class<?> fieldType = field.getType();
        if (fieldType == byte.class || fieldType == Byte.class) {
            putByte(field, message, buffer);
            return true;
        }

        if (fieldType == char.class || fieldType == Character.class) {
            putChar(field, message, buffer);
            return true;
        }

        if (fieldType == int.class || fieldType == Integer.class) {
            putInteger(field, message, buffer);
            return true;
        }

        if (fieldType == long.class || fieldType == Long.class) {
            putLong(field, message, buffer);
            return true;
        }

        if (fieldType == float.class || fieldType == Float.class) {
            putFloat(field, message, buffer);
            return true;
        }

        if (fieldType == double.class || fieldType == Double.class) {
            putDouble(field, message, buffer);
            return true;
        }
        return false;
    }

    private void putDouble(Field field, Message<?, ?> message, ByteBuffer buffer) throws IllegalAccessException {
        double value = (double) field.get(message);
        buffer.putDouble(value);
    }

    private void putFloat(Field field, Message<?, ?> message, ByteBuffer buffer) throws IllegalAccessException {
        float value = (float) field.get(message);
        buffer.putFloat(value);
    }

    private void putLong(Field field, Message<?, ?> message, ByteBuffer buffer) throws IllegalAccessException {
        long value = (long) field.get(message);
        buffer.putLong(value);
    }

    private void putInteger(Field field, Message<?, ?> message, ByteBuffer buffer) throws IllegalAccessException {
        int value = (int) field.get(message);
        buffer.putInt(value);
    }

    private void putChar(Field field, Message<?, ?> message, ByteBuffer buffer) throws IllegalAccessException {
        char value = (char) field.get(message);
        buffer.putChar(value);
    }

    private void putByte(Field field, Message<?, ?> message, ByteBuffer buffer) throws IllegalAccessException {
        byte value = (byte) field.get(message);
        buffer.put(value);
    }

    @Override
    public byte[] serialize(Message<?, ? extends SmpMessageType> message) {
        ByteBuffer buffer = ByteBuffer.allocate(0xFF);
        SmpMessageType messageType = message.getType();
        Class<? extends Message<?, ?>> typeDef = messageType.type();
        buffer.put(messageType.code());

        for (Field field : typeDef.getDeclaredFields()) {
            if (!isStatic(field.getModifiers())) {
                serializeField(field, message, buffer);
            }
        }

        byte[] body = new byte[buffer.position()];
        buffer.flip();
        buffer.get(body);
        return body;
    }
}
