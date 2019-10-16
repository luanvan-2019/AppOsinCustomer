package com.example.coosincustomer.Model;

public class ListOrderCOOK {

    private String status, date, ca, diadiem;
    private int gia,mahoadon;

    public ListOrderCOOK(String status, String date, String ca, String diadiem, int mahoadon, int gia) {
        this.status = status;
        this.date = date;
        this.ca = ca;
        this.diadiem = diadiem;
        this.mahoadon = mahoadon;
        this.gia = gia;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getCa() {
        return ca;
    }

    public void setCa(String ca) {
        this.ca = ca;
    }

    public String getDiadiem() {
        return diadiem;
    }

    public void setDiadiem(String diadiem) {
        this.diadiem = diadiem;
    }

    public int getMahoadon() {
        return mahoadon;
    }

    public void setMahoadon(int mahoadon) {
        this.mahoadon = mahoadon;
    }

    public int getGia() {
        return gia;
    }

    public void setGia(int gia) {
        this.gia = gia;
    }
}