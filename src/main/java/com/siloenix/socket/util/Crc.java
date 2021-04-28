package com.siloenix.socket.util;

import com.github.snksoft.crc.CRC;
import com.siloenix.socket.errors.GatewayErrorCode;
import com.siloenix.socket.errors.SocketGatewayException;

public class Crc {
    public static final int CRC_POLYNOMIAL = 0x8005;

    public static void check(byte[] messageBody, long messageCrc) {
        long crc = calculate(messageBody);
        if (crc != messageCrc) {
            throw new SocketGatewayException(GatewayErrorCode.BAD_PACKET, "Crc is not equal: " + messageCrc + " and " + crc);
        }
    }

    public static long calculate(byte[] bytes) {
        CRC.Parameters CRC16 = new CRC.Parameters(16, CRC_POLYNOMIAL, 0L, true, true, 0L);
        return CRC.calculateCRC(CRC16, bytes);
    }
}
