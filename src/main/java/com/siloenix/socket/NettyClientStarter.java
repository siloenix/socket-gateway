package com.siloenix.socket;

import com.siloenix.socket.client.SocketClient;
import com.siloenix.socket.errors.GatewayErrorCode;
import com.siloenix.socket.message.types.AckMessage;
import com.siloenix.socket.message.types.StartMessage;

import java.util.EnumSet;
import java.util.Set;

public class NettyClientStarter {
    private static final String MACHINE_ID = "14";
//    private static final SocketClient.Mode MODE = SocketClient.Mode.REMOTE;
    private static final SocketClient.Mode MODE = SocketClient.Mode.LOCAL;

    public static void main(String[] args) throws InterruptedException {
        fullTest();
//        testErrors();
//        testDeviceInfo();
    }

    private static void testDeviceInfo() throws InterruptedException {
        SocketClient client = new SocketClient();
//        client.queue(new StartMessage(SIM_ID));

        for (int i = 0; i < 10000; i++) {
//            client.queue(new DeviceInfoMessage(33, 33, 33, "12345678"));
        }
        client.start(MODE);
    }

    private static void testErrors() throws InterruptedException {
        SocketClient client = new SocketClient();
//        client.queue(new StartMessage(SIM_ID));

        Set<GatewayErrorCode> ignore = EnumSet.of(
                GatewayErrorCode.BAD_PACKET,
                GatewayErrorCode.MACHINE_ALREADY_INITIALIZED,
                GatewayErrorCode.MACHINE_NOT_INITIALIZED,
                GatewayErrorCode.INTERNAL_SERVER_ERROR,
                GatewayErrorCode.TCP_SERVER_ERROR,
                GatewayErrorCode.MACHINE_OFFLINE
//                GatewayErrorCode.OUTDATED_PRICE_PER_LITER
        );
        for (GatewayErrorCode type : GatewayErrorCode.values()) {
            if (!ignore.contains(type)) {
//                client.queue(new ErrorMessage(type));
            }
        }
        client.start(MODE);
    }

    private static void fullTest() throws InterruptedException {
        char[] test = {0x79, 0x24, 0xfb, 0xc2, 0x64, 0x00, 0x00, 0x00};
        String s = new String(test);
        new SocketClient()
                .queue(new StartMessage(MACHINE_ID))
                .queue(AckMessage.OK)
                .queue(AckMessage.OK)
                .queue(AckMessage.OK)

//                .queue(new CashCollectionMessage())
//                .queue(new PingMessage())
//                .queue(new DeviceInfoMessage(33, 33, 33, "12345678"))
//                .queue(new WaterCalibrationMessage(600))
//                .queue(new WaterRefillMessage(2000))
//                .queue(new WaterReleaseMessage(4, 1))
//                .queue(new CashPaymentMessage(4, 4))
//                .queue(new CashPaymentMessage(4, 4))
//                .queue(new CashPaymentMessage(4, 4))
//                .queue(new CashPaymentMessage(4, 4))
//                .queue(new PingMessage())
//                .queue(new CardInitMessage(s))
//                .queue(new CardPaymentMessage(s, 4, 10))
//                .queue(new CardPaymentMessage(s, 4, 0))
//                .queue(new CardInitMessage("222"))
//                .queue(new CardPaymentMessage("222", 4, 10))
//                .queue(new CardPaymentMessage("222", 2, 0))

                .start(MODE);
    }
}