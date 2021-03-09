package com.siloenix.socket.message.types;

import com.siloenix.socket.message.Message;
import com.siloenix.socket.message.MessageType;
import com.siloenix.socket.Utils;
import lombok.Getter;
import lombok.ToString;

import java.nio.ByteBuffer;

import static com.siloenix.socket.Utils.logicalConvert;

@ToString(callSuper = true)
public class SettingsMessage extends Message {
    public static Integer FULL_LENGTH
            = 1 // size
            + 2 // command type
            + 2 // outdoor temp
            + 2 // indoor temp
            + 2 // impulse per litre
            + 2 // price per litre
            + 2; // crc

    private int banknoteTemperature;
    private int indoorTemperature;
    @Getter
    private int impulsePerLitre;
    private int pricePerLitre;

    public SettingsMessage(byte[] body) {
        super(MessageType.SETTINGS, body);
    }

    public SettingsMessage(
            float banknoteTemperature,
            float indoorTemperature,
            int impulsePerLitre,
            float pricePerLitre) {
        super(MessageType.SETTINGS);

        this.banknoteTemperature = Utils.logicalConvert(banknoteTemperature);
        this.indoorTemperature = Utils.logicalConvert(indoorTemperature);
        this.impulsePerLitre = impulsePerLitre;
        this.pricePerLitre = Utils.logicalConvert(pricePerLitre);
    }

    @Override
    protected void deserializeData(ByteBuffer buffer) {
        this.banknoteTemperature = buffer.getChar();
        this.indoorTemperature = buffer.getChar();
        this.impulsePerLitre = buffer.getChar();
        this.pricePerLitre = buffer.getChar();
    }

    @Override
    protected void serializeData(ByteBuffer buffer) {
        buffer.putChar((char) this.banknoteTemperature);
        buffer.putChar((char) this.indoorTemperature);
        buffer.putChar((char) this.impulsePerLitre);
        buffer.putChar((char) this.pricePerLitre);
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

    public float getPricePerLitre() {
        return Utils.logicalConvert(pricePerLitre);
    }
}
