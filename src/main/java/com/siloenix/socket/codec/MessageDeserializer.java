package com.siloenix.socket.codec;

import com.siloenix.socket.message.Message;

public interface MessageDeserializer {
    Message<?, ?> deserialize(byte[] bytes);
}
