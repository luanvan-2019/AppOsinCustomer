package com.example.coosincustomer;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.Manifest;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

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

public class OrderDinhKyActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    EditText edtMap;
    TextView dateStart;
    int REQUEST_CODE_MAP = 1997;
    private TextView mTxtSang, mTxtChieu, mTxtToi;
    private Spinner spinner1,spinner2;
    String buoi = "Sáng", startTime, endTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_dinh_ky);

        //anh xa
        edtMap = findViewById(R.id.edt_diadiem);
        Toolbar toolbar = findViewById(R.id.toolbar_datDinhky);
        spinner1 = findViewById(R.id.select_time_1);
        spinner2 = findViewById(R.id.select_time_2);
        mTxtSang = findViewById(R.id.txt_sang);
        mTxtChieu = findViewById(R.id.txt_chieu);
        mTxtToi = findViewById(R.id.txt_toi);
        dateStart = findViewById(R.id.txt_date_start);

        //default
        mTxtSang.setBackgroundResource(R.drawable.bg_time_lamviec);
        mTxtSang.setTextColor(Color.WHITE);

        PushDownAnim.setPushDownAnimTo(edtMap).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Dexter.withActivity(OrderDinhKyActivity.this)
                        .withPermission(Manifest.permission.ACCESS_FINE_LOCATION)
                        .withListener(new PermissionListener() {
                            @Override
                            public void onPermissionGranted(PermissionGrantedResponse response) {
                                Intent intent = new Intent(OrderDinhKyActivity.this, MapActivity.class);
                                startActivityForResult(intent,REQUEST_CODE_MAP);
                            }

                            @Override
                            public void onPermissionDenied(PermissionDeniedResponse response) {
                                if (response.isPermanentlyDenied()){
                                    AlertDialog.Builder builder = new AlertDialog.Builder(OrderDinhKyActivity.this) ;
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
                                    Toast.makeText(OrderDinhKyActivity.this,"Permission Denied",Toast.LENGTH_SHORT).show();
                                }
                            }

                            @Override
                            public void onPermissionRationaleShouldBeShown(PermissionRequest permission, PermissionToken token) {
                                token.continuePermissionRequest();
                            }
                        }).check();
            }
        });

        //set check ca lam viec
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

        //ca lam click
        mTxtSang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mTxtSang.setBackgroundResource(R.drawable.bg_time_lamviec);
                mTxtSang.setTextColor(Color.WHITE);
                mTxtToi.setTextColor(Color.BLACK);
                mTxtChieu.setTextColor(Color.BLACK);
                mTxtChieu.setBackgroundResource(0);
                mTxtToi.setBackgroundResource(0);
                buoi = "Sáng";
                ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource(OrderDinhKyActivity.this,R.array.spiner1, android.R.layout.simple_spinner_item);
                adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinner1.setAdapter(adapter1);
                spinner1.setOnItemSelectedListener(OrderDinhKyActivity.this);
                ArrayAdapter<CharSequence> adapter2 = ArrayAdapter.createFromResource(OrderDinhKyActivity.this,R.array.spiner2, android.R.layout.simple_spinner_item);
                adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinner2.setAdapter(adapter2);
                spinner2.setOnItemSelectedListener(OrderDinhKyActivity.this);
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
                buoi = "Chiều";
                ArrayAdapter<CharSequence> adapter3 = ArrayAdapter.createFromResource(OrderDinhKyActivity.this,R.array.spiner3, android.R.layout.simple_spinner_item);
                adapter3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinner1.setAdapter(adapter3);
                spinner1.setOnItemSelectedListener(OrderDinhKyActivity.this);
                ArrayAdapter<CharSequence> adapter4 = ArrayAdapter.createFromResource(OrderDinhKyActivity.this,R.array.spiner4, android.R.layout.simple_spinner_item);
                adapter4.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinner2.setAdapter(adapter4);
                spinner2.setOnItemSelectedListener(OrderDinhKyActivity.this);
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
                buoi = "Tối";
                mTxtToi.setBackgroundResource(R.drawable.bg_time_lamviec);
                ArrayAdapter<CharSequence> adapter5 = ArrayAdapter.createFromResource(OrderDinhKyActivity.this,R.array.spiner5, android.R.layout.simple_spinner_item);
                adapter5.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinner1.setAdapter(adapter5);
                spinner1.setOnItemSelectedListener(OrderDinhKyActivity.this);
                ArrayAdapter<CharSequence> adapter6 = ArrayAdapter.createFromResource(OrderDinhKyActivity.this,R.array.spiner6, android.R.layout.simple_spinner_item);
                adapter6.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinner2.setAdapter(adapter6);
                spinner2.setOnItemSelectedListener(OrderDinhKyActivity.this);
            }
        });

    }

    //set text map
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == REQUEST_CODE_MAP && resultCode == RESULT_OK && data != null){
            String address = data.getStringExtra("address");
            edtMap.setText(address);
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    //back button
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        if (spinner1.getSelectedItemPosition() > spinner2.getSelectedItemPosition() ) {
            spinner2.setSelection(spinner1.getSelectedItemPosition());
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    //get curent day
    private void getToDay(){
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_YEAR, 0);
        Date tomorrow = calendar.getTime();
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        String tomorrowAsString = dateFormat.format(tomorrow);
        dateStart.setText("Hôm nay"+", "+tomorrowAsString);
    }
}
