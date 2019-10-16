package com.example.coosincustomer;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.FrameLayout;
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

//        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
//        fragmentTransaction.replace(R.id.main_frame, homeFragment);
//        fragmentTransaction.addToBackStack("home").commit();
        setFragment(homeFragment);

        //nav action
        mMainNav.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()){

                    case R.id.nav_home :
                        setFragment(homeFragment);
                        return true;
                    case R.id.nav_ordered :
                        new MyTask();
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
                startActivity(intent);
            }
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }
    class MyTask extends AsyncTask<Void, Void, Void> {

        ProgressDialog Asycdialog = new ProgressDialog(HomeActivity.this);

        @Override
        protected void onPreExecute() {

            super.onPreExecute();
            Asycdialog.setMessage("Loading...");
            Asycdialog.show();
        }

        @Override
        protected Void doInBackground(Void... arg0) {

            // do the task you want to do. This will be executed in background.
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {

            super.onPostExecute(result);
            Asycdialog.dismiss();
        }
    }

}
