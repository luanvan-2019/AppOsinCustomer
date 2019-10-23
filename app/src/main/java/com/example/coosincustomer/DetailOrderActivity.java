package com.example.coosincustomer;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.DialogFragment;

import com.thekhaeng.pushdownanim.PushDownAnim;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DetailOrderActivity extends AppCompatActivity implements DanhGiaDialog.DanhGiaListener {


    Integer idOrder;
    Connection connect;
    TextView txtOrderType,txtThu,txtNgay,txtCa,txtGioCa,txtAddress,txtSoBuoi,txtSoGio,txtTongTien,txtPaymentType,
            txtStaffName,txtStaffPhone,txtTimduocNV,txtDangtrongca,txtPayStatus,txtMDH,txtHoantat;
    String orderType,orderStatus,userSubmit,idOrderString;
    RelativeLayout relStaffSubmit,relFindStaff,relLine;
    LinearLayout linConfirmStaffCome,linCancelStaff,linConfirmSuccess,linCall,linChat;
    View line1,line2,line3;
    ImageView node2,node3,node4;
    Integer paymentStatus;
    Button btnBaoNghi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_order);

        anhXa();

        relStaffSubmit.setVisibility(View.GONE);
        linConfirmSuccess.setVisibility(View.GONE);

        idOrder = getIntent().getIntExtra("idOrder",0);
        orderType = getIntent().getStringExtra("orderType");

        //anh xa
        Toolbar toolbar = findViewById(R.id.toolbar_detail);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        checkOrderType();

        try {
            com.example.coosincustomer.ConnectionDB conStr=new com.example.coosincustomer.ConnectionDB();
            connect =conStr.CONN();
            // Connect to database
            if (connect == null){checkConnectDialog();}
            else {
                // Change below query according to your own database.
                String query = "select * from EMPLOYEE where PHONE_NUM= '" + userSubmit  + "'";
                Statement stmt = connect.createStatement();
                ResultSet rs = stmt.executeQuery(query);
                if (rs.next()){
                    txtStaffName.setText(rs.getString("FULL_NAME"));
                    txtStaffPhone.setText(userSubmit);
                    connect.close();
                }
            }
        }
        catch (Exception ex){
            Toast.makeText(getApplicationContext(), ex.getMessage(), Toast.LENGTH_LONG).show();
        }

        if (orderStatus.trim().equals("Đã tìm được NV")){
            relStaffSubmit.setVisibility(View.VISIBLE);
            line1.setBackgroundColor(getResources().getColor(R.color.enable));
            node2.setImageResource(R.drawable.ic_node_enable);
            relFindStaff.setVisibility(View.GONE);
            txtTimduocNV.setTextColor(getResources().getColor(R.color.enable));
        }
        if (orderStatus.trim().equals("Đang trong ca làm")){
            relStaffSubmit.setVisibility(View.VISIBLE);
            relFindStaff.setVisibility(View.GONE);
            linConfirmStaffCome.setVisibility(View.GONE);
            linConfirmSuccess.setVisibility(View.VISIBLE);
            linCancelStaff.setVisibility(View.GONE);
            line1.setBackgroundColor(getResources().getColor(R.color.enable));
            node2.setImageResource(R.drawable.ic_node_enable);
            txtTimduocNV.setTextColor(getResources().getColor(R.color.enable));
            line2.setBackgroundColor(getResources().getColor(R.color.enable));
            node3.setImageResource(R.drawable.ic_node_enable);
            txtDangtrongca.setTextColor(getResources().getColor(R.color.enable));
            btnBaoNghi.setVisibility(View.GONE);
            relLine.setVisibility(View.GONE);
        }

        checkSuccess();

        PushDownAnim.setPushDownAnimTo(linConfirmStaffCome).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                linConfirmStaffCome.setVisibility(View.GONE);
                linCancelStaff.setVisibility(View.GONE);
                linConfirmSuccess.setVisibility(View.VISIBLE);
                line2.setBackgroundColor(getResources().getColor(R.color.enable));
                node3.setImageResource(R.drawable.ic_node_enable);
                txtDangtrongca.setTextColor(getResources().getColor(R.color.enable));
                btnBaoNghi.setVisibility(View.GONE);
                relLine.setVisibility(View.GONE);
                try {
                    com.example.coosincustomer.ConnectionDB conStr=new com.example.coosincustomer.ConnectionDB();
                    connect =conStr.CONN();
                    // Connect to database
                    if (connect == null){checkConnectDialog();}
                    else {
                        String query = "UPDATE ORDER_SINGLE SET STATUS_ORDER=N'Đang trong ca làm' WHERE ID="+idOrder+"";
                        Statement stmt = connect.createStatement();
                        stmt.executeQuery(query);
                        connect.close();
                    }
                }
                catch (Exception ex){
                    Toast.makeText(getApplicationContext(),"Xác nhận thành công", Toast.LENGTH_LONG).show();
                }
            }
        });

        PushDownAnim.setPushDownAnimTo(linConfirmSuccess).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    com.example.coosincustomer.ConnectionDB conStr=new com.example.coosincustomer.ConnectionDB();
                    connect =conStr.CONN();
                    // Connect to database
                    if (connect == null){checkConnectDialog();}
                    else {
                        String query = "select * from ORDER_SINGLE where ID= '" + idOrder  + "'";
                        Statement stmt = connect.createStatement();
                        ResultSet rs = stmt.executeQuery(query);
                        if (rs.next()){
                            paymentStatus = rs.getInt("PAYMENT_STATUS");
                        }
                    }
                    if (paymentStatus == 0){
                        checkPayment();
                    }else {
                        checkSuccess();
                        DialogFragment danhgiaDialog = new DanhGiaDialog();
                        danhgiaDialog.setCancelable(false);
                        danhgiaDialog.show(getSupportFragmentManager(),"Danh gia dialog");
                        String query = "UPDATE ORDER_SINGLE SET STATUS_ORDER=N'Hoàn thành' WHERE ID="+idOrder+"";
                        Statement stmt = connect.createStatement();
                        stmt.executeQuery(query);
                    }
                    connect.close();
                }
                catch (Exception ex){

                }
            }
        });

        PushDownAnim.setPushDownAnimTo(linCancelStaff).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DateFormat formatter = new SimpleDateFormat("HH:mm dd/MM/yyyy");
                Date date = new Date();
                String today = formatter.format(date);
                String timecancelString = today.substring(0,2);
                String timeValidString = txtGioCa.getText().toString().substring(1,3);
                if (timecancelString.substring(1,2).equals(":")){
                    timecancelString= timecancelString.substring(0,1);
                }
                if (timeValidString.substring(1,2).equals(":")){
                    timeValidString = timeValidString.substring(0,1);
                }
                Integer timecancel = Integer.valueOf(timecancelString);
                Integer timeValid = Integer.valueOf(timeValidString);
                if (txtNgay.getText().toString().substring(0,4).equals(today.substring(6,11))
                        && timeValid-timecancel<3){
                    checkCancel();
                }else {
                    cancelNVDialog();
                }
            }
        });

        PushDownAnim.setPushDownAnimTo(btnBaoNghi).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DateFormat formatter = new SimpleDateFormat("HH:mm dd/MM/yyyy");
                Date date = new Date();
                String today = formatter.format(date);
                String timecancelString = today.substring(0,2);
                String timeValidString = txtGioCa.getText().toString().substring(1,3);
                if (timecancelString.substring(1,2).equals(":")){
                    timecancelString= timecancelString.substring(0,1);
                }
                if (timeValidString.substring(1,2).equals(":")){
                    timeValidString = timeValidString.substring(0,1);
                }
                Integer timecancel = Integer.valueOf(timecancelString);
                Integer timeValid = Integer.valueOf(timeValidString);
                if (txtNgay.getText().toString().substring(0,4).equals(today.substring(6,11))
                        && timeValid-timecancel<3){
                    checkCancel();
                }else {
                    Intent baonghi = new Intent(DetailOrderActivity.this,CancelOrderActivity.class);
                    baonghi.putExtra("idOrder",idOrder);
                    baonghi.putExtra("oldDate",txtNgay.getText().toString());
                    startActivity(baonghi);
                }
            }
        });

        PushDownAnim.setPushDownAnimTo(linCall).setOnClickListener(new View.OnClickListener() {
            @SuppressLint("MissingPermission")
            @Override
            public void onClick(View view) {
                String s = "tel:" + userSubmit;
                Log.d("BBB",s);
                Intent intent = new Intent(Intent.ACTION_CALL);
                intent.setData(Uri.parse(s));
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

    public void checkConnectDialog(){
        TextView title = new TextView(this);
        title.setText("THÔNG BÁO");
        title.setPadding(10, 40, 10, 10);
        title.setGravity(Gravity.CENTER);
        title.setTextSize(20);
        title.setTextColor(Color.RED);

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCustomTitle(title);
        builder.setMessage("Không có kết nối mạng !");
        builder.setCancelable(false);
        builder.setNegativeButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
        Button b = alertDialog.getButton(DialogInterface.BUTTON_NEGATIVE);
        b.setBackground(getResources().getDrawable(R.drawable.btn_round_coner));
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        layoutParams.gravity = Gravity.CENTER;
        layoutParams.setMargins(20,20,20,20);
        alertDialog.getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor(Color.WHITE);
        alertDialog.getButton(AlertDialog.BUTTON_NEGATIVE).setLayoutParams(layoutParams);

        TextView messageText = alertDialog.findViewById(android.R.id.message);
        messageText.setGravity(Gravity.CENTER);
    }

    private void anhXa(){
        txtOrderType = findViewById(R.id.txt_order_type);
        txtThu = findViewById(R.id.txt_thu);
        txtNgay = findViewById(R.id.txt_ngay);
        txtCa = findViewById(R.id.txt_buoi);
        txtGioCa = findViewById(R.id.txt_time);
        txtAddress = findViewById(R.id.txt_address);
        txtSoBuoi = findViewById(R.id.txt_sobuoi);
        txtSoGio = findViewById(R.id.txt_sogio);
        txtTongTien = findViewById(R.id.txt_tongtien);
        txtPaymentType = findViewById(R.id.txt_payment_type);
        relStaffSubmit = findViewById(R.id.relative_staff_submit);
        relFindStaff = findViewById(R.id.relative_find_staff);
        line1 = findViewById(R.id.line_1);
        line2 = findViewById(R.id.line_2);
        node2 = findViewById(R.id.node2);
        node3 = findViewById(R.id.node3);
        txtStaffName = findViewById(R.id.txt_staff_name);
        txtStaffPhone = findViewById(R.id.txt_staff_phone);
        txtTimduocNV = findViewById(R.id.txt_timduocNV);
        linConfirmStaffCome = findViewById(R.id.linear_confirm_staff_come);
        linCancelStaff = findViewById(R.id.linear_cancel_staff);
        txtDangtrongca= findViewById(R.id.txt_dangtrong_calam);
        linConfirmSuccess = findViewById(R.id.linear_confirm_success);
        txtPayStatus = findViewById(R.id.payment_status);
        txtMDH = findViewById(R.id.txt_ma_dh);
        line3 = findViewById(R.id.line_3);
        node4 = findViewById(R.id.node4);
        txtHoantat = findViewById(R.id.txt_hoantat);
        btnBaoNghi = findViewById(R.id.btn_baonghi);
        relLine = findViewById(R.id.line);
        linCall = findViewById(R.id.linear_call);
        linChat = findViewById(R.id.linear_chat);
    }

    private void checkPayment(){
        TextView title = new TextView(this);
        title.setText("THÔNG BÁO");
        title.setPadding(10, 30, 10, 10);
        title.setGravity(Gravity.CENTER);
        title.setTextSize(20);
        title.setTextColor(Color.RED);

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCustomTitle(title);
        builder.setMessage("Dường như ca làm chưa được thanh toán. Vui lòng thanh toán cho nhân viên để hoàn tất !");
        builder.setCancelable(false);
        builder.setNegativeButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
        Button b = alertDialog.getButton(DialogInterface.BUTTON_NEGATIVE);
        b.setBackground(getResources().getDrawable(R.drawable.btn_round_coner));
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        layoutParams.gravity = Gravity.CENTER;
        layoutParams.setMargins(20,20,20,20);
        alertDialog.getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor(Color.WHITE);
        alertDialog.getButton(AlertDialog.BUTTON_NEGATIVE).setLayoutParams(layoutParams);

        TextView messageText = alertDialog.findViewById(android.R.id.message);
        messageText.setGravity(Gravity.CENTER);
    }

    private void checkOrderType(){
        com.example.coosincustomer.ConnectionDB conStr=new com.example.coosincustomer.ConnectionDB();
        connect =conStr.CONN();
        if (orderType.trim().equals("Dùng lẻ")){
            try
            {
                idOrderString="DL"+idOrder;
                // Connect to database
                if (connect == null)
                {
                    checkConnectDialog();
                }
                else
                {
                    // Change below query according to your own database.
                    String query = "select * from ORDER_SINGLE where ID= '" + idOrder  + "'";
                    Statement stmt = connect.createStatement();
                    ResultSet rs = stmt.executeQuery(query);
                    if (rs.next())
                    {
                        txtOrderType.setText(orderType);
                        txtThu.setText(rs.getString("DATE_WORK").substring(0,rs.getString("DATE_WORK").length()-12));
                        txtNgay.setText(rs.getString("DATE_WORK").substring(rs.getString("DATE_WORK").length()-10));
                        txtCa.setText(rs.getString("TIME_WORK").substring(0,rs.getString("TIME_WORK").length()-15));
                        txtGioCa.setText(rs.getString("TIME_WORK").substring(rs.getString("TIME_WORK").length()-14,rs.getString("TIME_WORK").length()-0));
                        txtAddress.setText(rs.getString("ADDRESS_ORDER"));
                        txtSoGio.setText(rs.getString("TOTAL_TIME"));
                        DecimalFormat decimalFormat = new DecimalFormat("#,###");
                        String totalGiaString = decimalFormat.format(rs.getInt("TOTAL_PRICE"));
                        txtTongTien.setText(totalGiaString+"đ");
                        txtPaymentType.setText(rs.getString("PAYMENT_TYPE"));
                        orderStatus = rs.getString("STATUS_ORDER");
                        userSubmit = rs.getString("USER_SUBMIT");
                        txtOrderType.setText(orderType);
                        paymentStatus=rs.getInt("PAYMENT_STATUS");
                        txtMDH.setText("MDH: DL"+idOrder);
                        if (paymentStatus==0){
                            txtPayStatus.setText("Chưa thanh toán");
                        }else{
                            txtPayStatus.setText("Đã thanh toán");
                            txtPayStatus.setBackgroundResource(R.drawable.bg_text);
                        }
                        connect.close();
                    }
                }
            }
            catch (Exception ex)
            {
                Toast.makeText(getApplicationContext(), ex.getMessage(), Toast.LENGTH_LONG).show();
            }
        }else if (orderType.trim().equals("Dùng định kỳ")){

        }
    }

    private void checkSuccess(){
        if (orderStatus.trim().equals("Hoàn thành")){
            relStaffSubmit.setVisibility(View.VISIBLE);
            relFindStaff.setVisibility(View.GONE);
            linConfirmStaffCome.setVisibility(View.GONE);
            linConfirmSuccess.setVisibility(View.VISIBLE);
            linCancelStaff.setVisibility(View.GONE);
            line1.setBackgroundColor(getResources().getColor(R.color.enable));
            node2.setImageResource(R.drawable.ic_node_enable);
            txtTimduocNV.setTextColor(getResources().getColor(R.color.enable));
            line2.setBackgroundColor(getResources().getColor(R.color.enable));
            node3.setImageResource(R.drawable.ic_node_enable);
            txtDangtrongca.setTextColor(getResources().getColor(R.color.enable));
            line3.setBackgroundColor(getResources().getColor(R.color.enable));
            node4.setImageResource(R.drawable.ic_node_enable);
            txtHoantat.setTextColor(getResources().getColor(R.color.enable));
            linConfirmSuccess.setVisibility(View.GONE);
        }
    }

    @Override
    public void onPositiveButtonClicked(String[] list, int position) {
        try {
            com.example.coosincustomer.ConnectionDB conStr=new com.example.coosincustomer.ConnectionDB();
            connect =conStr.CONN();
            // Connect to database
            if (connect == null)
            {
                checkConnectDialog();
            }
            else {
                DateFormat formatter = new SimpleDateFormat("HH:mm dd/MM/yyyy");
                Date date = new Date();
                String create_at = formatter.format(date);
                // Change below query according to your own database.
                String query = "INSERT INTO DANHGIA (EMP_ID,CONTENT,CREATE_AT,ORDER_ID) VALUES('"+userSubmit+"',N'"+list[position]+"','"+create_at+"','"+idOrderString+"')";
                Statement stmt = connect.createStatement();
                stmt.executeQuery(query);
                connect.close();
            }
        }
        catch (Exception ex){
            Intent intent = new Intent(DetailOrderActivity.this,HistoryActivity.class);
            startActivity(intent);
        }
    }

    @Override
    public void onNegativeButtonClicked() {
        Intent intent = new Intent(DetailOrderActivity.this,HistoryActivity.class);
        startActivity(intent);
    }

    private void checkCancel(){
        TextView title = new TextView(this);
        title.setText("THÔNG BÁO");
        title.setPadding(10, 30, 10, 10);
        title.setGravity(Gravity.CENTER);
        title.setTextSize(20);
        title.setTextColor(Color.RED);

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCustomTitle(title);
        builder.setMessage("Ca làm sắp bắt đầu, bạn không thể hủy lúc này.\nVui lòng thực hiện hủy trước 3 tiếng khi ca làm bắt đầu !");
        builder.setCancelable(false);
        builder.setNegativeButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
        Button b = alertDialog.getButton(DialogInterface.BUTTON_NEGATIVE);
        b.setBackground(getResources().getDrawable(R.drawable.btn_round_coner));
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        layoutParams.gravity = Gravity.CENTER;
        layoutParams.setMargins(20,20,20,20);
        alertDialog.getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor(Color.WHITE);
        alertDialog.getButton(AlertDialog.BUTTON_NEGATIVE).setLayoutParams(layoutParams);

        TextView messageText = alertDialog.findViewById(android.R.id.message);
        messageText.setGravity(Gravity.CENTER);
    }

    private void cancelNVDialog(){
        TextView title = new TextView(this);
        title.setText("THÔNG BÁO");
        title.setPadding(10, 30, 10, 10);
        title.setGravity(Gravity.CENTER);
        title.setTextSize(20);
        title.setTextColor(Color.RED);

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCustomTitle(title);
        builder.setMessage("Nếu hủy nhân viên này sẽ mất cơ hội việc làm. Bạn có muốn tiếp tục ?");
        builder.setCancelable(false);
        builder.setNegativeButton("Tiếp tục", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                try {
                    com.example.coosincustomer.ConnectionDB conStr=new com.example.coosincustomer.ConnectionDB();
                    connect =conStr.CONN();
                    // Connect to database
                    if (connect == null){checkConnectDialog();}
                    else {
                        relStaffSubmit.setVisibility(View.GONE);
                        relFindStaff.setVisibility(View.VISIBLE);
                        linConfirmStaffCome.setVisibility(View.GONE);
                        linConfirmSuccess.setVisibility(View.GONE);
                        linCancelStaff.setVisibility(View.GONE);
                        line1.setBackgroundColor(getResources().getColor(R.color.disable_line));
                        node2.setImageResource(R.drawable.ic_node);
                        txtTimduocNV.setTextColor(getResources().getColor(R.color.disable));
                        line2.setBackgroundColor(getResources().getColor(R.color.disable_line));
                        node3.setImageResource(R.drawable.ic_node);
                        txtDangtrongca.setTextColor(getResources().getColor(R.color.disable));
                        String query = "UPDATE ORDER_SINGLE SET STATUS_ORDER=N'Đang tìm kiếm NV', USER_SUBMIT='',DATE_SUBMIT='' WHERE ID="+idOrder+"";
                        Statement stmt = connect.createStatement();
                        stmt.executeQuery(query);
                        connect.close();
                    }
                }
                catch (Exception ex){

                }
            }
        });
        builder.setPositiveButton("Không", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();

        TextView messageText = alertDialog.findViewById(android.R.id.message);
        messageText.setGravity(Gravity.CENTER);
    }
}
