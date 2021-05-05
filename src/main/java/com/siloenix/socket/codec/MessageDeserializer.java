package com.siloenix.socket.codec;

import com.siloenix.socket.message.Message;

public interface MessageDeserializer {
    <T extends Message<?, ?>> T deserialize(byte[] bytes);
}
