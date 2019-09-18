package com.example.coosincustomer;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.thekhaeng.pushdownanim.PushDownAnim;

public class ConfirmActivity extends AppCompatActivity {

    private Button btnXacNhanThanhToan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirm);

        //anh xa
        Toolbar toolbar = findViewById(R.id.toolbar_xacnhan_dichvu);
        btnXacNhanThanhToan = findViewById(R.id.btn_xacnhan_thanhtoan);

        //toolbar action
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

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
