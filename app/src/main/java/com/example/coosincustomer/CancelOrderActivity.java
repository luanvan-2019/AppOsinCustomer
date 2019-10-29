package com.example.coosincustomer;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.thekhaeng.pushdownanim.PushDownAnim;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;

import java.sql.Connection;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class CancelOrderActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {

    Integer checked=1,idOrder;
    RelativeLayout relHuyLuonCa,relLamBu,relNgayBu,rel_nghihomnay,rel_selectCancel;
    ImageView imgCheck1,imgCheck2;
    Button btnConfirmCancel;
    EditText edtDate;
    DatePickerDialog datePickerDialog;
    Calendar calendar;
    SimpleDateFormat simpleDateFormat;
    int Year, Month, Day;
    String oldDate,orderType;
    Connection connect;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cancel_order);

        //anh xa
        Toolbar toolbar = findViewById(R.id.toolbar_cancel_order);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        relHuyLuonCa = findViewById(R.id.relative_dat_lan_sau);
        relLamBu = findViewById(R.id.relative_dat_bu);
        imgCheck1 = findViewById(R.id.ic_check_dat_luc_khac);
        imgCheck2 = findViewById(R.id.ic_check_dat_bu_ngay_khac);
        btnConfirmCancel= findViewById(R.id.btn_confirm_cancel);
        edtDate = findViewById(R.id.edt_date);
        relNgayBu = findViewById(R.id.relative_ngay_bu);
        rel_selectCancel = findViewById(R.id.rel_select_cancel);
        rel_nghihomnay = findViewById(R.id.rel_nghihomnay);

        relNgayBu.setVisibility(View.GONE);

        oldDate = getIntent().getStringExtra("oldDate");
        idOrder = getIntent().getIntExtra("idOrder",0);
        orderType = getIntent().getStringExtra("orderType");

        if (orderType.trim().equals("Định kỳ")){
            rel_selectCancel.setVisibility(View.GONE);
        }
        else rel_nghihomnay.setVisibility(View.GONE);

        PushDownAnim.setPushDownAnimTo(relHuyLuonCa).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checked = 1;
                imgCheck1.setImageResource(R.drawable.ic_checked);
                imgCheck2.setImageResource(R.drawable.ic_checkbox_uncheck_circle);
                relNgayBu.setVisibility(View.GONE);
            }
        });
        PushDownAnim.setPushDownAnimTo(relLamBu).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checked = 2;
                imgCheck2.setImageResource(R.drawable.ic_checked);
                imgCheck1.setImageResource(R.drawable.ic_checkbox_uncheck_circle);
                relNgayBu.setVisibility(View.VISIBLE);
            }
        });

        PushDownAnim.setPushDownAnimTo(btnConfirmCancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                com.example.coosincustomer.ConnectionDB conStr=new com.example.coosincustomer.ConnectionDB();
                connect =conStr.CONN();
                if (!orderType.trim().equals("Định kỳ")){
                    if (checked == 1){
                        try {
                            // Connect to database
                            if (connect == null){Toast.makeText(getApplicationContext(),"Không có kết nối mạng!",Toast.LENGTH_LONG).show();}
                            else {
                                String query = "UPDATE ORDER_SINGLE SET STATUS_ORDER=N'Đã hủy', USER_SUBMIT='',DATE_SUBMIT='' WHERE ID="+idOrder+"";
                                Statement stmt = connect.createStatement();
                                stmt.executeQuery(query);
                                connect.close();
                            }
                        }
                        catch (Exception ex){

                        }
                        Intent intent = new Intent(CancelOrderActivity.this,CanceledActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);
                        finish();
                    }else {
                        try {
                            // Connect to database
                            if (connect == null){Toast.makeText(getApplicationContext(),"Không có kết nối mạng!",Toast.LENGTH_LONG).show();}
                            if (edtDate.getText().toString().equals("")){
                                Toast.makeText(getApplicationContext(),"Bạn chưa chọn ngày làm bù!",Toast.LENGTH_LONG).show();
                            }
                            else {
                                String query = "UPDATE ORDER_SINGLE SET DATE_WORK='"+edtDate.getText().toString()+"' WHERE ID="+idOrder+"";
                                Statement stmt = connect.createStatement();
                                stmt.executeQuery(query);
                                connect.close();
                            }
                        }
                        catch (Exception ex){

                        }
                    }
                }
                Intent intent = new Intent(CancelOrderActivity.this,HomeActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                finish();
            }
        });

        simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
        calendar = Calendar.getInstance();
        Year = calendar.get(Calendar.YEAR) ;
        Month = calendar.get(Calendar.MONTH);
        Day = calendar.get(Calendar.DAY_OF_MONTH);
        //chon lai ngay
        PushDownAnim.setPushDownAnimTo(edtDate).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                datePickerDialog = DatePickerDialog.newInstance(CancelOrderActivity.this, Year, Month, Day);
                datePickerDialog.setThemeDark(false);
                datePickerDialog.showYearPickerFirst(false);
                datePickerDialog.setTitle("Chọn lại ngày");

                // Setting Min Date to today date
                Calendar min_date_c = Calendar.getInstance();
                min_date_c.set(Calendar.DATE,Day+1);
                datePickerDialog.setMinDate(min_date_c);

                // Setting Max Date to next 2 years
                Calendar max_date_c = Calendar.getInstance();
                max_date_c.set(Calendar.DATE, Day+8);
                datePickerDialog.setMaxDate(max_date_c);

                //disable ngay muon doi
                java.util.Date date = null;
                SimpleDateFormat ooldDate = new SimpleDateFormat("dd/MM/yyyy");
                try {
                    date = ooldDate.parse(oldDate);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                calendar = dateToCalendar(date);
                List<Calendar> dates = new ArrayList<>();
                dates.add(calendar);
                Calendar[] disabledDays1 = dates.toArray(new Calendar[dates.size()]);
                datePickerDialog.setDisabledDays(disabledDays1);

                datePickerDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {

                    @Override
                    public void onCancel(DialogInterface dialogInterface) {

                        Toast.makeText(CancelOrderActivity.this, "Bạn chưa chọn ngày", Toast.LENGTH_SHORT).show();
                    }
                });

                datePickerDialog.show(getFragmentManager(), "Đã chọn ngày");
            }
        });

    }

    //toolbar back button
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
        SimpleDateFormat sdf = new SimpleDateFormat("EEEE");
        Date d_name = new Date(year,monthOfYear,dayOfMonth-1);
        String dayOfTheWeek = sdf.format(d_name);
        calendar.set(year,monthOfYear,dayOfMonth);
        edtDate.setText( dayOfTheWeek+", "+simpleDateFormat.format(calendar.getTime()));
    }

    private Calendar dateToCalendar(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar;
    }
}
