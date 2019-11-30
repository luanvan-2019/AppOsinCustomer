package com.example.coosincustomer;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.coosincustomer.config.Config;
import com.example.coosincustomer.notification.MySingleton;
import com.paypal.android.sdk.payments.PayPalConfiguration;
import com.paypal.android.sdk.payments.PayPalPayment;
import com.paypal.android.sdk.payments.PayPalService;
import com.paypal.android.sdk.payments.PaymentActivity;
import com.paypal.android.sdk.payments.PaymentConfirmation;

import org.json.JSONException;
import org.json.JSONObject;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class PayPalActivity extends AppCompatActivity {

    private static final int PAYPAL_REQUEST_CODE = 7171;

    private static PayPalConfiguration config = new PayPalConfiguration()
            .environment(PayPalConfiguration.ENVIRONMENT_SANDBOX)
            .clientId(Config.PAYPAL_CLIENT_ID);

    Button btnPayNow;
    TextView txtAmount;
    String amount = "";
    Connection connect;
    String phone_num, paymentType="PayPal";
    Double latitude = null;
    Double longitude = null;
    final private String FCM_API = "https://fcm.googleapis.com/fcm/send";
    final private String serverKey = "key=" + "AAAAZNB3xoU:APA91bGxISuo_YVJ7-142Aua8xMYvafuhaZvNIf01IeOzVrZ1hEypTqdP53X3pMZg_Mx3XkkVJOdiiDCMnHp00ytrTJxLDaozcdVpEXc1AsciThWq5ZkiDOHswqSHsLEskoVXOpC8SZC";
    final private String contentType = "application/json";
    final String TAG = "NOTIFICATION TAG";
    String NOTIFICATION_TITLE;
    String NOTIFICATION_MESSAGE;


    @Override
    protected void onDestroy() {
        stopService(new Intent(this,PayPalService.class));
        super.onDestroy();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pay_pal);

        txtAmount = findViewById(R.id.txt_amount);
        btnPayNow = findViewById(R.id.btn_paynow);

        //lay so dien thoai da luu khi dang nhap
        SharedPreferences SP = getApplicationContext().getSharedPreferences("PHONE",0);
        phone_num = SP.getString("phone_num",null);


        latitude = getIntent().getDoubleExtra("latitude",0);
        longitude = getIntent().getDoubleExtra("longitude",0);

        Intent intent = new Intent(this,PayPalService.class);
        intent.putExtra(PayPalService.EXTRA_PAYPAL_CONFIGURATION,config);
        startService(intent);

        processPayment();
    }

    private void processPayment(){
        amount = String.valueOf(getIntent().getIntExtra("amount",0));
        PayPalPayment payPalPayment = new PayPalPayment(new BigDecimal(amount),
                "USD","Thanh toán cho CoOsin",PayPalPayment.PAYMENT_INTENT_SALE);
        Intent intent = new Intent(this, PaymentActivity.class);
        intent.putExtra(PayPalService.EXTRA_PAYPAL_CONFIGURATION,config);
        intent.putExtra(PaymentActivity.EXTRA_PAYMENT,payPalPayment);
        startActivityForResult(intent,PAYPAL_REQUEST_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == PAYPAL_REQUEST_CODE){
            if (resultCode == RESULT_OK){
                PaymentConfirmation confirmation = data.getParcelableExtra(PaymentActivity.EXTRA_RESULT_CONFIRMATION);
                if (confirmation!= null){
                    try {
                        String paymentDetails = confirmation.toJSONObject().toString(4);

//                        startActivity(new Intent(this,PaymentDetailsActivity.class)
//                                .putExtra("PaymentDetails",paymentDetails)
//                                .putExtra("PaymentAmount",amount));
                        insertDataOrder();
                    }catch (JSONException e){
                        e.printStackTrace();
                    }
                }
            }else if (resultCode == Activity.RESULT_CANCELED){
                Toast.makeText(getApplicationContext(),"Cancel",Toast.LENGTH_LONG).show();
            }
        }else if (requestCode == PaymentActivity.RESULT_EXTRAS_INVALID){
            Toast.makeText(getApplicationContext(),"Invalid",Toast.LENGTH_LONG).show();
        }
    }

    private void insertDataOrder(){
        DateFormat formatter = new SimpleDateFormat("HH:mm dd/MM/yyyy");
        Date date = new Date();
        String create_at = formatter.format(date);
        Integer orderType;
        orderType =getIntent().getIntExtra("orderType",0);
        String address = getIntent().getStringExtra("address");
        String dateWork = getIntent().getStringExtra("date");
        String ca = getIntent().getStringExtra("ca");
        String ghiChu = getIntent().getStringExtra("ghichu");
        String maKH = getIntent().getStringExtra("makhuyenmai");
        Integer totalPrice = getIntent().getIntExtra("amount",0);
        String totalTime = getIntent().getStringExtra("totalTime");



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

                if (orderType == 1){
                    query = "INSERT INTO ORDER_SINGLE " +
                            "(USER_ORDER,ADDRESS_ORDER,DATE_WORK,TIME_WORK,NOTE_ORDER,SALE_CODE,TOTAL_PRICE,CREATE_AT,PAYMENT_TYPE,PAYMENT_STATUS,SEEN,TOTAL_TIME,LATITUDE,LONGITUDE)" +
                            "VALUES('"+phone_num+"',N'"+address+"',N'"+dateWork
                            +"',N'"+ca+"',N'"+ghiChu+"','"+ maKH+"',"+totalPrice+
                            ",'"+create_at+"',N'"+paymentType+"',"+1+","+0+",'"+totalTime+"',"+latitude+","+longitude+")";
                }
                if (orderType == 2){
                    query = "INSERT INTO ORDER_MULTI " +
                            "(USER_ORDER,ADDRESS_ORDER,SCHEDULE,TIME_WORK,DATE_START,DATE_END,NOTE_ORDER,SALE_CODE,TOTAL_PRICE,CREATE_AT,PAYMENT_TYPE,PAYMENT_STATUS,SEEN,LATITUDE,LONGITUDE,TOTAL_TIME,TOTAL_BUOI)" +
                            "VALUES('"+phone_num+"',N'"+getIntent().getStringExtra("address")+"',N'"+getIntent().getStringExtra("schedule")+"'," +
                            "N'"+getIntent().getStringExtra("timeWork")+"','"+getIntent().getStringExtra("dateStart")+"','"+getIntent().getStringExtra("dateEnd")+"'," +
                            "'"+getIntent().getStringExtra("note")+"','',"+totalPrice+",'"+create_at+"'," +
                            "N'"+paymentType+"',"+1+","+0+","+latitude+","+longitude+",'"+getIntent().getStringExtra("totalTime")+"',"+getIntent().getIntExtra("totalBuoi",0)+")";
                }

                if (orderType == 3){
                    query = "INSERT INTO ORDER_OVERVIEW " +
                            "(USER_ORDER,ADDRESS_ORDER,DATE_WORK,TIME_START,AREA_TYPE,NOTE_ORDER,SALE_CODE,TOTAL_PRICE,CREATE_AT,PAYMENT_TYPE,PAYMENT_STATUS,SEEN,LATITUDE,LONGITUDE)" +
                            "VALUES('"+phone_num+"',N'"+getIntent().getStringExtra("address")+"',N'"+getIntent().getStringExtra("dateWork")+"'," +
                            "'"+getIntent().getStringExtra("timeStart")+"',"+getIntent().getIntExtra("areaType",0)+",N'"+getIntent().getStringExtra("note")+"',''," +
                            "'"+totalPrice+"','"+create_at+"',N'"+paymentType+"',"+1+","+0+","+latitude+","+longitude+")";
                }

                if (orderType == 4){
                    query = "INSERT INTO ORDER_COOK " +
                            "(USER_ORDER,ADDRESS_ORDER,DATE_WORK,TIME_WORK,PEOPLE_AMOUNT,DISH_AMOUNT,DISH_NAME,TASTE,FRUIT,MARKET,MAX_MARKET_PRICE,SALE_CODE,TOTAL_PRICE,NOTE_ORDER,CREATE_AT,PAYMENT_TYPE" +
                            ",PAYMENT_STATUS,SEEN,LATITUDE,LONGITUDE)" +
                            "VALUES('"+phone_num+"',N'"+getIntent().getStringExtra("address")+"',N'"+getIntent().getStringExtra("dateWork")+"'," +
                            "'"+getIntent().getStringExtra("timeWork")+"','"+getIntent().getStringExtra("peopleAmount")+"','"+
                            getIntent().getStringExtra("dishAmount")+"',N'"+getIntent().getStringExtra("dishName")+"',N'"+getIntent().getStringExtra("taste")+"','"+
                            getIntent().getStringExtra("fruit")+"','"+getIntent().getStringExtra("market")+"','"+getIntent().getIntExtra("marketPrice",0)+"','','"+
                            totalPrice+"',N'"+getIntent().getStringExtra("note")+"','"+create_at+"',N'"+paymentType+"',"+1+","+0+","+latitude+","+longitude+")";
                }
                Statement stmt = connect.createStatement();
                stmt.executeQuery(query);
                connect.close();
            }
        }
        catch (Exception ex)
        {
            //notification
            NOTIFICATION_TITLE = "CoOsin";
            NOTIFICATION_MESSAGE ="Có lịch mới";

            JSONObject notification = new JSONObject();
            JSONObject notifcationBody = new JSONObject();
            try {
                notifcationBody.put("title", NOTIFICATION_TITLE);
                notifcationBody.put("message", NOTIFICATION_MESSAGE);

                notification.put("to", "/topics/lich");

                notification.put("data", notifcationBody);
            } catch (JSONException e) {
                Log.e(TAG, "onCreate: " + e.getMessage());
            }
            sendNotification(notification);

            Intent  intent = new Intent(PayPalActivity.this,OrderSuccessActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            intent.putExtra("tongtien",getIntent().getIntExtra("amount",0));
            intent.putExtra("paymentType","paypal");
            startActivity(intent);
            try {
                //cap nhat lai dia chi
                String query = "UPDATE CUSTOMER SET ADDRESS = N'" + getIntent().getStringExtra("address") +"' WHERE PHONE_NUM='"+phone_num+"'";
                Statement stmt = connect.createStatement();
                stmt.executeQuery(query);
            } catch (SQLException e) {

            }
        }
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
                        Toast.makeText(PayPalActivity.this, "Request error", Toast.LENGTH_LONG).show();
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

}
