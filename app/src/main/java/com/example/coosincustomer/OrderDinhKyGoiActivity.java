package com.example.coosincustomer;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.thekhaeng.pushdownanim.PushDownAnim;

import java.text.DecimalFormat;

public class OrderDinhKyGoiActivity extends AppCompatActivity {

    Button btnToConfirm;
    ImageView checkBox1thang,checkBox2thang,checkBox3thang,checkBox6thang,checkBox12thang;
    RelativeLayout relativeLayout1Thang,relativeLayout2Thang,relativeLayout3Thang,relativeLayout6Thang,relativeLayout12Thang;
    Integer selected,buoi1thang,buoi2thang,buoi3thang,buoi6thang,buoi12thang,totalGia1thang,totalGia2thang,totalGia3thang,
            totalGia6thang,totalGia12thang,tietkiem2thang,tietkiem3thang,tietkiem6thang,tietkiem12thang,
            select1thang=0,select2thang=0,select3thang=0,select6thang=0,select12thang=0;
    String thu,startTime,endtime1thang,endtime2thang,endtime3thang,endtime6thang,endtime12thang,gio1buoi,address,time,ghichu;
    TextView txtBuoi1Thang,txtBuoi2Thang,txtBuoi3Thang,txtBuoi6Thang,txtBuoi12Thang,
            txtTongTien1Thang,txtTongTien2Thang,txtTongTien3Thang,txtTongTien6Thang,txtTongTien12Thang,
            txtTietKiem2Thang,txtTietKiem3Thang,txtTietKiem6Thang,txtTietKiem12Thang;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_dinh_ky_goi);

        //anh xa
        anhXa();

        //kiem tra chon goi
        selected = 0;

        //lay du lieu tu activity order dinh ky
        thu = getIntent().getStringExtra("thu");
        startTime = getIntent().getStringExtra("startTime");
        endtime1thang = getIntent().getStringExtra("endtime1thang");
        endtime2thang = getIntent().getStringExtra("endtime2thang");
        endtime3thang = getIntent().getStringExtra("endtime3thang");
        endtime6thang = getIntent().getStringExtra("endtime6thang");
        endtime12thang = getIntent().getStringExtra("endtime12thang");
        gio1buoi = getIntent().getStringExtra("gio1buoi");
        address = getIntent().getStringExtra("address");
        time = getIntent().getStringExtra("ca");
        ghichu = getIntent().getStringExtra("ghichu");

        buoi1thang = getIntent().getIntExtra("buoi1thang",0);
        buoi2thang = getIntent().getIntExtra("buoi2thang",0);
        buoi3thang = getIntent().getIntExtra("buoi3thang",0);
        buoi6thang = getIntent().getIntExtra("buoi6thang",0);
        buoi12thang = getIntent().getIntExtra("buoi12thang",0);
        totalGia1thang = getIntent().getIntExtra("totalGia1thang",0);
        totalGia2thang = getIntent().getIntExtra("totalGia2thang",0);
        totalGia3thang = getIntent().getIntExtra("totalGia3thang",0);
        totalGia6thang = getIntent().getIntExtra("totalGia6thang",0);
        totalGia12thang = getIntent().getIntExtra("totalGia12thang",0);
        tietkiem2thang = getIntent().getIntExtra("tietkiem2thang",0);
        tietkiem3thang = getIntent().getIntExtra("tietkiem3thang",0);
        tietkiem6thang = getIntent().getIntExtra("tietkiem6thang",0);
        tietkiem12thang = getIntent().getIntExtra("tietkiem12thang",0);

        //set text
        setText();

        //back button
        Toolbar toolbar = findViewById(R.id.toolbar_datDinhky_chongoi);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        PushDownAnim.setPushDownAnimTo(btnToConfirm).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (selected == 0){
                    Toast.makeText(OrderDinhKyGoiActivity.this,"Bạn chưa chọn gói dịch vụ!",Toast.LENGTH_LONG).show();
                }else {
                    Intent toConfirm = new Intent(OrderDinhKyGoiActivity.this,ConfirmDinhKyActivity.class);
                    toConfirm.putExtra("address",address);
                    toConfirm.putExtra("thu",thu);
                    toConfirm.putExtra("startTime",startTime);
                    toConfirm.putExtra("ca",time);
                    toConfirm.putExtra("sogiolam",gio1buoi);
                    toConfirm.putExtra("ghichu",ghichu);
                    toConfirm.putExtra("latitude",getIntent().getDoubleExtra("latitude",0));
                    toConfirm.putExtra("longitude",getIntent().getDoubleExtra("longitude",0));
                    if (select1thang == 1){
                        toConfirm.putExtra("endtime",endtime1thang);
                        toConfirm.putExtra("tongtien",totalGia1thang);
                        toConfirm.putExtra("sobuoilam",buoi1thang);
                    }
                    if (select2thang == 1){
                        toConfirm.putExtra("endtime",endtime2thang);
                        toConfirm.putExtra("tongtien",totalGia2thang);
                        toConfirm.putExtra("sobuoilam",buoi2thang);
                    }
                    if (select3thang == 1){
                        toConfirm.putExtra("endtime",endtime3thang);
                        toConfirm.putExtra("tongtien",totalGia3thang);
                        toConfirm.putExtra("sobuoilam",buoi3thang);
                    }
                    if (select6thang == 1){
                        toConfirm.putExtra("endtime",endtime6thang);
                        toConfirm.putExtra("tongtien",totalGia6thang);
                        toConfirm.putExtra("sobuoilam",buoi6thang);
                    }
                    if (select12thang == 1){
                        toConfirm.putExtra("endtime",endtime12thang);
                        toConfirm.putExtra("tongtien",totalGia12thang);
                        toConfirm.putExtra("sobuoilam",buoi12thang);
                    }
                    startActivity(toConfirm);
                }
            }
        });
        //1 thang
        PushDownAnim.setPushDownAnimTo(relativeLayout1Thang).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selected = 1;
                select1thang = 1;
                select2thang = 0;
                select3thang = 0;
                select6thang = 0;
                select12thang = 0;
                checkBox1thang.setImageResource(R.drawable.ic_checked);
                relativeLayout1Thang.setBackgroundResource(R.drawable.bg_layout_licked);
                checkBox2thang.setImageResource(R.drawable.ic_checkbox_uncheck_circle);
                relativeLayout2Thang.setBackgroundResource(R.drawable.bg_layout1);
                checkBox3thang.setImageResource(R.drawable.ic_checkbox_uncheck_circle);
                relativeLayout3Thang.setBackgroundResource(R.drawable.bg_layout1);
                checkBox6thang.setImageResource(R.drawable.ic_checkbox_uncheck_circle);
                relativeLayout6Thang.setBackgroundResource(R.drawable.bg_layout1);
                checkBox12thang.setImageResource(R.drawable.ic_checkbox_uncheck_circle);
                relativeLayout12Thang.setBackgroundResource(R.drawable.bg_layout1);
            }
        });

        //2 thang
        PushDownAnim.setPushDownAnimTo(relativeLayout2Thang).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selected = 1;
                select1thang = 0;
                select2thang = 1;
                select3thang = 0;
                select6thang = 0;
                select12thang = 0;
                checkBox1thang.setImageResource(R.drawable.ic_checkbox_uncheck_circle);
                relativeLayout1Thang.setBackgroundResource(R.drawable.bg_layout1);
                checkBox2thang.setImageResource(R.drawable.ic_checked);
                relativeLayout2Thang.setBackgroundResource(R.drawable.bg_layout_licked);
                checkBox3thang.setImageResource(R.drawable.ic_checkbox_uncheck_circle);
                relativeLayout3Thang.setBackgroundResource(R.drawable.bg_layout1);
                checkBox6thang.setImageResource(R.drawable.ic_checkbox_uncheck_circle);
                relativeLayout6Thang.setBackgroundResource(R.drawable.bg_layout1);
                checkBox12thang.setImageResource(R.drawable.ic_checkbox_uncheck_circle);
                relativeLayout12Thang.setBackgroundResource(R.drawable.bg_layout1);
            }
        });

        //3thang
        PushDownAnim.setPushDownAnimTo(relativeLayout3Thang).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selected = 1;
                select1thang = 0;
                select2thang = 0;
                select3thang = 1;
                select6thang = 0;
                select12thang = 0;
                checkBox1thang.setImageResource(R.drawable.ic_checkbox_uncheck_circle);
                relativeLayout1Thang.setBackgroundResource(R.drawable.bg_layout1);
                checkBox2thang.setImageResource(R.drawable.ic_checkbox_uncheck_circle);
                relativeLayout2Thang.setBackgroundResource(R.drawable.bg_layout1);
                checkBox3thang.setImageResource(R.drawable.ic_checked);
                relativeLayout3Thang.setBackgroundResource(R.drawable.bg_layout_licked);
                checkBox6thang.setImageResource(R.drawable.ic_checkbox_uncheck_circle);
                relativeLayout6Thang.setBackgroundResource(R.drawable.bg_layout1);
                checkBox12thang.setImageResource(R.drawable.ic_checkbox_uncheck_circle);
                relativeLayout12Thang.setBackgroundResource(R.drawable.bg_layout1);
            }
        });

        //6thang
        PushDownAnim.setPushDownAnimTo(relativeLayout6Thang).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selected = 1;
                select1thang = 0;
                select2thang = 0;
                select3thang = 0;
                select6thang = 1;
                select12thang = 0;
                checkBox1thang.setImageResource(R.drawable.ic_checkbox_uncheck_circle);
                relativeLayout1Thang.setBackgroundResource(R.drawable.bg_layout1);
                checkBox2thang.setImageResource(R.drawable.ic_checkbox_uncheck_circle);
                relativeLayout2Thang.setBackgroundResource(R.drawable.bg_layout1);
                checkBox3thang.setImageResource(R.drawable.ic_checkbox_uncheck_circle);
                relativeLayout3Thang.setBackgroundResource(R.drawable.bg_layout1);
                checkBox6thang.setImageResource(R.drawable.ic_checked);
                relativeLayout6Thang.setBackgroundResource(R.drawable.bg_layout_licked);
                checkBox12thang.setImageResource(R.drawable.ic_checkbox_uncheck_circle);
                relativeLayout12Thang.setBackgroundResource(R.drawable.bg_layout1);
            }
        });

        //12thang
        PushDownAnim.setPushDownAnimTo(relativeLayout12Thang).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selected = 1;
                select1thang = 0;
                select2thang = 0;
                select3thang = 0;
                select6thang = 0;
                select12thang = 1;
                checkBox1thang.setImageResource(R.drawable.ic_checkbox_uncheck_circle);
                relativeLayout1Thang.setBackgroundResource(R.drawable.bg_layout1);
                checkBox2thang.setImageResource(R.drawable.ic_checkbox_uncheck_circle);
                relativeLayout2Thang.setBackgroundResource(R.drawable.bg_layout1);
                checkBox3thang.setImageResource(R.drawable.ic_checkbox_uncheck_circle);
                relativeLayout3Thang.setBackgroundResource(R.drawable.bg_layout1);
                checkBox6thang.setImageResource(R.drawable.ic_checkbox_uncheck_circle);
                relativeLayout6Thang.setBackgroundResource(R.drawable.bg_layout1);
                checkBox12thang.setImageResource(R.drawable.ic_checked);
                relativeLayout12Thang.setBackgroundResource(R.drawable.bg_layout_licked);
            }
        });
    }

    //back button
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    //anh xa
    private void anhXa(){
        btnToConfirm = findViewById(R.id.btn_continue_toConfirm_dinhky);
        checkBox1thang = findViewById(R.id.checkbox_1thang);
        checkBox2thang = findViewById(R.id.checkbox_2thang);
        checkBox3thang = findViewById(R.id.checkbox_3thang);
        checkBox6thang = findViewById(R.id.checkbox_6thang);
        checkBox12thang = findViewById(R.id.checkbox_12thang);
        relativeLayout1Thang = findViewById(R.id.relative_1thang);
        relativeLayout2Thang = findViewById(R.id.relative_2thang);
        relativeLayout3Thang = findViewById(R.id.relative_3thang);
        relativeLayout6Thang = findViewById(R.id.relative_6thang);
        relativeLayout12Thang = findViewById(R.id.relative_12thang);
        txtBuoi1Thang = findViewById(R.id.tongbuoi_1thang);
        txtBuoi2Thang = findViewById(R.id.tongbuoi_2thang);
        txtBuoi3Thang = findViewById(R.id.tongbuoi_3thang);
        txtBuoi6Thang = findViewById(R.id.tongbuoi_6thang);
        txtBuoi12Thang = findViewById(R.id.tongbuoi_12thang);
        txtTongTien1Thang = findViewById(R.id.tongtien_1thang);
        txtTongTien2Thang = findViewById(R.id.tongtien_2thang);
        txtTongTien3Thang = findViewById(R.id.tongtien_3thang);
        txtTongTien6Thang = findViewById(R.id.tongtien_6thang);
        txtTongTien12Thang = findViewById(R.id.tongtien_12thang);
        txtTietKiem2Thang = findViewById(R.id.txt_tiet_kiem_2thang);
        txtTietKiem3Thang = findViewById(R.id.txt_tiet_kiem_3thang);
        txtTietKiem6Thang = findViewById(R.id.txt_tiet_kiem_6thang);
        txtTietKiem12Thang = findViewById(R.id.txt_tiet_kiem_12thang);
    }

    private void setText(){
        txtBuoi1Thang.setText("("+buoi1thang+" buổi)");
        txtBuoi2Thang.setText("("+buoi2thang+" buổi)");
        txtBuoi3Thang.setText("("+buoi3thang+" buổi)");
        txtBuoi6Thang.setText("("+buoi6thang+" buổi)");
        txtBuoi12Thang.setText("("+buoi12thang+" buổi)");

        //set kieu cho tien
        DecimalFormat decimalFormat = new DecimalFormat("#,###");
        String tongtien1thang = decimalFormat.format(totalGia1thang);
        String tongtien2thang = decimalFormat.format(totalGia2thang);
        String tongtien3thang = decimalFormat.format(totalGia3thang);
        String tongtien6thang = decimalFormat.format(totalGia6thang);
        String tongtien12thang = decimalFormat.format(totalGia12thang);
        String TK2thang = decimalFormat.format(tietkiem2thang);
        String TK3thang = decimalFormat.format(tietkiem3thang);
        String TK6thang = decimalFormat.format(tietkiem6thang);
        String TK12thang = decimalFormat.format(tietkiem12thang);

        txtTongTien1Thang.setText(tongtien1thang+" đ");
        txtTongTien2Thang.setText(tongtien2thang+" đ");
        txtTongTien3Thang.setText(tongtien3thang+" đ");
        txtTongTien6Thang.setText(tongtien6thang+" đ");
        txtTongTien12Thang.setText(tongtien12thang+" đ");

        txtTietKiem2Thang.setText("Tiết kiệm "+TK2thang+"đ");
        txtTietKiem3Thang.setText("Tiết kiệm "+TK3thang+"đ");
        txtTietKiem6Thang.setText("Tiết kiệm "+TK6thang+"đ");
        txtTietKiem12Thang.setText("Tiết kiệm "+TK12thang+"đ");
    }
}
