package com.siloenix.socket.codec;

import com.siloenix.socket.message.Message;

public interface MessageSerializer<Type> {
    byte[] serialize(Message<?, ? extends Type> message);
}
