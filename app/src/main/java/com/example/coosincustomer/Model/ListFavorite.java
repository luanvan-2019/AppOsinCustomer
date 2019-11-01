package com.example.coosincustomer.Model;

public class ListFavorite {

    private String name,phone,type;

    public ListFavorite(String name, String phone, String type) {
        this.name = name;
        this.phone = phone;
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
