package com.example.mrchat.model;

public class Message {
    private String senderId;
    private String receiverId;
    private String text;
    private long timestamp;

    public Message() {}

    public Message(String senderId, String receiverId, String text) {
        this.senderId = senderId;
        this.receiverId = receiverId;
        this.text = text;
        this.timestamp = System.currentTimeMillis(); // auto-set when created
    }

    public String getSenderId() { return senderId; }
    public String getReceiverId() { return receiverId; }
    public String getText() { return text; }
    public long getTimestamp() { return timestamp; }
}
