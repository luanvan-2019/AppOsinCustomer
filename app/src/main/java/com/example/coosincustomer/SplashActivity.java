package com.example.coosincustomer;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.coosincustomer.Model.CheckLogined;
import com.google.firebase.auth.FirebaseAuth;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        CheckUser();
        finish();
    }

    public void CheckUser(){
//        Boolean check = Boolean.valueOf(CheckLogined.readSharedSetting(SplashActivity.this,"CoOsin","true"));
////        SharedPreferences SP = getApplicationContext().getSharedPreferences("PHONE",0);
////        String a = SP.getString("phone_num",null);
//        Intent introIntent = new Intent(SplashActivity.this,MainActivity.class);
//        introIntent.putExtra("phone_num",check);
//
//        if (check) {
//            startActivity(introIntent);
//        }
//        else {
//            Intent intent = new Intent(SplashActivity.this,HomeActivity.class);
//            startActivity(intent);
//        }
        if (FirebaseAuth.getInstance().getCurrentUser() != null) {
            Intent intent = new Intent(this, HomeActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
        }
        else {
            Intent intent = new Intent(this, MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
        }
    }
}
