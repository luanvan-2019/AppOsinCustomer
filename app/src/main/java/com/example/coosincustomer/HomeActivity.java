package com.example.coosincustomer;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
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
    private CardView cardViewDungLe;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        cardViewDungLe = findViewById(R.id.card_dungle);
        dotsLayout = findViewById(R.id.dotsContainer);
        viewPager = findViewById(R.id.viewpager_image);
        mMainNav = findViewById(R.id.main_nav);
        mMainFrame = findViewById(R.id.main_frame);
        viewPager.setAdapter(viewPagerAdapter);
        prepareDots();

        //anh xa fragment
        orderFragment = new OrderFragment();
        favoriteFragment = new FavoriteFragment();
        notificationFragment = new NotificationFragment();
        differenceFragment = new DifferenceFragment();


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

        PushDownAnim.setPushDownAnimTo(cardViewDungLe).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HomeActivity.this,OrderDungLeActivity.class);
                startActivity(intent);
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
}
