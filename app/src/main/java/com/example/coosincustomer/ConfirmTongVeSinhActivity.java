package com.example.coosincustomer;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.thekhaeng.pushdownanim.PushDownAnim;

import java.text.DecimalFormat;

public class ConfirmTongVeSinhActivity extends AppCompatActivity {

    TextView txtAddress,txtDay,timeStart,txtGhiChu,txtTongTien,txtKhuyenMai,txtTongGio,txtTongNguoi,txtDienTich,txtPhiDungCu;
    String address,day,time,ghichu,dientich,tonggio,tongnguoi;
    Integer tongtien,phidungcu,areaType;
    Button btnToThanhToan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirm_tong_ve_sinh);

        //anh xa
        txtAddress = findViewById(R.id.txt_xacnhan_diadiem);
        txtDay = findViewById(R.id.txt_xacnhan_ngay);
        timeStart = findViewById(R.id.txt_xacnhan_giobatdau);
        txtGhiChu = findViewById(R.id.txt_ghichu);
        txtTongTien = findViewById(R.id.txt_tien);
        txtKhuyenMai = findViewById(R.id.txt_tien_khuyenmai);
        txtTongGio = findViewById(R.id.txt_sogio);
        txtTongNguoi = findViewById(R.id.txt_songuoilam);
        txtDienTich = findViewById(R.id.txt_dientich);
        txtPhiDungCu = findViewById(R.id.txt_phidungcu);
        btnToThanhToan = findViewById(R.id.btn_xacnhan_thanhtoan);

        //tool bar action
        Toolbar toolbar = findViewById(R.id.toolbar_xacnhan_dichvu);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        //lay du lieu tu order tong ve sinh
        address = getIntent().getStringExtra("address");
        day = getIntent().getStringExtra("day");
        time = getIntent().getStringExtra("time");
        ghichu = getIntent().getStringExtra("ghichu");
        tongtien = getIntent().getIntExtra("tongtien",0);
        dientich = getIntent().getStringExtra("dientich");
        tonggio = getIntent().getStringExtra("tonggio");
        tongnguoi = getIntent().getStringExtra("tongnguoi");
        phidungcu = getIntent().getIntExtra("phidungcu",0);
        DecimalFormat decimalFormat = new DecimalFormat("#,###");
        String totalGiaString = decimalFormat.format(tongtien);
        String phidungcuString = decimalFormat.format(phidungcu);

        if (dientich.trim().equals("80m\u00B2")){
            areaType = 1;
        }else if (dientich.trim().equals("100m\u00B2")){
            areaType = 2;
        }else areaType = 3;

        //settext
        txtAddress.setText(address);
        txtDay.setText(day);
        timeStart.setText("Thời gian bắt đầu: "+time);
        txtGhiChu.setText(ghichu);
        txtTongTien.setText(totalGiaString+" đ");
        txtTongGio.setText(tonggio);
        txtTongNguoi.setText(tongnguoi);
        txtDienTich.setText(dientich);
        txtPhiDungCu.setText(phidungcuString+"đ");

        PushDownAnim.setPushDownAnimTo(btnToThanhToan).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent toThanhToan = new Intent(ConfirmTongVeSinhActivity.this,ThanhtoanActivity.class);
                toThanhToan.putExtra("address",address);
                toThanhToan.putExtra("dateWork",day);
                toThanhToan.putExtra("timeStart",time);
                toThanhToan.putExtra("areaType",areaType);
                toThanhToan.putExtra("note",ghichu);
                toThanhToan.putExtra("totalPrice",tongtien);
                toThanhToan.putExtra("orderType",3);
                startActivity(toThanhToan);
            }
        });
    }

    //back button
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
