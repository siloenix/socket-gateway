package com.siloenix.socket.message;

import com.github.snksoft.crc.CRC;
import com.siloenix.socket.communication.MessageSender;
import com.siloenix.socket.errors.ErrorCode;
import com.siloenix.socket.errors.SocketGatewayException;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.util.Arrays;

@ToString(exclude = {"body", "type"})
public abstract class Message {
    public static final int SPECIAL_SYMBOL = 2;
    public static final int MESSAGE_TYPE_INDEX = 2;
    public static final int MESSAGE_EXTENDED_TYPE_INDEX = 1;
    public static final int CRC_POLYNOMIAL = 0x8005;

    @Getter
    protected MessageType type;
    protected byte[] body;

    @Getter
    @Setter
    protected String simId;

    protected Message(MessageType type) {
        this.type = type;
        this.body = new byte[0];
    }

    protected Message(MessageType type, byte[] body) {
        this.type = type;
        this.body = body;
        this.deserialize();
    }

    protected abstract void deserializeData(ByteBuffer buffer);

    protected abstract void serializeData(ByteBuffer buffer);

    public abstract Integer messageSize();

    public void respond(Message message) {
        message.setSimId(simId);
        MessageSender.send(message);
    }

    public byte[] serialize() {
        ByteBuffer buffer = ByteBuffer.allocate(messageSize());

        buffer.put(messageSize().byteValue());
        buffer.put((byte) 0);
        buffer.put(this.type.getCode().byteValue());

        serializeData(buffer);

        ByteBuffer copy = buffer.duplicate();
        byte[] array = copy.array();
        byte[] crcInput = Arrays.copyOfRange(array, 1, array.length - 2);

        buffer.putChar((char) calculateCrc16(crcInput));

        ((Buffer) buffer).flip();
        return buffer.array();
    }

    public byte[] deserialize() {
        ByteBuffer buffer = ByteBuffer.wrap(this.body);
//        ((Buffer) buffer).flip();
        deserializeData(buffer);
        return buffer.array();
    }

    public static Message deserialize(byte[] bytes) {
        Message.checkCrc(bytes);

        int typeIndex = bytes[MESSAGE_TYPE_INDEX];
        MessageType type = MessageType.define(typeIndex);
        byte[] body = Arrays.copyOfRange(bytes, MESSAGE_TYPE_INDEX + 1, bytes.length - 2);
        return type.factory().create(body);
    }

    private static void checkCrc(byte[] bytes) {
        ByteBuffer buffer = ByteBuffer.wrap(bytes);
        byte[] body = Arrays.copyOfRange(bytes, 1, bytes.length - 2);

        long messageCrc = buffer.getChar(bytes.length - 2);
        long crc = calculateCrc16(body);
        if (crc != messageCrc) {
            throw new SocketGatewayException(ErrorCode.BAD_PACKET, "Crc is not equal: " + messageCrc + " and " + crc);
        }
    }

    private static long calculateCrc16(byte[] bytes) {
        CRC.Parameters CRC16 = new CRC.Parameters(16, CRC_POLYNOMIAL, 0L, true, true, 0L);
        return CRC.calculateCRC(CRC16, bytes);
    }

}
