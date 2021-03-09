package com.siloenix.socket.message.types;

import com.siloenix.socket.message.Message;
import com.siloenix.socket.message.MessageType;
import com.siloenix.socket.Utils;
import lombok.Getter;
import lombok.ToString;

import java.nio.ByteBuffer;

import static com.siloenix.socket.Utils.logicalConvert;

@ToString(callSuper = true)
public class WaterReleaseMessage extends Message {
    public static Integer FULL_LENGTH
            = 1 // size
            + 2 // command type
            + 4 // water
            + 8 // userId
            + 2; // crc

    private int water;
    @Getter
    private long userId;

    public WaterReleaseMessage(byte[] body) {
        super(MessageType.WATER_RELEASE, body);
    }

    public WaterReleaseMessage(float water, long userId) {
        super(MessageType.WATER_RELEASE);
        this.water = Utils.logicalConvert(water);
        this.userId = userId;
    }

    public float getWater() {
        return Utils.logicalConvert(this.water);
    }

    @Override
    protected void deserializeData(ByteBuffer buffer) {
        this.water = buffer.getInt();
        this.userId = buffer.getLong();
    }

    @Override
    protected void serializeData(ByteBuffer buffer) {
        buffer.putInt(this.water);
        buffer.putLong(this.userId);
    }

    @Override
    public Integer messageSize() {
        return FULL_LENGTH;
    }
}
