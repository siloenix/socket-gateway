package com.siloenix.socket.message.types;

import com.siloenix.socket.message.Message;
import com.siloenix.socket.message.MessageType;
import com.siloenix.socket.Utils;
import lombok.Getter;
import lombok.ToString;

import java.nio.ByteBuffer;

@ToString(callSuper = true)
public class CardPaymentMessage extends Message {
    private static int CLIENT_ID_SIZE = 16;
    public static Integer FULL_LENGTH
            = 1 // size
            + 2 // command
            + CLIENT_ID_SIZE
            + 4 // water
            + 4 // cash
//            + 4 // money refill
            + 2; // crc

    @Getter
    private String clientId;
    private int water;
    private int money;

    public CardPaymentMessage(byte[] body) {
        super(MessageType.CARD_PAYMENT, body);
    }

    public CardPaymentMessage(String clientId, float water, float money) {
        super(MessageType.CARD_PAYMENT);
        this.clientId = clientId;
        this.water = Utils.logicalConvert(water);
        this.money = Utils.logicalConvert(money);
    }

    @Override
    protected void deserializeData(ByteBuffer buffer) {
        byte[] clientIdBytes = new byte[CLIENT_ID_SIZE];
        buffer.get(clientIdBytes);

        this.clientId = Utils.asString(clientIdBytes);
        this.water = buffer.getInt();
        this.money = buffer.getInt();
    }

    @Override
    protected void serializeData(ByteBuffer buffer) {
        buffer.put(Utils.asBytes(this.clientId, CLIENT_ID_SIZE));
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
