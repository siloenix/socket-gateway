package com.siloenix.socket.message.types;

import com.siloenix.socket.message.Message;
import com.siloenix.socket.message.MessageType;
import com.siloenix.socket.Utils;
import lombok.Getter;
import lombok.ToString;

import java.nio.ByteBuffer;

@ToString(callSuper = true)
public class DeviceInfoMessage extends Message {
    public static final int ENERGY_COUNTER_LENGTH = 8;
    public static Integer FULL_LENGTH
            = 1 // size
            + 2 // command type
            + 2 // banknote temp
            + 2 // indoor temp
            + ENERGY_COUNTER_LENGTH // energy counter
            + 1 // signal level
            + 2; // crc

    private int banknoteTemperature;
    private int indoorTemperature;
    @Getter
    private int signalLevel;
    @Getter
    private String energyCounter;

    public DeviceInfoMessage(byte[] body) {
        super(MessageType.DEVICE_INFO, body);
    }

    public DeviceInfoMessage(
            float banknoteTemperature,
            float indoorTemperature,
            int signalLevel,
            String energyCounter) {
        super(MessageType.DEVICE_INFO);

        this.banknoteTemperature = Utils.logicalConvert(banknoteTemperature);
        this.indoorTemperature = Utils.logicalConvert(indoorTemperature);
        this.signalLevel = signalLevel;
        this.energyCounter = energyCounter;
    }

    @Override
    protected void deserializeData(ByteBuffer buffer) {
        this.banknoteTemperature = buffer.getChar();
        this.indoorTemperature = buffer.getChar();
        this.signalLevel = buffer.get();

        byte[] energyCounterBytes = new byte[ENERGY_COUNTER_LENGTH];
        buffer.get(energyCounterBytes);
        this.energyCounter = Utils.asString(energyCounterBytes);
    }

    @Override
    protected void serializeData(ByteBuffer buffer) {
        buffer.putChar((char) this.banknoteTemperature);
        buffer.putChar((char) this.indoorTemperature);
        buffer.put((byte) this.signalLevel);
        buffer.put(Utils.asBytes(this.energyCounter, ENERGY_COUNTER_LENGTH));
    }

    @Override
    public Integer messageSize() {
        return FULL_LENGTH;
    }

    public float getBanknoteTemperature() {
        return Utils.logicalConvert(banknoteTemperature);
    }

    public float getIndoorTemperature() {
        return Utils.logicalConvert(indoorTemperature);
    }
}
