package com.example.coosincustomer;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.SharedMemory;
import android.os.StrictMode;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.coosincustomer.Model.CheckLogined;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.DateFormat;
import java.util.Random;
import java.text.SimpleDateFormat;
import java.util.Date;

public class VerifyActivity extends AppCompatActivity {

    EditText edtInputVerify;
    TextView txtRepVerify,txtSDT,countdown;
    Button btnLogin;
    String phone_num;
    ProgressBar pgbar_verify;
    int randomNumber1;
    int verify_code;
    Connection connect;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verify);

        Toolbar toolbar = findViewById(R.id.toolbar_login);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        TextView mTitle = (TextView) toolbar.findViewById(R.id.toolbar_title);

        edtInputVerify = findViewById(R.id.input_verify_num);
        txtRepVerify = findViewById(R.id.txt_rep_verify);
        btnLogin = findViewById(R.id.btn_login);
        pgbar_verify = findViewById(R.id.pgbar_verify);
        txtSDT = findViewById(R.id.label_sdt);
        countdown = findViewById(R.id.count_down);
        pgbar_verify.setVisibility(View.GONE);
        StrictMode.ThreadPolicy policy= new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        Intent intent = getIntent();
        if (intent != null){
            phone_num = intent.getStringExtra("phone_num");
            txtSDT.setText("+84 "+ phone_num.substring(1));
            verify_code = intent.getIntExtra("verify_code",Integer.MIN_VALUE);

            Log.d("BBB", ""+ verify_code);
        }


        CountDown();
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String _edtVerify = edtInputVerify.getText().toString();
                if (_edtVerify.trim().equals(""))
                    Toast.makeText(getApplicationContext(),"Bạn chưa nhập mã xác thực!", Toast.LENGTH_LONG).show();
                else if(1 == Integer.valueOf(edtInputVerify.getText().toString())){
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
                            String query = "select * from CUSTOMER where PHONE_NUM= '" + phone_num  +"'  ";
                            Statement stmt = connect.createStatement();
                            ResultSet rs = stmt.executeQuery(query);
                            if(rs.next())
                            {
                                //save data
                                CheckLogined.saveSharedSetting(VerifyActivity.this,"CoOsin","false");
                                CheckLogined.SharedPrefesSAVE(getApplicationContext(),phone_num);

                                Intent intent = new Intent(VerifyActivity.this, HomeActivity.class);
//                                intent.putExtra("phone_num",phone_num);
                                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                startActivity(intent);
                                connect.close();
                            }
                            else
                            {
                                //save data
                                CheckLogined.saveSharedSetting(VerifyActivity.this,"phone_num","false");
                                CheckLogined.SharedPrefesSAVE(getApplicationContext(),phone_num);

                                DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
                                Date date = new Date();
                                String current_date = formatter.format(date);
                                String query1 = "INSERT INTO CUSTOMER (PHONE_NUM,FULL_NAME,EMAIL,CREATE_AT,ADDRESS) VALUES ('" + phone_num + "','"+ "" + "','" +"" + "','"  + current_date + "','"+""+"')";
                                Statement stmt1 = connect.createStatement();
                                ResultSet rs1 = stmt1.executeQuery(query1);
                                if(rs1.next())
                                {
                                    Intent intent = new Intent(VerifyActivity.this, HomeActivity.class);
//                                    intent.putExtra("phone_num",phone_num);
                                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                    startActivity(intent);
                                    connect.close();
                                }
                            }
                        }
                    }
                    catch (Exception ex)
                    {
//                        Toast.makeText(VerifyActivity.this,"Có lỗi không xác định vui lòng thử lại!",Toast.LENGTH_SHORT).show();
////                        Intent intent = new Intent(VerifyActivity.this, HomeActivity.class);
////                        startActivity(intent);
                    }


                }else{
                    Toast.makeText(getApplicationContext(), "Sai mã xác thực!", Toast.LENGTH_LONG).show();
                }
            }
        });
        txtRepVerify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    String apiKey = "apikey=" + "ySlxYoZ94hk-TeA2bWOq1wqFMzeGzeDMVROBaEo4MN";
                    Random random = new Random();
                    randomNumber1 = random.nextInt(999999);
                    String message = "&message=" + "CoOsin xin chao! " + " Ma xac thuc cua ban la: " + randomNumber1;
                    String sender = "&sender=" + "CoOsin";
                    String numbers = "&numbers=" + "0084" + phone_num.substring(1);

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
                    verify_code = randomNumber1;
                    Log.d("BBB","" + verify_code);
//return stringBuffer.toString();
                } catch (Exception e) {
//System.out.println("Error SMS "+e);
///return "Error "+e;
                    Toast.makeText(getApplicationContext(), "Lỗi SMS " + e, Toast.LENGTH_LONG).show();
                    Toast.makeText(getApplicationContext(), "Lỗi " + e, Toast.LENGTH_LONG).show();
                }
                CountDown();
            }
        });
    }


//    back button
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
    public void CountDown(){
        //countdown
        txtRepVerify.setEnabled(false);
        txtRepVerify.setTextColor(getResources().getColor(R.color.disable));
        new CountDownTimer(60000, 1000) {

            public void onTick(long millisUntilFinished) {
                countdown.setText("(" + millisUntilFinished / 1000+"s)");
            }

            public void onFinish() {
                txtRepVerify.setTextColor(getResources().getColor(R.color.enable));
                txtRepVerify.setEnabled(true);
                countdown.setText("");
            }
        }.start();
    }
}
