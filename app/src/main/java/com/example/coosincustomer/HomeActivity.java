package com.example.coosincustomer;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager.widget.ViewPager;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.coosincustomer.Adapter.ViewPagerAdapter;
import com.example.coosincustomer.Fragment.DifferenceFragment;
import com.example.coosincustomer.Fragment.FavoriteFragment;
import com.example.coosincustomer.Fragment.NotificationFragment;
import com.example.coosincustomer.Fragment.OrderFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.thekhaeng.pushdownanim.PushDownAnim;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;


public class HomeActivity extends AppCompatActivity {

    ViewPager viewPager;
    ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(this);
    private LinearLayout dotsLayout;
    private BottomNavigationView mMainNav;
    private FrameLayout mMainFrame;
    private OrderFragment orderFragment;
    private FavoriteFragment favoriteFragment;
    private NotificationFragment notificationFragment;
    private DifferenceFragment differenceFragment;
    private FragmentManager mfragmentManager;
    private CardView cardViewDungLe,cardDinhKy;
    ImageView imgAccount;
    String phone_num;
    Connection connect;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        cardViewDungLe = findViewById(R.id.card_dungle);
        cardDinhKy = findViewById(R.id.card_dinh_ky);
        dotsLayout = findViewById(R.id.dotsContainer);
        viewPager = findViewById(R.id.viewpager_image);
        mMainNav = findViewById(R.id.main_nav);
        mMainFrame = findViewById(R.id.main_frame);
        viewPager.setAdapter(viewPagerAdapter);
        imgAccount = findViewById(R.id.avatar_account);
        prepareDots();

        //anh xa fragment
        orderFragment = new OrderFragment();
        favoriteFragment = new FavoriteFragment();
        notificationFragment = new NotificationFragment();
        differenceFragment = new DifferenceFragment();

//        //lay so dien thoai da luu khi dang nhap
        SharedPreferences SP = getApplicationContext().getSharedPreferences("PHONE",0);
        phone_num = SP.getString("phone_num",null);

        //manager account
        PushDownAnim.setPushDownAnimTo(imgAccount).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HomeActivity.this,AccountInfoActivity.class);
                startActivity(intent);
            }
        });

        //connect database and check information enough
        StrictMode.ThreadPolicy policy= new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
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
                String query = "select * from CUSTOMER where PHONE_NUM= '" + phone_num  + "' AND FULL_NAME= '" + "" + "'";
                Statement stmt = connect.createStatement();
                ResultSet rs = stmt.executeQuery(query);
                if(rs.next())
                {
                    //dialog
                    showAlertDialog();
                }
            }
        }
        catch (Exception ex)
        {
            Toast.makeText(getApplicationContext(), ex.getMessage(), Toast.LENGTH_LONG).show();

        }

        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new MyTimerTask(),2000,4000);

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if (viewPager.getCurrentItem() > viewPagerAdapter.getCount())
                    viewPager.setCurrentItem(0);
                prepareDots();
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        //nav action
        mMainNav.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()){

                    case R.id.nav_home :

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

        //order dung le
        PushDownAnim.setPushDownAnimTo(cardViewDungLe).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent dungle = new Intent(HomeActivity.this,OrderDungLeActivity.class);
                startActivity(dungle);
            }
        });

        //order dinh ky
        PushDownAnim.setPushDownAnimTo(cardDinhKy).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent dinhky = new Intent(HomeActivity.this,OrderDinhKyActivity.class);
                startActivity(dinhky);
            }
        });
    }
    private void setFragment(Fragment fragment) {

        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.main_frame, fragment);
        fragmentTransaction.addToBackStack("tag").commit();
    }

    public class MyTimerTask extends TimerTask{

        @Override
        public void run() {
            HomeActivity.this.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                        if (viewPager.getCurrentItem() == viewPagerAdapter.getCount() - 1) {
                            viewPager.setCurrentItem(0);
                        } else {
                            viewPager.setCurrentItem(viewPager.getCurrentItem() + 1);
                        }
                }
            });
        }
    }

    private void prepareDots(){
        if (dotsLayout.getChildCount() > 0){
            dotsLayout.removeAllViews();
        }
        ImageView dots[] = new ImageView[viewPagerAdapter.getCount()];
        for (int i = 0; i < viewPagerAdapter.getCount();i++){
            dots[i] = new ImageView(this);
            if (i == viewPager.getCurrentItem())
                dots[i].setImageDrawable(ContextCompat.getDrawable(this,R.drawable.active_dot));
            else dots[i].setImageDrawable(ContextCompat.getDrawable(this,R.drawable.inactive_dot));
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT);
            layoutParams.setMargins(4,0,4,0);
            dotsLayout.addView(dots[i],layoutParams);
        }
    }
    public void showAlertDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Cập nhật thông tin");
        builder.setMessage(R.string.cap_nhat_thong_tin);
        builder.setCancelable(false);
        builder.setNegativeButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
                Intent intent = new Intent(HomeActivity.this,AccountEditInfoActivity.class);
                startActivity(intent);
                finish();
            }
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
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
                startActivity(intent);
            }
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }
}
