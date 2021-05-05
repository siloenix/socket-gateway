package com.siloenix.socket.smp;

import com.siloenix.socket.message.Message;

public interface SmpMessageType {
    byte code();

    <T extends Message<?, ?>> Class<T> type();
}
