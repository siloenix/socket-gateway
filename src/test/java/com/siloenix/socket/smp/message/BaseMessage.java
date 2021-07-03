package com.siloenix.socket.smp.message;

import com.siloenix.socket.message.Message;

public abstract class BaseMessage extends Message<String, TestMessageType> {
    protected BaseMessage(TestMessageType testMessageType) {
        super(testMessageType);
    }
}
