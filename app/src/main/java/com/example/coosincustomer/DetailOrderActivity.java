package com.example.coosincustomer;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.DialogFragment;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

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
            txtStaffName,txtStaffPhone,txtTimduocNV,txtDangtrongca,txtPayStatus,txtMDH,txtHoantat,txtSobuoicon,txtDientich,
            txtStaffName2,txtStaffPhone2,txtStaffName3,txtStaffPhone3,txtSoMon,txtTenMon,txtKhauVi,txtTraiCay,txtMaxMarket,labelSonguoi;
    String orderType,orderStatus,userSubmit,userSubmit2,userSubmit3,idOrderString;
    RelativeLayout relStaffSubmit,relFindStaff,relLine,relSobuoicon,rel_staff1,rel_staff2,rel_staff3,relDientich,relNauAn,relMaxMarket,relCancelTVS;
    LinearLayout linConfirmStaffCome,linCancelStaff,linConfirmSuccess,linCall,linChat;
    View line1,line2,line3;
    ImageView node2,node3,node4,avatarCus1,avatarCus2,avatarCus3;
    Integer paymentStatus;
    Button btnBaoNghi,btnCloseTVS,btnConfirmTVS;
    RadioButton rbtnNV1,rbtnNV2,rbtnNV3;
    RadioGroup radioGroupTVS;
    int cancel=1;
    int sobuoiDalam,seeingStaff,areaType;
    Dialog dialog;
    String account;
    SwipeRefreshLayout swipeRefreshLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_order);

        anhXa();
        swipeRefreshLayout = findViewById(R.id.swipe_detail);

        idOrder = getIntent().getIntExtra("idOrder",0);
        orderType = getIntent().getStringExtra("orderType");
        Log.d("BBB",orderType);
        //lay so dien thoai
        SharedPreferences SP = getApplicationContext().getSharedPreferences("PHONE",0);
        account = SP.getString("phone_num",null);

        //anh xa
        Toolbar toolbar = findViewById(R.id.toolbar_detail);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        todo();

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                todo();
                swipeRefreshLayout.setRefreshing(false);
            }
        });
    }

    //toolbar back button
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    public void todo(){
        relStaffSubmit.setVisibility(View.GONE);
        linConfirmSuccess.setVisibility(View.GONE);
        relSobuoicon.setVisibility(View.GONE);
        rel_staff2.setVisibility(View.GONE);
        rel_staff3.setVisibility(View.GONE);
        relDientich.setVisibility(View.GONE);
        relNauAn.setVisibility(View.GONE);
        relMaxMarket.setVisibility(View.GONE);
        relCancelTVS.setVisibility(View.GONE);
        rbtnNV3.setVisibility(View.GONE);
        dialog = new Dialog(this);

        checkOrderType();

        if (paymentStatus==0){
            txtPayStatus.setText("Chưa thanh toán");
        }else{
            txtPayStatus.setText("Đã thanh toán");
            txtPayStatus.setBackgroundResource(R.drawable.bg_text);
        }

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
        if (orderType.trim().equals("Tổng vệ sinh")){
            if (!userSubmit2.equals("")){
                avatarCus2.setImageResource(R.drawable.ic_avatar_staff);
                txtStaffName2.setTextColor(getResources().getColor(R.color.enable));
                try {
                    com.example.coosincustomer.ConnectionDB conStr=new com.example.coosincustomer.ConnectionDB();
                    connect =conStr.CONN();
                    // Connect to database
                    if (connect == null){checkConnectDialog();}
                    else {
                        // Change below query according to your own database.
                        String query = "select * from EMPLOYEE where PHONE_NUM= '" + userSubmit2  + "'";
                        Statement stmt = connect.createStatement();
                        ResultSet rs = stmt.executeQuery(query);
                        if (rs.next()){
                            txtStaffName2.setText(rs.getString("FULL_NAME"));
                            txtStaffPhone2.setText(userSubmit2);
                            connect.close();
                        }
                    }
                }catch (Exception ex){
                    Toast.makeText(getApplicationContext(), ex.getMessage(), Toast.LENGTH_LONG).show();
                }
            }
            if (!userSubmit3.equals("")){
                avatarCus3.setImageResource(R.drawable.ic_avatar_staff);
                txtStaffName2.setTextColor(getResources().getColor(R.color.enable));
                try {
                    com.example.coosincustomer.ConnectionDB conStr=new com.example.coosincustomer.ConnectionDB();
                    connect =conStr.CONN();
                    // Connect to database
                    if (connect == null){checkConnectDialog();}
                    else {
                        // Change below query according to your own database.
                        String query = "select * from EMPLOYEE where PHONE_NUM= '" + userSubmit3  + "'";
                        Statement stmt = connect.createStatement();
                        ResultSet rs = stmt.executeQuery(query);
                        if (rs.next()){
                            txtStaffName3.setText(rs.getString("FULL_NAME"));
                            txtStaffPhone3.setText(userSubmit3);
                            connect.close();
                        }
                    }
                }catch (Exception ex){
                    Toast.makeText(getApplicationContext(), ex.getMessage(), Toast.LENGTH_LONG).show();
                }
            }
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
                com.example.coosincustomer.ConnectionDB conStr=new com.example.coosincustomer.ConnectionDB();
                connect =conStr.CONN();
                if (orderType.trim().equals("Dùng lẻ")){
                    try {
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
                }else if (orderType.trim().equals("Định kỳ")){
                    try {
                        // Connect to database
                        if (connect == null){checkConnectDialog();}
                        else {
                            String query = "UPDATE ORDER_MULTI SET ORDER_STATUS=N'Đang trong ca làm' WHERE ID="+idOrder+"";
                            Statement stmt = connect.createStatement();
                            stmt.executeQuery(query);
                            connect.close();
                        }
                    }
                    catch (Exception ex){
                        Toast.makeText(getApplicationContext(),"Xác nhận thành công", Toast.LENGTH_LONG).show();
                    }
                }else if (orderType.trim().equals("Tổng vệ sinh")){
                    try {
                        // Connect to database
                        if (connect == null){checkConnectDialog();}
                        else {
                            String query = "UPDATE ORDER_OVERVIEW SET ORDER_STATUS=N'Đang trong ca làm' WHERE ID="+idOrder+"";
                            Statement stmt = connect.createStatement();
                            stmt.executeQuery(query);
                            connect.close();
                        }
                    }
                    catch (Exception ex){
                        Toast.makeText(getApplicationContext(),"Xác nhận thành công", Toast.LENGTH_LONG).show();
                    }
                }else {
                    try {
                        // Connect to database
                        if (connect == null){checkConnectDialog();}
                        else {
                            String query = "UPDATE ORDER_COOK SET ORDER_STATUS=N'Đang trong ca làm' WHERE ID="+idOrder+"";
                            Statement stmt = connect.createStatement();
                            stmt.executeQuery(query);
                            connect.close();
                        }
                    }
                    catch (Exception ex){
                        Toast.makeText(getApplicationContext(),"Xác nhận thành công", Toast.LENGTH_LONG).show();
                    }
                }
            }
        });

        PushDownAnim.setPushDownAnimTo(linConfirmSuccess).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                com.example.coosincustomer.ConnectionDB conStr=new com.example.coosincustomer.ConnectionDB();
                connect =conStr.CONN();
                if (orderType.trim().equals("Dùng lẻ")){
                    try {
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
                    }
                    catch (Exception ex){
                    }
                }else if (orderType.trim().equals("Định kỳ")){
                    try {
                        // Connect to database
                        if (connect == null){checkConnectDialog();}
                        else {
                            checkSuccess();
                            DialogFragment danhgiaDialog = new DanhGiaDialog();
                            danhgiaDialog.setCancelable(false);
                            danhgiaDialog.show(getSupportFragmentManager(),"Danh gia dialog");
                            sobuoiDalam =sobuoiDalam+1;
                            String query = "UPDATE ORDER_MULTI SET ORDER_STATUS=N'Đã tìm được NV' ,TOTAL_WORKED="+sobuoiDalam+" WHERE ID="+idOrder+"";
                            Statement stmt = connect.createStatement();
                            stmt.executeQuery(query);
                        }
                    }
                    catch (Exception ex){
                    }
                }else if (orderType.trim().equals("Tổng vệ sinh")){
                    try {
                        // Connect to database
                        if (connect == null){checkConnectDialog();}
                        else {
                            String query = "select * from ORDER_OVERVIEW where ID= '" + idOrder  + "'";
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
                            sobuoiDalam =sobuoiDalam+1;
                            String query = "UPDATE ORDER_OVERVEW SET ORDER_STATUS=N'Hoàn thành' WHERE ID="+idOrder+"";
                            Statement stmt = connect.createStatement();
                            stmt.executeQuery(query);
                        }
                    }
                    catch (Exception ex){
                    }
                }else {
                    try {
                        // Connect to database
                        if (connect == null){checkConnectDialog();}
                        else {
                            String query = "select * from ORDER_COOK where ID= '" + idOrder  + "'";
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
                            sobuoiDalam =sobuoiDalam+1;
                            String query = "UPDATE ORDER_COOK SET ORDER_STATUS=N'Hoàn thành' WHERE ID="+idOrder+"";
                            Statement stmt = connect.createStatement();
                            stmt.executeQuery(query);
                        }
                    }
                    catch (Exception ex){
                    }
                }
                if (!orderType.trim().equals("Tổng vệ sinh")){
                    try {
                        int amount=0;
                        String query = "SELECT * FROM EMPLOYEE WHERE PHONE_NUM='"+userSubmit+"'";
                        Statement stmt = connect.createStatement();
                        ResultSet rs = stmt.executeQuery(query);
                        if (rs.next()){
                            amount = rs.getInt("WORKED_AMOUNT")+1;
                        }
                        Log.d("BBB",amount+"");
                        String query1 = "UPDATE EMPLOYEE SET WORKED_AMOUNT="+amount+" WHERE PHONE_NUM='"+userSubmit+"'";
                        Statement stmt1 = connect.createStatement();
                        stmt1.executeQuery(query1);
                        connect.close();
                    }
                    catch (Exception ex){
                    }
                }else {
                    try {
                        int amountNV1=0;
                        int amountNV2=0;
                        int amountNV3=0;
                        if (areaType==1){
                            try {
                                String query = "SELECT * FROM EMPLOYEE WHERE PHONE_NUM='"+userSubmit+"'";
                                Statement stmt = connect.createStatement();
                                ResultSet rs = stmt.executeQuery(query);
                                if (rs.next()){
                                    amountNV1 = rs.getInt("WORKED_AMOUNT")+1;
                                }
                                String query1 = "SELECT * FROM EMPLOYEE WHERE PHONE_NUM='"+userSubmit2+"'";
                                Statement stmt1 = connect.createStatement();
                                ResultSet rs1 = stmt1.executeQuery(query1);
                                if (rs1.next()){
                                    amountNV2 = rs1.getInt("WORKED_AMOUNT")+1;
                                }
                                String query2 = "UPDATE EMPLOYEE SET WORKED_AMOUNT="+amountNV1+" WHERE PHONE_NUM='"+userSubmit+"'";
                                Statement stmt2 = connect.createStatement();
                                stmt2.executeQuery(query2);
                            }catch (Exception e){
                                String query2 = "UPDATE EMPLOYEE SET WORKED_AMOUNT="+amountNV2+" WHERE PHONE_NUM='"+userSubmit2+"'";
                                Statement stmt2 = connect.createStatement();
                                stmt2.executeQuery(query2);
                            }
                        }else {
                            try {
                                String query = "SELECT * FROM EMPLOYEE WHERE PHONE_NUM='"+userSubmit+"'";
                                Statement stmt = connect.createStatement();
                                ResultSet rs = stmt.executeQuery(query);
                                if (rs.next()){
                                    amountNV1 = rs.getInt("WORKED_AMOUNT")+1;
                                }
                                String query1 = "SELECT * FROM EMPLOYEE WHERE PHONE_NUM='"+userSubmit2+"'";
                                Statement stmt1 = connect.createStatement();
                                ResultSet rs1 = stmt1.executeQuery(query1);
                                if (rs1.next()){
                                    amountNV2 = rs1.getInt("WORKED_AMOUNT")+1;
                                }
                                try {
                                    String query3 = "SELECT * FROM EMPLOYEE WHERE PHONE_NUM='"+userSubmit3+"'";
                                    Statement stmt3 = connect.createStatement();
                                    ResultSet rs3 = stmt3.executeQuery(query3);
                                    if (rs3.next()){
                                        amountNV3 = rs1.getInt("WORKED_AMOUNT")+1;
                                    }
                                    String query4 = "UPDATE EMPLOYEE SET WORKED_AMOUNT="+amountNV3+" WHERE PHONE_NUM='"+userSubmit3+"'";
                                    Statement stmt4 = connect.createStatement();
                                    stmt4.executeQuery(query4);
                                }catch (Exception e){
                                    String query2 = "UPDATE EMPLOYEE SET WORKED_AMOUNT="+amountNV1+" WHERE PHONE_NUM='"+userSubmit+"'";
                                    Statement stmt2 = connect.createStatement();
                                    stmt2.executeQuery(query2);
                                }
                            }catch (Exception e){
                                String query2 = "UPDATE EMPLOYEE SET WORKED_AMOUNT="+amountNV2+" WHERE PHONE_NUM='"+userSubmit2+"'";
                                Statement stmt2 = connect.createStatement();
                                stmt2.executeQuery(query2);
                            }
                        }
                        connect.close();
                    }
                    catch (Exception ex){
                    }
                }

            }
        });

        PushDownAnim.setPushDownAnimTo(linCancelStaff).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("BBB","aaaaaaaaa");
                DateFormat formatter = new SimpleDateFormat("HH:mm dd/MM/yyyy");
                Date date = new Date();
                String today = formatter.format(date);
                String timecancelString = today.substring(0,2);
                String timeValidString = txtGioCa.getText().toString().substring(0,2);
                if (timecancelString.substring(1,2).equals(":")){
                    timecancelString= timecancelString.substring(0,1);
                }
                if (timeValidString.substring(1,2).equals(":")){
                    timeValidString = timeValidString.substring(0,1);
                }
                Integer timecancel = Integer.valueOf(timecancelString);
                Integer timeValid = Integer.valueOf(timeValidString);
                if (orderType.trim().equals("Dùng lẻ") || orderType.trim().equals("Nấu ăn")){
                    if (txtNgay.getText().toString().substring(0,5).equals(today.substring(6,11))
                            && timeValid-timecancel<3){
                        checkCancel();
                    }else {
                        cancelNVDialog();
                    }
                }else if (orderType.trim().equals("Định kỳ")){
                    cancelNVDialog();
                }else if (orderType.trim().equals("Tổng vệ sinh")){
                    if (txtNgay.getText().toString().substring(0,5).equals(today.substring(6,11))
                            && timeValid-timecancel<3){
                        checkCancel();
                    }else {
                        if (userSubmit2.trim().equals("")){
                            cancelNVDialog();
                        }else {
                            relCancelTVS.setVisibility(View.VISIBLE);
                            rbtnNV1.setText(txtStaffName.getText().toString());
                            rbtnNV2.setText(txtStaffName2.getText().toString());
                            if (!userSubmit3.trim().equals("")){
                                rbtnNV3.setVisibility(View.VISIBLE);
                                rbtnNV3.setText(txtStaffName3.getText().toString());
                            }
                        }
                    }
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
                String timeValidString = txtGioCa.getText().toString().substring(0,2);
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
                    baonghi.putExtra("orderType",orderType);
                    baonghi.putExtra("userSubmit",userSubmit);
                    startActivity(baonghi);
                }
            }
        });

        PushDownAnim.setPushDownAnimTo(linCall).setOnClickListener(new View.OnClickListener() {
            @SuppressLint("MissingPermission")
            @Override
            public void onClick(View view) {
                String s = "tel:" + userSubmit;
                Intent intent = new Intent(Intent.ACTION_CALL);
                intent.setData(Uri.parse(s));
                startActivity(intent);
            }
        });

        radioGroupTVS.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                if (i == R.id.btn_radio_nv1){
                    cancel =1;
                }
                if (i == R.id.btn_radio_nv2){
                    cancel=2;
                }
                if (i == R.id.btn_radio_nv3){
                    cancel=3;
                }
            }
        });
        PushDownAnim.setPushDownAnimTo(btnCloseTVS).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                relCancelTVS.setVisibility(View.GONE);
            }
        });
        PushDownAnim.setPushDownAnimTo(btnConfirmTVS).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TextView title = new TextView(DetailOrderActivity.this);
                title.setText("THÔNG BÁO");
                title.setPadding(10, 30, 10, 10);
                title.setGravity(Gravity.CENTER);
                title.setTextSize(20);
                title.setTextColor(Color.RED);

                AlertDialog.Builder builder = new AlertDialog.Builder(DetailOrderActivity.this);
                builder.setCustomTitle(title);
                builder.setMessage("Nếu hủy nhân viên này sẽ mất cơ hội việc làm. Bạn có muốn tiếp tục ?");
                builder.setCancelable(false);
                builder.setNegativeButton("Tiếp tục", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        if (cancel==1){
                            try {
                                com.example.coosincustomer.ConnectionDB conStr=new com.example.coosincustomer.ConnectionDB();
                                connect =conStr.CONN();
                                // Connect to database
                                if (connect == null){checkConnectDialog();}
                                else {
                                    String query = "UPDATE ORDER_OVERVIEW SET USER_SUBMIT1='"+userSubmit2+"' ,USER_SUBMIT2='"+userSubmit3+"' ,USER_SUBMIT3=''  WHERE ID="+idOrder+"";
                                    Log.d("BBB",query);
                                    Statement stmt = connect.createStatement();
                                    stmt.executeQuery(query);
                                    connect.close();
                                }
                            }
                            catch (Exception ex){
                                Toast.makeText(getApplicationContext(),"Đã hủy thành công",Toast.LENGTH_LONG).show();
                            }
                        }else if (cancel==2){
                            avatarCus2.setImageResource(R.drawable.ic_avatar_staff_disable);
                            txtStaffName2.setText("Đang tìm");
                            txtStaffName2.setTextColor(getResources().getColor(R.color.disable));
                            try {
                                com.example.coosincustomer.ConnectionDB conStr=new com.example.coosincustomer.ConnectionDB();
                                connect =conStr.CONN();
                                // Connect to database
                                if (connect == null){checkConnectDialog();}
                                else {
                                    String query = "UPDATE ORDER_OVERVIEW SET USER_SUBMIT2='"+userSubmit3+"' ,USER_SUBMIT3=''  WHERE ID="+idOrder+"";
                                    Statement stmt = connect.createStatement();
                                    stmt.executeQuery(query);
                                    connect.close();
                                }
                            }
                            catch (Exception ex){
                                Toast.makeText(getApplicationContext(),"Đã hủy thành công",Toast.LENGTH_LONG).show();
                            }
                        }else {
                            avatarCus3.setImageResource(R.drawable.ic_avatar_staff_disable);
                            txtStaffName3.setText("Đang tìm");
                            txtStaffName3.setTextColor(getResources().getColor(R.color.disable));
                            try {
                                com.example.coosincustomer.ConnectionDB conStr=new com.example.coosincustomer.ConnectionDB();
                                connect =conStr.CONN();
                                // Connect to database
                                if (connect == null){checkConnectDialog();}
                                else {
                                    String query = "UPDATE ORDER_OVERVIEW SET USER_SUBMIT3=''  WHERE ID="+idOrder+"";
                                    Statement stmt = connect.createStatement();
                                    stmt.executeQuery(query);
                                    connect.close();
                                }
                            }
                            catch (Exception ex){
                                Toast.makeText(getApplicationContext(),"Đã hủy thành công",Toast.LENGTH_LONG).show();
                            }
                        }

                    }
                });
                builder.setPositiveButton("Không", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                });
                AlertDialog alertDialog = builder.create();
                alertDialog.show();

                TextView messageText = alertDialog.findViewById(android.R.id.message);
                messageText.setGravity(Gravity.CENTER);
            }
        });

        PushDownAnim.setPushDownAnimTo(rel_staff1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ShowPopup(userSubmit);
            }
        });

        PushDownAnim.setPushDownAnimTo(rel_staff2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!txtStaffName2.getText().toString().trim().equals("Đang tìm")) {
                    ShowPopup(userSubmit2);
                }
            }
        });

        PushDownAnim.setPushDownAnimTo(rel_staff3).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!txtStaffName3.getText().toString().trim().equals("Đang tìm")){
                    ShowPopup(userSubmit3);
                }
            }
        });

        PushDownAnim.setPushDownAnimTo(linChat).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DetailOrderActivity.this,ChatActivity.class);
                intent.putExtra("receiver",userSubmit);
                startActivity(intent);
            }
        });
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
        relSobuoicon =findViewById(R.id.rel_sobuoicon);
        txtSobuoicon = findViewById(R.id.txt_sobuoicon);
        rel_staff2 = findViewById(R.id.rel_staff2);
        rel_staff3 = findViewById(R.id.rel_staff3);
        txtDientich = findViewById(R.id.txt_dientich);
        txtStaffName2 = findViewById(R.id.txt_staff_name2);
        txtStaffName3 = findViewById(R.id.txt_staff_name3);
        txtStaffPhone2 = findViewById(R.id.txt_staff_phone2);
        txtStaffPhone3 = findViewById(R.id.txt_staff_phone3);
        relDientich = findViewById(R.id.rel_dientich);
        avatarCus2 = findViewById(R.id.avatar_customer2);
        avatarCus3 = findViewById(R.id.avatar_customer3);
        relNauAn = findViewById(R.id.rel_nauan);
        relMaxMarket =  findViewById(R.id.rel_max_market);
        txtSoMon = findViewById(R.id.txt_somon);
        txtTenMon = findViewById(R.id.txt_ten_mon);
        txtKhauVi = findViewById(R.id.txt_khauvi);
        txtTraiCay = findViewById(R.id.txt_traicay);
        txtMaxMarket = findViewById(R.id.txt_max_market);
        labelSonguoi = findViewById(R.id.labelGio_to_labelSonguoi);
        relCancelTVS = findViewById(R.id.rel_cancelNV_TVS);
        radioGroupTVS = findViewById(R.id.group_radio);
        rbtnNV1 = findViewById(R.id.btn_radio_nv1);
        rbtnNV2 = findViewById(R.id.btn_radio_nv2);
        rbtnNV3 = findViewById(R.id.btn_radio_nv3);
        btnCloseTVS = findViewById(R.id.btn_close_cancel);
        btnConfirmTVS = findViewById(R.id.btn_confirm_cancel);
        avatarCus1 = findViewById(R.id.avatar_customer);
        rel_staff1 = findViewById(R.id.rel_staff1);
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
                        txtGioCa.setText(rs.getString("TIME_WORK").substring(rs.getString("TIME_WORK").length()-13,rs.getString("TIME_WORK").length()-1));
                        if (txtGioCa.getText().toString().substring(0,1).equals("(")){
                            txtGioCa.setText(rs.getString("TIME_WORK").substring(rs.getString("TIME_WORK").length()-14,rs.getString("TIME_WORK").length()-1));
                        }
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
                        txtMDH.setText("MĐH: DL"+idOrder);
                        connect.close();
                    }
                }
            }
            catch (Exception ex)
            {
                Toast.makeText(getApplicationContext(), ex.getMessage(), Toast.LENGTH_LONG).show();
            }
        }
        else if (orderType.trim().equals("Định kỳ")){
            try
            {
                idOrderString="DK"+idOrder;
                // Connect to database
                if (connect == null)
                {
                    checkConnectDialog();
                }
                else
                {
                    // Change below query according to your own database.
                    String query = "select * from ORDER_MULTI where ID= '" + idOrder  + "'";
                    Statement stmt = connect.createStatement();
                    ResultSet rs = stmt.executeQuery(query);
                    if (rs.next())
                    {
                        txtOrderType.setText(orderType);
                        txtThu.setText(rs.getString("SCHEDULE"));
                        txtNgay.setText(rs.getString("DATE_START")+"\n"+rs.getString("DATE_END"));
                        txtCa.setText(rs.getString("TIME_WORK").substring(0,rs.getString("TIME_WORK").length()-15));
                        txtGioCa.setText(rs.getString("TIME_WORK").substring(rs.getString("TIME_WORK").length()-13,rs.getString("TIME_WORK").length()-1));
                        if (txtGioCa.getText().toString().substring(0,1).equals("(")){
                            txtGioCa.setText(rs.getString("TIME_WORK").substring(rs.getString("TIME_WORK").length()-14,rs.getString("TIME_WORK").length()-1));
                        }
                        txtAddress.setText(rs.getString("ADDRESS_ORDER"));
                        txtSoGio.setText(rs.getString("TOTAL_TIME"));
                        DecimalFormat decimalFormat = new DecimalFormat("#,###");
                        String totalGiaString = decimalFormat.format(rs.getInt("TOTAL_PRICE"));
                        txtTongTien.setText(totalGiaString+"đ");
                        txtPaymentType.setText(rs.getString("PAYMENT_TYPE"));
                        orderStatus = rs.getString("ORDER_STATUS");
                        userSubmit = rs.getString("USER_SUBMIT");
                        txtOrderType.setText(orderType);
                        paymentStatus=rs.getInt("PAYMENT_STATUS");
                        int sobuoi = rs.getInt("TOTAL_BUOI");
                        sobuoiDalam = rs.getInt("TOTAL_WORKED");
                        int sobuoicon = sobuoi-sobuoiDalam;
                        txtSobuoicon.setText(sobuoicon+" buổi");
                        txtSoBuoi.setText(sobuoi+" buổi");
                        relSobuoicon.setVisibility(View.VISIBLE);
                        txtMDH.setText("MĐH: DK"+idOrder);
                        connect.close();
                    }
                }
            }
            catch (Exception ex)
            {
                Toast.makeText(getApplicationContext(), ex.getMessage(), Toast.LENGTH_LONG).show();
            }
        }else if (orderType.trim().equals("Tổng vệ sinh")){
            try
            {
                idOrderString="TVS"+idOrder;
                // Connect to database
                if (connect == null)
                {
                    checkConnectDialog();
                }
                else
                {
                    // Change below query according to your own database.
                    String query = "select * from ORDER_OVERVIEW where ID= '" + idOrder  + "'";
                    Statement stmt = connect.createStatement();
                    ResultSet rs = stmt.executeQuery(query);
                    if (rs.next())
                    {
                        txtOrderType.setText(orderType);
                        txtThu.setText(rs.getString("DATE_WORK").substring(0,rs.getString("DATE_WORK").length()-12));
                        txtNgay.setText(rs.getString("DATE_WORK").substring(rs.getString("DATE_WORK").length()-10));
                        txtGioCa.setText(rs.getString("TIME_START"));
                        txtCa.setText("Giờ bắt đầu");
                        txtAddress.setText(rs.getString("ADDRESS_ORDER"));
                        areaType = rs.getInt("AREA_TYPE");
                        if (rs.getInt("AREA_TYPE")==2){
                            txtSoGio.setText("3h");
                        }else txtSoGio.setText("4h");
                        relDientich.setVisibility(View.VISIBLE);
                        if (rs.getInt("AREA_TYPE")==1){
                            txtDientich.setText("80m\u00B2");
                            rel_staff2.setVisibility(View.VISIBLE);
                        }else if (rs.getInt("AREA_TYPE")==2){
                            rel_staff2.setVisibility(View.VISIBLE);
                            rel_staff3.setVisibility(View.VISIBLE);
                            txtDientich.setText("100m\u00B2");
                        }else {
                            txtDientich.setText("150m\u00B2");
                            rel_staff2.setVisibility(View.VISIBLE);
                            rel_staff3.setVisibility(View.VISIBLE);
                        }
                        DecimalFormat decimalFormat = new DecimalFormat("#,###");
                        String totalGiaString = decimalFormat.format(rs.getInt("TOTAL_PRICE"));
                        txtTongTien.setText(totalGiaString+"đ");
                        txtPaymentType.setText(rs.getString("PAYMENT_TYPE"));
                        orderStatus = rs.getString("ORDER_STATUS");
                        userSubmit = rs.getString("USER_SUBMIT1");
                        userSubmit2 = rs.getString("USER_SUBMIT2");
                        userSubmit3 = rs.getString("USER_SUBMIT3");
                        txtOrderType.setText(orderType);
                        paymentStatus=rs.getInt("PAYMENT_STATUS");
                        txtMDH.setText("MĐH: TVS"+idOrder);
                        connect.close();
                    }
                }
            }
            catch (Exception ex)
            {
                Toast.makeText(getApplicationContext(), ex.getMessage(), Toast.LENGTH_LONG).show();
            }
        }else {
            try
            {
                relNauAn.setVisibility(View.VISIBLE);
                idOrderString="NA"+idOrder;
                // Connect to database
                if (connect == null)
                {
                    checkConnectDialog();
                }
                else
                {
                    // Change below query according to your own database.
                    String query = "select * from ORDER_COOK where ID= " + idOrder  + "";
                    Statement stmt = connect.createStatement();
                    ResultSet rs = stmt.executeQuery(query);
                    if (rs.next())
                    {
                        txtOrderType.setText(orderType);
                        txtThu.setText(rs.getString("DATE_WORK").substring(0,rs.getString("DATE_WORK").length()-12));
                        txtNgay.setText(rs.getString("DATE_WORK").substring(rs.getString("DATE_WORK").length()-10));
                        txtCa.setText("Giờ ăn");
                        txtGioCa.setText(rs.getString("TIME_WORK"));
                        txtAddress.setText(rs.getString("ADDRESS_ORDER"));
                        txtSoGio.setText(rs.getString("PEOPLE_AMOUNT")+" người");
                        labelSonguoi.setText("Số người ăn");
                        DecimalFormat decimalFormat = new DecimalFormat("#,###");
                        String totalGiaString = decimalFormat.format(rs.getInt("TOTAL_PRICE"));
                        if (rs.getInt("MAX_MARKET_PRICE")!=0){
                            String maxMarketString = decimalFormat.format(rs.getInt("MAX_MARKET_PRICE"));
                            relMaxMarket.setVisibility(View.VISIBLE);
                            txtMaxMarket.setText(maxMarketString+"đ");
                        }
                        txtTraiCay.setText(rs.getString("FRUIT"));
                        txtKhauVi.setText(rs.getString("TASTE"));
                        txtSoMon.setText(rs.getString("DISH_AMOUNT")+" món");
                        txtTenMon.setText(rs.getString("DISH_NAME"));
                        txtTongTien.setText(totalGiaString+"đ");
                        txtPaymentType.setText(rs.getString("PAYMENT_TYPE"));
                        orderStatus = rs.getString("ORDER_STATUS");
                        userSubmit = rs.getString("USER_SUBMIT");
                        txtOrderType.setText(orderType);
                        paymentStatus=rs.getInt("PAYMENT_STATUS");
                        txtMDH.setText("MĐH: NA"+idOrder);
                        connect.close();
                    }
                }
            }
            catch (Exception ex)
            {
                Log.d("BBB",ex.getMessage());
            }
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
                finish();
            }
        }
        catch (Exception ex){
        }
        if (orderType.trim().equals("Dùng lẻ")){
            Intent intent = new Intent(DetailOrderActivity.this,HistoryActivity.class);
            startActivity(intent);
        }else if(orderType.trim().equals("Định kỳ")){
            Intent intent = new Intent(DetailOrderActivity.this,HomeActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
        }
    }

    @Override
    public void onNegativeButtonClicked() {
        if (orderType.trim().equals("Dùng lẻ")){
            Intent intent = new Intent(DetailOrderActivity.this,HistoryActivity.class);
            startActivity(intent);
        }else if(orderType.trim().equals("Định kỳ")){
            Intent intent = new Intent(DetailOrderActivity.this,HomeActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
        }
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
                if (orderType.trim().equals("Dùng lẻ")){
                    try {
                        com.example.coosincustomer.ConnectionDB conStr=new com.example.coosincustomer.ConnectionDB();
                        connect =conStr.CONN();
                        // Connect to database
                        if (connect == null){checkConnectDialog();}
                        else {
                            String query = "UPDATE ORDER_SINGLE SET STATUS_ORDER=N'Đang tìm kiếm NV', USER_SUBMIT='',DATE_SUBMIT='' WHERE ID="+idOrder+"";
                            Statement stmt = connect.createStatement();
                            stmt.executeQuery(query);
                            connect.close();
                        }
                    }
                    catch (Exception ex){

                    }
                }else if (orderType.trim().equals("Định kỳ")){
                    try {
                        com.example.coosincustomer.ConnectionDB conStr=new com.example.coosincustomer.ConnectionDB();
                        connect =conStr.CONN();
                        // Connect to database
                        if (connect == null){checkConnectDialog();}
                        else {
                            String query = "UPDATE ORDER_MULTI SET ORDER_STATUS=N'Đang tìm kiếm NV', USER_SUBMIT='',DATE_SUBMIT='' WHERE ID="+idOrder+"";
                            Statement stmt = connect.createStatement();
                            stmt.executeQuery(query);
                            connect.close();
                        }
                    }
                    catch (Exception ex){

                    }
                }else if (orderType.trim().equals("Tổng vệ sinh")){
                    try {
                        com.example.coosincustomer.ConnectionDB conStr=new com.example.coosincustomer.ConnectionDB();
                        connect =conStr.CONN();
                        // Connect to database
                        if (connect == null){checkConnectDialog();}
                        else {
                            String query = "UPDATE ORDER_OVERVIEW SET ORDER_STATUS=N'Đang tìm kiếm NV', USER_SUBMIT1='' WHERE ID="+idOrder+"";
                            Statement stmt = connect.createStatement();
                            stmt.executeQuery(query);
                            connect.close();
                        }
                    }
                    catch (Exception ex){

                    }
                }

            }
        });
        builder.setPositiveButton("Không", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();

        TextView messageText = alertDialog.findViewById(android.R.id.message);
        messageText.setGravity(Gravity.CENTER);
    }

    public void ShowPopup(final String staffphone){
        ImageView imgClosePopup;
        final Button btnAddFavotite,btnRemoveFavorite;
        LinearLayout linCalendarStaff;
        final TextView txtIdStaff,txtStaffNameInfo,txtStaffType,txtStaffPhonePopup,txtDanhGia,txtYTAmount,txtSocadalam;
        dialog.setContentView(R.layout.popup_menu);
        imgClosePopup = dialog.findViewById(R.id.close_popup);
        btnAddFavotite = dialog.findViewById(R.id.btn_add_favorite);
        txtIdStaff = dialog.findViewById(R.id.txt_mnv);
        txtStaffNameInfo = dialog.findViewById(R.id.staff_name);
        txtStaffType = dialog.findViewById(R.id.txt_staff_type);
        txtStaffPhonePopup = dialog.findViewById(R.id.txt_staff_phone_popup);
        txtDanhGia = dialog.findViewById(R.id.txt_danhgia);
        txtYTAmount = dialog.findViewById(R.id.txt_favorite_amount);
        txtSocadalam = dialog.findViewById(R.id.worked_amount);
        btnRemoveFavorite = dialog.findViewById(R.id.btn_remove_favorite);

        try {
            com.example.coosincustomer.ConnectionDB conStr=new com.example.coosincustomer.ConnectionDB();
            connect =conStr.CONN();
            // Connect to database
            if (connect == null){checkConnectDialog();}
            else {
                String query = "SELECT * FROM EMPLOYEE WHERE PHONE_NUM='"+staffphone+"'";
                Statement stmt = connect.createStatement();
                ResultSet rs =  stmt.executeQuery(query);
                if (rs.next()){
                    txtStaffNameInfo.setText(rs.getString("FULL_NAME"));
                    txtYTAmount.setText(rs.getInt("FAVORITE")+"");
                    txtSocadalam.setText(rs.getInt("WORKED_AMOUNT")+"");
                    txtStaffPhonePopup.setText("SĐT: "+staffphone);
                    if (rs.getInt("EMP_TYPE")==1){
                        txtIdStaff.setText("NVVS"+rs.getString("ID"));
                        txtStaffType.setText("Nhân viên vệ sinh");
                    }else if (rs.getInt("EMP_TYPE")==2){
                        txtIdStaff.setText("NVNA"+rs.getString("ID"));
                        txtStaffType.setText("Nhân viên nấu ăn");
                    }else {
                        txtIdStaff.setText("NVDN"+rs.getString("ID"));
                        txtStaffType.setText("Nhân viên đa năng");
                    }
                }
                String query1 = "SELECT TOP 1 * FROM DANHGIA WHERE EMP_ID='"+staffphone+"' ORDER BY ID DESC";
                Statement stmt1 = connect.createStatement();
                ResultSet rs1 =  stmt1.executeQuery(query1);
                if (rs1.next()){
                    txtDanhGia.setText(rs1.getString("CONTENT"));
                }else txtDanhGia.setText("Không có đánh giá nào");
                connect.close();
            }
        }
        catch (Exception ex){
            Log.d("BBB",ex.getMessage());
        }

        PushDownAnim.setPushDownAnimTo(btnAddFavotite).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DateFormat formatter = new SimpleDateFormat("HH:mm dd/MM/yyyy");
                Date date = new Date();
                String create_at = formatter.format(date);
                try {
                    com.example.coosincustomer.ConnectionDB conStr=new com.example.coosincustomer.ConnectionDB();
                    connect =conStr.CONN();
                    // Connect to database
                    if (connect == null){checkConnectDialog();}
                    else {
                        String query = "INSERT INTO FAVORITE (CUS_PHONE,STAFF_PHONE,CREATE_AT) VALUES('"+account+"','"+staffphone+"','"+create_at+"')";
                        Statement stmt = connect.createStatement();
                        stmt.executeQuery(query);
                        connect.close();
                    }
                }
                catch (Exception ex){
                    Toast.makeText(getApplicationContext(),"Đã thêm vào danh sách yêu tích",Toast.LENGTH_LONG).show();
                }
                try {
                    com.example.coosincustomer.ConnectionDB conStr=new com.example.coosincustomer.ConnectionDB();
                    connect =conStr.CONN();
                    // Connect to database
                    if (connect == null){checkConnectDialog();}
                    else {
                        int favorite=0;
                        String query = "SELECT * FROM EMPLOYEE WHERE PHONE_NUM='"+staffphone+"'";
                        Statement stmt = connect.createStatement();
                        ResultSet rs = stmt.executeQuery(query);
                        if (rs.next()){
                            favorite = rs.getInt("FAVORITE")+1;
                        }
                        txtYTAmount.setText(favorite+"");
                        String query1 = "UPDATE EMPLOYEE SET FAVORITE="+favorite+" WHERE PHONE_NUM='"+staffphone+"'";
                        Statement stmt1 = connect.createStatement();
                        stmt1.executeQuery(query1);
                        connect.close();
                    }
                }
                catch (Exception ex){

                }
                if (btnAddFavotite.getVisibility()==View.VISIBLE){
                    btnAddFavotite.setVisibility(View.GONE);
                }else btnAddFavotite.setVisibility(View.VISIBLE);
                if (btnRemoveFavorite.getVisibility()==View.GONE){
                    btnRemoveFavorite.setVisibility(View.VISIBLE);
                }else btnRemoveFavorite.setVisibility(View.GONE);
            }
        });

        //BO THICH
        try {
            com.example.coosincustomer.ConnectionDB conStr=new com.example.coosincustomer.ConnectionDB();
            connect =conStr.CONN();
            // Connect to database
            if (connect == null){checkConnectDialog();}
            else {
                String query = "SELECT * FROM FAVORITE WHERE CUS_PHONE='"+account+"' AND STAFF_PHONE='"+staffphone+"'";
                Statement stmt = connect.createStatement();
                ResultSet rs = stmt.executeQuery(query);
                if (rs.next()){
                    btnRemoveFavorite.setVisibility(View.VISIBLE);
                    btnAddFavotite.setVisibility(View.GONE);
                }else {
                    btnRemoveFavorite.setVisibility(View.GONE);
                    btnAddFavotite.setVisibility(View.VISIBLE);
                }
                connect.close();
            }
        }
        catch (Exception ex){

        }

        PushDownAnim.setPushDownAnimTo(btnRemoveFavorite).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    com.example.coosincustomer.ConnectionDB conStr=new com.example.coosincustomer.ConnectionDB();
                    connect =conStr.CONN();
                    // Connect to database
                    if (connect == null){checkConnectDialog();}
                    else {
                        String query = "DELETE FROM FAVORITE WHERE CUS_PHONE='"+account+"' AND STAFF_PHONE='"+staffphone+"'";
                        Statement stmt = connect.createStatement();
                        stmt.executeQuery(query);
                        connect.close();
                    }
                }
                catch (Exception ex){

                }
                try {
                    com.example.coosincustomer.ConnectionDB conStr=new com.example.coosincustomer.ConnectionDB();
                    connect =conStr.CONN();
                    // Connect to database
                    if (connect == null){checkConnectDialog();}
                    else {
                        int favorite=0;
                        String query = "SELECT * FROM EMPLOYEE WHERE PHONE_NUM='"+staffphone+"'";
                        Statement stmt = connect.createStatement();
                        ResultSet rs = stmt.executeQuery(query);
                        if (rs.next()){
                            favorite = rs.getInt("FAVORITE")-1;
                        }
                        txtYTAmount.setText(favorite+"");
                        String query1 = "UPDATE EMPLOYEE SET FAVORITE="+favorite+" WHERE PHONE_NUM='"+staffphone+"'";
                        Statement stmt1 = connect.createStatement();
                        stmt1.executeQuery(query1);
                        connect.close();
                    }
                }
                catch (Exception ex){

                }
                if (btnAddFavotite.getVisibility()==View.VISIBLE){
                    btnAddFavotite.setVisibility(View.GONE);
                }else btnAddFavotite.setVisibility(View.VISIBLE);
                if (btnRemoveFavorite.getVisibility()==View.GONE){
                    btnRemoveFavorite.setVisibility(View.VISIBLE);
                }else btnRemoveFavorite.setVisibility(View.GONE);
            }
        });

        imgClosePopup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        dialog.setCancelable(false);
        dialog.show();
    }

}
