package com.example.coosincustomer;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.thekhaeng.pushdownanim.PushDownAnim;

public class ConfirmActivity extends AppCompatActivity {

    private Button btnXacNhanThanhToan;
    private TextView txtConfirmDiaDiem, txtConfirmDate, txtConfirmCa, txtConfirmMaKM, txtConfirmGhiChu,
            txtConfirmTongTien,txtConfirmTotalTime,txtConfirmDonGia,txtConfirmPhiDC;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirm);

        //anh xa
        Toolbar toolbar = findViewById(R.id.toolbar_xacnhan_dichvu);
        btnXacNhanThanhToan = findViewById(R.id.btn_xacnhan_thanhtoan);
        txtConfirmDiaDiem = findViewById(R.id.txt_xacnhan_diadiem);
        txtConfirmDate = findViewById(R.id.txt_xacnhan_ngay);
        txtConfirmCa = findViewById(R.id.txt_xacnhan_ca);
        txtConfirmGhiChu = findViewById(R.id.txt_ghichu);
        txtConfirmMaKM = findViewById(R.id.txt_tien_khuyenmai);
        txtConfirmTongTien = findViewById(R.id.txt_tien);
        txtConfirmTotalTime = findViewById(R.id.txt_sogio);
        txtConfirmDonGia = findViewById(R.id.txt_dongia);
        txtConfirmPhiDC = findViewById(R.id.txt_phidungcu);

        //toolbar action
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        //lay du lieu tu Order activity
        Intent intent = getIntent();
        if (intent != null){
            String confirmDiaDiem = intent.getStringExtra("address");
            String confirmDate = intent.getStringExtra("date");
            String confirmCa = intent.getStringExtra("ca");
            String confirmMaKH = intent.getStringExtra("makhuyenmai");
            String confirmGhiChu = intent.getStringExtra("ghichu");
            String confirmTongTien = intent.getStringExtra("tongtien");
            String confirmTotalTime = intent.getStringExtra("totaltime");
            String confirmDongia = intent.getStringExtra("dongia").substring(0,2) + "."+intent.getStringExtra("dongia").substring(2)+"đ";
            String confirmDungCu = intent.getStringExtra("phidungcu").substring(0,2) + "."+intent.getStringExtra("phidungcu").substring(2)+"đ";
            txtConfirmDiaDiem.setText(confirmDiaDiem);
            txtConfirmDate.setText(confirmDate);
            txtConfirmCa.setText(confirmCa);
            txtConfirmMaKM.setText(confirmMaKH);
            txtConfirmGhiChu.setText(confirmGhiChu);
            txtConfirmTongTien.setText(confirmTongTien);
            txtConfirmDonGia.setText(confirmDongia);
            txtConfirmTotalTime.setText(confirmTotalTime);
            txtConfirmPhiDC.setText(confirmDungCu);
        }

        //button xac nhan & thanh toan
        PushDownAnim.setPushDownAnimTo(btnXacNhanThanhToan).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ConfirmActivity.this, ThanhtoanActivity.class);
                startActivity(intent);
            }
        });
    }

    //toolbar back button
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
