package com.siloenix.socket.errors;

import lombok.Getter;
import lombok.ToString;

@ToString
public enum ErrorCode {
    BAD_PACKET(0),
    LOW_WATER_LEVEL(0x01),
    LOW_MONEY_ON_SIM(0x02),
    NO_WATER(0x03),
    DOOR_OPEN_ALERT(0x04),

    LOW_BANKNOTE_TEMP(0x20),
    LOW_INDOOR_TEMP(0x21),
    BROKEN_MAIN_PUMP(0x22),
    CASH_RECEIVER_ERROR(0x23),
    COIN_RECEIVER_ERROR(0x24),

    BVU_OFFLINE(0x30),
    TWL_OFFLINE(0x31),
    PLU_12_OFFLINE(0x32),
    PLU_220_OFFLINE(0x33),
    DBU_OFFLINE(0x34),
    RFID_OFFLINE(0x35),

    // server errors
    OUTDATED_PRICE_PER_LITER(247),
    AUTOMATON_OFFLINE(248),
    ALREADY_INITIALIZED(249),
    NOT_INITIALIZED(250),
    INTERNAL_SERVER_ERROR(251),
    TCP_SERVER_ERROR(252);

    @Getter
    public int code;

    ErrorCode(int code) {
        this.code = code;
    }


    public static ErrorCode define(byte code) {
        for (ErrorCode type : ErrorCode.values()) {
            if (type.code == code) {
                return type;
            }
        }
        return null;
    }
}
