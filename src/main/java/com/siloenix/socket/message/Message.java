package com.siloenix.socket.message;

import com.siloenix.socket.smp.annotations.SmpIgnore;
//import com.siloenix.socket.communication.MessageSender;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString(exclude = {"body", "type"})
public abstract class Message<Id, Type> {
    public static final int SPECIAL_SYMBOL = 2;

    @Getter
    @SmpIgnore
    protected Type type;
    @SmpIgnore
    protected byte[] body;

    @Getter
    @Setter
    @SmpIgnore
    protected Id machineId;

    protected Message(Type type) {
        this.type = type;
        this.body = new byte[0];
    }

    protected Message(Type type, byte[] body) {
        this.type = type;
        this.body = body;
    }

    public void respond(Message<Id, Type> message) {
        message.setMachineId(machineId);
//        MessageSender.send(message);
    }

//    public byte[] serialize() {
//        ByteBuffer buffer = ByteBuffer.allocate(messageSize());
//
//        buffer.put(messageSize().byteValue());
//        buffer.put((byte) 0);
//        buffer.put(this.type.getCode().byteValue());
//
//        serializeData(buffer);
//
//        ByteBuffer copy = buffer.duplicate();
//        byte[] array = copy.array();
//        byte[] crcInput = Arrays.copyOfRange(array, 1, array.length - 2);
//
//        buffer.putChar((char) calculateCrc16(crcInput));
//
//        ((Buffer) buffer).flip();
//        return buffer.array();
//    }
//
//    public byte[] deserialize() {
//        ByteBuffer buffer = ByteBuffer.wrap(this.body);
////        ((Buffer) buffer).flip();
//        deserializeData(buffer);
//        return buffer.array();
//    }
//
//    public static Message deserialize(byte[] bytes) {
//        Message.checkCrc(bytes);
//
//        int typeIndex = bytes[MESSAGE_TYPE_INDEX];
//        MessageType type = MessageType.define(typeIndex);
//        byte[] body = Arrays.copyOfRange(bytes, MESSAGE_TYPE_INDEX + 1, bytes.length - 2);
//        return type.factory().create(body);
//    }
//
//    private static void checkCrc(byte[] bytes) {
//        ByteBuffer buffer = ByteBuffer.wrap(bytes);
//        byte[] body = Arrays.copyOfRange(bytes, 1, bytes.length - 2);
//
//        long messageCrc = buffer.getChar(bytes.length - 2);
//        long crc = calculateCrc16(body);
//        if (crc != messageCrc) {
//            throw new SocketGatewayException(ErrorCode.BAD_PACKET, "Crc is not equal: " + messageCrc + " and " + crc);
//        }
//    }
//
//    private static long calculateCrc16(byte[] bytes) {
//        CRC.Parameters CRC16 = new CRC.Parameters(16, CRC_POLYNOMIAL, 0L, true, true, 0L);
//        return CRC.calculateCRC(CRC16, bytes);
//    }

}
