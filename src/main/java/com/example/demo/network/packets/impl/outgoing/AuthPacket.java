package com.example.demo.network.packets.impl.outgoing;

import com.example.demo.network.packets.Packet;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

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
        try {
            stream.writeInt(1);
            stream.writeUTF(username);
            stream.writeUTF(password);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void read(DataInputStream stream) {

    }
}
