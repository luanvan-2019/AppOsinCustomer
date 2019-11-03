package com.example.coosincustomer;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.example.coosincustomer.Adapter.ListAdapterCanceled;
import com.example.coosincustomer.Adapter.ListAdapterHistory;
import com.example.coosincustomer.Model.ListCanceled;
import com.example.coosincustomer.Model.ListHistory;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

public class CanceledActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    ArrayList<ListCanceled> listCanceleds;
    ListAdapterCanceled adapterCanceled;
    String account;
    ArrayList<String> address = new ArrayList<String>();
    ArrayList<String> userSubmit = new ArrayList<String>();
    ArrayList<String> date = new ArrayList<String>();
    ArrayList<String> time = new ArrayList<String>();
    ArrayList<Integer> price = new ArrayList<Integer>();
    ArrayList<Integer> id = new ArrayList<Integer>();

    String[] addressArr,dateArr,timeArr,userSubmitArr;
    Integer[] priceArr,idArr;
    Connection connect;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_canceled);

        //lay so dien thoai
        SharedPreferences SP = getApplicationContext().getSharedPreferences("PHONE",0);
        account = SP.getString("phone_num",null);

        recyclerView = findViewById(R.id.recyclerview_canceled);

        //back button
        Toolbar toolbar = findViewById(R.id.toolbar_history);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        listCanceleds = new ArrayList<>();
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
                Toast.makeText(getApplicationContext(),"Không có kết nối mạng",Toast.LENGTH_LONG).show();
            }
            else
            {
                // load dung le
                String query = "select * from ORDER_SINGLE where USER_ORDER= '" + account  + "' AND STATUS_ORDER='Đã hủy'";
                Statement stmt = connect.createStatement();
                ResultSet rs = stmt.executeQuery(query);
                while (rs.next())
                {
                    address.add(rs.getString("ADDRESS_ORDER"));
                    date.add(rs.getString("DATE_WORK"));
                    time.add(rs.getString("TIME_WORK"));
                    price.add(rs.getInt("TOTAL_PRICE"));
                    id.add(rs.getInt("ID"));

                }
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
                    listCanceleds.add(new ListCanceled("Dùng lẻ",dateArr[i],timeArr[i],addressArr[i],
                            idArr[i],priceArr[i]));
                }

                // load dinh ky
                int a = address.size();
                String query1 = "select * from ORDER_MULTI where USER_ORDER= '" + account  + "' AND ORDER_STATUS='Đã hủy'";
                Statement stmt1 = connect.createStatement();
                ResultSet rs1 = stmt1.executeQuery(query1);
                while (rs1.next())
                {
                    address.add(rs1.getString("ADDRESS_ORDER"));
                    date.add(rs1.getString("DATE_START")+" - "+rs1.getString("DATE_END"));
                    time.add(rs1.getString("TIME_WORK"));
                    price.add(rs1.getInt("TOTAL_PRICE"));
                    id.add(rs1.getInt("ID"));

                }
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
                for (int i = a; i < address.size();i++){
                    listCanceleds.add(new ListCanceled("Định kỳ",dateArr[i],timeArr[i],addressArr[i],
                            idArr[i],priceArr[i]));
                }

                // load tongvesinh
                int b = address.size();
                String query2 = "select * from ORDER_OVERVIEW where USER_ORDER= '" + account  + "' AND ORDER_STATUS='Đã hủy'";
                Statement stmt2 = connect.createStatement();
                ResultSet rs2 = stmt2.executeQuery(query2);
                while (rs2.next())
                {
                    address.add(rs2.getString("ADDRESS_ORDER"));
                    date.add(rs2.getString("DATE_WORK"));
                    time.add(rs2.getString("TIME_START"));
                    price.add(rs2.getInt("TOTAL_PRICE"));
                    id.add(rs2.getInt("ID"));

                }
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
                for (int i = b; i < address.size();i++){
                    listCanceleds.add(new ListCanceled("Tổng vệ sinh",dateArr[i],timeArr[i],addressArr[i],
                            idArr[i],priceArr[i]));
                }
                // load nau an
                int c = address.size();
                String query3 = "select * from ORDER_COOK where USER_ORDER= '" + account  + "' AND ORDER_STATUS='Đã hủy'";
                Statement stmt3 = connect.createStatement();
                ResultSet rs3 = stmt3.executeQuery(query3);
                while (rs3.next())
                {
                    address.add(rs3.getString("ADDRESS_ORDER"));
                    date.add(rs3.getString("DATE_WORK"));
                    time.add(rs3.getString("TIME_WORK"));
                    price.add(rs3.getInt("TOTAL_PRICE"));
                    id.add(rs3.getInt("ID"));

                }
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
                for (int i = c; i < address.size();i++){
                    listCanceleds.add(new ListCanceled("Nấu ăn",dateArr[i],timeArr[i],addressArr[i],
                            idArr[i],priceArr[i]));
                }
            }
            connect.close();
        }
        catch (Exception ex)
        {

        }
        adapterCanceled = new ListAdapterCanceled(listCanceleds);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapterCanceled);
    }

    //back button
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
