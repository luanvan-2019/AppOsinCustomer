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

import com.example.coosincustomer.Adapter.OrderListAdapterDK;
import com.example.coosincustomer.Adapter.OrderListAdapterTVS;
import com.example.coosincustomer.DetailOrderActivity;
import com.example.coosincustomer.Model.ListOrderTVS;
import com.example.coosincustomer.Model.OnItemClickListener;
import com.example.coosincustomer.R;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

public class Fragment_tongvs extends Fragment {

    View view;
    RecyclerView recyclerView;
    ArrayList<ListOrderTVS> listOrders;
    OrderListAdapterTVS orderListAdapter;
    String phone_num;
    Connection connect;

    ArrayList<String> address = new ArrayList<String>();
    ArrayList<String> date = new ArrayList<String>();
    ArrayList<String> time = new ArrayList<String>();
    ArrayList<String> status = new ArrayList<String>();
    ArrayList<Integer> price = new ArrayList<Integer>();
    ArrayList<Integer> id = new ArrayList<Integer>();
    String[] addressArr,dateArr,timeArr,statusArr;
    Integer[] priceArr,idArr;

    public Fragment_tongvs() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.tongvs_fragment,container,false);

        recyclerView = view.findViewById(R.id.recyclerview_tvs);

        //lay so dien thoai
        SharedPreferences SP = getContext().getSharedPreferences("PHONE",0);
        phone_num = SP.getString("phone_num",null);

        listOrders = new ArrayList<>();
        address.clear();
        date.clear();
        time.clear();
        price.clear();
        id.clear();

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
                String query = "select * from ORDER_OVERVIEW where USER_ORDER= '" + phone_num  + "'";
                Statement stmt = connect.createStatement();
                ResultSet rs = stmt.executeQuery(query);
                while (rs.next())
                {
                    address.add(rs.getString("ADDRESS_ORDER"));
                    date.add(rs.getString("DATE_WORK"));
                    time.add(rs.getString("TIME_START"));
                    price.add(rs.getInt("TOTAL_PRICE"));
                    id.add(rs.getInt("ID"));
                    status.add(rs.getString("ORDER_STATUS"));
                }
                statusArr = new String[status.size()];
                statusArr = status.toArray(statusArr);
                addressArr = new String[address.size()];
                addressArr = address.toArray(addressArr);
                dateArr = new String[date.size()];
                dateArr = date.toArray(dateArr);
                timeArr = new String[time.size()];
                timeArr = time.toArray(timeArr);
                priceArr = new Integer[price.size()];
                priceArr = price.toArray(priceArr);
                idArr = new Integer[id.size()];
                idArr = id.toArray(idArr);
                for (int i = 0; i < address.size();i++){
                    listOrders.add(new ListOrderTVS(statusArr[i],dateArr[i],timeArr[i],addressArr[i],
                            idArr[i],priceArr[i]));
                }
            }
        }
        catch (Exception ex)
        {
            Toast.makeText(getContext(), ex.getMessage(), Toast.LENGTH_LONG).show();
        }
        orderListAdapter = new OrderListAdapterTVS(listOrders);
        recyclerView.setLayoutManager(new LinearLayoutManager(this.getActivity()));
        recyclerView.setAdapter(orderListAdapter);

        if (recyclerView.getAdapter() != null) {
            ((OrderListAdapterTVS) recyclerView.getAdapter()).setOnItemClickListener(new OnItemClickListener() {
                @Override
                public void onClick(View v, @NonNull int position) {
                    Intent detail = new Intent(getActivity(), DetailOrderActivity.class);
                    detail.putExtra("idOrder",idArr[position]);
                    detail.putExtra("orderType","Tổng vệ sinh");
                    startActivity(detail);
                }

                @Override
                public void onLongClick(View v, @NonNull int position) {

                }
            });
        }

        return view;
    }
}
