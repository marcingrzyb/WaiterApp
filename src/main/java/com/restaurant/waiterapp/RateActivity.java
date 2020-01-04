package com.restaurant.waiterapp;

import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hsalf.smilerating.BaseRating;
import com.hsalf.smilerating.SmileRating;
import com.restaurant.waiterapp.api.resources.FeedbackEnum;
import com.restaurant.waiterapp.api.resources.FeedbackPojo;
import com.restaurant.waiterapp.apiConnection.requestsPATCH;
import com.restaurant.waiterapp.apiConnection.requestsPOST;

import java.util.Objects;

public class RateActivity extends AppCompatActivity {
    Long orderid;
    SmileRating beverage;
    SmileRating dish;
    SmileRating service;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rate);
        orderid=Long.valueOf(Objects.requireNonNull(getIntent().getStringExtra("orderid")));
        beverage=findViewById(R.id.smile_rating_beverage);
        dish=findViewById(R.id.smile_rating_dish);
        service=findViewById(R.id.smile_rating_service);
        beverage.setSelectedSmile(BaseRating.OKAY);
        dish.setSelectedSmile(BaseRating.OKAY);
        service.setSelectedSmile(BaseRating.OKAY);
    }

    @SuppressLint("StaticFieldLeak")
    public void onClickSendFeedback(View view) {
        String feedbackPojo=prepareRequest(dish.getRating(),beverage.getRating(),service.getRating(),orderid);
        Log.d("pojo",feedbackPojo);
        new AsyncTask<String, Void, Void>() {
            Boolean result=false;
            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                Log.d("result",result.toString());
                if (result){
                    Toast.makeText(getBaseContext(), "Feedback sent", Toast.LENGTH_SHORT).show();
                }
                else {
                    Toast.makeText(getBaseContext(), "Error while sending feedback", Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            protected Void doInBackground(String... strings) {
                result= requestsPOST.sendFeedback(strings[0], strings[1]);
                return null;
            }
        }.execute("http://10.0.2.2:8080/api/waiter/clientFeedback/",feedbackPojo);
        finish();
    }

    @SuppressLint("StaticFieldLeak")
    public void onClickSkipFeedback(View view) {
        new AsyncTask<String, Void, Void>() {
            Boolean result=false;
            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                Log.d("result",result.toString());
                Log.d("orderid",orderid.toString());
                if (result){
                    Toast.makeText(getBaseContext(), "Finalized", Toast.LENGTH_SHORT).show();
                }
                else {
                    Toast.makeText(getBaseContext(), "Error while finalizing", Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            protected Void doInBackground(String... strings) {
                result= requestsPATCH.finalizeOrder(strings[0]);
                return null;
            }
        }.execute("http://10.0.2.2:8080/api/waiter/finalize?orderId="+orderid.toString());
        finish();
    }
    String prepareRequest(int dish,int bevarage,int service,Long orderid){
        String jsonOrderRequest="";
        FeedbackPojo feedbackPojo=new FeedbackPojo(FeedbackEnum.fromInt(service),FeedbackEnum.fromInt(bevarage),FeedbackEnum.fromInt(dish),orderid);
        ObjectMapper mapper = new ObjectMapper();
        try {
            jsonOrderRequest = mapper.writeValueAsString(feedbackPojo);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return jsonOrderRequest;
    }
}
