package com.example.demo.network.packets;

import com.example.demo.network.internal.NetworkHandler;

import java.io.DataInputStream;
import java.io.DataOutputStream;

public interface Packet {

    void write(DataOutputStream stream);

    void read(DataInputStream stream);

    default void send(){
        NetworkHandler.sendPacket(this);
    }

}
