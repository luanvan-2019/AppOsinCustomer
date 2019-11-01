package com.example.coosincustomer;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.coosincustomer.Model.CheckLogined;
import com.google.firebase.auth.FirebaseAuth;
import com.thekhaeng.pushdownanim.PushDownAnim;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

public class AccountInfoActivity extends AppCompatActivity {

    TextView txtHoTen, txtEmail, txtSDT, txtDiaChi,txtUpdateInfo,txtLOGOUT;
    String phone_num;
    Connection connect;
    int REQUEST_CODE_UPDATE = 1997;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_info);

        //anh xa
        txtHoTen = findViewById(R.id.txt_input_hoten);
        txtDiaChi = findViewById(R.id.txt_input_address);
        txtEmail = findViewById(R.id.txt_input_email);
        txtSDT = findViewById(R.id.txt_input_sdt);
        txtUpdateInfo = findViewById(R.id.txt_chinhsuathongtin);
        txtLOGOUT = findViewById(R.id.logout);

        //back button
        Toolbar toolbar = findViewById(R.id.toolbar_account_info);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        //lay so dien thoai
        SharedPreferences SP = getApplicationContext().getSharedPreferences("PHONE",0);
        phone_num = SP.getString("phone_num",null);

        try
        {
            com.example.coosincustomer.ConnectionDB conStr=new com.example.coosincustomer.ConnectionDB();
            connect =conStr.CONN();        // Connect to database
            if (connect == null)
            {
                Toast.makeText(getApplicationContext(), "Không có kết nối mạng!", Toast.LENGTH_LONG).show();
            }
            else
            {
                // Change below query according to your own database.
                String query = "select * from CUSTOMER where PHONE_NUM= '" + phone_num  + "'";
                Statement stmt = connect.createStatement();
                ResultSet rs = stmt.executeQuery(query);
                if(rs.next())
                {
                    txtHoTen.setText(rs.getString("FULL_NAME"));
                    txtDiaChi.setText(rs.getString("ADDRESS"));
                    txtSDT.setText(rs.getString("PHONE_NUM"));
                    txtEmail.setText(rs.getString("EMAIL"));
                }
            }
        }
        catch (Exception ex)
        {
            Toast.makeText(getApplicationContext(), ex.getMessage(), Toast.LENGTH_LONG).show();

        }

        //update info
        PushDownAnim.setPushDownAnimTo(txtUpdateInfo).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AccountInfoActivity.this,AccountUpdateInfoActivity.class);
                intent.putExtra("hovaten",txtHoTen.getText().toString());
                intent.putExtra("email",txtEmail.getText().toString());
                startActivityForResult(intent,REQUEST_CODE_UPDATE);
            }
        });

        PushDownAnim.setPushDownAnimTo(txtLOGOUT).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                showAlertDialog();
            }
        });
    }
    //back button
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    //reload lai trang
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == REQUEST_CODE_UPDATE && resultCode == RESULT_OK && data != null){
            finish();
            Intent intent = new Intent(AccountInfoActivity.this,AccountInfoActivity.class);
            startActivity(intent);
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    public void showAlertDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Xác nhận");
        builder.setMessage("Bạn có muốn đăng xuất tài khoản?");
        builder.setCancelable(false);
        builder.setNegativeButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
                Intent intent = new Intent(AccountInfoActivity.this, MainActivity.class);
                CheckLogined.SharedPrefesSAVE(getApplicationContext(),"");
                FirebaseAuth.getInstance().signOut();
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
            }
        });
        builder.setPositiveButton("Hủy", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }
}
