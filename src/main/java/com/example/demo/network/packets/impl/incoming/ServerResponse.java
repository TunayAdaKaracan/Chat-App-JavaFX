package com.example.demo.network.packets.impl.incoming;

import com.example.demo.network.packets.Packet;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

// ID 2
public class ServerResponse implements Packet {

    public enum StatusCode {
        SUCCESS(0),
        FAILURE(1);

        private int code;
        StatusCode(int code){
            this.code = code;
        }

        public int getCode() {
            return code;
        }

        public static StatusCode getStatusCodeFromID(int id){
            for(StatusCode statusCode : StatusCode.values()){
                if(id == statusCode.getCode()){
                    return statusCode;
                }
            }
            return null;
        }
    }

    private StatusCode code;
    private String text;

    public StatusCode getCode() {
        return code;
    }

    public String getText() {
        return text;
    }

    @Override
    public void write(DataOutputStream stream) {}

    @Override
    public void read(DataInputStream stream) {
        try {
            code = StatusCode.getStatusCodeFromID(stream.readInt());
            text = stream.readUTF();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
