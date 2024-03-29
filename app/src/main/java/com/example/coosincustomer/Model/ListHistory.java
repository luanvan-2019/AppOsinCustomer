package com.example.coosincustomer.Model;

public class ListHistory {

    private String ordertype,date, ca, diadiem,empName,dateEnd;
    private int gia,mahoadon;

    public ListHistory(String ordertype, String date, String ca, String diadiem, int mahoadon, int gia, String empName,String dateEnd) {
        this.ordertype = ordertype;
        this.date = date;
        this.ca = ca;
        this.diadiem = diadiem;
        this.mahoadon = mahoadon;
        this.gia = gia;
        this.empName = empName;
        this.dateEnd = dateEnd;
    }

    public String getOrdertype() {
        return ordertype;
    }

    public void setOrdertype (String ordertype) {
        this.ordertype = ordertype;
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

    public String getEmpName() {
        return empName;
    }

    public void setEmpName(String empName) {
        this.empName = empName;
    }

    public String getDateEnd() {
        return dateEnd;
    }

    public void setDateEnd(String dateEnd) {
        this.dateEnd = dateEnd;
    }
}