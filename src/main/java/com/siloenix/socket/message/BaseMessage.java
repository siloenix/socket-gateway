package com.siloenix.socket.message;

public class BaseMessage extends Message<String, BaseMessageType> {
    protected BaseMessage(BaseMessageType messageType) {
        super(messageType);
    }
}
