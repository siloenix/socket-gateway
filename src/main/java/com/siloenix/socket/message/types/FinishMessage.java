package com.siloenix.socket.message.types;

import com.siloenix.socket.message.BaseMessage;
import com.siloenix.socket.message.BaseMessageType;
import lombok.ToString;

@ToString(callSuper = true)
public class FinishMessage extends BaseMessage {
    public FinishMessage() {
        super(BaseMessageType.FINISH);
    }
}
