package com.example.coosincustomer;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.coosincustomer.Adapter.ListMessageAdapter;
import com.example.coosincustomer.Model.ChatMessage;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.thekhaeng.pushdownanim.PushDownAnim;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

public class ChatActivity extends AppCompatActivity {

    RelativeLayout activity_chat;
    FloatingActionButton fab;
    String sender,receiver;
    Connection connect;
    TextView txtReceiverToolbar;
    ListMessageAdapter listMessageAdapter;
    List<ChatMessage> chatMessages;
    RecyclerView recyclerView;
    DatabaseReference reference;
    String username;
    Boolean notify = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        activity_chat = findViewById(R.id.activity_chat);
        fab = findViewById(R.id.fab);
        recyclerView = findViewById(R.id.recyclerview_chat);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
        linearLayoutManager.setStackFromEnd(true);
        recyclerView.setLayoutManager(linearLayoutManager);

        //lay so dien thoai
        SharedPreferences SP = getApplicationContext().getSharedPreferences("PHONE",0);
        sender = SP.getString("phone_num",null);
        if (getIntent().getStringExtra("receiver").equals("")){
            receiver = "admin";
        }else {
            receiver = getIntent().getStringExtra("receiver");
        }

        //back button
        Toolbar toolbar = findViewById(R.id.toolbar_chat_only);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        txtReceiverToolbar = findViewById(R.id.toolbar_name_receiver);

        try{
            com.example.coosincustomer.ConnectionDB conStr=new com.example.coosincustomer.ConnectionDB();
            connect =conStr.CONN();        // Connect to database
            if (connect == null)
            {
                Toast.makeText(getApplicationContext(), "Không có kết nối mạng!", Toast.LENGTH_LONG).show();
            }
            else
            {
                // Change below query according to your own database.
                if (!receiver.equals("admin")){
                    String query = "select * from EMPLOYEE where PHONE_NUM= '" + receiver  + "'";
                    Statement stmt = connect.createStatement();
                    ResultSet rs = stmt.executeQuery(query);
                    if(rs.next())
                    {
                        txtReceiverToolbar.setText(rs.getString("FULL_NAME"));
                        username = rs.getString("FULL_NAME");
                    }
                }else txtReceiverToolbar.setText("Quản trị viên");
            }
        }catch (Exception e){

        }

        PushDownAnim.setPushDownAnimTo(fab).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                notify=true;
                EditText input = findViewById(R.id.input);
                String msg = input.getText().toString();
                if (!msg.equals("")){
                    sendMessage(sender,receiver,msg);
                }else {
                    Toast.makeText(getApplicationContext(),"Không thể gửi tin nhắn trống!",Toast.LENGTH_LONG).show();
                }
                input.setText("");
            }
        });

        reference = FirebaseDatabase.getInstance().getReference("chats");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                readMessage(sender,receiver);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void sendMessage(final String sender, final String receiver, String message){
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference();

        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("sender",sender);
        hashMap.put("receiver",receiver);
        hashMap.put("message",message);
        long time;
        time = new Date().getTime();
        hashMap.put("time",time);

        reference.child("chats").push().setValue(hashMap);

//        final String msg = message;
//        reference = FirebaseDatabase.getInstance().getReference("chats");
//        reference.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                if (notify){
//                    sendNotification(receiver,username,msg);
//                }
//                notify = false;
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//
//            }
//        });


    }

    private void readMessage(final String sender, final String receiver){

        chatMessages = new ArrayList<>();
        reference = FirebaseDatabase.getInstance().getReference("chats");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                chatMessages.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()){
                    ChatMessage chatMessage = snapshot.getValue(ChatMessage.class);
                    if(chatMessage.getSender()!=null && chatMessage.getReceiver()!= null){
                        if (chatMessage.getSender().equals(sender) && chatMessage.getReceiver().equals(receiver)
                                || chatMessage.getSender().equals(receiver) && chatMessage.getReceiver().equals(sender)){
                            chatMessages.add(chatMessage);
                        }
                    }
                    listMessageAdapter = new ListMessageAdapter(ChatActivity.this,chatMessages);
                    recyclerView.setAdapter(listMessageAdapter);
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

////    private void sendNotification(final String receiver, final String userName, final String message){
////        DatabaseReference tokens = FirebaseDatabase.getInstance().getReference("Tokens");
////        Query query = tokens.orderByKey().equalTo(receiver);
////        query.addValueEventListener(new ValueEventListener() {
////            @Override
////            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
////                for (DataSnapshot snapshot : dataSnapshot.getChildren()){
////                    Token token = snapshot.getValue(Token.class);
////                    Data data = new Data(sender,R.mipmap.ic_launcher,userName+": "+message,"Tin nhắn mới",sender);
////
////                    Sender sender = new Sender(data,token.getToken());
////                    apiService.sendNotification(sender)
////                            .enqueue(new Callback<MyResponse>() {
////                                @Override
////                                public void onResponse(Call<MyResponse> call, Response<MyResponse> response) {
////                                    if (response.code() == 200){
////                                        if (response.body().success==1){
////                                            Toast.makeText(getApplicationContext(),"Failed",Toast.LENGTH_LONG).show();
////                                        }
////                                    }
////                                }
////
////                                @Override
////                                public void onFailure(Call<MyResponse> call, Throwable t) {
////
////                                }
////                            });
////                }
////            }
////
////            @Override
////            public void onCancelled(@NonNull DatabaseError databaseError) {
////
////            }
////        });
//    }

    //back button
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
