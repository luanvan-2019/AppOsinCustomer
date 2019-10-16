package com.example.coosincustomer;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.coosincustomer.Fragment.OrderFragment;
import com.thekhaeng.pushdownanim.PushDownAnim;

import java.text.DecimalFormat;

public class OrderSuccessActivity extends AppCompatActivity {

    TextView txtTongTien;
    Button btnDanhSachCaLam;
    Integer tongtien;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_success);

        //anh xa
        Toolbar toolbar = findViewById(R.id.toolbar_success);
        btnDanhSachCaLam = findViewById(R.id.btn_danhsach_calam);
        txtTongTien = findViewById(R.id.txt_sotien_success);

        //toolbar action
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        //nhan du lieu tu thanhtoan activity
        Intent intent = getIntent();
        if (intent != null){
            tongtien = intent.getIntExtra("tongtien",0);
            DecimalFormat decimalFormat = new DecimalFormat("#,###");
            String tongTienString = decimalFormat.format(tongtien);
            txtTongTien.setText(tongTienString+" đ");
        }

        PushDownAnim.setPushDownAnimTo(btnDanhSachCaLam).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                OrderFragment orderFragment = new OrderFragment();
                FragmentManager fragmentManager = getSupportFragmentManager();

                Bundle bundle = new Bundle();
                bundle.putString("check", "check");
                orderFragment.setArguments(bundle);

                fragmentManager.beginTransaction().addToBackStack(null).replace(R.id.frame_danhsach_calam, orderFragment).commit();
            }
        });

    }

    //toolbar back button
    @Override
    public boolean onSupportNavigateUp() {
        Intent intent = new Intent(OrderSuccessActivity.this, HomeActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        finish();
        return true;
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(OrderSuccessActivity.this, HomeActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        finish();
    }
}
