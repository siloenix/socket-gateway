package com.siloenix.socket.message.types;

import com.siloenix.socket.errors.ErrorCode;
import com.siloenix.socket.message.BaseMessage;
import com.siloenix.socket.message.BaseMessageType;
import lombok.ToString;

@ToString(callSuper = true)
public class ErrorMessage extends BaseMessage {
    private byte code;

    public ErrorMessage() {
        super(BaseMessageType.ERROR);
    }

    public ErrorMessage(ErrorCode code) {
        super(BaseMessageType.ERROR);
        this.code = code.code();
    }
}
