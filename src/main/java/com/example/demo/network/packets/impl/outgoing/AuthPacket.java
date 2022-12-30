package com.example.demo.network.packets.impl.outgoing;

import com.example.demo.network.packets.Packet;

import java.io.DataInputStream;
import java.io.DataOutputStream;

// ID 1
public class AuthPacket implements Packet {
    private String username;
    private String password;

    public AuthPacket setPassword(String password) {
        this.password = password;
        return this;
    }

    public AuthPacket setUsername(String username) {
        this.username = username;
        return this;
    }

    @Override
    public void write(DataOutputStream stream) {

    }

    @Override
    public void read(DataInputStream stream) {

    }
}
