package com.example.coosincustomer;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.thekhaeng.pushdownanim.PushDownAnim;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

public class AccountEditInfoActivity extends AppCompatActivity {

    EditText edtUpdateFullname, edtUpdateEmail;
    ProgressBar progressBar;
    Button btnSave;
    Connection connect;
    String phone_num;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_edit_info);

        //anh xa
        edtUpdateFullname = findViewById(R.id.edt_update_fullname);
        edtUpdateEmail = findViewById(R.id.edt_update_email);
        btnSave = findViewById(R.id.btn_save);
        progressBar = findViewById(R.id.ProgressBar_update_tt);
        progressBar.setVisibility(View.GONE);

        //database
        StrictMode.ThreadPolicy policy= new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        //back button
        Toolbar toolbar = findViewById(R.id.toolbar_login);
        setSupportActionBar(toolbar);
//        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
//        getSupportActionBar().setDisplayShowTitleEnabled(false);
//        getSupportActionBar().setDisplayShowHomeEnabled(true);

        //lay so dien thoai
        SharedPreferences SP = getApplicationContext().getSharedPreferences("PHONE",0);
        phone_num = SP.getString("phone_num",null);

        PushDownAnim.setPushDownAnimTo(btnSave).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String full_name = edtUpdateFullname.getText().toString();
                String email = edtUpdateEmail.getText().toString();
                String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
                if (full_name.trim().equals("") || email.trim().equals("")){
                    edtUpdateFullname.setError("Chưa nhập họ và tên");
                    edtUpdateEmail.setError("Chưa nhập email");
                }else if (email.trim().matches(emailPattern)){
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
                            String query = "UPDATE CUSTOMER SET FULL_NAME=N'"+ full_name + "',EMAIL='"+ email +"'WHERE PHONE_NUM='"+ phone_num+"'";
                            Statement stmt = connect.createStatement();
                            stmt.executeQuery(query);
                        }
                    }
                    catch (Exception ex)
                    {

                    }
                    Intent intent1 = new Intent(AccountEditInfoActivity.this,AccountInfoActivity.class);
                    startActivity(intent1);
                    finish();
                }else{
                    Toast.makeText(AccountEditInfoActivity.this,"Địa chỉ email không đúng!",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    @Override
    public void onBackPressed() {
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_HOME);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }
}
