package com.siloenix.socket.smp;

import com.siloenix.socket.codec.MessageDeserializer;
import com.siloenix.socket.errors.GatewayErrorCode;
import com.siloenix.socket.errors.SocketGatewayException;
import com.siloenix.socket.message.Message;
import com.siloenix.socket.smp.annotations.*;
import lombok.SneakyThrows;

import java.lang.reflect.Field;
import java.nio.ByteBuffer;

import static com.siloenix.socket.util.Utils.asString;
import static java.lang.reflect.Modifier.isStatic;

public class SmpDeserializer implements MessageDeserializer {
    @Override
    public <T extends Message<?, ?>> T deserialize(byte[] bytes) {
        ByteBuffer buffer = ByteBuffer.wrap(bytes);
        byte messageType = buffer.get();
        SmpMessageType type = SmpTypeRegistry.resolve(messageType);
        if (type == null) {
            throw new SocketGatewayException(GatewayErrorCode.BAD_PACKET, "Message code does not exist: " + messageType);
        }
        try {
            return deserializeByType(buffer, type);
        } catch (Exception e) {
            throw new SocketGatewayException(GatewayErrorCode.TCP_SERVER_ERROR, "Failed to deserialize message bytes", e);
        }
    }

    @SneakyThrows
    private <T extends Message<?, ?>> T deserializeByType(ByteBuffer buffer, SmpMessageType type) {
        Class<T> typeDef = (Class<T>) type.type();
        T message = typeDef.newInstance();

        for (Field field : typeDef.getDeclaredFields()) {
            if (!isStatic(field.getModifiers())) {
                deserializeField(field, message, buffer);
            }
        }
        return message;
    }

    @SneakyThrows
    private void deserializeField(Field field, Message<?, ?> message, ByteBuffer buffer) {
        field.setAccessible(true);
        Class<?> fieldType = field.getType();

        SmpIgnore ignore = field.getDeclaredAnnotation(SmpIgnore.class);
        if (ignore != null) {
            return;
        }

        SmpIntFloat smpIntFloat = field.getDeclaredAnnotation(SmpIntFloat.class);
        if (smpIntFloat != null) {
            int value = buffer.getInt();
            field.set(message, (float) (value / Math.pow(10, smpIntFloat.precision())));
            return;
        }

        SmpIntDouble smpIntDouble = field.getDeclaredAnnotation(SmpIntDouble.class);
        if (smpIntDouble != null) {
            int value = buffer.getInt();
            field.set(message, value / Math.pow(10, smpIntDouble.precision()));
            return;
        }

        SmpByte smpByte = field.getDeclaredAnnotation(SmpByte.class);
        if (smpByte != null || fieldType == byte.class || fieldType == Byte.class) {
            byte value = buffer.get();
            field.set(message, value);
            return;
        }

        SmpChar smpChar = field.getDeclaredAnnotation(SmpChar.class);
        if (smpChar != null || fieldType == char.class || fieldType == Character.class) {
            char value = buffer.getChar();
            field.set(message, value);
            return;
        }

        SmpInteger smpInteger = field.getDeclaredAnnotation(SmpInteger.class);
        if (smpInteger != null || fieldType == int.class || fieldType == Integer.class) {
            int value = buffer.getInt();
            field.set(message, value);
            return;
        }

        SmpLong smpLong = field.getDeclaredAnnotation(SmpLong.class);
        if (smpLong != null || fieldType == long.class || fieldType == Long.class) {
            long value = buffer.getLong();
            field.set(message, value);
            return;
        }

        SmpFloat smpFloat = field.getDeclaredAnnotation(SmpFloat.class);
        if (smpFloat != null || fieldType == float.class || fieldType == Float.class) {
            float value = buffer.getFloat();
            field.set(message, value);
            return;
        }

        SmpDouble smpDouble = field.getDeclaredAnnotation(SmpDouble.class);
        if (smpDouble != null || fieldType == double.class || fieldType == Double.class) {
            double value = buffer.getDouble();
            field.set(message, value);
            return;
        }

        SmpString smpString = field.getDeclaredAnnotation(SmpString.class);
        if (smpString != null) {
            byte[] bytes = new byte[smpString.length()];
            buffer.get(bytes);
            field.set(message, asString(bytes));
            return;
        } else if (fieldType == String.class) {
            throw new SocketGatewayException(
                    GatewayErrorCode.TCP_SERVER_ERROR,
                    String.format(
                            "Failed to deserialize type -- %s -- String field must have SmpString annotation: '%s'",
                            message.getClass().getSimpleName(),
                            field.getName()
                    )
            );
        }

        SmpByteArray smpByteArray = field.getDeclaredAnnotation(SmpByteArray.class);
        if (smpByteArray != null) {
            byte[] bytes = new byte[smpByteArray.length()];
            buffer.get(bytes);
            field.set(message, bytes);
            return;
        } else if (fieldType == byte[].class) {
            throw new SocketGatewayException(
                    GatewayErrorCode.TCP_SERVER_ERROR,
                    String.format(
                            "Failed to deserialize type -- %s -- byte[] field must have SmpByteArray annotation: '%s'",
                            message.getClass().getSimpleName(),
                            field.getName()
                    )
            );
        }

        throw new SocketGatewayException(
                GatewayErrorCode.TCP_SERVER_ERROR,
                String.format(
                        "Failed to deserialize type -- %s -- Unknown type for field: '%s'",
                        message.getClass().getSimpleName(),
                        field.getName()
                ));
    }
}
