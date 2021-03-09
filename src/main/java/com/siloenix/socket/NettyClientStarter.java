package com.siloenix.socket;

import com.siloenix.socket.client.SocketClient;
import com.siloenix.socket.errors.ErrorCode;
import com.siloenix.aquabox.socket.message.types.*;
import com.siloenix.socket.message.types.*;

import java.util.EnumSet;
import java.util.Set;

public class NettyClientStarter {
    private static final String SIM_ID = "14";
//    private static final SocketClient.Mode MODE = SocketClient.Mode.REMOTE;
    private static final SocketClient.Mode MODE = SocketClient.Mode.LOCAL;

    public static void main(String[] args) throws InterruptedException {
        fullTest();
//        testErrors();
//        testDeviceInfo();
    }

    private static void testDeviceInfo() throws InterruptedException {
        SocketClient client = new SocketClient();
        client.queue(new StartMessage(SIM_ID));

        for (int i = 0; i < 10000; i++) {
            client.queue(new DeviceInfoMessage(33, 33, 33, "12345678"));
        }
        client.start(MODE);
    }

    private static void testErrors() throws InterruptedException {
        SocketClient client = new SocketClient();
        client.queue(new StartMessage(SIM_ID));

        Set<ErrorCode> ignore = EnumSet.of(
                ErrorCode.BAD_PACKET,
                ErrorCode.ALREADY_INITIALIZED,
                ErrorCode.NOT_INITIALIZED,
                ErrorCode.INTERNAL_SERVER_ERROR,
                ErrorCode.TCP_SERVER_ERROR,
                ErrorCode.AUTOMATON_OFFLINE,
                ErrorCode.OUTDATED_PRICE_PER_LITER
        );
        for (ErrorCode type : ErrorCode.values()) {
            if (!ignore.contains(type)) {
                client.queue(new ErrorMessage(type));
            }
        }
        client.start(MODE);
    }

    private static void fullTest() throws InterruptedException {
        char[] test = {0x79, 0x24, 0xfb, 0xc2, 0x64, 0x00, 0x00, 0x00};
        String s = new String(test);
        new SocketClient()
                .queue(new StartMessage(SIM_ID))

                .queue(new CashCollectionMessage())
                .queue(new PingMessage())
                .queue(new DeviceInfoMessage(33, 33, 33, "12345678"))
                .queue(new WaterCalibrationMessage(600))
                .queue(new WaterRefillMessage(2000))
                .queue(new WaterReleaseMessage(4, 1))
                .queue(new CashPaymentMessage(4, 4))
                .queue(new CashPaymentMessage(4, 4))
                .queue(new CashPaymentMessage(4, 4))
                .queue(new CashPaymentMessage(4, 4))
                .queue(new PingMessage())
                .queue(new CardInitMessage(s))
                .queue(new CardPaymentMessage(s, 4, 10))
                .queue(new CardPaymentMessage(s, 4, 0))
                .queue(new CardInitMessage("222"))
                .queue(new CardPaymentMessage("222", 4, 10))
                .queue(new CardPaymentMessage("222", 2, 0))

                .start(MODE);
    }
}