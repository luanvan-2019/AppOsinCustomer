package com.example.coosincustomer;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.widget.Toolbar;

import com.google.android.gms.auth.api.phone.SmsRetriever;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Random;

public class LoginActivity extends AppCompatActivity {

    EditText edtInputPhone;
    Button btnContinue;
    ProgressBar progress_bar;
    int randomNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //back button
        Toolbar toolbar = findViewById(R.id.toolbar_login);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        TextView mTitle = (TextView) toolbar.findViewById(R.id.toolbar_title);

        edtInputPhone = findViewById(R.id.input_phone_num);
        btnContinue = findViewById(R.id.btn_continue);
        progress_bar = findViewById(R.id.ProgressBar);
        progress_bar.setVisibility(View.GONE);
        StrictMode.ThreadPolicy policy= new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        btnContinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progress_bar.setVisibility(View.VISIBLE);
                if (edtInputPhone.getText().toString().trim().equals("")) {
                    Toast.makeText(getApplicationContext(), "Vui lòng nhập số điện thoại để tiếp tục", Toast.LENGTH_LONG).show();
                    progress_bar.setVisibility(View.GONE);
                }
                else if (edtInputPhone.getText().toString().length() < 10) {
                    Toast.makeText(getApplicationContext(), "Số điện thoại không đúng", Toast.LENGTH_LONG).show();
                    progress_bar.setVisibility(View.GONE);
                }
                else{
                        try {
                            String apiKey = "apikey=" + "ySlxYoZ94hk-TeA2bWOq1wqFMzeGzeDMVROBaEo4MN";
                            Random random = new Random();
                            randomNumber = random.nextInt(9999);
                            String message = "&message=" + "CoOsin xin chao! " + " Ma xac thuc cua ban la: " + randomNumber;
                            String sender = "&sender=" + "CoOsin";
                            String numbers = "&numbers="  + "0084" + edtInputPhone.getText().toString().substring(1);

    // Send data
                            HttpURLConnection conn = (HttpURLConnection) new URL("https://api.txtlocal.com/send/?").openConnection();
                            String data = apiKey + numbers + message + sender;
                            conn.setDoOutput(true);
                            conn.setRequestMethod("POST");
                            conn.setRequestProperty("Content-Length", Integer.toString(data.length()));
                            conn.getOutputStream().write(data.getBytes("UTF-8"));
                            final BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                            final StringBuffer stringBuffer = new StringBuffer();
                            String line;
                            while ((line = rd.readLine()) != null) {
                                stringBuffer.append(line);
                            }
                            rd.close();
                            Toast.makeText(getApplicationContext(), "Mã xác thực đã được gửi đến số điện thoại", Toast.LENGTH_LONG).show();

    //return stringBuffer.toString();
                        } catch (Exception e) {
                            Toast.makeText(getApplicationContext(), "Lỗi gửi tin", Toast.LENGTH_LONG).show();
                        }
                        Intent intent = new Intent(LoginActivity.this, VerifyActivity.class);
                        intent.putExtra("phone_num", edtInputPhone.getText().toString());
                        intent.putExtra("verify_code", randomNumber);
                        startActivity(intent);
                        progress_bar.setVisibility(View.GONE);
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
