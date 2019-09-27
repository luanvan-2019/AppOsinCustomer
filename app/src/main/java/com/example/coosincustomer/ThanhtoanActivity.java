package com.example.coosincustomer;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.thekhaeng.pushdownanim.PushDownAnim;

import java.sql.Connection;
import java.sql.Statement;

public class ThanhtoanActivity extends AppCompatActivity {

    private ImageView iconThanhtoan;
    private RelativeLayout relavetiveTienMat;
    private Button btnXacnhanThanhtoan;
    String TongTien,DiaChi;
    Connection connect;
    String phone_num;
    Integer i;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thanhtoan);

        //anh xa
        Toolbar toolbar = findViewById(R.id.toolbar_hinhthuc_thanhtoan);
        iconThanhtoan = findViewById(R.id.img_payment);
        relavetiveTienMat = findViewById(R.id.relative_tienmat);
        btnXacnhanThanhtoan = findViewById(R.id.btn_xacnhan_thanhtoan);

        //connect db
        StrictMode.ThreadPolicy policy= new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

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
                iconThanhtoan.setImageResource(R.drawable.ic_checked);
                relavetiveTienMat.setBackgroundResource(R.drawable.bg_layout_licked);
            }
        });

        //nhan du lieu tu confirmActivity
        Intent intent = getIntent();
        if (intent != null){
            TongTien = intent.getStringExtra("tongtien");
            DiaChi = intent.getStringExtra("address");

        }
        PushDownAnim.setPushDownAnimTo(btnXacnhanThanhtoan).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent  intent = new Intent(ThanhtoanActivity.this,OrderSuccessActivity.class);
                intent.putExtra("tongtien",TongTien);
                startActivity(intent);

                //cap nhat lai dia chi cho tai khoan
                if (i == 0){
                    Toast.makeText(getApplicationContext(),"Chưa chọn hình thức thanh toán",Toast.LENGTH_LONG).show();
                }else {
                    try
                    {
                        com.example.coosincustomer.ConnectionDB conStr=new com.example.coosincustomer.ConnectionDB();
                        connect =conStr.CONN();        // Connect to database
                        if (connect == null)
                        {
                            Toast.makeText(getApplicationContext(), "Không có kết nối mạng!", Toast.LENGTH_LONG).show();
                        }
                        else
                        {
                            //lay so dien thoai da luu khi dang nhap
                            SharedPreferences SP = getApplicationContext().getSharedPreferences("PHONE",0);
                            phone_num = SP.getString("phone_num",null);

                            String query = "UPDATE CUSTOMER SET ADDRESS = N'" + DiaChi +"' WHERE PHONE_NUM='"+phone_num+"'";
                            Statement stmt = connect.createStatement();
                            stmt.executeQuery(query);
                            connect.close();
                        }
                    }
                    catch (Exception ex)
                    {
//                    Toast.makeText(ThanhtoanActivity.this,"Có lỗi không xác định vui lòng thử lại!",Toast.LENGTH_SHORT).show();
                    }
                }
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
