package com.example.coosincustomer;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.thekhaeng.pushdownanim.PushDownAnim;

public class ThanhToanDinhKyActivity extends AppCompatActivity {

    private ImageView iconThanhtoanTienMat,iconThanhToanMoMo,iconThanhToanPaypal;
    private RelativeLayout relavetiveTienMat,relativeLayoutMoMo,relativeLayoutPaypal;
    private Button btnXacnhanThanhtoan;
    Integer i;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thanh_toan_dinh_ky);

        //anh xa
        Toolbar toolbar = findViewById(R.id.toolbar_hinhthuc_thanhtoan);
        iconThanhtoanTienMat = findViewById(R.id.img_payment_tienmat);
        iconThanhToanMoMo = findViewById(R.id.img_payment_momo);
        iconThanhToanPaypal = findViewById(R.id.img_payment_paypal);
        relavetiveTienMat = findViewById(R.id.relative_tienmat);
        relativeLayoutMoMo = findViewById(R.id.relative_momo);
        relativeLayoutPaypal = findViewById(R.id.relative_paypal);
        btnXacnhanThanhtoan = findViewById(R.id.btn_xacnhan_thanhtoan);

        //toolbar action
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        //check da chon hinh thuc thanh toan
        i = 0;

        //thanh toan tien mat click
        PushDownAnim.setPushDownAnimTo(relavetiveTienMat).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                i = 1;
                iconThanhtoanTienMat.setImageResource(R.drawable.ic_checked);
                relavetiveTienMat.setBackgroundResource(R.drawable.bg_layout_licked);
                iconThanhToanMoMo.setImageResource(R.drawable.ic_momo);
                relavetiveTienMat.setBackgroundResource(R.drawable.bg_layout1);
                iconThanhToanPaypal.setImageResource(R.drawable.ic_paypal);
                relativeLayoutPaypal.setBackgroundResource(R.drawable.bg_layout1);
            }
        });

        //thanh toan momo click
        PushDownAnim.setPushDownAnimTo(relativeLayoutMoMo).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                i = 1;
                iconThanhtoanTienMat.setImageResource(R.drawable.ic_payment);
                relavetiveTienMat.setBackgroundResource(R.drawable.bg_layout1);
                iconThanhToanMoMo.setImageResource(R.drawable.ic_checked);
                relativeLayoutMoMo.setBackgroundResource(R.drawable.bg_layout_licked);
                iconThanhToanPaypal.setImageResource(R.drawable.ic_paypal);
                relativeLayoutPaypal.setBackgroundResource(R.drawable.bg_layout1);
            }
        });

        //thanh toan paypal click
        PushDownAnim.setPushDownAnimTo(relativeLayoutPaypal).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                i = 1;
                iconThanhToanPaypal.setImageResource(R.drawable.ic_checked);
                relativeLayoutPaypal.setBackgroundResource(R.drawable.bg_layout_licked);
                iconThanhtoanTienMat.setImageResource(R.drawable.ic_payment);
                relavetiveTienMat.setBackgroundResource(R.drawable.bg_layout1);
                iconThanhToanMoMo.setImageResource(R.drawable.ic_momo);
                relativeLayoutMoMo.setBackgroundResource(R.drawable.bg_layout1);
            }
        });

        //thanh toan
        PushDownAnim.setPushDownAnimTo(btnXacnhanThanhtoan).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent thanhtoan = new Intent(ThanhToanDinhKyActivity.this,MoMoActivity.class);
                startActivity(thanhtoan);
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
