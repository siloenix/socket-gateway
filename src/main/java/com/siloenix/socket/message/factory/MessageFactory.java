package com.siloenix.socket.message.factory;

import com.siloenix.socket.message.Message;

@FunctionalInterface
public interface MessageFactory<T extends Message> {
    T create(byte[] body);
}
