package com.example.demo.network.internal;

import com.example.demo.network.events.EventHandler;
import com.example.demo.network.packets.Packet;
import com.example.demo.network.packets.PacketFactory;
import javafx.application.Platform;

import java.io.*;
import java.net.Socket;
import java.util.function.Consumer;

public class NetworkHandler implements Runnable{

    public static NetworkHandler INSTANCE;


    private final String host;
    private final int port;
    private Socket socket;
    private boolean shutdown;
    private ConnectionState state;

    private PacketSender packetSender;
    private PacketFactory packetFactory;

    public NetworkHandler(String host, int port) {
        if(INSTANCE != null){
            throw new RuntimeException("You can't create 2 NetworkHandler instance");
        }

        INSTANCE = this;
        this.host = host;
        this.port = port;
        this.shutdown = false;
    }

    private void connectToServer(){
        while ((socket == null || !socket.isConnected()) && !shutdown){
            try {
                setConnectionState(ConnectionState.CONNECTING);
                socket = new Socket(host, port);
                setConnectionState(ConnectionState.CONNECTED);
            } catch (IOException e) {
                System.out.println("Can't connect to server");
            }
            try {
                Thread.sleep(300);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }

        packetFactory = new PacketFactory();

        try {
            packetSender = new PacketSender(socket.getOutputStream());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Thread thread = new Thread(packetSender);
        thread.setDaemon(true);
        thread.start();
    }

    @Override
    public void run() {
        while(!shutdown){
            connectToServer();

            System.out.println("Connection has been established");

            DataInputStream dataInputStream = null;
            try {
                dataInputStream = new DataInputStream(socket.getInputStream());
            } catch (IOException e) {
                stop();
            }
            while(!shutdown && dataInputStream != null){
                try {
                    Packet packet = packetFactory.fromId(dataInputStream.readInt());
                    packet.read(dataInputStream);

                    Consumer<Packet> consumer = EventHandler.getPacketCallback(packet.getClass());
                    if(consumer != null){
                        consumer.accept(packet);
                    }
                } catch (IOException e) {
                    packetSender.stop();
                    try {
                        socket.close();
                    } catch (IOException ex) {
                        throw new RuntimeException(ex);
                    }
                    socket = null;
                    break;
                }
            }
        }
    }

    private void setConnectionState(ConnectionState state){
        this.state = state;
        EventHandler.getConnectionStateEvent().accept(state);
    }

    /*
        STATIC METHODS
     */

    public static void connect(String host, int port){
        Thread thread = new Thread(new NetworkHandler(host, port));
        thread.setDaemon(true);
        thread.start();
    }

    public static void stop(){
        INSTANCE.shutdown = true;
        INSTANCE.packetSender.stop();
        try {
            INSTANCE.socket.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void sendPacket(Packet packet){
        INSTANCE.packetSender.addPacket(packet);
    }

    public static ConnectionState getConnectionState(){
        return INSTANCE.state;
    }
}
