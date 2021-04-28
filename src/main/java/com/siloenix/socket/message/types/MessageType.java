package com.siloenix.socket.message.types;

import com.siloenix.socket.message.Message;
import com.siloenix.socket.smp.SmpMessageType;

public enum MessageType implements SmpMessageType {

    ACK(0, AckMessage.class);
//    ERROR(1, ErrorMessage::new),
//    PING(5, PingMessage::new),
//    SETTINGS(10, SettingsMessage::new),
//    DEVICE_INFO(11, DeviceInfoMessage::new),
//
//    START(20, StartMessage::new),
//    FINISH(21, FinishMessage::new),
//    CASH_PAYMENT(30, CashPaymentMessage::new),
//    CARD_PAYMENT(31, CardPaymentMessage::new),
//    CARD_INIT(32, CardInitMessage::new),
//    CASH_COLLECTION(33, CashCollectionMessage::new),
//
//    CLIENT_INFO(40, ClientInfoMessage::new),
//
//    WATER_REFILL(50, WaterRefillMessage::new),
//    WATER_RELEASE(51, WaterReleaseMessage::new),
//    WATER_CALIBRATION(52, WaterCalibrationMessage::new),
//    OPEN_AUTOMATON(53, OpenAutomatonMessage::new),
//    OPEN_AUTOMATON_FOR_SERVICE(54, OpenAutomatonForServiceMessage::new),


//    EBAT(255, EbatMessage::new);

    private final Integer code;
    private final Class<? extends BaseMessage> type;

    MessageType(Integer code, Class<? extends BaseMessage> type) {
        this.code = code;
        this.type = type;
    }

    @Override
    public byte code() {
        return ((byte)(int) code);
    }

    @Override
    public Class<? extends Message<?, ?>> type() {
        return type;
    }
}
