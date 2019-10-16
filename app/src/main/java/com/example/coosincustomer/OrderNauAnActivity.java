package com.example.coosincustomer;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;
import com.thekhaeng.pushdownanim.PushDownAnim;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;
import com.wdullaer.materialdatetimepicker.time.TimePickerDialog;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class OrderNauAnActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener,TimePickerDialog.OnTimeSetListener {

    SeekBar seekBar;
    TextView fade_text,soNguoiAn,gioUocTinh,txtTongTien;
    LinearLayout linearLayoutMon3,linearLayoutMon4;
    RelativeLayout relativeLayoutTienDiCho;
    RadioButton radioButton2mon,radioButton3mon,radioButton4mon;
    RadioGroup radioGroup,radioGroupKhauVi,radioGroupTraiCay,radioGroupDiCho;
    EditText edtMap,edtDate,edtTime,edtGhiChu,edtMon1,edtMon2,edtMon3,edtMon4,edtTienDiCho;
    int Year, Month, Day, Hour, Minute,REQUEST_CODE_MAP = 1997,tienCongLamViec = 70000,tienCongDiCho=0,tongtien=0,songuoi=2,soMon=2,tien1nguoian=10000,tien1mon=10000;
    DatePickerDialog datePickerDialog;
    TimePickerDialog timePickerDialog ;
    SimpleDateFormat simpleDateFormat;
    Calendar calendar;
    String address, day, time,khauVi="Bắc",traiCay="Không",diCho="Không",somonString="2";
    Button btnToConfirm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_nau_an);

        anhxa();

        linearLayoutMon3.setVisibility(View.GONE);
        linearLayoutMon4.setVisibility(View.GONE);
        relativeLayoutTienDiCho.setVisibility(View.GONE);

        //datepicker dialog
        calendar = Calendar.getInstance();
        Year = calendar.get(Calendar.YEAR) ;
        Month = calendar.get(Calendar.MONTH);
        Day = calendar.get(Calendar.DAY_OF_MONTH);
        simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
        getTomorrowDay();

        //tool bar action
        Toolbar toolbar = findViewById(R.id.toolbar_datNauAn);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        //permision map
        PushDownAnim.setPushDownAnimTo(edtMap).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Dexter.withActivity(OrderNauAnActivity.this)
                        .withPermission(Manifest.permission.ACCESS_FINE_LOCATION)
                        .withListener(new PermissionListener() {
                            @Override
                            public void onPermissionGranted(PermissionGrantedResponse response) {
                                Intent intent = new Intent(OrderNauAnActivity.this, MapActivity.class);
                                startActivityForResult(intent,REQUEST_CODE_MAP);
                            }

                            @Override
                            public void onPermissionDenied(PermissionDeniedResponse response) {
                                if (response.isPermanentlyDenied()){
                                    AlertDialog.Builder builder = new AlertDialog.Builder(OrderNauAnActivity.this) ;
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
                                    Toast.makeText(OrderNauAnActivity.this,"Permission Denied",Toast.LENGTH_SHORT).show();
                                }
                            }

                            @Override
                            public void onPermissionRationaleShouldBeShown(PermissionRequest permission, PermissionToken token) {
                                token.continuePermissionRequest();
                            }
                        }).check();
            }
        });

        //edt date click chon ngay
        PushDownAnim.setPushDownAnimTo(edtDate).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                datePickerDialog = DatePickerDialog.newInstance(OrderNauAnActivity.this, Year, Month, Day);
                datePickerDialog.setThemeDark(false);
                datePickerDialog.showYearPickerFirst(false);
                datePickerDialog.setTitle("Chọn ngày bắt đầu");

                // Setting Min Date to today date
                Calendar min_date_c = Calendar.getInstance();
                min_date_c.add(Calendar.DATE,1);
                datePickerDialog.setMinDate(min_date_c);

                // Setting Max Date to next 2 years
                Calendar max_date_c = Calendar.getInstance();
                max_date_c.set(Calendar.DATE, Day+7);
                datePickerDialog.setMaxDate(max_date_c);

                datePickerDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {

                    @Override
                    public void onCancel(DialogInterface dialogInterface) {

                    }
                });

                datePickerDialog.show(getFragmentManager(), "Đã chọn ngày");
            }
        });

        //edt time click chon gio
        PushDownAnim.setPushDownAnimTo(edtTime).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                timePickerDialog = TimePickerDialog.newInstance(OrderNauAnActivity.this, Hour, Minute,false );
                timePickerDialog.setThemeDark(false);
                //timePickerDialog.showYearPickerFirst(false);
                timePickerDialog.setTitle("Time Picker");

                timePickerDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {

                    @Override
                    public void onCancel(DialogInterface dialogInterface) {

                        Toast.makeText(OrderNauAnActivity.this, "Bạn chưa chọn giờ", Toast.LENGTH_SHORT).show();
                    }
                });

                timePickerDialog.show(getFragmentManager(), "Đã chọn giờ");
            }
        });

        tinhtien();
        //button tiep tuc
        PushDownAnim.setPushDownAnimTo(btnToConfirm).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Integer tiendicho=0;

                if (edtMap.getText().toString().trim().equals("")){
                    edtMap.setError("Bạn chưa chọn địa chỉ!");
                    edtMap.requestFocus();
                    Toast.makeText(getApplicationContext(),"Bạn chưa chọn địa chỉ!",Toast.LENGTH_LONG).show();
                    return;
                }else if (edtMon1.getText().toString().trim().equals("") || edtMon2.getText().toString().trim().equals("")){
                    Toast.makeText(getApplicationContext(),"Bạn chưa chọn món!",Toast.LENGTH_LONG).show();
                    return;
                }else if (relativeLayoutTienDiCho.getVisibility() == View.VISIBLE && edtTienDiCho.getText().toString().trim().equals("")){
                    Toast.makeText(getApplicationContext(),"Bạn chưa nhập tiền đi chợ!",Toast.LENGTH_LONG).show();
                    return;
                }else if (relativeLayoutTienDiCho.getVisibility() == View.VISIBLE && !edtTienDiCho.getText().toString().trim().equals("")){
                    tiendicho = Integer.valueOf(edtTienDiCho.getText().toString());
                }
                Log.d("BBB",tiendicho+"");
                Intent toCofirm = new Intent(OrderNauAnActivity.this,ConfirmNauAnActivity.class);
                toCofirm.putExtra("address",address);
                toCofirm.putExtra("day",edtDate.getText().toString());
                toCofirm.putExtra("time",edtTime.getText().toString());
                toCofirm.putExtra("ghichu",edtGhiChu.getText().toString());
                toCofirm.putExtra("songuoian",soNguoiAn.getText().toString());
                toCofirm.putExtra("somon",somonString);
                toCofirm.putExtra("tenmon",edtMon1.getText().toString()+","+edtMon2.getText().toString()+","+
                        edtMon3.getText().toString()+","+edtMon4.getText().toString());
                toCofirm.putExtra("khauvi",khauVi);
                toCofirm.putExtra("traicay",traiCay);
                toCofirm.putExtra("nguoilamdicho",diCho);
                toCofirm.putExtra("tiendicho",tiendicho);
                toCofirm.putExtra("tongtien",tongtien);
                startActivity(toCofirm);
            }
        });

        seekBar.setProgress(0);
        seekBar.setMax(6);
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                fade_text.setText(i+2+"");
                songuoi = i + 2;
                tinhtien();
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        //chon so mon an
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                if (i == R.id.btn_radio_2_mon){
                    linearLayoutMon3.setVisibility(View.GONE);
                    linearLayoutMon4.setVisibility(View.GONE);
                    soMon = 2;
                    somonString = "2";
                }
                if (i == R.id.btn_radio_3_mon){
                    linearLayoutMon3.setVisibility(View.VISIBLE);
                    linearLayoutMon4.setVisibility(View.GONE);
                    soMon = 3;
                    somonString = "3";
                }
                if (i == R.id.btn_radio_4_mon){
                    linearLayoutMon3.setVisibility(View.VISIBLE);
                    linearLayoutMon4.setVisibility(View.VISIBLE);
                    soMon = 4;
                    somonString = "4";
                }
                tinhtien();
            }
        });

        //chon khau vi
        radioGroupKhauVi.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                if (i == R.id.btn_radio_khauvi_bac){
                    khauVi = "Bắc";
                }
                if (i == R.id.btn_radio_khauvi_trung){
                    khauVi = "Trung";
                }
                if (i == R.id.btn_radio_khauvi_nam){
                    khauVi = "Nam";
                }
            }
        });

        //chon trai cay
        radioGroupTraiCay.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                if (i == R.id.btn_radio_traicay_khong){
                    traiCay = "Không";
                }
                if (i == R.id.btn_radio_traicay_co){
                    traiCay = "Có";
                }
            }
        });

        //chon di cho
        radioGroupDiCho.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                if (i == R.id.btn_radio_dicho_khong){
                    diCho = "Không";
                    relativeLayoutTienDiCho.setVisibility(View.GONE);
                    tienCongDiCho = 0;
                }
                if (i == R.id.btn_radio_dicho_co){
                    diCho = "Có";
                    relativeLayoutTienDiCho.setVisibility(View.VISIBLE);
                    tienCongDiCho = 20000;
                }
                tinhtien();
            }
        });
    }

    private void getTomorrowDay(){
        calendar.add(Calendar.DAY_OF_YEAR, 1);
        Date tomorrow = calendar.getTime();
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        String tomorrowAsString = dateFormat.format(tomorrow);
        edtDate.setText("Ngày mai"+", "+tomorrowAsString);
    }

    @Override
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
        SimpleDateFormat sdf = new SimpleDateFormat("EEEE");
        Date d_name = new Date(year,monthOfYear,dayOfMonth-1);
        String dayOfTheWeek = sdf.format(d_name);
        calendar.set(year,monthOfYear,dayOfMonth);
        if (dayOfMonth == Day+1){
            edtDate.setText( "Ngày mai, "+simpleDateFormat.format(calendar.getTime()));
            day = "Ngày mai, "+simpleDateFormat.format(calendar.getTime());
        }else{
            edtDate.setText( dayOfTheWeek+", "+simpleDateFormat.format(calendar.getTime()));
            day = dayOfTheWeek+", "+simpleDateFormat.format(calendar.getTime());
        }
    }

    @Override
    public void onTimeSet(TimePickerDialog view, int hourOfDay, int minute, int second) {
        String giouoctinh1;
        if (minute == 0){
            time = hourOfDay+" : "+"00";
            giouoctinh1 = hourOfDay-2+" : "+"00";
        }else {
            time = hourOfDay+" : "+minute;
            giouoctinh1 = hourOfDay-2+" : "+minute;
        }
        if (hourOfDay == 1 || hourOfDay == 2 || hourOfDay == 3 || hourOfDay == 4 || hourOfDay == 5 || hourOfDay == 18|| hourOfDay == 19
                || hourOfDay == 20|| hourOfDay == 21|| hourOfDay == 22 || hourOfDay == 23 ){
            Toast.makeText(getApplicationContext(),"Vui lòng chọn giờ trong khoảng 6h đến 17h!",Toast.LENGTH_LONG).show();
            edtTime.setText("7"+" : "+"00");
            gioUocTinh.setText("5"+" : "+"00");
        }
        else {
            edtTime.setText(time);
            gioUocTinh.setText(giouoctinh1);
        }
    }

    //back button
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == REQUEST_CODE_MAP && resultCode == RESULT_OK && data != null){
            address = data.getStringExtra("address");
            edtMap.setText(address);
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    //ANH XA
    private void anhxa(){
        seekBar = findViewById(R.id.seekbar);
        fade_text = findViewById(R.id.textView1);
        linearLayoutMon3 = findViewById(R.id.linear_mon3);
        linearLayoutMon4 = findViewById(R.id.linear_mon4);
        radioButton2mon = findViewById(R.id.btn_radio_2_mon);
        radioButton3mon = findViewById(R.id.btn_radio_3_mon);
        radioButton4mon = findViewById(R.id.btn_radio_4_mon);
        radioGroup = findViewById(R.id.group_radio);
        edtMap = findViewById(R.id.edt_diadiem);
        edtDate = findViewById(R.id.txt_input_date);
        edtTime = findViewById(R.id.txt_input_time);
        btnToConfirm = findViewById(R.id.btn_continue_datNauAn);
        edtGhiChu = findViewById(R.id.txt_input_ghichu);
        edtMon1 = findViewById(R.id.edt_mon1);
        edtMon2 = findViewById(R.id.edt_mon2);
        edtMon3 = findViewById(R.id.edt_mon3);
        edtMon4 = findViewById(R.id.edt_mon4);
        soNguoiAn = findViewById(R.id.textView1);
        radioGroupKhauVi = findViewById(R.id.group_radio_khauvi);
        radioGroupTraiCay = findViewById(R.id.group_radio_traicay);
        radioGroupDiCho = findViewById(R.id.group_radio_dicho);
        gioUocTinh = findViewById(R.id.txt_time_uoctinh);
        relativeLayoutTienDiCho = findViewById(R.id.relative_tiendicho);
        txtTongTien = findViewById(R.id.id_tong_tien);
        edtTienDiCho = findViewById(R.id.tien_di_cho);
    }

    //tinh tien
    private void tinhtien(){
        tongtien = tienCongLamViec + (tien1nguoian) * songuoi + (tien1mon * soMon) + tienCongDiCho;
        DecimalFormat decimalFormat = new DecimalFormat("#,###");
        String totalGiaString = decimalFormat.format(tongtien);
        txtTongTien.setText(totalGiaString+"đ");
    }
}
