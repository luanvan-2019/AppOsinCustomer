package com.example.coosincustomer;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.coosincustomer.Adapter.ListUserChatAdapter;
import com.example.coosincustomer.Model.ListFavorite;
import com.example.coosincustomer.Model.OnItemClickListener;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

public class ListChatActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    ArrayList<ListFavorite> listFavorites;
    ListUserChatAdapter listUserChatAdapter;
    String account;
    Connection connect;

    ArrayList<String> name = new ArrayList<String>();
    ArrayList<String> type = new ArrayList<String>();
    ArrayList<String> phone = new ArrayList<String>();
    String[] nameArr,typeArr,phoneArr;
    ImageView imgCancel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_chat);

        recyclerView = findViewById(R.id.recyclerview_select_chat);
        imgCancel = findViewById(R.id.img_cancel_select_chat);

        //lay so dien thoai
        SharedPreferences SP = this.getSharedPreferences("PHONE",0);
        account = SP.getString("phone_num",null);

        listFavorites = new ArrayList<>();
        name.clear();
        type.clear();
        phone.clear();

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
                String query = "select * from FAVORITE where CUS_PHONE='"+ account +"'";
                Statement stmt = connect.createStatement();
                ResultSet rs = stmt.executeQuery(query);
                while (rs.next())
                {
                    phone.add(rs.getString("STAFF_PHONE"));
                    String query1 = "select * from EMPLOYEE where PHONE_NUM='"+ rs.getString("STAFF_PHONE") +"'";
                    Statement stmt1 = connect.createStatement();
                    ResultSet rs1 = stmt1.executeQuery(query1);
                    if (rs1.next()){
                        name.add(rs1.getString("FULL_NAME"));
                        if (rs1.getInt("EMP_TYPE")==1){
                            type.add("Nhân viên vệ sinh");
                        }else if (rs1.getInt("EMP_TYPE")==2){
                            type.add("Nhân viên nấu ăn");
                        }else type.add("Nhân viên đa năng");
                    }
                }
                nameArr = new String[name.size()];
                nameArr = name.toArray(nameArr);
                typeArr = new String[type.size()];
                typeArr = type.toArray(typeArr);
                phoneArr = new String[phone.size()];
                phoneArr = phone.toArray(phoneArr);
                for (int i = 0; i < name.size();i++){
                    listFavorites.add(new ListFavorite(nameArr[i],phoneArr[i],typeArr[i]));
                }
            }
        }
        catch (Exception ex)
        {
            Toast.makeText(getApplicationContext(), ex.getMessage(), Toast.LENGTH_LONG).show();
        }

        listUserChatAdapter = new ListUserChatAdapter(listFavorites);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(listUserChatAdapter);

        if (recyclerView.getAdapter() != null) {
            ((ListUserChatAdapter) recyclerView.getAdapter()).setOnItemClickListener(new OnItemClickListener() {
                @Override
                public void onClick(View v, @NonNull int position) {
                    LoadingDialog loadingDialog = new LoadingDialog();
                    loadingDialog.loading(ListChatActivity.this);
                    Intent detail = new Intent(ListChatActivity.this, ChatActivity.class);
                    detail.putExtra("receiver",phoneArr[position]);
                    startActivity(detail);
                }

                @Override
                public void onLongClick(View v, @NonNull int position) {

                }
            });
        }

        imgCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }

    //toolbar back button
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
