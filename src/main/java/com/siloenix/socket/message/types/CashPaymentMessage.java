package com.siloenix.socket.message.types;

import com.siloenix.socket.Utils;
import com.siloenix.socket.message.Message;
import com.siloenix.socket.message.MessageType;
import lombok.ToString;

import java.nio.ByteBuffer;

@ToString(callSuper = true)
public class CashPaymentMessage extends Message {
    public static Integer FULL_LENGTH
            = 1 // size
            + 2 // command type
            + 4 // water
            + 4 // cash
            + 2; // crc

    private int water;
    private int money;

    public CashPaymentMessage(byte[] body) {
        super(MessageType.CASH_PAYMENT, body);
    }

    public CashPaymentMessage(float water, float money) {
        super(MessageType.CASH_PAYMENT);

        this.water = Utils.logicalConvert(water);
        this.money = Utils.logicalConvert(money);
    }

    @Override
    protected void deserializeData(ByteBuffer buffer) {
        this.water = buffer.getInt();
        this.money = buffer.getInt();
    }

    @Override
    protected void serializeData(ByteBuffer buffer) {
        buffer.putInt(this.water);
        buffer.putInt(this.money);
    }

    @Override
    public Integer messageSize() {
        return FULL_LENGTH;
    }

    public float getWater() {
        return Utils.logicalConvert(water);
    }

    public float getMoney() {
        return Utils.logicalConvert(money);
    }
}
