package com.example.coosincustomer;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.thekhaeng.pushdownanim.PushDownAnim;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ThanhtoanActivity extends AppCompatActivity {

    private ImageView iconThanhtoanTienMat,iconThanhToanMoMo,iconThanhToanPaypal;
    private RelativeLayout relavetiveTienMat,relativeLayoutMoMo,relativeLayoutPaypal;
    private Button btnXacnhanThanhtoan;
    String DiaChi,paymentType="Tiền mặt";
    Connection connect;
    String phone_num;
    Integer i;
    Double latitude = null;
    Double longitude = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thanhtoan);


        //anh xa
        Toolbar toolbar = findViewById(R.id.toolbar_hinhthuc_thanhtoan);
        iconThanhtoanTienMat = findViewById(R.id.img_payment_tienmat);
        iconThanhToanMoMo = findViewById(R.id.img_payment_momo);
        iconThanhToanPaypal = findViewById(R.id.img_payment_paypal);
        relavetiveTienMat = findViewById(R.id.relative_tienmat);
        relativeLayoutMoMo = findViewById(R.id.relative_momo);
        relativeLayoutPaypal = findViewById(R.id.relative_paypal);
        btnXacnhanThanhtoan = findViewById(R.id.btn_xacnhan_thanhtoan);

        latitude = getIntent().getDoubleExtra("latitude",0);
        longitude = getIntent().getDoubleExtra("longitude",0);
        Log.d("BBB",latitude+"");
        Log.d("BBB",longitude+"");

        //lay so dien thoai da luu khi dang nhap
        SharedPreferences SP = getApplicationContext().getSharedPreferences("PHONE",0);
        phone_num = SP.getString("phone_num",null);

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
                iconThanhtoanTienMat.setImageResource(R.drawable.ic_checked);
                relavetiveTienMat.setBackgroundResource(R.drawable.bg_layout_licked);
                iconThanhToanMoMo.setImageResource(R.drawable.ic_momo);
                relativeLayoutMoMo.setBackgroundResource(R.drawable.bg_layout1);
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


        PushDownAnim.setPushDownAnimTo(btnXacnhanThanhtoan).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DateFormat formatter = new SimpleDateFormat("HH:mm dd/MM/yyyy");
                Date date = new Date();
                String create_at = formatter.format(date);
                //cap nhat lai dia chi cho tai khoan
                if (i == 0){
                    Toast.makeText(getApplicationContext(),"Chưa chọn hình thức thanh toán",Toast.LENGTH_LONG).show();
                    return;
                }else {
                    try
                    {
                        com.example.coosincustomer.ConnectionDB conStr=new com.example.coosincustomer.ConnectionDB();
                        connect =conStr.CONN();        // Connect to database
                        if (connect == null)
                        {
                            Toast.makeText(getApplicationContext(), "Không có kết nối mạng!", Toast.LENGTH_LONG).show();
                            return;
                        }
                        else
                        {
                            String query="";

                            if (getIntent().getIntExtra("orderType",0) == 1){
                                query = "INSERT INTO ORDER_SINGLE " +
                                        "(USER_ORDER,ADDRESS_ORDER,DATE_WORK,TIME_WORK,NOTE_ORDER,SALE_CODE,TOTAL_PRICE,CREATE_AT,PAYMENT_TYPE,PAYMENT_STATUS,SEEN,TOTAL_TIME,LATITUDE,LONGITUDE)" +
                                        "VALUES('"+phone_num+"',N'"+getIntent().getStringExtra("address")+"',N'"+getIntent().getStringExtra("date")
                                        +"',N'"+getIntent().getStringExtra("ca")+"',N'"+getIntent().getStringExtra("ghichu")+"','"+
                                        getIntent().getStringExtra("makhuyenmai")+"',"+getIntent().getIntExtra("totalPrice",0)+",'"+
                                        create_at+"',N'"+paymentType+"',"+0+","+0+",'"+getIntent().getStringExtra("totalTime")+"',"+latitude+","+longitude+")";
                            }


                            if (getIntent().getIntExtra("orderType",0) == 2){
                                query = "INSERT INTO ORDER_MULTI " +
                                        "(USER_ORDER,ADDRESS_ORDER,SCHEDULE,TIME_WORK,DATE_START,DATE_END,NOTE_ORDER,SALE_CODE,TOTAL_PRICE,CREATE_AT,PAYMENT_TYPE,PAYMENT_STATUS,SEEN,LATITUDE,LONGITUDE,TOTAL_TIME,TOTAL_BUOI)" +
                                        "VALUES('"+phone_num+"',N'"+getIntent().getStringExtra("address")+"',N'"+getIntent().getStringExtra("schedule")+"'," +
                                        "N'"+getIntent().getStringExtra("timeWork")+"','"+getIntent().getStringExtra("dateStart")+"','"+getIntent().getStringExtra("dateEnd")+"'," +
                                        "'"+getIntent().getStringExtra("note")+"','',"+getIntent().getIntExtra("totalPrice",0)+",'"+create_at+"'," +
                                        "N'"+paymentType+"',"+0+","+0+","+latitude+","+longitude+",'"+getIntent().getStringExtra("totalTime")+"',"+getIntent().getIntExtra("totalBuoi",0)+")";
                            }

                            if (getIntent().getIntExtra("orderType",0) == 3){
                                query = "INSERT INTO ORDER_OVERVIEW " +
                                        "(USER_ORDER,ADDRESS_ORDER,DATE_WORK,TIME_START,AREA_TYPE,NOTE_ORDER,SALE_CODE,TOTAL_PRICE,CREATE_AT,PAYMENT_TYPE,PAYMENT_STATUS,SEEN,LATITUDE,LONGITUDE)" +
                                        "VALUES('"+phone_num+"',N'"+getIntent().getStringExtra("address")+"',N'"+getIntent().getStringExtra("dateWork")+"'," +
                                        "'"+getIntent().getStringExtra("timeStart")+"',"+getIntent().getIntExtra("areaType",0)+",N'"+getIntent().getStringExtra("note")+"',''," +
                                        "'"+getIntent().getIntExtra("totalPrice",0)+"','"+create_at+"',N'"+paymentType+"',"+0+","+0+","+latitude+","+longitude+")";
                            }

                            if (getIntent().getIntExtra("orderType",0) == 4){
                                query = "INSERT INTO ORDER_COOK " +
                                        "(USER_ORDER,ADDRESS_ORDER,DATE_WORK,TIME_WORK,PEOPLE_AMOUNT,DISH_AMOUNT,DISH_NAME,TASTE,FRUIT,MARKET,MAX_MARKET_PRICE,SALE_CODE,TOTAL_PRICE,NOTE_ORDER,CREATE_AT,PAYMENT_TYPE,LATITUDE,LONGITUDE" +
                                        ",PAYMENT_STATUS,SEEN)" +
                                        "VALUES('"+phone_num+"',N'"+getIntent().getStringExtra("address")+"',N'"+getIntent().getStringExtra("dateWork")+"'," +
                                        "'"+getIntent().getStringExtra("timeWork")+"','"+getIntent().getStringExtra("peopleAmount")+"','"+
                                        getIntent().getStringExtra("dishAmount")+"','"+getIntent().getStringExtra("dishName")+"','"+getIntent().getStringExtra("taste")+"','"+
                                        getIntent().getStringExtra("fruit")+"','"+getIntent().getStringExtra("market")+"','"+getIntent().getIntExtra("marketPrice",0)+"','','"+
                                        getIntent().getIntExtra("totalPrice",0)+"',N'"+getIntent().getStringExtra("note")+"','"+create_at+"',N'"+paymentType+"',"+0+","+0+","+latitude+","+longitude+")";
                            }
                            Statement stmt = connect.createStatement();
                            stmt.executeQuery(query);
                            connect.close();


                        }
                    }
                    catch (Exception ex)
                    {
                        Intent  intent = new Intent(ThanhtoanActivity.this,OrderSuccessActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        intent.putExtra("tongtien",getIntent().getIntExtra("totalPrice",0));
                        startActivity(intent);
                        try {
                            //cap nhat lai dia chi
                            String query = "UPDATE CUSTOMER SET ADDRESS = N'" + getIntent().getStringExtra("address") +"' WHERE PHONE_NUM='"+phone_num+"'";
                            Statement stmt = connect.createStatement();
                            stmt.executeQuery(query);
                        } catch (SQLException e) {

                        }
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
