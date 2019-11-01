package com.example.coosincustomer;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Gravity;
import android.view.MenuItem;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.coosincustomer.Fragment.DifferenceFragment;
import com.example.coosincustomer.Fragment.FavoriteFragment;
import com.example.coosincustomer.Fragment.HomeFragment;
import com.example.coosincustomer.Fragment.NotificationFragment;
import com.example.coosincustomer.Fragment.OrderFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class HomeActivity extends AppCompatActivity {

    private BottomNavigationView mMainNav;
    private FrameLayout mMainFrame;
    private HomeFragment homeFragment;
    private OrderFragment orderFragment;
    private FavoriteFragment favoriteFragment;
    private NotificationFragment notificationFragment;
    private DifferenceFragment differenceFragment;
    private FragmentManager mfragmentManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        mMainNav = findViewById(R.id.main_nav);
        mMainFrame = findViewById(R.id.main_frame);

        //anh xa fragment
        homeFragment = new HomeFragment();
        orderFragment = new OrderFragment();
        favoriteFragment = new FavoriteFragment();
        notificationFragment = new NotificationFragment();
        differenceFragment = new DifferenceFragment();

        setFragment(homeFragment);

        //nav action
        mMainNav.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                LoadingDialog loadingDialog = new LoadingDialog();
                loadingDialog.loading(HomeActivity.this);
                switch (menuItem.getItemId()){

                    case R.id.nav_home :
                        setFragment(homeFragment);
                        return true;
                    case R.id.nav_ordered :
                        setFragment(orderFragment);
                        return true;
                    case R.id.nav_favorite :
                        setFragment(favoriteFragment);
                        return true;
                    case R.id.nav_notification :
                        setFragment(notificationFragment);
                        return true;
                    case R.id.nav_difference :
                        setFragment(differenceFragment);
                        return true;
                        default:
                            return false;
                }
            }
        });

    }
    private void setFragment(Fragment fragment) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.main_frame, fragment);
        fragmentTransaction.commit();
    }

    @Override
    public void onBackPressed() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Xác nhận");
        builder.setMessage("Bạn muốn đóng ứng dụng?");
        builder.setCancelable(false);
        builder.setPositiveButton("Ở lại", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        builder.setNegativeButton("Thoát", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Intent intent = new Intent(Intent.ACTION_MAIN);
                intent.addCategory(Intent.CATEGORY_HOME);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
            }
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

//    public void loaddingDialog(){
//
//        AlertDialog.Builder builder = new AlertDialog.Builder(this);
//        builder.setMessage("Đang nạp dữ liệu...");
//        builder.setCancelable(false);
//        final AlertDialog alertDialog = builder.create();
//        alertDialog.show();
//        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
//        layoutParams.gravity = Gravity.CENTER;
//        layoutParams.setMargins(20,20,20,20);
//
//        TextView messageText = alertDialog.findViewById(android.R.id.message);
//        messageText.setGravity(Gravity.CENTER);
//
//        final Handler handler  = new Handler();
//        final Runnable runnable = new Runnable() {
//            @Override
//            public void run() {
//                if (alertDialog.isShowing()) {
//                    alertDialog.dismiss();
//                }
//            }
//        };
//
//        alertDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
//            @Override
//            public void onDismiss(DialogInterface dialog) {
//                handler.removeCallbacks(runnable);
//            }
//        });
//
//        handler.postDelayed(runnable, 500);
//    }
}
