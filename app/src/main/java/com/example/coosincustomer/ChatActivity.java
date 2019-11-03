package com.example.coosincustomer;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.coosincustomer.Model.ChatMessage;
import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.database.FirebaseListAdapter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.thekhaeng.pushdownanim.PushDownAnim;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

public class ChatActivity extends AppCompatActivity {

    private static int SIGN_IN_REQUEST_CODE =1;
    private FirebaseListAdapter<ChatMessage> adapter;
    RelativeLayout activity_chat;
    FloatingActionButton fab;
    String sender,receiver;
    Connection connect;
    TextView txtReceiverToolbar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        activity_chat = findViewById(R.id.activity_chat);
        fab = findViewById(R.id.fab);

        Log.d("BBB",getIntent().getStringExtra("receiver"));
        //lay so dien thoai
        SharedPreferences SP = getApplicationContext().getSharedPreferences("PHONE",0);
        sender = SP.getString("phone_num",null);
        receiver = getIntent().getStringExtra("receiver");

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
                String query = "select * from EMPLOYEE where PHONE_NUM= '" + receiver  + "'";
                Statement stmt = connect.createStatement();
                ResultSet rs = stmt.executeQuery(query);
                if(rs.next())
                {
                    txtReceiverToolbar.setText(rs.getString("FULL_NAME"));
                }
            }
        }catch (Exception e){

        }

        PushDownAnim.setPushDownAnimTo(fab).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText input = findViewById(R.id.input);
                FirebaseDatabase.getInstance().getReference().push().setValue(new ChatMessage(input.getText().toString(),
                        sender,receiver));
                input.setText("");
            }
        });

        if (FirebaseAuth.getInstance().getCurrentUser() == null){
            startActivityForResult(AuthUI.getInstance().createSignInIntentBuilder().build(),SIGN_IN_REQUEST_CODE);
        }else {
            Snackbar.make(activity_chat,"Welcome "+FirebaseAuth.getInstance().getCurrentUser().getPhoneNumber(),Snackbar.LENGTH_SHORT).show();
            displayChatMessage();
        }
    }

    private void displayChatMessage() {
        final ListView listOfMessage = findViewById(R.id.list_of_message);
        adapter = new FirebaseListAdapter<ChatMessage>(this, ChatMessage.class, R.layout.list_chat_item, FirebaseDatabase.getInstance().getReference())
        {
            @Override
            protected void populateView(View v, ChatMessage model, int position) {
                TextView messageSender,messageReceiver,messageTime;
                ImageView avatar;
                RelativeLayout relReceiver;
                messageSender = v.findViewById(R.id.message_text_sender);
                messageReceiver = v.findViewById(R.id.message_text_receiver);
                messageTime = v.findViewById(R.id.time_send_message);
                relReceiver = v.findViewById(R.id.rel_receiver);

                if (model.getUserSender().trim().equals(sender) && model.getUserReceiver().trim().equals(receiver)){
                    messageSender.setText(model.getMessageText());
                    messageTime.setText(DateFormat.format("(HH:mm) dd-MM-yyyy",model.getMessageTime()));
                    relReceiver.setVisibility(View.GONE);
                }else if (model.getUserSender().trim().equals(receiver) && model.getUserReceiver().trim().equals(sender)){
                    messageReceiver.setText(model.getMessageText());
                    messageTime.setText(DateFormat.format("(HH:mm) dd-MM-yyyy",model.getMessageTime()));
                    messageSender.setVisibility(View.GONE);

                }else {
                    relReceiver.setVisibility(View.GONE);
                    messageSender.setVisibility(View.GONE);
                    messageTime.setVisibility(View.GONE);
                }

            }
        };
        listOfMessage.setAdapter(adapter);
    }

    //back button
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
