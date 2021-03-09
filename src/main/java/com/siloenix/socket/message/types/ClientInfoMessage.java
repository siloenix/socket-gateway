package com.siloenix.socket.message.types;

import com.siloenix.socket.message.Message;
import com.siloenix.socket.message.MessageType;
import com.siloenix.socket.Utils;
import lombok.Getter;
import lombok.ToString;

import java.nio.ByteBuffer;

import static com.siloenix.socket.Utils.logicalConvert;

@ToString(callSuper = true)
public class ClientInfoMessage extends Message {
    public static Integer FULL_LENGTH
            = 1 // size
            + 2 // command type
            + 4 // cash
            + 8 // clientId
            + 2; // crc

    @Getter
    private int money;
    @Getter
    private long clientId;

    public ClientInfoMessage(byte[] body) {
        super(MessageType.CLIENT_INFO, body);
    }

    public ClientInfoMessage(float money, long clientId) {
        super(MessageType.CLIENT_INFO);
        this.money = Utils.logicalConvert(money);
        this.clientId = clientId;
    }

    @Override
    protected void deserializeData(ByteBuffer buffer) {
        this.money = buffer.getInt();
        this.clientId = buffer.getLong();
    }

    @Override
    protected void serializeData(ByteBuffer buffer) {
        buffer.putInt(this.money);
        buffer.putLong(this.clientId);
    }

    @Override
    public Integer messageSize() {
        return FULL_LENGTH;
    }
}
