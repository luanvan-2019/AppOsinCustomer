package com.example.coosincustomer;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.coosincustomer.notification.MySingleton;
import com.thekhaeng.pushdownanim.PushDownAnim;

import org.json.JSONException;
import org.json.JSONObject;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;

public class ThanhtoanActivity extends AppCompatActivity {

    private ImageView iconThanhtoanTienMat,iconThanhToanMoMo,iconThanhToanPaypal;
    private RelativeLayout relavetiveTienMat,relativeLayoutMoMo,relativeLayoutPaypal;
    private Button btnXacnhanThanhtoan;
    String DiaChi,paymentType="Tiền mặt";
    Connection connect;
    String phone_num;
    Integer i;
    Double latitude = null;
    Double longitude = null;
    final private String FCM_API = "https://fcm.googleapis.com/fcm/send";
    final private String serverKey = "key=" + "AAAAZNB3xoU:APA91bGxISuo_YVJ7-142Aua8xMYvafuhaZvNIf01IeOzVrZ1hEypTqdP53X3pMZg_Mx3XkkVJOdiiDCMnHp00ytrTJxLDaozcdVpEXc1AsciThWq5ZkiDOHswqSHsLEskoVXOpC8SZC";
    final private String contentType = "application/json";
    final String TAG = "NOTIFICATION TAG";
    String NOTIFICATION_TITLE;
    String NOTIFICATION_MESSAGE;
    String TOPIC;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thanhtoan);


        //anh xa
        Toolbar toolbar = findViewById(R.id.toolbar_hinhthuc_thanhtoan);
        iconThanhtoanTienMat = findViewById(R.id.img_payment_tienmat);
        iconThanhToanMoMo = findViewById(R.id.img_payment_momo);
        iconThanhToanPaypal = findViewById(R.id.img_payment_paypal);
        relavetiveTienMat = findViewById(R.id.relative_tienmat);
        relativeLayoutMoMo = findViewById(R.id.relative_momo);
        relativeLayoutPaypal = findViewById(R.id.relative_paypal);
        btnXacnhanThanhtoan = findViewById(R.id.btn_xacnhan_thanhtoan);

        latitude = getIntent().getDoubleExtra("latitude",0);
        longitude = getIntent().getDoubleExtra("longitude",0);

        //lay so dien thoai da luu khi dang nhap
        SharedPreferences SP = getApplicationContext().getSharedPreferences("PHONE",0);
        phone_num = SP.getString("phone_num",null);

        //connect db
        StrictMode.ThreadPolicy policy= new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        //toolbar action
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        //check da chon hinh thuc thanh toan
        i = 0;

        //thanh toan tien mat click
        PushDownAnim.setPushDownAnimTo(relavetiveTienMat).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                i = 1;
                iconThanhtoanTienMat.setImageResource(R.drawable.ic_checked);
                relavetiveTienMat.setBackgroundResource(R.drawable.bg_layout_licked);
                iconThanhToanMoMo.setImageResource(R.drawable.ic_momo);
                relativeLayoutMoMo.setBackgroundResource(R.drawable.bg_layout1);
                iconThanhToanPaypal.setImageResource(R.drawable.ic_paypal);
                relativeLayoutPaypal.setBackgroundResource(R.drawable.bg_layout1);
            }
        });

        //thanh toan momo click
        PushDownAnim.setPushDownAnimTo(relativeLayoutMoMo).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                i = 2;
                iconThanhtoanTienMat.setImageResource(R.drawable.ic_payment);
                relavetiveTienMat.setBackgroundResource(R.drawable.bg_layout1);
                iconThanhToanMoMo.setImageResource(R.drawable.ic_checked);
                relativeLayoutMoMo.setBackgroundResource(R.drawable.bg_layout_licked);
                iconThanhToanPaypal.setImageResource(R.drawable.ic_paypal);
                relativeLayoutPaypal.setBackgroundResource(R.drawable.bg_layout1);
            }
        });

        //thanh toan paypal click
        PushDownAnim.setPushDownAnimTo(relativeLayoutPaypal).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                i = 3;
                iconThanhToanPaypal.setImageResource(R.drawable.ic_checked);
                relativeLayoutPaypal.setBackgroundResource(R.drawable.bg_layout_licked);
                iconThanhtoanTienMat.setImageResource(R.drawable.ic_payment);
                relavetiveTienMat.setBackgroundResource(R.drawable.bg_layout1);
                iconThanhToanMoMo.setImageResource(R.drawable.ic_momo);
                relativeLayoutMoMo.setBackgroundResource(R.drawable.bg_layout1);
            }
        });


        PushDownAnim.setPushDownAnimTo(btnXacnhanThanhtoan).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DateFormat formatter = new SimpleDateFormat("HH:mm dd/MM/yyyy");
                Date date = new Date();
                String create_at = formatter.format(date);
                //cap nhat lai dia chi cho tai khoan
                if (i == 0){
                    Toast.makeText(getApplicationContext(),"Chưa chọn hình thức thanh toán",Toast.LENGTH_LONG).show();
                    return;
                }else if (i == 1){
                    try
                    {
                        com.example.coosincustomer.ConnectionDB conStr=new com.example.coosincustomer.ConnectionDB();
                        connect =conStr.CONN();        // Connect to database
                        if (connect == null)
                        {
                            Toast.makeText(getApplicationContext(), "Không có kết nối mạng!", Toast.LENGTH_LONG).show();
                            return;
                        }
                        else
                        {
                            String query="";

                            if (getIntent().getIntExtra("orderType",0) == 1){
                                query = "INSERT INTO ORDER_SINGLE " +
                                        "(USER_ORDER,ADDRESS_ORDER,DATE_WORK,TIME_WORK,NOTE_ORDER,SALE_CODE,TOTAL_PRICE,CREATE_AT,PAYMENT_TYPE,PAYMENT_STATUS,SEEN,TOTAL_TIME,LATITUDE,LONGITUDE)" +
                                        "VALUES('"+phone_num+"',N'"+getIntent().getStringExtra("address")+"',N'"+getIntent().getStringExtra("date")
                                        +"',N'"+getIntent().getStringExtra("ca")+"',N'"+getIntent().getStringExtra("ghichu")+"','"+
                                        getIntent().getStringExtra("makhuyenmai")+"',"+getIntent().getIntExtra("totalPrice",0)+",'"+
                                        create_at+"',N'"+paymentType+"',"+0+","+0+",'"+getIntent().getStringExtra("totalTime")+"',"+latitude+","+longitude+")";

                            }


                            if (getIntent().getIntExtra("orderType",0) == 2){
                                query = "INSERT INTO ORDER_MULTI " +
                                        "(USER_ORDER,ADDRESS_ORDER,SCHEDULE,TIME_WORK,DATE_START,DATE_END,NOTE_ORDER,SALE_CODE,TOTAL_PRICE,CREATE_AT,PAYMENT_TYPE,PAYMENT_STATUS,SEEN,LATITUDE,LONGITUDE,TOTAL_TIME,TOTAL_BUOI)" +
                                        "VALUES('"+phone_num+"',N'"+getIntent().getStringExtra("address")+"',N'"+getIntent().getStringExtra("schedule")+"'," +
                                        "N'"+getIntent().getStringExtra("timeWork")+"','"+getIntent().getStringExtra("dateStart")+"','"+getIntent().getStringExtra("dateEnd")+"'," +
                                        "'"+getIntent().getStringExtra("note")+"','',"+getIntent().getIntExtra("totalPrice",0)+",'"+create_at+"'," +
                                        "N'"+paymentType+"',"+0+","+0+","+latitude+","+longitude+",'"+getIntent().getStringExtra("totalTime")+"',"+getIntent().getIntExtra("totalBuoi",0)+")";
                            }

                            if (getIntent().getIntExtra("orderType",0) == 3){
                                query = "INSERT INTO ORDER_OVERVIEW " +
                                        "(USER_ORDER,ADDRESS_ORDER,DATE_WORK,TIME_START,AREA_TYPE,NOTE_ORDER,SALE_CODE,TOTAL_PRICE,CREATE_AT,PAYMENT_TYPE,PAYMENT_STATUS,SEEN,LATITUDE,LONGITUDE)" +
                                        "VALUES('"+phone_num+"',N'"+getIntent().getStringExtra("address")+"',N'"+getIntent().getStringExtra("dateWork")+"'," +
                                        "'"+getIntent().getStringExtra("timeStart")+"',"+getIntent().getIntExtra("areaType",0)+",N'"+getIntent().getStringExtra("note")+"',''," +
                                        "'"+getIntent().getIntExtra("totalPrice",0)+"','"+create_at+"',N'"+paymentType+"',"+0+","+0+","+latitude+","+longitude+")";
                            }

                            if (getIntent().getIntExtra("orderType",0) == 4){
                                query = "INSERT INTO ORDER_COOK " +
                                        "(USER_ORDER,ADDRESS_ORDER,DATE_WORK,TIME_WORK,PEOPLE_AMOUNT,DISH_AMOUNT,DISH_NAME,TASTE,FRUIT,MARKET,MAX_MARKET_PRICE,SALE_CODE,TOTAL_PRICE,NOTE_ORDER,CREATE_AT,PAYMENT_TYPE" +
                                        ",PAYMENT_STATUS,SEEN,LATITUDE,LONGITUDE)" +
                                        "VALUES('"+phone_num+"',N'"+getIntent().getStringExtra("address")+"',N'"+getIntent().getStringExtra("dateWork")+"'," +
                                        "'"+getIntent().getStringExtra("timeWork")+"','"+getIntent().getStringExtra("peopleAmount")+"','"+
                                        getIntent().getStringExtra("dishAmount")+"',N'"+getIntent().getStringExtra("dishName")+"',N'"+getIntent().getStringExtra("taste")+"','"+
                                        getIntent().getStringExtra("fruit")+"','"+getIntent().getStringExtra("market")+"','"+getIntent().getIntExtra("marketPrice",0)+"','','"+
                                        getIntent().getIntExtra("totalPrice",0)+"',N'"+getIntent().getStringExtra("note")+"','"+create_at+"',N'"+paymentType+"',"+0+","+0+","+latitude+","+longitude+")";
                            }
                            Statement stmt = connect.createStatement();
                            stmt.executeQuery(query);
                            connect.close();


                        }
                    }
                    catch (Exception ex)
                    {
                        //notification
                        TOPIC = "/topics/lich";
                        NOTIFICATION_TITLE = "CoOsin khách hàng";
                        NOTIFICATION_MESSAGE ="Có lịch mới";

                        JSONObject notification = new JSONObject();
                        JSONObject notifcationBody = new JSONObject();
                        try {
                            notifcationBody.put("title", NOTIFICATION_TITLE);
                            notifcationBody.put("message", NOTIFICATION_MESSAGE);

                            notification.put("to", TOPIC);

                            notification.put("data", notifcationBody);
                        } catch (JSONException e) {
                            Log.e(TAG, "onCreate: " + e.getMessage());
                        }
                        sendNotification(notification);


                        Intent  intent = new Intent(ThanhtoanActivity.this,OrderSuccessActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        intent.putExtra("tongtien",getIntent().getIntExtra("totalPrice",0));
                        intent.putExtra("paymentType","Tiền mặt");
                        startActivity(intent);
                        try {
                            //cap nhat lai dia chi
                            String query = "UPDATE CUSTOMER SET ADDRESS = N'" + getIntent().getStringExtra("address") +"' WHERE PHONE_NUM='"+phone_num+"'";
                            Statement stmt = connect.createStatement();
                            stmt.executeQuery(query);
                        } catch (SQLException e) {

                        }
                    }
                }else if (i == 2){
                    Toast.makeText(getApplicationContext(),"Phương thức thanh toán này đang được phát triển",Toast.LENGTH_LONG).show();
                    return;
                }else if (i==3){
                    Intent intent = new Intent(ThanhtoanActivity.this,PayPalActivity.class);
                    intent.putExtra("amount",getIntent().getIntExtra("totalPrice",0));
                    intent.putExtra("orderType",getIntent().getIntExtra("orderType",0));

                    //dung le
                    intent.putExtra("address",getIntent().getStringExtra("address"));
                    intent.putExtra("date",getIntent().getStringExtra("date"));
                    intent.putExtra("ca",getIntent().getStringExtra("ca"));
                    intent.putExtra("ghichu",getIntent().getStringExtra("ghichu"));
                    intent.putExtra("makhuyenmai",getIntent().getStringExtra("makhuyenmai"));
                    intent.putExtra("totalTime",getIntent().getStringExtra("totalTime"));
                    intent.putExtra("latitude",getIntent().getDoubleExtra("latitude",0));
                    intent.putExtra("longitude",getIntent().getDoubleExtra("longitude",0));

                    //dinh ky
                    intent.putExtra("schedule",getIntent().getStringExtra("schedule"));
                    intent.putExtra("timeWork",getIntent().getStringExtra("timeWork"));
                    intent.putExtra("dateStart",getIntent().getStringExtra("dateStart"));
                    intent.putExtra("dateEnd",getIntent().getStringExtra("dateEnd"));
                    intent.putExtra("totalBuoi",getIntent().getIntExtra("totalBuoi",0));

                    //TVS
                    intent.putExtra("dateWork",getIntent().getStringExtra("dateWork"));
                    intent.putExtra("timeStart",getIntent().getStringExtra("timeStart"));
                    intent.putExtra("areaType",getIntent().getIntExtra("areaType",0));
                    //NAUAN
                    intent.putExtra("peopleAmount",getIntent().getStringExtra("peopleAmount"));
                    intent.putExtra("dishAmount",getIntent().getStringExtra("dishAmount"));
                    intent.putExtra("dishName", getIntent().getStringExtra("dishName"));
                    intent.putExtra("taste", getIntent().getStringExtra("taste"));
                    intent.putExtra("fruit", getIntent().getStringExtra("fruit"));
                    intent.putExtra("market", getIntent().getStringExtra("market"));
                    intent.putExtra("marketPrice", getIntent().getIntExtra("marketPrice",0));
                    intent.putExtra("note",  getIntent().getStringExtra("note"));

                    startActivity(intent);
                }
                finish();
            }
        });
    }

    //toolbar back button
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    private void sendNotification(JSONObject notification) {
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(FCM_API, notification,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(ThanhtoanActivity.this, "Request error", Toast.LENGTH_LONG).show();
                        Log.i(TAG, "onErrorResponse: Didn't work");
                    }
                }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("Authorization", serverKey);
                params.put("Content-Type", contentType);
                return params;
            }
        };
        MySingleton.getInstance(getApplicationContext()).addToRequestQueue(jsonObjectRequest);
    }

//    public static final MediaType JSON
//            = MediaType.parse("application/json; charset=utf-8");
//    private void sendNotification(final String regToken) {
//        new AsyncTask<Void,Void,Void>(){
//            @Override
//            protected Void doInBackground(Void... params) {
//                try {
//                    OkHttpClient client = new OkHttpClient();
//                    JSONObject json=new JSONObject();
//                    JSONObject dataJson=new JSONObject();
//                    dataJson.put("body","Hi this is sent from device to device");
//                    dataJson.put("title","dummy title");
//                    json.put("notification",dataJson);
//                    json.put("to",regToken);
//                    RequestBody body = RequestBody.create(JSON, json.toString());
//                    Request request = new Request.Builder()
//                            .header("Authorization",serverKey)
//                            .url("https://fcm.googleapis.com/fcm/send")
//                            .post(body)
//                            .build();
//                    okhttp3.Response response = client.newCall(request).execute();
//                    String finalResponse = response.body().string();
//                }catch (Exception e){
//                    //Log.d(TAG,e+"");
//                }
//                return null;
//            }
////        }.execute();
//
//    }
}
