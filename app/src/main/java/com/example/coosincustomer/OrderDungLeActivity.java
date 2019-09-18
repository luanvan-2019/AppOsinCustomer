package com.example.coosincustomer;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;
import com.thekhaeng.pushdownanim.PushDownAnim;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class OrderDungLeActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private EditText date, edtMap;
    private TextView mTxtSang, mTxtChieu, mTxtToi, mTxtTangGia,mTxtTotalTime,mtxtTongTien;
    private Spinner spinner1,spinner2;
    private Button btnContinueToConfirm;
    private Double totalGia;
    private Integer dongia = 50000;
    DatePickerDialog datePickerDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_dung_le);

        //anh xa
        spinner1 = findViewById(R.id.select_time_1);
        spinner2 = findViewById(R.id.select_time_2);
        mTxtSang = findViewById(R.id.txt_sang);
        mTxtChieu = findViewById(R.id.txt_chieu);
        mTxtToi = findViewById(R.id.txt_toi);
        mTxtTotalTime = findViewById(R.id.txt_total_time);
        mtxtTongTien = findViewById(R.id.id_tong_tien);
        mTxtTangGia = findViewById(R.id.txt_dichvu_gap);
        edtMap = findViewById(R.id.edt_diadiem);
        mTxtSang.setBackgroundResource(R.drawable.bg_time_lamviec);
        mTxtSang.setTextColor(Color.WHITE);
        Toolbar toolbar = findViewById(R.id.toolbar_datDungle);
        btnContinueToConfirm = findViewById(R.id.btn_continue_datDungle);

        //permision map
        edtMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Dexter.withActivity(OrderDungLeActivity.this)
                        .withPermission(Manifest.permission.ACCESS_FINE_LOCATION)
                        .withListener(new PermissionListener() {
                            @Override
                            public void onPermissionGranted(PermissionGrantedResponse response) {
                                startActivity(new Intent(OrderDungLeActivity.this, MapActivity.class));
                            }

                            @Override
                            public void onPermissionDenied(PermissionDeniedResponse response) {
                                if (response.isPermanentlyDenied()){
                                    AlertDialog.Builder builder = new AlertDialog.Builder(OrderDungLeActivity.this) ;
                                    builder.setTitle("Permission Denied").setMessage("Permission to access device location is permanently denied. You need to go to setting to allow the permission.  ")
                                            .setNegativeButton("Cancel",null)
                                            .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialogInterface, int i) {
                                            Intent intent = new Intent();
                                            intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                                            intent.setData(Uri.fromParts("package",getPackageName(),null));
                                        }
                                    })
                                     .show();
                                } else {
                                    Toast.makeText(OrderDungLeActivity.this,"Permission Denied",Toast.LENGTH_SHORT).show();
                                }
                            }

                            @Override
                            public void onPermissionRationaleShouldBeShown(PermissionRequest permission, PermissionToken token) {
                                token.continuePermissionRequest();
                            }
                        }).check();
            }
        });

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,R.array.spiner1, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner1.setAdapter(adapter);
        spinner1.setOnItemSelectedListener(this);
        ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource(this,R.array.spiner2, android.R.layout.simple_spinner_item);
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner2.setAdapter(adapter1);
        spinner2.setOnItemSelectedListener(this);

        //tool bar action
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        //button tiep tuc to xac nhan thong tin
        PushDownAnim.setPushDownAnimTo(btnContinueToConfirm).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(OrderDungLeActivity.this, ConfirmActivity.class);
                startActivity(intent);
            }
        });

        //calendar
        date = findViewById(R.id.txt_input_date);
        getTomorrowDay();
        PushDownAnim.setPushDownAnimTo(date).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar calendar = Calendar.getInstance();
                final int day = calendar.get(Calendar.DAY_OF_MONTH);
                final int month = calendar.get(Calendar.MONTH);
                final int year = calendar.get(Calendar.YEAR);
                datePickerDialog = new DatePickerDialog(OrderDungLeActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                        if (i1 == 12) i1 = 1;
                        else i1 = i1+1;
                        SimpleDateFormat sdf = new SimpleDateFormat("EEEE");
                        Date d_name = new Date(i,i1,i2-3);
                        String dayOfTheWeek = sdf.format(d_name);
                        if (day == i2){
                            date.setText("Hôm nay"+", "+i2+"/"+i1+"/"+i);
                            mTxtTangGia.setText("Giá tăng do đặt dịch vụ gấp!");
                            dongia = dongia + 10000;
                            String currentTime = new SimpleDateFormat("HH:mm:ss", Locale.getDefault()).format(new Date());
                            Integer currentTimeInt = Integer.valueOf(currentTime.substring(0,2));
                            if (currentTimeInt < 12){
                                mTxtSang.setClickable(false);
                                mTxtSang.setBackgroundResource(0);
                                mTxtToi.setBackgroundResource(0);
                                mTxtChieu.setBackgroundResource(R.drawable.bg_time_lamviec);
                                mTxtChieu.setTextColor(Color.WHITE);
                                mTxtToi.setTextColor(Color.BLACK);
                                mTxtSang.setTextColor(Color.BLACK);
                                ArrayAdapter<CharSequence> adapter3 = ArrayAdapter.createFromResource(OrderDungLeActivity.this,R.array.spiner3, android.R.layout.simple_spinner_item);
                                adapter3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                spinner1.setAdapter(adapter3);
                                spinner1.setOnItemSelectedListener(OrderDungLeActivity.this);
                                ArrayAdapter<CharSequence> adapter4 = ArrayAdapter.createFromResource(OrderDungLeActivity.this,R.array.spiner4, android.R.layout.simple_spinner_item);
                                adapter4.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                spinner2.setAdapter(adapter4);
                                spinner2.setOnItemSelectedListener(OrderDungLeActivity.this);
                            }
                            if (currentTimeInt < 17 && currentTimeInt >= 12){
                                mTxtChieu.setClickable(false);
                                mTxtSang.setClickable(false);
                                mTxtChieu.setBackgroundResource(0);
                                mTxtSang.setBackgroundResource(0);
                                mTxtToi.setBackgroundResource(R.drawable.bg_time_lamviec);
                                mTxtToi.setTextColor(Color.WHITE);
                                mTxtChieu.setTextColor(Color.BLACK);
                                mTxtSang.setTextColor(Color.BLACK);
                                ArrayAdapter<CharSequence> adapter5 = ArrayAdapter.createFromResource(OrderDungLeActivity.this,R.array.spiner5, android.R.layout.simple_spinner_item);
                                adapter5.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                spinner1.setAdapter(adapter5);
                                spinner1.setOnItemSelectedListener(OrderDungLeActivity.this);
                                ArrayAdapter<CharSequence> adapter6 = ArrayAdapter.createFromResource(OrderDungLeActivity.this,R.array.spiner6, android.R.layout.simple_spinner_item);
                                adapter6.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                spinner2.setAdapter(adapter6);
                                spinner2.setOnItemSelectedListener(OrderDungLeActivity.this);

                            }
                            if (currentTimeInt > 17 ) {
                                date.setText("Ngày mai"+", "+i2+"/"+i1+"/"+i);
                            }
                        }
                        if (day+1 == i2) {
                            date.setText("Ngày mai"+", "+i2+"/"+i1+"/"+i);
                            dongia = 50000;
                            mTxtChieu.setClickable(true);
                            mTxtSang.setClickable(true);
                            mTxtToi.setClickable(true);
                            mTxtTangGia.setText("");
                            mTxtSang.setBackgroundResource(R.drawable.bg_time_lamviec);
                            mTxtSang.setTextColor(Color.WHITE);
                            mTxtToi.setTextColor(Color.BLACK);
                            mTxtChieu.setTextColor(Color.BLACK);
                            mTxtChieu.setBackgroundResource(0);
                            mTxtToi.setBackgroundResource(0);
                            ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource(OrderDungLeActivity.this,R.array.spiner1, android.R.layout.simple_spinner_item);
                            adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                            spinner1.setAdapter(adapter1);
                            spinner1.setOnItemSelectedListener(OrderDungLeActivity.this);
                            ArrayAdapter<CharSequence> adapter2 = ArrayAdapter.createFromResource(OrderDungLeActivity.this,R.array.spiner2, android.R.layout.simple_spinner_item);
                            adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                            spinner2.setAdapter(adapter2);
                            spinner2.setOnItemSelectedListener(OrderDungLeActivity.this);
                        }
                        if (day != i2 && day+1 != i2) {
                            date.setText(dayOfTheWeek+", "+i2+"/"+i1+"/"+i);
                            mTxtTangGia.setText("");
                            mTxtChieu.setClickable(true);
                            mTxtSang.setClickable(true);
                            mTxtToi.setClickable(true);
                            mTxtSang.setBackgroundResource(R.drawable.bg_time_lamviec);
                            mTxtSang.setTextColor(Color.WHITE);
                            mTxtToi.setTextColor(Color.BLACK);
                            mTxtChieu.setTextColor(Color.BLACK);
                            mTxtChieu.setBackgroundResource(0);
                            mTxtToi.setBackgroundResource(0);
                            ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource(OrderDungLeActivity.this,R.array.spiner1, android.R.layout.simple_spinner_item);
                            adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                            spinner1.setAdapter(adapter1);
                            spinner1.setOnItemSelectedListener(OrderDungLeActivity.this);
                            ArrayAdapter<CharSequence> adapter2 = ArrayAdapter.createFromResource(OrderDungLeActivity.this,R.array.spiner2, android.R.layout.simple_spinner_item);
                            adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                            spinner2.setAdapter(adapter2);
                            spinner2.setOnItemSelectedListener(OrderDungLeActivity.this);
                        }

                    }
                },year,month,day);
                datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
                datePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis() - 1000 + (1000*60*60*24*7));
                datePickerDialog.show();
            }
        });

        mTxtSang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mTxtSang.setBackgroundResource(R.drawable.bg_time_lamviec);
                mTxtSang.setTextColor(Color.WHITE);
                mTxtToi.setTextColor(Color.BLACK);
                mTxtChieu.setTextColor(Color.BLACK);
                mTxtChieu.setBackgroundResource(0);
                mTxtToi.setBackgroundResource(0);
                ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource(OrderDungLeActivity.this,R.array.spiner1, android.R.layout.simple_spinner_item);
                adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinner1.setAdapter(adapter1);
                spinner1.setOnItemSelectedListener(OrderDungLeActivity.this);
                ArrayAdapter<CharSequence> adapter2 = ArrayAdapter.createFromResource(OrderDungLeActivity.this,R.array.spiner2, android.R.layout.simple_spinner_item);
                adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinner2.setAdapter(adapter2);
                spinner2.setOnItemSelectedListener(OrderDungLeActivity.this);
            }
        });
        mTxtChieu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mTxtSang.setBackgroundResource(0);
                mTxtSang.setTextColor(Color.BLACK);
                mTxtToi.setTextColor(Color.BLACK);
                mTxtChieu.setTextColor(Color.WHITE);
                mTxtChieu.setBackgroundResource(R.drawable.bg_time_lamviec);
                mTxtToi.setBackgroundResource(0);
                ArrayAdapter<CharSequence> adapter3 = ArrayAdapter.createFromResource(OrderDungLeActivity.this,R.array.spiner3, android.R.layout.simple_spinner_item);
                adapter3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinner1.setAdapter(adapter3);
                spinner1.setOnItemSelectedListener(OrderDungLeActivity.this);
                ArrayAdapter<CharSequence> adapter4 = ArrayAdapter.createFromResource(OrderDungLeActivity.this,R.array.spiner4, android.R.layout.simple_spinner_item);
                adapter4.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinner2.setAdapter(adapter4);
                spinner2.setOnItemSelectedListener(OrderDungLeActivity.this);
            }
        });
        mTxtToi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mTxtSang.setBackgroundResource(0);
                mTxtChieu.setBackgroundResource(0);
                mTxtSang.setTextColor(Color.BLACK);
                mTxtToi.setTextColor(Color.WHITE);
                mTxtChieu.setTextColor(Color.BLACK);
                mTxtToi.setBackgroundResource(R.drawable.bg_time_lamviec);
                ArrayAdapter<CharSequence> adapter5 = ArrayAdapter.createFromResource(OrderDungLeActivity.this,R.array.spiner5, android.R.layout.simple_spinner_item);
                adapter5.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinner1.setAdapter(adapter5);
                spinner1.setOnItemSelectedListener(OrderDungLeActivity.this);
                ArrayAdapter<CharSequence> adapter6 = ArrayAdapter.createFromResource(OrderDungLeActivity.this,R.array.spiner6, android.R.layout.simple_spinner_item);
                adapter6.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinner2.setAdapter(adapter6);
                spinner2.setOnItemSelectedListener(OrderDungLeActivity.this);
            }
        });
    }

    private void getTomorrowDay(){
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_YEAR, 1);
        Date tomorrow = calendar.getTime();
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        String tomorrowAsString = dateFormat.format(tomorrow);
        date.setText("Ngày mai"+", "+tomorrowAsString);
    }

    //back button
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int i, long l) {

        double totalTime = 0;
        double dungcu = 15500;
        if (spinner1.getSelectedItemPosition() > spinner2.getSelectedItemPosition() ) {
            spinner2.setSelection(spinner1.getSelectedItemPosition());
        }
        if (spinner1.getSelectedItemPosition() == spinner2.getSelectedItemPosition()){
            totalTime = 2;
        }
        if (spinner1.getSelectedItemPosition() == 0 ) {
            if (spinner2.getSelectedItemPosition() == 0) totalTime = 2;
            if (spinner2.getSelectedItemPosition() == 1) totalTime = 2.5;
            if (spinner2.getSelectedItemPosition() == 2) totalTime = 3;
            if (spinner2.getSelectedItemPosition() == 3) totalTime = 3.5;
            if (spinner2.getSelectedItemPosition() == 4) totalTime = 4;
        }
        if (spinner1.getSelectedItemPosition() == 1 ) {
            if (spinner2.getSelectedItemPosition() == 2) totalTime = 2.5;
            if (spinner2.getSelectedItemPosition() == 3) totalTime = 3;
            if (spinner2.getSelectedItemPosition() == 4) totalTime = 3.5;
        }
        if (spinner1.getSelectedItemPosition() == 2 ) {
            if (spinner2.getSelectedItemPosition() == 3) totalTime = 2.5;
            if (spinner2.getSelectedItemPosition() == 4) totalTime = 3;
        }
        if (spinner1.getSelectedItemPosition() == 3 ) {
            if (spinner2.getSelectedItemPosition() == 4) totalTime = 2.5;
        }
        totalGia =  dongia * totalTime + dungcu;
        DecimalFormat decimalFormat = new DecimalFormat("#,###");
        String totalGiaString = decimalFormat.format(totalGia);

        String totalTimeString = Double.toString(totalTime);
        if (totalTimeString.substring(2,3).equals("0")){
            mTxtTotalTime.setText(totalTimeString.substring(0,1)+"h");
            mtxtTongTien.setText(totalGiaString+"đ");
        }
        else mTxtTotalTime.setText(totalTimeString+"h");
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
