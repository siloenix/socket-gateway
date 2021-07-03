package com.siloenix.socket.message.types;

import com.siloenix.socket.message.BaseMessage;
import com.siloenix.socket.message.BaseMessageType;
import com.siloenix.socket.smp.annotations.SmpString;
import lombok.Getter;
import lombok.ToString;

@ToString
public class StartMessage extends BaseMessage {
    @Getter
    @SmpString(length = 32)
    protected String machineId;

    public StartMessage() {
        super(BaseMessageType.START);
    }

    public StartMessage(String machineId) {
        super(BaseMessageType.START);
        this.machineId = machineId;
    }
}
