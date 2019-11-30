package com.example.coosincustomer.Fragment;


import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.example.coosincustomer.AccountEditInfoActivity;
import com.example.coosincustomer.AccountInfoActivity;
import com.example.coosincustomer.Adapter.ViewPagerAdapter;
import com.example.coosincustomer.LoadingDialog;
import com.example.coosincustomer.OrderDinhKyActivity;
import com.example.coosincustomer.OrderDungLeActivity;
import com.example.coosincustomer.OrderNauAnActivity;
import com.example.coosincustomer.OrderTongVeSinhActivity;
import com.example.coosincustomer.R;
import com.thekhaeng.pushdownanim.PushDownAnim;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Timer;
import java.util.TimerTask;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment {

    ViewPager viewPager;
    ViewPagerAdapter viewPagerAdapter;
    private LinearLayout dotsLayout;
    private CardView cardViewDungLe,cardDinhKy,cardTongVeSinh,cardNauAn;
    ImageView imgAccount;
    String phone_num;
    Connection connect;

    public HomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        cardViewDungLe = view.findViewById(R.id.card_dungle);
        cardDinhKy = view.findViewById(R.id.card_dinh_ky);
        cardTongVeSinh = view.findViewById(R.id.card_tong_ve_sinh);
        cardNauAn = view.findViewById(R.id.card_nauan);
        imgAccount = view.findViewById(R.id.avatar_account);

        viewPager = view.findViewById(R.id.viewpager_image);
        viewPagerAdapter = new ViewPagerAdapter(this.getActivity());
        dotsLayout = view.findViewById(R.id.dotsContainer);
        viewPager.setAdapter(viewPagerAdapter);
        prepareDots();

        //lay so dien thoai da luu khi dang nhap
        SharedPreferences SP = this.getActivity().getSharedPreferences("PHONE",0);
        phone_num = SP.getString("phone_num",null);

        //manager account
        PushDownAnim.setPushDownAnimTo(imgAccount).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LoadingDialog loadingDialog = new LoadingDialog();
                loadingDialog.loading(getActivity());
                Intent intent = new Intent(getActivity(), AccountInfoActivity.class);
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
               checkConnectDialog();
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
            Toast.makeText(getActivity(), ex.getMessage(), Toast.LENGTH_LONG).show();

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

        //order dung le
        PushDownAnim.setPushDownAnimTo(cardViewDungLe).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LoadingDialog loadingDialog = new LoadingDialog();
                loadingDialog.loading(getActivity());
                Intent dungle = new Intent(getActivity(), OrderDungLeActivity.class);
                startActivity(dungle);
            }
        });

        //order dinh ky
        PushDownAnim.setPushDownAnimTo(cardDinhKy).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LoadingDialog loadingDialog = new LoadingDialog();
                loadingDialog.loading(getActivity());
                Intent dinhky = new Intent(getActivity(), OrderDinhKyActivity.class);
                startActivity(dinhky);
            }
        });

        //order tong ve sinh
        PushDownAnim.setPushDownAnimTo(cardTongVeSinh).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LoadingDialog loadingDialog = new LoadingDialog();
                loadingDialog.loading(getActivity());
                Intent tongvesinh = new Intent(getActivity(), OrderTongVeSinhActivity.class);
                startActivity(tongvesinh);
            }
        });

        //order nau an
        PushDownAnim.setPushDownAnimTo(cardNauAn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LoadingDialog loadingDialog = new LoadingDialog();
                loadingDialog.loading(getActivity());
                Intent nauan = new Intent(getActivity(), OrderNauAnActivity.class);
                startActivity(nauan);
            }
        });
        return view;
    }

    public class MyTimerTask extends TimerTask{

        @Override
        public void run() {
            if (getActivity() != null){
                getActivity().runOnUiThread(new Runnable() {
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
    }

    private void prepareDots(){
        if (dotsLayout.getChildCount() > 0){
            dotsLayout.removeAllViews();
        }
        ImageView dots[] = new ImageView[viewPagerAdapter.getCount()];
        for (int i = 0; i < viewPagerAdapter.getCount();i++){
            if (getContext() == null) {
                return;
            }
            dots[i] = new ImageView(this.getActivity());
            if (i == viewPager.getCurrentItem())
                dots[i].setImageDrawable(ContextCompat.getDrawable(this.getActivity(),R.drawable.active_dot));
            else dots[i].setImageDrawable(ContextCompat.getDrawable(this.getActivity(),R.drawable.inactive_dot));
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT);
            layoutParams.setMargins(4,0,4,0);
            dotsLayout.addView(dots[i],layoutParams);
        }
    }

    public void showAlertDialog(){
        TextView title = new TextView(this.getContext());
        title.setText("CẬP NHẬT THÔNG TIN");
        title.setPadding(10, 40, 10, 10);
        title.setGravity(Gravity.CENTER);
        title.setTextSize(18);
        title.setTextColor(Color.RED);

        AlertDialog.Builder builder = new AlertDialog.Builder(this.getActivity());
        builder.setCustomTitle(title);
        builder.setMessage(R.string.cap_nhat_thong_tin);
        builder.setCancelable(false);
        builder.setNegativeButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
                Intent intent = new Intent(getActivity(), AccountEditInfoActivity.class);
                startActivity(intent);
            }
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
        Button b = alertDialog.getButton(DialogInterface.BUTTON_NEGATIVE);
        b.setBackground(getResources().getDrawable(R.drawable.btn_round_coner));
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        layoutParams.gravity = Gravity.CENTER;
        layoutParams.setMargins(20,20,20,20);
        alertDialog.getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor(Color.WHITE);
        alertDialog.getButton(AlertDialog.BUTTON_NEGATIVE).setLayoutParams(layoutParams);

        TextView messageText = alertDialog.findViewById(android.R.id.message);
        messageText.setGravity(Gravity.CENTER);
    }
    public void checkConnectDialog(){
        TextView title = new TextView(this.getContext());
        title.setText("THÔNG BÁO");
        title.setPadding(10, 40, 10, 10);
        title.setGravity(Gravity.CENTER);
        title.setTextSize(20);
        title.setTextColor(Color.RED);

        AlertDialog.Builder builder = new AlertDialog.Builder(this.getActivity());
        builder.setCustomTitle(title);
        builder.setMessage("Không có kết nối mạng !");
        builder.setCancelable(false);
        builder.setNegativeButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
        Button b = alertDialog.getButton(DialogInterface.BUTTON_NEGATIVE);
        b.setBackground(getResources().getDrawable(R.drawable.btn_round_coner));
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        layoutParams.gravity = Gravity.CENTER;
        layoutParams.setMargins(20,20,20,20);
        alertDialog.getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor(Color.WHITE);
        alertDialog.getButton(AlertDialog.BUTTON_NEGATIVE).setLayoutParams(layoutParams);

        TextView messageText = alertDialog.findViewById(android.R.id.message);
        messageText.setGravity(Gravity.CENTER);
    }
}
