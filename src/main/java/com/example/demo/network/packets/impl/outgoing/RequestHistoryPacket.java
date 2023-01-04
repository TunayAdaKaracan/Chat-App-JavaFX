package com.example.demo.network.packets.impl.outgoing;

import com.example.demo.network.packets.Packet;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class RequestHistoryPacket implements Packet {
    @Override
    public void write(DataOutputStream stream) {
        try {
            stream.writeInt(4);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void read(DataInputStream stream) {

    }
}