package com.siloenix.socket.message.types;

import com.siloenix.socket.message.Message;
import com.siloenix.socket.message.MessageType;
import com.siloenix.socket.Utils;
import lombok.Getter;
import lombok.ToString;

import java.nio.ByteBuffer;

@ToString(callSuper = true)
public class CardInitMessage extends Message {
    private static final int CLIENT_ID_SIZE = 16;
    private static final Integer FULL_LENGTH
            = 1 // size
            + 2 // command
            + CLIENT_ID_SIZE
            + 2; // crc

    @Getter
    private String clientId;

    public CardInitMessage(byte[] body) {
        super(MessageType.CARD_INIT, body);
    }

    public CardInitMessage(String clientId) {
        super(MessageType.CARD_INIT);
        this.clientId = clientId;
    }

    @Override
    protected void deserializeData(ByteBuffer buffer) {
        byte[] clientIdBytes = new byte[CLIENT_ID_SIZE];
        buffer.get(clientIdBytes);

        this.clientId = Utils.asString(clientIdBytes);
    }

    @Override
    protected void serializeData(ByteBuffer buffer) {
        buffer.put(Utils.asBytes(this.clientId, CLIENT_ID_SIZE));
    }

    @Override
    public Integer messageSize() {
        return FULL_LENGTH;
    }
}
