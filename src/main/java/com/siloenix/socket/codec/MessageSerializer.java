package com.siloenix.socket.codec;

import com.siloenix.socket.message.Message;

public interface MessageSerializer<Type> {
    <T extends Message<?, ? extends Type>> byte[] serialize(T message);
}
