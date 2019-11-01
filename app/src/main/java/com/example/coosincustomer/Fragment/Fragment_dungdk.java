package com.example.coosincustomer.Fragment;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.coosincustomer.Adapter.OrderListAdapter;
import com.example.coosincustomer.Adapter.OrderListAdapterDK;
import com.example.coosincustomer.DetailOrderActivity;
import com.example.coosincustomer.LoadingDialog;
import com.example.coosincustomer.Model.ListOrder;
import com.example.coosincustomer.Model.ListOrderDK;
import com.example.coosincustomer.Model.OnItemClickListener;
import com.example.coosincustomer.R;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

public class Fragment_dungdk extends Fragment {

    View view;
    RecyclerView recyclerView;
    ArrayList<ListOrderDK> listOrders;
    OrderListAdapterDK orderListAdapter;
    String phone_num;
    Connection connect;

    ArrayList<String> address = new ArrayList<String>();
    ArrayList<String> dateStart = new ArrayList<String>();
    ArrayList<String> time = new ArrayList<String>();
    ArrayList<String> status = new ArrayList<String>();
    ArrayList<String> dateEnd = new ArrayList<String>();
    ArrayList<String> schedule = new ArrayList<String>();
    ArrayList<Integer> price = new ArrayList<Integer>();
    ArrayList<Integer> id = new ArrayList<Integer>();
    String[] addressArr,dateStartArr,dateEndArr,timeArr,scheduleArr,statusArr;
    Integer[] priceArr,idArr;
    SwipeRefreshLayout swipeRefreshLayout;

    public Fragment_dungdk() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.dungdk_fragment,container,false);

        recyclerView = view.findViewById(R.id.recyclerview_dk);
        swipeRefreshLayout = view.findViewById(R.id.swipe_dinhky);

        //lay so dien thoai
        SharedPreferences SP = getContext().getSharedPreferences("PHONE",0);
        phone_num = SP.getString("phone_num",null);

        todo();
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                todo();
                swipeRefreshLayout.setRefreshing(false);
            }
        });


        return view;
    }

    public void todo(){
        listOrders = new ArrayList<>();
        address.clear();
        dateStart.clear();
        dateEnd.clear();
        schedule.clear();
        time.clear();
        price.clear();
        id.clear();
        status.clear();
        try
        {
            com.example.coosincustomer.ConnectionDB conStr=new com.example.coosincustomer.ConnectionDB();
            connect =conStr.CONN();        // Connect to database
            if (connect == null)
            {
                Toast.makeText(getContext(), "Không có kết nối mạng!", Toast.LENGTH_LONG).show();
            }
            else
            {
                // Change below query according to your own database.
                String query = "select * from ORDER_MULTI where USER_ORDER= '" + phone_num  + "'";
                Statement stmt = connect.createStatement();
                ResultSet rs = stmt.executeQuery(query);
                while (rs.next())
                {
                    address.add(rs.getString("ADDRESS_ORDER"));
                    dateStart.add(rs.getString("DATE_START"));
                    dateEnd.add(rs.getString("DATE_END"));
                    schedule.add(rs.getString("SCHEDULE"));
                    time.add(rs.getString("TIME_WORK"));
                    price.add(rs.getInt("TOTAL_PRICE"));
                    id.add(rs.getInt("ID"));
                    status.add(rs.getString("ORDER_STATUS"));
                }
                addressArr = new String[address.size()];
                addressArr = address.toArray(addressArr);
                dateStartArr = new String[dateStart.size()];
                dateStartArr = dateStart.toArray(dateStartArr);
                statusArr = new String[status.size()];
                statusArr = status.toArray(statusArr);
                dateEndArr = new String[dateEnd.size()];
                dateEndArr = dateEnd.toArray(dateEndArr);
                timeArr = new String[time.size()];
                timeArr = time.toArray(timeArr);
                scheduleArr = new String[schedule.size()];
                scheduleArr = schedule.toArray(scheduleArr);
                priceArr = new Integer[price.size()];
                priceArr = price.toArray(priceArr);
                idArr = new Integer[id.size()];
                idArr = id.toArray(idArr);
                for (int i = 0; i < address.size();i++){
                    listOrders.add(new ListOrderDK(statusArr[i],dateStartArr[i],dateEndArr[i],scheduleArr[i],timeArr[i],addressArr[i],
                            priceArr[i],idArr[i]));
                }
            }
        }
        catch (Exception ex)
        {
            Toast.makeText(getContext(), ex.getMessage(), Toast.LENGTH_LONG).show();
            Log.d("BBB", ex.getMessage());
        }
        orderListAdapter = new OrderListAdapterDK(listOrders);
        recyclerView.setLayoutManager(new LinearLayoutManager(this.getActivity()));
        recyclerView.setAdapter(orderListAdapter);

        if (recyclerView.getAdapter() != null) {
            ((OrderListAdapterDK) recyclerView.getAdapter()).setOnItemClickListener(new OnItemClickListener() {
                @Override
                public void onClick(View v, @NonNull int position) {
                    LoadingDialog loadingDialog = new LoadingDialog();
                    loadingDialog.loading(getActivity());
                    Intent detail = new Intent(getActivity(), DetailOrderActivity.class);
                    detail.putExtra("idOrder",idArr[position]);
                    detail.putExtra("orderType","Định kỳ");
                    startActivity(detail);
                }

                @Override
                public void onLongClick(View v, @NonNull int position) {

                }
            });
        }
    }
}

