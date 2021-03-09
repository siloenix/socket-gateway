package com.siloenix.socket.communication;

import com.siloenix.socket.message.Message;

@FunctionalInterface
public interface MessageHandler {
    void handle(Message message);
}
