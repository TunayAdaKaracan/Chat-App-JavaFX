package com.example.demo.common;

public class Message {
    private String username;
    private String messageContent;

    public Message(String username, String messageContent){
        this.username = username;
        this.messageContent = messageContent;
    }

    public String getUsername() {
        return username;
    }

    public String getMessageContent() {
        return messageContent;
    }

}
