package com.example.coosincustomer;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;
import com.thekhaeng.pushdownanim.PushDownAnim;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class OrderDinhKyActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener,DatePickerDialog.OnDateSetListener {

    EditText edtMap,edtGhiChu;
    TextView dateStart;
    int REQUEST_CODE_MAP = 1997,buoi1Tuan=0,buoi1Thang,buoi2Thang,buoi3Thang,buoi6Thang,buoi12Thang;
    private TextView mTxtSang, mTxtChieu, mTxtToi;
    private Spinner spinner1,spinner2;
    String buoi = "Sáng", startTime, endTime1thang,endTime2thang,endTime3thang,endTime6thang,endTime12thang, Thu2 = "", Thu3 ="", Thu4="", Thu5="", Thu6="", Thu7="", ChuNhat="",
            lichLamViec,gio1buoi;
    Button btnContinueDinhKy;
    CheckBox checkBoxT2,checkBoxT3,checkBoxT4,checkBoxT5,checkBoxT6,checkBoxT7,checkBoxCN;
    private Integer dongia = 50000;
    Integer dungcu = 15500;
    private Double totalGia;
    Double latitude = null;
    Double longitude = null;
    double totalTime = 0;
    DatePickerDialog datePickerDialog;
    Calendar calendar;
    SimpleDateFormat simpleDateFormat;
    int Year, Month, Day,T2=0,T3=0,T4=0,T5=0,T6=0,T7=0,CN=0,gia1buoi,totalGia1thang,totalGia2thang,totalGia3thang,totalGia6thang,totalGia12thang,
            tietkiem2thang,tietkiem3thang,tietkiem6thang,tietkiem12thang;

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
        btnContinueDinhKy = findViewById(R.id.btn_continue_datDinhky);
        checkBoxT2 = findViewById(R.id.checkbox_thu2);
        checkBoxT3 = findViewById(R.id.checkbox_thu3);
        checkBoxT4 = findViewById(R.id.checkbox_thu4);
        checkBoxT5 =findViewById(R.id.checkbox_thu5);
        checkBoxT6 = findViewById(R.id.checkbox_thu6);
        checkBoxT7 = findViewById(R.id.checkbox_thu7);
        checkBoxCN = findViewById(R.id.checkbox_chunhat);
        edtGhiChu = findViewById(R.id.txt_input_ghichu);
        simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");

        calendar = Calendar.getInstance();
        Year = calendar.get(Calendar.YEAR) ;
        Month = calendar.get(Calendar.MONTH);
        Day = calendar.get(Calendar.DAY_OF_MONTH);

        //lay thu lam viec
        initialUISetup();

        //default
        mTxtSang.setBackgroundResource(R.drawable.bg_time_lamviec);
        mTxtSang.setTextColor(Color.WHITE);

        PushDownAnim.setPushDownAnimTo(btnContinueDinhKy).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (edtMap.getText().toString().trim().equals("")){
                    edtMap.setError("Bạn chưa chọn địa chỉ!");
                    edtMap.requestFocus();
                    Toast.makeText(getApplicationContext(),"Bạn chưa chọn địa chỉ!",Toast.LENGTH_LONG).show();
                }
                else if (Thu2.equals("") && Thu3.equals("") && Thu4.equals("") && Thu5.equals("")
                        && Thu6.equals("") && Thu7.equals("") && ChuNhat.equals("")){
                    Toast.makeText(getApplicationContext(),"Bạn chưa chọn lịch làm việc!",Toast.LENGTH_LONG).show();
                }else if (dateStart.getText().toString().trim().equals("Chọn ngày bắt đầu")){
                    Toast.makeText(getApplicationContext(),"Bạn chưa chọn ngày bắt đầu!",Toast.LENGTH_LONG).show();
                }
                else {
                    buoi1Thang = buoi1Tuan *4;
                    buoi2Thang = buoi1Thang * 2;
                    buoi3Thang = buoi1Thang * 3;
                    buoi6Thang = buoi1Thang * 6;
                    buoi12Thang = buoi1Thang * 12;
                    totalGia1thang = buoi1Thang * gia1buoi;
                    totalGia2thang = (int) (buoi2Thang * gia1buoi - (buoi2Thang * gia1buoi * 0.02));
                    totalGia3thang = (int) (buoi3Thang * gia1buoi - (buoi3Thang * gia1buoi * 0.03));
                    totalGia6thang = (int) (buoi6Thang * gia1buoi - (buoi6Thang * gia1buoi * 0.06));
                    totalGia12thang = (int) (buoi12Thang * gia1buoi - (buoi12Thang * gia1buoi * 0.12));
                    tietkiem2thang = (int) (buoi2Thang * gia1buoi * 0.02);
                    tietkiem3thang = (int) (buoi3Thang * gia1buoi * 0.03);
                    tietkiem6thang = (int) (buoi6Thang * gia1buoi * 0.06);
                    tietkiem12thang = (int) (buoi12Thang * gia1buoi * 0.12);
                    String time = buoi + " (" + spinner1.getSelectedItem().toString() + " - " + spinner2.getSelectedItem().toString() + ")";
                    String ghichu = edtGhiChu.getText().toString();
                    Intent toChooseGoi = new Intent(OrderDinhKyActivity.this,OrderDinhKyGoiActivity.class);
                    toChooseGoi.putExtra("thu",lichLamViec.substring(0, lichLamViec.length() - 1));
                    toChooseGoi.putExtra("buoi1thang",buoi1Thang);
                    toChooseGoi.putExtra("buoi2thang",buoi2Thang);
                    toChooseGoi.putExtra("buoi3thang",buoi3Thang);
                    toChooseGoi.putExtra("buoi6thang",buoi6Thang);
                    toChooseGoi.putExtra("buoi12thang",buoi12Thang);
                    toChooseGoi.putExtra("totalGia1thang",totalGia1thang);
                    toChooseGoi.putExtra("totalGia2thang",totalGia2thang);
                    toChooseGoi.putExtra("totalGia3thang",totalGia3thang);
                    toChooseGoi.putExtra("totalGia6thang",totalGia6thang);
                    toChooseGoi.putExtra("totalGia12thang",totalGia12thang);
                    toChooseGoi.putExtra("startTime",startTime);
                    toChooseGoi.putExtra("endtime1thang",endTime1thang);
                    toChooseGoi.putExtra("endtime2thang",endTime2thang);
                    toChooseGoi.putExtra("endtime3thang",endTime3thang);
                    toChooseGoi.putExtra("endtime6thang",endTime6thang);
                    toChooseGoi.putExtra("endtime12thang",endTime12thang);
                    toChooseGoi.putExtra("gio1buoi",gio1buoi);
                    toChooseGoi.putExtra("tietkiem2thang",tietkiem2thang);
                    toChooseGoi.putExtra("tietkiem3thang",tietkiem3thang);
                    toChooseGoi.putExtra("tietkiem6thang",tietkiem6thang);
                    toChooseGoi.putExtra("tietkiem12thang",tietkiem12thang);
                    toChooseGoi.putExtra("address",edtMap.getText().toString());
                    toChooseGoi.putExtra("ca",time);
                    toChooseGoi.putExtra("ghichu",ghichu);
                    toChooseGoi.putExtra("latitude",latitude);
                    toChooseGoi.putExtra("longitude",longitude);
                    startActivity(toChooseGoi);
                }
            }
        });

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
                                    builder.setTitle("Quyền vị trí đã bị từ chối").setMessage("Ứng dụng không có quyền truy cập vào vị trí. Vui lòng vào phần cài đặt của thiết bị cấp quyền để tiếp tục !")
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

        //ngay bat dau
        PushDownAnim.setPushDownAnimTo(dateStart).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Thu2.equals("") && Thu3.equals("") && Thu4.equals("") && Thu5.equals("")
                        && Thu6.equals("") && Thu7.equals("") && ChuNhat.equals("")){
                    Toast.makeText(getApplicationContext(),"Vui lòng chọn lịch làm việc trước!",Toast.LENGTH_LONG).show();
                }else {
                    datePickerDialog = DatePickerDialog.newInstance(OrderDinhKyActivity.this, Year, Month, Day);
                    datePickerDialog.setThemeDark(false);
                    datePickerDialog.showYearPickerFirst(false);
                    datePickerDialog.setTitle("Chọn ngày bắt đầu");

                    // Setting Min Date to today date
                    Calendar min_date_c = Calendar.getInstance();
                    datePickerDialog.setMinDate(min_date_c);

                    // Setting Max Date to next 2 years
                    Calendar max_date_c = Calendar.getInstance();
                    max_date_c.set(Calendar.MONTH, Month+1);
                    datePickerDialog.setMaxDate(max_date_c);

                    //Disable all SUNDAYS and SATURDAYS between Min and Max Dates
                    for (Calendar loopdate = min_date_c; min_date_c.before(max_date_c); min_date_c.add(Calendar.DATE, 1), loopdate = min_date_c) {
                        int dayOfWeek = loopdate.get(Calendar.DAY_OF_WEEK);
                        if (dayOfWeek == T2) {
                            Calendar[] disabledDays =  new Calendar[1];
                            disabledDays[0] = loopdate;
                            datePickerDialog.setSelectableDays(disabledDays);
                        }
                        if (dayOfWeek == T3) {
                            Calendar[] disabledDays =  new Calendar[1];
                            disabledDays[0] = loopdate;
                            datePickerDialog.setSelectableDays(disabledDays);
                        }
                        if (dayOfWeek == T4) {
                            Calendar[] disabledDays =  new Calendar[1];
                            disabledDays[0] = loopdate;
                            datePickerDialog.setSelectableDays(disabledDays);
                        }
                        if (dayOfWeek == T5) {
                            Calendar[] disabledDays =  new Calendar[1];
                            disabledDays[0] = loopdate;
                            datePickerDialog.setSelectableDays(disabledDays);
                        }
                        if (dayOfWeek == T6) {
                            Calendar[] disabledDays =  new Calendar[1];
                            disabledDays[0] = loopdate;
                            datePickerDialog.setSelectableDays(disabledDays);
                        }
                        if (dayOfWeek == T7) {
                            Calendar[] disabledDays =  new Calendar[1];
                            disabledDays[0] = loopdate;
                            datePickerDialog.setSelectableDays(disabledDays);
                        }
                        if (dayOfWeek == CN) {
                            Calendar[] disabledDays =  new Calendar[1];
                            disabledDays[0] = loopdate;
                            datePickerDialog.setSelectableDays(disabledDays);
                        }
                    }

                    datePickerDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {

                        @Override
                        public void onCancel(DialogInterface dialogInterface) {

                            Toast.makeText(OrderDinhKyActivity.this, "Bạn chưa chọn ngày", Toast.LENGTH_SHORT).show();
                        }
                    });

                    datePickerDialog.show(getFragmentManager(), "Đã chọn ngày");
                }
            }
        });

    }

    //set text map
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == REQUEST_CODE_MAP && resultCode == RESULT_OK && data != null){
            String address = data.getStringExtra("address");
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

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

        TextView textView = (TextView)adapterView.getChildAt(0);
        textView.setTextColor(getResources().getColor(R.color.enable));

        if (spinner1.getSelectedItemPosition() > spinner2.getSelectedItemPosition() ) {
            spinner2.setSelection(spinner1.getSelectedItemPosition());
        }
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
        DecimalFormat decimalFormat = new DecimalFormat("####");
        String totalGiaString = decimalFormat.format(totalGia);
        gia1buoi = Integer.valueOf(totalGiaString);
        gio1buoi = Double.toString(totalTime);
        if (gio1buoi.substring(2,3).equals("0")){
            gio1buoi =gio1buoi.substring(0,1);
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


    //lay lich lam viec
    public void initialUISetup() {
        checkBoxT2.setOnCheckedChangeListener(new myCheckBoxChnageClicker());
        checkBoxT3.setOnCheckedChangeListener(new myCheckBoxChnageClicker());
        checkBoxT4.setOnCheckedChangeListener(new myCheckBoxChnageClicker());
        checkBoxT5.setOnCheckedChangeListener(new myCheckBoxChnageClicker());
        checkBoxT6.setOnCheckedChangeListener(new myCheckBoxChnageClicker());
        checkBoxT7.setOnCheckedChangeListener(new myCheckBoxChnageClicker());
        checkBoxCN.setOnCheckedChangeListener(new myCheckBoxChnageClicker());
    }

    @Override
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
        SimpleDateFormat sdf = new SimpleDateFormat("EEEE");
        Date d_name = new Date(year,monthOfYear,dayOfMonth-1);
        String dayOfTheWeek = sdf.format(d_name);
        calendar.set(year,monthOfYear,dayOfMonth);
        dateStart.setText( dayOfTheWeek+", "+simpleDateFormat.format(calendar.getTime()));
        startTime = simpleDateFormat.format(calendar.getTime());

        calendar.add(Calendar.MONTH,1);
        endTime1thang = simpleDateFormat.format(calendar.getTime());
        calendar.set(year,monthOfYear,dayOfMonth);
        calendar.add(Calendar.MONTH,2);
        endTime2thang = simpleDateFormat.format(calendar.getTime());
        calendar.set(year,monthOfYear,dayOfMonth);
        calendar.add(Calendar.MONTH,3);
        endTime3thang = simpleDateFormat.format(calendar.getTime());
        calendar.set(year,monthOfYear,dayOfMonth);
        calendar.add(Calendar.MONTH,6);
        endTime6thang = simpleDateFormat.format(calendar.getTime());
        calendar.set(year,monthOfYear,dayOfMonth);
        calendar.add(Calendar.MONTH,12);
        endTime12thang = simpleDateFormat.format(calendar.getTime());
    }

    class myCheckBoxChnageClicker implements CheckBox.OnCheckedChangeListener
    {

        @Override
        public void onCheckedChanged(CompoundButton buttonView,
                                     boolean isChecked) {
            // TODO Auto-generated method stub

            // Toast.makeText(CheckBoxCheckedDemo.this, &quot;Checked =&gt; &quot;+isChecked, Toast.LENGTH_SHORT).show();

            if(isChecked) {
                if(buttonView==checkBoxT2) {
                    Thu2 = "Hai, ";
                    T2 = 2;
                    buoi1Tuan++;
                }
                if(buttonView==checkBoxT3) {
                    Thu3 = "Ba, ";
                    T3 = 3;
                    buoi1Tuan++;
                }
                if(buttonView==checkBoxT4) {
                    Thu4 = "Tư, ";
                    T4 = 4;
                    buoi1Tuan++;
                }
                if(buttonView==checkBoxT5) {
                    Thu5 = "Năm, ";
                    T5= 5;
                    buoi1Tuan++;
                }
                if(buttonView==checkBoxT6) {
                    Thu6 = "Sáu, ";
                    T6=6;
                    buoi1Tuan++;
                }
                if(buttonView==checkBoxT7) {
                    Thu7 = "Bảy, ";
                    T7=7;
                    buoi1Tuan++;
                }
                if(buttonView==checkBoxCN) {
                    ChuNhat = "Chủ nhật,";
                    CN = 1;
                    buoi1Tuan++;
                }
            }else {
                if(buttonView==checkBoxT2) {
                    Thu2 = "";
                    T2 = 0;
                    buoi1Tuan--;
                }
                if(buttonView==checkBoxT3) {
                    Thu3 = "";
                    T3=0;
                    buoi1Tuan--;
                }
                if(buttonView==checkBoxT4) {
                    Thu4 = "";
                    T4=0;
                    buoi1Tuan--;
                }
                if(buttonView==checkBoxT5) {
                    Thu5 = "";
                    T5=0;
                    buoi1Tuan--;
                }
                if(buttonView==checkBoxT6) {
                    Thu6 = "";
                    T6=0;
                    buoi1Tuan--;
                }
                if(buttonView==checkBoxT7) {
                    Thu7 = "";
                    T7=0;
                    buoi1Tuan--;
                }
                if(buttonView==checkBoxCN) {
                    ChuNhat = "";
                    CN= 0;
                    buoi1Tuan--;
                }
            }
            lichLamViec = Thu2 + Thu3 + Thu4 + Thu5 + Thu6 + Thu7 + ChuNhat;
        }
    }
}
