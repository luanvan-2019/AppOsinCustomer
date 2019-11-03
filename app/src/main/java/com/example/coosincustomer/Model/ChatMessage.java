package com.example.coosincustomer.Model;

import java.util.Date;

public class ChatMessage {

    private String messageText;
    private String userSender;
    private String userReceiver;
    private long messageTime;

    public ChatMessage(String messageText,String userSender,String userReceiver) {
        this.messageText = messageText;
        this.userSender = userSender;
        this.userReceiver = userReceiver;

        messageTime = new Date().getTime();
    }

    public ChatMessage() {
    }

    public String getMessageText() {
        return messageText;
    }

    public void setMessageText(String messageText) {
        this.messageText = messageText;
    }

    public long getMessageTime() {
        return messageTime;
    }

    public void setMessageTime(long messageTime) {
        this.messageTime = messageTime;
    }

    public String getUserSender() {
        return userSender;
    }

    public void setUserSender(String userSender) {
        this.userSender = userSender;
    }

    public String getUserReceiver() {
        return userReceiver;
    }

    public void setUserReceiver(String userReceiver) {
        this.userReceiver = userReceiver;
    }
}
