package com.example.demo.network.events;

import com.example.demo.network.internal.ConnectionState;
import com.example.demo.network.packets.Packet;

import java.util.HashMap;
import java.util.function.Consumer;

public class EventHandler {
    private static Consumer<ConnectionState> connectionStateEvent;

    static {
        setConnectionStateEvent((v) -> {});
    }

    public static Consumer<ConnectionState> getConnectionStateEvent() {
        return connectionStateEvent;
    }

    public static void setConnectionStateEvent(Consumer<ConnectionState> stateEvent){
        connectionStateEvent = stateEvent;
    }

    /*
        PACKETS
     */

    private static HashMap<Class<? extends Packet>, Consumer<Packet>> packetCallbacks;

    static {
        packetCallbacks = new HashMap<>();
    }

    public static Consumer<Packet> getPacketCallback(Class<? extends Packet> clazz){
        return packetCallbacks.get(clazz);
    }

    public static void setPacketCallback(Class<? extends  Packet> clazz, Consumer<Packet> objectConsumer){
        packetCallbacks.put(clazz, objectConsumer);
    }
}
