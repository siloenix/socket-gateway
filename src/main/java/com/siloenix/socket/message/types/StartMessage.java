package com.siloenix.socket.message.types;

import com.siloenix.socket.message.Message;
import com.siloenix.socket.message.MessageType;
import com.siloenix.socket.Utils;
import lombok.Builder;
import lombok.ToString;

import java.nio.ByteBuffer;

@Builder
@ToString(callSuper = true)
public class StartMessage extends Message {
    private static final int SIM_ID_LENGTH = 32;
    public static final Integer FULL_LENGTH
            = 1 // size
            + 2 // command
            + SIM_ID_LENGTH // automatonId
            + 2; // crc

    public StartMessage() {
        super(MessageType.START);
    }

    public StartMessage(byte[] body) {
        super(MessageType.START, body);
    }

    public StartMessage(String simId) {
        super(MessageType.START);
        this.simId = simId;
    }

    @Override
    protected void deserializeData(ByteBuffer buffer) {
        byte[] automatonIdBytes = new byte[SIM_ID_LENGTH];
        buffer.get(automatonIdBytes);
        this.simId = Utils.asString(automatonIdBytes);
    }

    @Override
    protected void serializeData(ByteBuffer buffer) {
        buffer.put(Utils.asBytes(this.simId, SIM_ID_LENGTH));
    }

    @Override
    public Integer messageSize() {
        return FULL_LENGTH;
    }
}
