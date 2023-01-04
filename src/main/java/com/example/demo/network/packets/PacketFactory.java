package com.example.demo.network.packets;

import com.example.demo.network.packets.impl.common.MessagePacket;
import com.example.demo.network.packets.impl.incoming.ServerResponse;
import com.example.demo.network.packets.impl.incoming.UnreadMessagePacket;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

public class PacketFactory {
    private final Map<Integer, Supplier<Packet>> packets = new HashMap<>();

    public PacketFactory(){
        register(0, MessagePacket::new)
                .register(2, ServerResponse::new)
                .register(5, UnreadMessagePacket::new);
    }

    public PacketFactory register(int id, Supplier<Packet> creator){
        packets.put(id, creator);
        return this;
    }

    public <T extends Packet> T fromId(int id){
        if(!packets.containsKey(id)) return null;
        return (T) packets.get(id).get();
    }
}
