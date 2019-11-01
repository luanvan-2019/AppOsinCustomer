package com.example.coosincustomer;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.thekhaeng.pushdownanim.PushDownAnim;

import java.sql.Connection;
import java.sql.Statement;

public class AccountUpdateInfoActivity extends AppCompatActivity {

    EditText edtUpdateFullname, edtUpdateEmail;
    ProgressBar progressBar;
    Button btnSave;
    Connection connect;
    String phone_num;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_update_info);

        //anh xa
        edtUpdateFullname = findViewById(R.id.edt_update_fullname);
        edtUpdateEmail = findViewById(R.id.edt_update_email);
        btnSave = findViewById(R.id.btn_save);
        progressBar = findViewById(R.id.ProgressBar_update_tt);
        progressBar.setVisibility(View.GONE);

        //toolbar action
        Toolbar toolbar = findViewById(R.id.toolbar_update_info);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        edtUpdateFullname.setText(getIntent().getStringExtra("hovaten"));
        edtUpdateEmail.setText(getIntent().getStringExtra("email"));

        //database
        StrictMode.ThreadPolicy policy= new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        //lay so dien thoai
        SharedPreferences SP = getApplicationContext().getSharedPreferences("PHONE",0);
        phone_num = SP.getString("phone_num",null);

        PushDownAnim.setPushDownAnimTo(btnSave).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String full_name = edtUpdateFullname.getText().toString();
                String email = edtUpdateEmail.getText().toString();
                String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
                if (full_name.trim().equals("") && email.trim().equals("")){
                    Toast.makeText(AccountUpdateInfoActivity.this,"Không có thông tin nào thay đổi!",Toast.LENGTH_SHORT).show();
                }else {
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
                            if (!full_name.trim().equals("") && email.trim().equals("")){
                                // Change below query according to your own database.
                                String query = "UPDATE CUSTOMER SET FULL_NAME=N'"+ full_name + "'WHERE PHONE_NUM='"+phone_num+"'";
                                Statement stmt = connect.createStatement();
                                stmt.executeQuery(query);
                                finish();
                            }else if(full_name.trim().equals("") && !email.trim().equals("")) {
                                if (email.trim().matches(emailPattern)){
                                    // Change below query according to your own database.
                                    String query = "UPDATE CUSTOMER SET EMAIL='"+ email + "'WHERE PHONE_NUM='"+phone_num+"'";
                                    Statement stmt = connect.createStatement();
                                    stmt.executeQuery(query);
                                    finish();
                                }else edtUpdateEmail.setError("Địa chỉ email không đúng");
                            }
                            else {
//                                 Change below query according to your own database.
                            String query = "UPDATE CUSTOMER SET FULL_NAME=N'"+ full_name + "',EMAIL='"+ email +"'WHERE PHONE_NUM='"+ phone_num+"'";
                            Statement stmt = connect.createStatement();
                            stmt.executeQuery(query);
                            finish();
                            }
                        }
                    }
                    catch (Exception ex)
                    {
                        Toast toast = Toast.makeText(getApplicationContext(),"Cập nhật thông tin thành công", Toast.LENGTH_LONG);
                        toast.setGravity(Gravity.CENTER, 0, 0);
                        toast.show();
                        Intent intent = new Intent();
                        intent.putExtra("a",full_name);
                        setResult(RESULT_OK, intent);
                        finish();
                    }
                }
            }
        });

    }

    //back button
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
