package com.example.coosincustomer;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.coosincustomer.Adapter.ListAdapterHistory;
import com.example.coosincustomer.Model.ListHistory;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

public class HistoryActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    ArrayList<ListHistory> listHistories;
    ListAdapterHistory adapterHistory;
    String account;
    ArrayList<String> address = new ArrayList<String>();
    ArrayList<String> empName = new ArrayList<String>();
    ArrayList<String> userSubmit = new ArrayList<String>();
    ArrayList<String> date = new ArrayList<String>();
    ArrayList<String> dateEnd = new ArrayList<String>();
    ArrayList<String> time = new ArrayList<String>();
    ArrayList<Integer> price = new ArrayList<Integer>();
    ArrayList<Integer> id = new ArrayList<Integer>();

    String[] addressArr,dateArr,timeArr,empNameArr,userSubmitArr,dateEndArr;
    Integer[] priceArr,idArr;
    Connection connect;
    TextView txtTotal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_canceled);

        //lay so dien thoai
        SharedPreferences SP = getApplicationContext().getSharedPreferences("PHONE",0);
        account = SP.getString("phone_num",null);

        recyclerView = findViewById(R.id.recyclerview_history);
        txtTotal = findViewById(R.id.txt_total_history);

        //back button
        Toolbar toolbar = findViewById(R.id.toolbar_history);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        listHistories = new ArrayList<>();
        address.clear();
        date.clear();
        time.clear();
        price.clear();
        id.clear();
        empName.clear();
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
                String query = "select * from ORDER_SINGLE where USER_ORDER= '" + account  + "' AND STATUS_ORDER='Hoàn thành'";
                Statement stmt = connect.createStatement();
                ResultSet rs = stmt.executeQuery(query);
                while (rs.next())
                {
                    address.add(rs.getString("ADDRESS_ORDER"));
                    date.add(rs.getString("DATE_WORK"));
                    time.add(rs.getString("TIME_WORK"));
                    price.add(rs.getInt("TOTAL_PRICE"));
                    id.add(rs.getInt("ID"));
                    userSubmit.add(rs.getString("USER_SUBMIT"));

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
                userSubmitArr = new String[userSubmit.size()];
                userSubmitArr = userSubmit.toArray(userSubmitArr);
                for (int i = 0; i < address.size();i++){
                    // Change below query according to your own database.
                    String query1 = "select * from EMPLOYEE where PHONE_NUM= '" + userSubmitArr[i]  + "'";
                    Statement stmt1 = connect.createStatement();
                    ResultSet rs1 = stmt1.executeQuery(query1);
                    if (rs1.next()){
                        empName.add(rs1.getString("FULL_NAME"));
                    }
                    empNameArr = new String[empName.size()];
                    empNameArr = empName.toArray(empNameArr);
                    listHistories.add(new ListHistory("Dùng lẻ",dateArr[i],timeArr[i],addressArr[i],
                            idArr[i],priceArr[i],empNameArr[i],""));
                }

                // load dinh ky
                int a = address.size();
                String query1 = "select * from ORDER_MULTI where USER_ORDER= '" + account  + "' AND ORDER_STATUS='Hoàn thành'";
                Statement stmt1 = connect.createStatement();
                ResultSet rs1 = stmt1.executeQuery(query1);
                while (rs1.next())
                {
                    address.add(rs1.getString("ADDRESS_ORDER"));
                    date.add(rs1.getString("DATE_START")+" - "+rs1.getString("DATE_END"));
                    time.add(rs1.getString("TIME_WORK"));
                    price.add(rs1.getInt("TOTAL_PRICE"));
                    id.add(rs1.getInt("ID"));
                    userSubmit.add(rs1.getString("USER_SUBMIT"));

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
                userSubmitArr = new String[userSubmit.size()];
                userSubmitArr = userSubmit.toArray(userSubmitArr);
                for (int i = a; i < address.size();i++){
                    // Change below query according to your own database.
                    String query2 = "select * from EMPLOYEE where PHONE_NUM= '" + userSubmitArr[i]  + "'";
                    Statement stmt2 = connect.createStatement();
                    ResultSet rs2 = stmt2.executeQuery(query2);
                    if (rs2.next()){
                        empName.add(rs2.getString("FULL_NAME"));
                    }
                    empNameArr = new String[empName.size()];
                    empNameArr = empName.toArray(empNameArr);
                    listHistories.add(new ListHistory("Định kỳ",dateArr[i],timeArr[i],addressArr[i],
                            idArr[i],priceArr[i],empNameArr[i],""));
                }

                // load tongvesinh
                int b = address.size();
                String query2 = "select * from ORDER_OVERVIEW where USER_ORDER= '" + account  + "' AND ORDER_STATUS='Hoàn thành'";
                Statement stmt2 = connect.createStatement();
                ResultSet rs2 = stmt2.executeQuery(query2);
                while (rs2.next())
                {
                    address.add(rs2.getString("ADDRESS_ORDER"));
                    date.add(rs2.getString("DATE_WORK"));
                    time.add(rs2.getString("TIME_START"));
                    price.add(rs2.getInt("TOTAL_PRICE"));
                    id.add(rs2.getInt("ID"));
                    userSubmit.add(rs2.getString("USER_SUBMIT1"));

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
                userSubmitArr = new String[userSubmit.size()];
                userSubmitArr = userSubmit.toArray(userSubmitArr);
                for (int i = b; i < address.size();i++){
                    // Change below query according to your own database.
                    String query3 = "select * from EMPLOYEE where PHONE_NUM= '" + userSubmitArr[i]  + "'";
                    Statement stmt3 = connect.createStatement();
                    ResultSet rs3 = stmt3.executeQuery(query3);
                    if (rs3.next()){
                        empName.add(rs3.getString("FULL_NAME"));
                    }
                    empNameArr = new String[empName.size()];
                    empNameArr = empName.toArray(empNameArr);
                    listHistories.add(new ListHistory("Tổng vệ sinh",dateArr[i],timeArr[i],addressArr[i],
                            idArr[i],priceArr[i],empNameArr[i],""));
                }
                // load nau an
                int c = address.size();
                String query3 = "select * from ORDER_COOK where USER_ORDER= '" + account  + "' AND ORDER_STATUS='Hoàn thành'";
                Statement stmt3 = connect.createStatement();
                ResultSet rs3 = stmt3.executeQuery(query3);
                while (rs3.next())
                {
                    address.add(rs3.getString("ADDRESS_ORDER"));
                    date.add(rs3.getString("DATE_WORK"));
                    time.add(rs3.getString("TIME_WORK"));
                    price.add(rs3.getInt("TOTAL_PRICE"));
                    id.add(rs3.getInt("ID"));
                    userSubmit.add(rs3.getString("USER_SUBMIT"));

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
                userSubmitArr = new String[userSubmit.size()];
                userSubmitArr = userSubmit.toArray(userSubmitArr);
                for (int i = c; i < address.size();i++){
                    // Change below query according to your own database.
                    String query4 = "select * from EMPLOYEE where PHONE_NUM= '" + userSubmitArr[i]  + "'";
                    Statement stmt4 = connect.createStatement();
                    ResultSet rs4 = stmt4.executeQuery(query4);
                    if (rs4.next()){
                        empName.add(rs4.getString("FULL_NAME"));
                    }
                    empNameArr = new String[empName.size()];
                    empNameArr = empName.toArray(empNameArr);
                    listHistories.add(new ListHistory("Nấu ăn",dateArr[i],timeArr[i],addressArr[i],
                            idArr[i],priceArr[i],empNameArr[i],""));
                }
            }
            connect.close();
        }
        catch (Exception ex)
        {

        }
        txtTotal.setText("Tổng: "+listHistories.size());
        adapterHistory = new ListAdapterHistory(listHistories);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapterHistory);
    }

    //back button
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
