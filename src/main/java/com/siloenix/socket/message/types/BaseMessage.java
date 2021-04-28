package com.siloenix.socket.message.types;

import com.siloenix.socket.message.Message;

public class BaseMessage extends Message<String, MessageType> {
    protected BaseMessage(MessageType messageType) {
        super(messageType);
    }

    public BaseMessage(MessageType messageType, byte[] body) {
        super(messageType, body);
    }
}
