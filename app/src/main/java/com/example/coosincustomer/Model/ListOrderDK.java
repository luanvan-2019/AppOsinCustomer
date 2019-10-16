package com.example.coosincustomer.Model;

public class ListOrderDK {

    private String status, dateStart, dateEnd,lich, ca, diadiem;
    private int gia,mahoadon;

    public ListOrderDK(String status, String dateStart, String dateEnd, String lich, String ca, String diadiem, int gia, int mahoadon) {
        this.status = status;
        this.dateStart = dateStart;
        this.dateEnd = dateEnd;
        this.lich = lich;
        this.ca = ca;
        this.diadiem = diadiem;
        this.gia = gia;
        this.mahoadon = mahoadon;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDateStart() {
        return dateStart;
    }

    public void setDateStart(String dateStart) {
        this.dateStart = dateStart;
    }

    public String getDateEnd() {
        return dateEnd;
    }

    public void setDateEnd(String dateEnd) {
        this.dateEnd = dateEnd;
    }

    public String getLich() {
        return lich;
    }

    public void setLich(String lich) {
        this.lich = lich;
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

    public int getGia() {
        return gia;
    }

    public void setGia(int gia) {
        this.gia = gia;
    }

    public int getMahoadon() {
        return mahoadon;
    }

    public void setMahoadon(int mahoadon) {
        this.mahoadon = mahoadon;
    }
}