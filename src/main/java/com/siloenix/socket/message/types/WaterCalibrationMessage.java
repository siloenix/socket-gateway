package com.siloenix.socket.message.types;

import com.siloenix.socket.Utils;
import com.siloenix.socket.message.Message;
import com.siloenix.socket.message.MessageType;
import lombok.ToString;

import java.nio.ByteBuffer;

@ToString(callSuper = true)
public class WaterCalibrationMessage extends Message {
    public static Integer FULL_LENGTH
            = 1 // size
            + 2 // command type
            + 4 // water
            + 2; // crc

    private int water;

    public WaterCalibrationMessage(byte[] body) {
        super(MessageType.WATER_CALIBRATION, body);
    }

    public WaterCalibrationMessage(float water) {
        super(MessageType.WATER_CALIBRATION);
        this.water = Utils.logicalConvert(water);
    }

    public float getWater() {
        return Utils.logicalConvert(this.water);
    }

    @Override
    protected void deserializeData(ByteBuffer buffer) {
        this.water = buffer.getChar();
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
