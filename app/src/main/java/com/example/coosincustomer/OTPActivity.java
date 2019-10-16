package com.example.coosincustomer;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.coosincustomer.Model.CheckLogined;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.TaskExecutors;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.thekhaeng.pushdownanim.PushDownAnim;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class OTPActivity extends AppCompatActivity {

    private String verificationid;
    private FirebaseAuth mAuth;
    private ProgressBar progressBar;
    private EditText editText;
    private TextView txtRepVerify,countdown;
    String phone_num,phonenumber;
    Connection connect;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otp);


        mAuth = FirebaseAuth.getInstance();

        progressBar = findViewById(R.id.progressbar);
        editText = findViewById(R.id.editTextCode);
        txtRepVerify = findViewById(R.id.txt_resend);
        countdown = findViewById(R.id.count_down);

        phonenumber = getIntent().getStringExtra("phonenumber");
        phone_num = getIntent().getStringExtra("phone_num");
        sendVerificationCode(phonenumber);

        //back button
        Toolbar toolbar = findViewById(R.id.toolbar_otp);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        //huy ma xac thuc sau 2phut
        CountDownCancelCode();

        //dem nguoc thoi gian khi gui lai ma
        CountDown();

        //gui lai ma
        PushDownAnim.setPushDownAnimTo(txtRepVerify).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CountDown();
                sendVerificationCode(phonenumber);
                Toast.makeText(getApplicationContext(),"Đã gửi lại mã xác thực đến "+phone_num,Toast.LENGTH_LONG).show();
            }
        });

        findViewById(R.id.buttonSignIn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String code = editText.getText().toString().trim();

                if ((code.isEmpty() || code.length() < 6)){

                    editText.setError("Chưa nhập mã hoặc mã không hợp lệ!");
                    editText.requestFocus();
                    return;
                }
                progressBar.setVisibility(View.VISIBLE);
                verifyCode(code);

            }
        });
    }

    private void verifyCode(String code){
        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(verificationid, code);
        signInWithCredential(credential);
    }

    private void signInWithCredential(PhoneAuthCredential credential) {
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
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
                                        Intent intent = new Intent(OTPActivity.this, HomeActivity.class);
                                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                        CheckLogined.SharedPrefesSAVE(getApplicationContext(),phone_num);
                                        startActivity(intent);
                                        connect.close();
                                    }
                                    else
                                    {
                                        DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
                                        Date date = new Date();
                                        String current_date = formatter.format(date);
                                        String query1 = "INSERT INTO CUSTOMER (PHONE_NUM,FULL_NAME,EMAIL,CREATE_AT,ADDRESS) VALUES ('" + phone_num + "','"+ "" + "','" +"" + "','"  + current_date + "','"+""+"')";
                                        Statement stmt1 = connect.createStatement();
                                        ResultSet rs1 = stmt1.executeQuery(query1);
                                        if(rs1.next())
                                        {
                                            Intent intent = new Intent(OTPActivity.this, HomeActivity.class);
                                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                            CheckLogined.SharedPrefesSAVE(getApplicationContext(),phone_num);
                                            startActivity(intent);
                                            connect.close();
                                        }
                                    }
                                }
                            }
                            catch (Exception ex)
                            {
//                        Toast.makeText(VerifyActivity.this,"Có lỗi không xác định vui lòng thử lại!",Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(OTPActivity.this, HomeActivity.class);
                                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                CheckLogined.SharedPrefesSAVE(getApplicationContext(),phone_num);
                                startActivity(intent);
                            }

                        } else {
                            Toast.makeText(OTPActivity.this, "Mã xác thực không chính xác!", Toast.LENGTH_LONG).show();
                            progressBar.setVisibility(View.GONE);
                        }
                    }

                });
    }

    private void sendVerificationCode(String number){

        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                number,
                60,
                TimeUnit.SECONDS,
                TaskExecutors.MAIN_THREAD,
                mCallBack
        );
    }

    private PhoneAuthProvider.OnVerificationStateChangedCallbacks
            mCallBack = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

        @Override
        public void onCodeSent(String s, PhoneAuthProvider.ForceResendingToken forceResendingToken) {
            super.onCodeSent(s, forceResendingToken);
            verificationid = s;
        }

        @Override
        public void onVerificationCompleted(PhoneAuthCredential phoneAuthCredential) {
            String code = phoneAuthCredential.getSmsCode();
            if (code != null){
                progressBar.setVisibility(View.VISIBLE);
                verifyCode(code);
            }
        }

        @Override
        public void onVerificationFailed(FirebaseException e) {
            Toast.makeText(OTPActivity.this, "Không thể gửi mã xác thực vui lòng kiểm tra lại số điện thoại!",Toast.LENGTH_LONG).show();

        }
    };

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

    //back button
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    public void CountDownCancelCode(){
        //countdown
        txtRepVerify.setEnabled(false);
        txtRepVerify.setTextColor(getResources().getColor(R.color.disable));
        new CountDownTimer(60000*2, 1000) {

            public void onTick(long millisUntilFinished) {

            }

            public void onFinish() {
                verificationid = "999999";
            }
        }.start();
    }
}
