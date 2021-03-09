package com.siloenix.socket.message.types;

import com.siloenix.socket.message.Message;
import com.siloenix.socket.message.MessageType;
import com.siloenix.socket.Utils;
import lombok.ToString;

import java.nio.ByteBuffer;

import static com.siloenix.socket.Utils.logicalConvert;

@ToString(callSuper = true)
public class WaterRefillMessage extends Message {
    public static Integer FULL_LENGTH
            = 1 // size
            + 2 // command type
            + 4 // water
            + 2; // crc

    private int water;

    public WaterRefillMessage(byte[] body) {
        super(MessageType.WATER_REFILL, body);
    }

    public WaterRefillMessage(float water) {
        super(MessageType.WATER_REFILL);
        this.water = Utils.logicalConvert(water);
    }

    public float getWater() {
        return Utils.logicalConvert(this.water);
    }

    @Override
    protected void deserializeData(ByteBuffer buffer) {
        this.water = buffer.getInt();
    }

    @Override
    protected void serializeData(ByteBuffer buffer) {
        buffer.putInt(this.water);
    }

    @Override
    public Integer messageSize() {
        return FULL_LENGTH;
    }
}
