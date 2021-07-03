package com.siloenix.socket.communication;

import com.siloenix.socket.message.BaseMessage;

@FunctionalInterface
public interface MessageHandler {
    void handle(BaseMessage message);
}
