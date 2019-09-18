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

public class ThanhtoanActivity extends AppCompatActivity {

    private ImageView iconThanhtoan;
    private RelativeLayout relavetiveTienMat;
    private Button btnXacnhanThanhtoan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thanhtoan);

        //anh xa
        Toolbar toolbar = findViewById(R.id.toolbar_hinhthuc_thanhtoan);
        iconThanhtoan = findViewById(R.id.img_payment);
        relavetiveTienMat = findViewById(R.id.relative_tienmat);
        btnXacnhanThanhtoan = findViewById(R.id.btn_xacnhan_thanhtoan);

        //toolbar action
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        //thanh toan tien mat click
        PushDownAnim.setPushDownAnimTo(relavetiveTienMat).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                iconThanhtoan.setImageResource(R.drawable.ic_checked);
                relavetiveTienMat.setBackgroundResource(R.drawable.bg_layout_licked);
            }
        });

        PushDownAnim.setPushDownAnimTo(btnXacnhanThanhtoan).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent  intent = new Intent(ThanhtoanActivity.this,OrderSuccessActivity.class);
                startActivity(intent);
                finish();
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
