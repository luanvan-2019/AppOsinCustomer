package com.example.coosincustomer;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.os.ParcelUuid;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.thekhaeng.pushdownanim.PushDownAnim;

import java.text.DecimalFormat;

public class ConfirmNauAnActivity extends AppCompatActivity {

    TextView txtAddress,txtDate,txtTime,txtTongTien,txtGhiChu,txtKhuyenMai,txtSoNguoiAn,
            txtSoMonAn,txtKhauVi,txtTraiCay,txtDiCho,txtTienDiCho;
    Integer tongtien,tienDiCho;
    Button btnToThanhToan;
    RelativeLayout relativeLayoutDiCho;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirm_nau_an);

        anhxa();

        tienDiCho = getIntent().getIntExtra("tiendicho",0);
        if (tienDiCho == 0){
            relativeLayoutDiCho.setVisibility(View.GONE);
        }else relativeLayoutDiCho.setVisibility(View.VISIBLE);
        tongtien = getIntent().getIntExtra("tongtien",0) + tienDiCho;

        Log.d("BBB",tongtien+"");

        //back button
        Toolbar toolbar = findViewById(R.id.toolbar_xacnhan_dichvu);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        setText();

        PushDownAnim.setPushDownAnimTo(btnToThanhToan).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent toThanhToan = new Intent(ConfirmNauAnActivity.this,ThanhtoanActivity.class);
                toThanhToan.putExtra("orderType",4);
                toThanhToan.putExtra("address",getIntent().getStringExtra("address"));
                toThanhToan.putExtra("dateWork",getIntent().getStringExtra("day"));
                toThanhToan.putExtra("timeWork",getIntent().getStringExtra("time"));
                toThanhToan.putExtra("peopleAmount",getIntent().getStringExtra("songuoian"));
                toThanhToan.putExtra("dishAmount",getIntent().getStringExtra("somon"));
                toThanhToan.putExtra("dishName",getIntent().getStringExtra("tenmon"));
                toThanhToan.putExtra("taste",getIntent().getStringExtra("khauvi"));
                toThanhToan.putExtra("fruit",getIntent().getStringExtra("traicay"));
                toThanhToan.putExtra("market",getIntent().getStringExtra("nguoilamdicho"));
                toThanhToan.putExtra("marketPrice",getIntent().getIntExtra("tiendicho",0));
                toThanhToan.putExtra("totalPrice",tongtien);
                toThanhToan.putExtra("note",getIntent().getStringExtra("ghichu"));
                toThanhToan.putExtra("latitude",getIntent().getDoubleExtra("latitude",0));
                toThanhToan.putExtra("longitude",getIntent().getDoubleExtra("longitude",0));
                startActivity(toThanhToan);
            }
        });
    }

    //anh xa
    private void anhxa(){
        txtAddress = findViewById(R.id.txt_xacnhan_diadiem);
        txtDate = findViewById(R.id.txt_xacnhan_ngay);
        txtTime = findViewById(R.id.txt_xacnhan_ca);
        txtTongTien = findViewById(R.id.txt_tien);
        txtGhiChu = findViewById(R.id.txt_ghichu);
        txtKhuyenMai = findViewById(R.id.txt_tien_khuyenmai);
        txtSoNguoiAn = findViewById(R.id.txt_songuoian);
        txtSoMonAn = findViewById(R.id.txt_somonan);
        txtKhauVi = findViewById(R.id.txt_khauvi);
        txtTraiCay = findViewById(R.id.txt_traicay);
        txtDiCho = findViewById(R.id.txt_dicho);
        txtTienDiCho = findViewById(R.id.txt_tiendichotoida);
        btnToThanhToan = findViewById(R.id.btn_xacnhan_thanhtoan);
        relativeLayoutDiCho = findViewById(R.id.relative_chiphi_dicho);
    }

    private void setText(){
        txtAddress.setText(getIntent().getStringExtra("address"));
        txtDate.setText(getIntent().getStringExtra("day"));
        txtTime.setText("Thời gian ăn:  "+getIntent().getStringExtra("time"));

        DecimalFormat decimalFormat = new DecimalFormat("#,###");
        String totalGiaString = decimalFormat.format(tongtien);
        String totaltienChoString = decimalFormat.format(tienDiCho);
        txtTongTien.setText(totalGiaString+"đ");
        txtTienDiCho.setText(totaltienChoString+"đ");
        txtGhiChu.setText(getIntent().getStringExtra("ghichu"));
        txtSoNguoiAn.setText(getIntent().getStringExtra("songuoian"));
        txtSoMonAn.setText(getIntent().getStringExtra("somon"));
        txtKhauVi.setText(getIntent().getStringExtra("khauvi"));
        txtTraiCay.setText(getIntent().getStringExtra("traicay"));
        txtDiCho.setText(getIntent().getStringExtra("nguoilamdicho"));
    }

    //back button
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
