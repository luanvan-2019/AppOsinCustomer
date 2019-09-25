package com.example.coosincustomer;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.coosincustomer.Model.CheckLogined;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        CheckUser();
        finish();
    }

    public void CheckUser(){
        Boolean check = Boolean.valueOf(CheckLogined.readSharedSetting(SplashActivity.this,"CoOsin","true"));
//        SharedPreferences SP = getApplicationContext().getSharedPreferences("PHONE",0);
//        String a = SP.getString("phone_num",null);
        Intent introIntent = new Intent(SplashActivity.this,MainActivity.class);
        introIntent.putExtra("phone_num",check);

        if (check) {
            startActivity(introIntent);
        }
        else {
            Intent intent = new Intent(SplashActivity.this,AccountInfoActivity.class);
            startActivity(intent);
        }
    }
}
