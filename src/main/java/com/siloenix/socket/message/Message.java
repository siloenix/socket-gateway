package com.siloenix.socket.message;

import com.siloenix.socket.smp.annotations.SmpIgnore;
//import com.siloenix.socket.communication.MessageSender;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString(exclude = {"type"})
public abstract class Message<Id, Type> {
    public static final int SPECIAL_SYMBOL = 2;

    @Getter
    @SmpIgnore
    protected Type type;

    @Getter
    @Setter
    @SmpIgnore
    protected Id machineId;

    protected Message(Type type) {
        this.type = type;
    }

    public void respond(Message<Id, Type> message) {
        message.setMachineId(machineId);
//        MessageSender.send(message);
    }

}
