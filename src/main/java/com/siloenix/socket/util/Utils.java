package com.siloenix.socket.util;

import io.netty.channel.ChannelHandlerContext;

import java.util.Arrays;

public class Utils {
    public static byte[] asBytes(String string, int desiredLength) {
        byte[] actual = string.getBytes();
        int lengthDifference = desiredLength - actual.length;
        return lengthDifference > 0
                ? Arrays.copyOf(actual, desiredLength)
                : actual;
    }

    public static String asString(byte[] bytes) {
        return new String(bytes, 0, bytes.length)
                .replaceAll("\0", "");
    }

    public static int logicalConvert(float value) {
        return (int) (value * 10);
    }

    public static float logicalConvert(int value) {
        return value / 10.0f;
    }

    public static int minus(int value) {
        return -value;
    }
    public static float minus(float value) {
        return -value;
    }

    public static String socketAddress(ChannelHandlerContext context) {
        return context.channel().remoteAddress().toString();
    }
}
