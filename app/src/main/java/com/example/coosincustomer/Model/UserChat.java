package com.example.coosincustomer.Model;

public class UserChat {

    private String phone;
    private String username;
    private String imageUrl;
    private String message;

    public UserChat(String phone, String username, String imageUrl, String message) {
        this.phone = phone;
        this.username = username;
        this.imageUrl = imageUrl;
        this.message = message;
    }

    public UserChat() {
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
