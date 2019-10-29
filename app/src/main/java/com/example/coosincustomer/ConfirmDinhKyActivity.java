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

public class ConfirmDinhKyActivity extends AppCompatActivity {

    Button btnToThanhToan;
    TextView txtAddress, txtLichLamViec, txtTongTien, txtGio1buoi, txtSoBuoi,txtCa,txtTongTienBottom,txtGhiChu;
    String address, thu, startTime, ca, sogiolam,endtime,ghichu;
    Integer sobuoilam,tongtien;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirm_dinh_ky);

        //anh xa
        btnToThanhToan = findViewById(R.id.btn_xacnhan_thanhtoan);
        txtAddress = findViewById(R.id.txt_xacnhan_diadiem);
        txtLichLamViec = findViewById(R.id.txt_xacnhan_ngay);
        txtTongTien = findViewById(R.id.txt_tien);
        txtGio1buoi = findViewById(R.id.txt_sogio);
        txtSoBuoi = findViewById(R.id.txt_sobuoi);
        txtCa = findViewById(R.id.txt_xacnhan_ca);
        txtTongTienBottom = findViewById(R.id.id_tong_tien);
        txtGhiChu = findViewById(R.id.txt_ghichu);

        //back button
        Toolbar toolbar = findViewById(R.id.toolbar_xacnhan_dichvu);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        //lay du lieu tu order dinh ky chon goi ACTIVITY
        address = getIntent().getStringExtra("address");
        thu = getIntent().getStringExtra("thu");
        startTime = getIntent().getStringExtra("startTime");
        ca = getIntent().getStringExtra("ca");
        ghichu = getIntent().getStringExtra("ghichu");
        sogiolam = getIntent().getStringExtra("sogiolam");
        endtime = getIntent().getStringExtra("endtime");
        tongtien = getIntent().getIntExtra("tongtien",0);
        sobuoilam = getIntent().getIntExtra("sobuoilam",0);

        //settext
        txtAddress.setText(address);
        txtLichLamViec.setText("Ngày làm : "+thu+" ("+startTime+" - "+endtime+")");
        txtCa.setText(ca);
        DecimalFormat decimalFormat = new DecimalFormat("#,###");
        final String tongTien = decimalFormat.format(tongtien);
        txtTongTien.setText(tongTien+" đ");
        txtTongTienBottom.setText(tongTien+" đ");
        txtGio1buoi.setText(sogiolam+"h/buổi");
        txtSoBuoi.setText(sobuoilam+"");
        txtGhiChu.setText(ghichu);

        PushDownAnim.setPushDownAnimTo(btnToThanhToan).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent toThanhToan = new Intent(ConfirmDinhKyActivity.this, ThanhtoanActivity.class);
                toThanhToan.putExtra("address",address);
                toThanhToan.putExtra("schedule",thu);
                toThanhToan.putExtra("timeWork",ca);
                toThanhToan.putExtra("dateStart",startTime);
                toThanhToan.putExtra("dateEnd",endtime);
                toThanhToan.putExtra("note",ghichu);
                toThanhToan.putExtra("totalPrice",tongtien);
                toThanhToan.putExtra("orderType",2);
                toThanhToan.putExtra("totalTime",sogiolam+"h");
                toThanhToan.putExtra("totalBuoi",sobuoilam);
                toThanhToan.putExtra("latitude",getIntent().getDoubleExtra("latitude",0));
                toThanhToan.putExtra("longitude",getIntent().getDoubleExtra("longitude",0));
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
