package com.example.coosincustomer.Fragment;


import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.coosincustomer.Adapter.ListFavoriteAdapter;
import com.example.coosincustomer.Adapter.OrderListAdapter;
import com.example.coosincustomer.DetailOrderActivity;
import com.example.coosincustomer.LoadingDialog;
import com.example.coosincustomer.Model.ListFavorite;
import com.example.coosincustomer.Model.ListOrder;
import com.example.coosincustomer.Model.OnItemClickListener;
import com.example.coosincustomer.Model.OnItemClickShowLichLV;
import com.example.coosincustomer.R;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class FavoriteFragment extends Fragment {

    View view;
    RecyclerView recyclerView;
    ArrayList<ListFavorite> listFavorites;
    ListFavoriteAdapter listFavoriteAdapter;
    String account;
    Connection connect;

    ArrayList<String> name = new ArrayList<String>();
    ArrayList<String> type = new ArrayList<String>();
    ArrayList<String> phone = new ArrayList<String>();
    String[] nameArr,typeArr,phoneArr;

    public FavoriteFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_favorite,container,false);
        recyclerView = view.findViewById(R.id.recyclerview_favorite);

        //lay so dien thoai
        SharedPreferences SP = getContext().getSharedPreferences("PHONE",0);
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
                Toast.makeText(getContext(), "Không có kết nối mạng!", Toast.LENGTH_LONG).show();
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
                    listFavorites.add(new ListFavorite(nameArr[i],typeArr[i],phoneArr[i]));
                }
            }
        }
        catch (Exception ex)
        {
            Toast.makeText(getContext(), ex.getMessage(), Toast.LENGTH_LONG).show();
        }

        listFavoriteAdapter = new ListFavoriteAdapter(listFavorites);
        recyclerView.setLayoutManager(new LinearLayoutManager(this.getActivity()));
        recyclerView.setAdapter(listFavoriteAdapter);

        if (recyclerView.getAdapter() != null) {
            ((ListFavoriteAdapter) recyclerView.getAdapter()).setOnItemClickListener(new OnItemClickListener() {
                @Override
                public void onClick(View v, @NonNull int position) {
                    try {
                        com.example.coosincustomer.ConnectionDB conStr=new com.example.coosincustomer.ConnectionDB();
                        connect =conStr.CONN();
                        // Connect to database
                        if (connect == null){ Toast.makeText(getActivity(),"Không có kết nối mạng!",Toast.LENGTH_LONG).show();}
                        else {
                            String query = "DELETE FROM FAVORITE WHERE CUS_PHONE='"+account+"' AND STAFF_PHONE='"+phoneArr[position]+"'";
                            Statement stmt = connect.createStatement();
                            stmt.executeQuery(query);
                            connect.close();
                        }
                    }
                    catch (Exception ex){

                    }
                    try {
                        com.example.coosincustomer.ConnectionDB conStr=new com.example.coosincustomer.ConnectionDB();
                        connect =conStr.CONN();
                        // Connect to database
                        if (connect == null){Toast.makeText(getActivity(),"Không có kết nối mạng!",Toast.LENGTH_LONG).show();}
                        else {
                            int favorite=0;
                            String query = "SELECT * FROM EMPLOYEE WHERE PHONE_NUM='"+phoneArr[position]+"'";
                            Statement stmt = connect.createStatement();
                            ResultSet rs = stmt.executeQuery(query);
                            if (rs.next()){
                                favorite = rs.getInt("FAVORITE")-1;
                            }
                            String query1 = "UPDATE EMPLOYEE SET FAVORITE="+favorite+" WHERE PHONE_NUM='"+phoneArr[position]+"'";
                            Statement stmt1 = connect.createStatement();
                            stmt1.executeQuery(query1);
                            connect.close();
                        }
                    }
                    catch (Exception ex){
                        listFavorites.remove(position);
                        listFavoriteAdapter.notifyDataSetChanged();
                    }
                }

                @Override
                public void onLongClick(View v, @NonNull int position) {

                }
            });

            ((ListFavoriteAdapter) recyclerView.getAdapter()).setmOnItemClickShowLichLV(new OnItemClickShowLichLV() {
                @Override
                public void onClick(View v, @NonNull int position) {
                    Toast.makeText(getActivity(),"BBBBBBB",Toast.LENGTH_LONG).show();
                }

                @Override
                public void onLongClick(View v, @NonNull int position) {
                }
            });
        }

        return view;
    }
}
