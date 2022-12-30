package com.example.demo.network.internal;

import com.example.demo.network.packets.Packet;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

public class PacketSender implements Runnable{
    private final OutputStream out;
    private volatile List<Packet> packetsToSend;
    private boolean shutdown;

    public PacketSender(OutputStream socketOut){
        this.out = socketOut;
        this.packetsToSend = new ArrayList<>();
        this.shutdown = false;
    }

    public void addPacket(Packet packet){
        this.packetsToSend.add(packet);
    }

    public void stop(){
        shutdown = true;
    }


    @Override
    public void run() {
        while(!shutdown){
            if(packetsToSend.size() != 0){
                System.out.println("Preparing to sending packet.");
                try {
                    Packet packet = packetsToSend.remove(0);
                    DataOutputStream dos = new DataOutputStream(out);
                    packet.write(dos);
                    dos.flush();
                    System.out.println("Packet sended");
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }
}
