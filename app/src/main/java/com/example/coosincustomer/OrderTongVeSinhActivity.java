package com.example.coosincustomer;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
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
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;
import com.wdullaer.materialdatetimepicker.time.TimePickerDialog;
import com.wdullaer.materialdatetimepicker.time.Timepoint;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class OrderTongVeSinhActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener,
        DatePickerDialog.OnDateSetListener,TimePickerDialog.OnTimeSetListener  {

    String[] users = { "Tối đa 80m\u00B2    2 người     4h", "Tối đa 100m\u00B2    3 người     3h",
            "Tối đa 150m\u00B2    3 người     4h" };
    EditText edtDate,edtTime,edtMap,edtGhiChu;
    TextView txtTotalTime, txtTotalGia;
    Button btnToConfirm;
    DatePickerDialog datePickerDialog;
    TimePickerDialog timePickerDialog ;
    Calendar calendar;
    int Year, Month, Day, Hour, Minute,REQUEST_CODE_MAP = 1997;
    SimpleDateFormat simpleDateFormat;
    String address, day, time, dientich,tonggio,tongnguoi;
    Spinner spinner;
    Integer totalGiaTongVS,totalTime,phiDungCu = 50000,gia80m2=650000, gia100m2=800000,gia150m2=1120000;
    Double latitude = null;
    Double longitude = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_tong_ve_sinh);

        //anh xa
        edtDate = findViewById(R.id.txt_input_date);
        edtTime = findViewById(R.id.txt_input_time);
        edtMap = findViewById(R.id.edt_diadiem);
        txtTotalTime = findViewById(R.id.txt_total_time);
        txtTotalGia = findViewById(R.id.id_tong_tien);
        btnToConfirm = findViewById(R.id.btn_continue_datTongVS);
        edtGhiChu= findViewById(R.id.txt_input_ghichu);

        //tool bar action
        Toolbar toolbar = findViewById(R.id.toolbar_datTongVS);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        //datepicker dialog
        calendar = Calendar.getInstance();
        Year = calendar.get(Calendar.YEAR) ;
        Month = calendar.get(Calendar.MONTH);
        Day = calendar.get(Calendar.DAY_OF_MONTH);

        simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");

        //chon ngay
        getTomorrowDay();

        //spiner
        spinner = findViewById(R.id.spinner1);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, users);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);

        //chon dia diem
        PushDownAnim.setPushDownAnimTo(edtMap).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Dexter.withActivity(OrderTongVeSinhActivity.this)
                        .withPermission(Manifest.permission.ACCESS_FINE_LOCATION)
                        .withListener(new PermissionListener() {
                            @Override
                            public void onPermissionGranted(PermissionGrantedResponse response) {
                                Intent intent = new Intent(OrderTongVeSinhActivity.this, MapActivity.class);
                                startActivityForResult(intent,REQUEST_CODE_MAP);
                            }

                            @Override
                            public void onPermissionDenied(PermissionDeniedResponse response) {
                                if (response.isPermanentlyDenied()){
                                    AlertDialog.Builder builder = new AlertDialog.Builder(OrderTongVeSinhActivity.this) ;
                                    builder.setTitle("Quyền vị trí đã bị từ chối").setMessage("Quyền vị trí đã bị từ chối. Bạn cần vào cài đặt của thiết bị để cấp lại quyền.")
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
                                    Toast.makeText(OrderTongVeSinhActivity.this,"Quyền vị trí đã bị từ chối!",Toast.LENGTH_SHORT).show();
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
                datePickerDialog = DatePickerDialog.newInstance(OrderTongVeSinhActivity.this, Year, Month, Day);
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
                timePickerDialog = TimePickerDialog.newInstance(OrderTongVeSinhActivity.this, Hour, Minute,false );
                timePickerDialog.setThemeDark(false);
                //timePickerDialog.showYearPickerFirst(false);
                timePickerDialog.setTitle("Time Picker");

                timePickerDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {

                    @Override
                    public void onCancel(DialogInterface dialogInterface) {

                        Toast.makeText(OrderTongVeSinhActivity.this, "Bạn chưa chọn giờ", Toast.LENGTH_SHORT).show();
                    }
                });

                timePickerDialog.show(getFragmentManager(), "Đã chọn giờ");
            }
        });

        //button tiep tuc
        PushDownAnim.setPushDownAnimTo(btnToConfirm).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (edtMap.getText().toString().trim().equals("")){
                    edtMap.setError("Bạn chưa chọn địa chỉ");
                    Toast.makeText(getApplicationContext(),"Bạn chưa chọn địa chỉ",Toast.LENGTH_LONG).show();
                }else {
                    Intent toCofirm = new Intent(OrderTongVeSinhActivity.this,ConfirmTongVeSinhActivity.class);
                    toCofirm.putExtra("address",address);
                    toCofirm.putExtra("day",edtDate.getText().toString());
                    toCofirm.putExtra("time",edtTime.getText().toString());
                    toCofirm.putExtra("ghichu",edtGhiChu.getText().toString());
                    toCofirm.putExtra("tongtien",totalGiaTongVS);
                    toCofirm.putExtra("dientich",dientich);
                    toCofirm.putExtra("tongnguoi",tongnguoi);
                    toCofirm.putExtra("tonggio",tonggio);
                    toCofirm.putExtra("phidungcu",phiDungCu);
                    toCofirm.putExtra("latitude",latitude);
                    toCofirm.putExtra("longitude",longitude);
                    startActivity(toCofirm);
                }
            }
        });
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        TextView textView = (TextView)adapterView.getChildAt(0);
        textView.setTextColor(getResources().getColor(R.color.enable));
        if (spinner.getSelectedItemPosition() == 0){
            totalGiaTongVS = gia80m2 + phiDungCu;
            DecimalFormat decimalFormat = new DecimalFormat("#,###");
            String totalGiaString = decimalFormat.format(totalGiaTongVS);
            txtTotalGia.setText(totalGiaString+"đ");
            txtTotalTime.setText("4h");
            dientich = "80m\u00B2";
            tonggio = "4h";
            tongnguoi = "2";
        }else if (spinner.getSelectedItemPosition() == 1){
            totalGiaTongVS = gia100m2 + phiDungCu;
            DecimalFormat decimalFormat = new DecimalFormat("#,###");
            String totalGiaString = decimalFormat.format(totalGiaTongVS);
            txtTotalGia.setText(totalGiaString+"đ");
            txtTotalTime.setText("3h");
            dientich = "100m\u00B2";
            tonggio = "3h";
            tongnguoi = "3";
        }else {
            totalGiaTongVS = gia150m2 + phiDungCu;
            DecimalFormat decimalFormat = new DecimalFormat("#,###");
            String totalGiaString = decimalFormat.format(totalGiaTongVS);
            txtTotalGia.setText(totalGiaString+"đ");
            txtTotalTime.setText("4h");
            dientich = "150m\u00B2";
            tonggio = "4h";
            tongnguoi = "3";
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

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
        if (minute == 0){
            time = hourOfDay+" : "+"00";
        }else time = hourOfDay+" : "+minute;
        if (hourOfDay == 1 || hourOfDay == 2 || hourOfDay == 3 || hourOfDay == 4 || hourOfDay == 5 || hourOfDay == 18|| hourOfDay == 19
                || hourOfDay == 20|| hourOfDay == 21|| hourOfDay == 22 || hourOfDay == 23 ){
            Toast.makeText(getApplicationContext(),"Vui lòng chọn giờ trong khoảng 6h đến 17h!",Toast.LENGTH_LONG).show();
            edtTime.setText("7"+" : "+"00");
        }
        else edtTime.setText(time);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == REQUEST_CODE_MAP && resultCode == RESULT_OK && data != null){
            address = data.getStringExtra("address");
            edtMap.setText(address);
            latitude = data.getDoubleExtra("latitude",0);
            longitude = data.getDoubleExtra("longitude",0);

        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    //back button
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
