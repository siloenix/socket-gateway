package com.siloenix.socket.message;

import com.siloenix.socket.errors.ErrorCode;
import com.siloenix.socket.errors.SocketGatewayException;
import com.siloenix.socket.message.factory.MessageFactory;
import com.siloenix.aquabox.socket.message.types.*;
import com.siloenix.socket.message.types.*;
import lombok.Getter;

public enum MessageType {

    ACK(0, AckMessage::new),
    ERROR(1, ErrorMessage::new),
    PING(5, PingMessage::new),
    SETTINGS(10, SettingsMessage::new),
    DEVICE_INFO(11, DeviceInfoMessage::new),

    START(20, StartMessage::new),
    FINISH(21, FinishMessage::new),
    CASH_PAYMENT(30, CashPaymentMessage::new),
    CARD_PAYMENT(31, CardPaymentMessage::new),
    CARD_INIT(32, CardInitMessage::new),
    CASH_COLLECTION(33, CashCollectionMessage::new),

    CLIENT_INFO(40, ClientInfoMessage::new),

    WATER_REFILL(50, WaterRefillMessage::new),
    WATER_RELEASE(51, WaterReleaseMessage::new),
    WATER_CALIBRATION(52, WaterCalibrationMessage::new),
    OPEN_AUTOMATON(53, OpenAutomatonMessage::new),
    OPEN_AUTOMATON_FOR_SERVICE(54, OpenAutomatonForServiceMessage::new),


    EBAT(255, EbatMessage::new);

    private final MessageFactory factory;
    @Getter
    private Integer code;

    <T extends Message> MessageType(int code, MessageFactory factory) {
        this.code = code;
        this.factory = factory;
    }

    public MessageFactory factory() {
        return this.factory;
    }

    public static MessageType define(int code) {
        for (MessageType type : MessageType.values()) {
            if (type.getCode() == code) {
                return type;
            }
        }
        throw new SocketGatewayException(ErrorCode.BAD_PACKET, String.format("No message type with code: %d", code));
    }
}
