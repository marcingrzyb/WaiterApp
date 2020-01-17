/*
 *
 *   Copyright 2020 Marcin Grzyb
 *
 *   Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 *   The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
 *
 *  THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 *
 */

package com.restaurant.waiterapp;

import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hsalf.smilerating.BaseRating;
import com.hsalf.smilerating.SmileRating;
import com.restaurant.waiterapp.api.resources.FeedbackEnum;
import com.restaurant.waiterapp.api.resources.FeedbackPojo;
import com.restaurant.waiterapp.apiconnection.RequestsPatch;
import com.restaurant.waiterapp.apiconnection.RequestsPost;

import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;

import static com.restaurant.waiterapp.apiconnection.ConnectionConfig.getConnectionConfig;

public class RateActivity extends AppCompatActivity {
    private static final Logger LOGGER = Logger.getLogger( RateActivity.class.getName() );
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
        //triggered after using Button
        String feedbackPojo=prepareRequest(dish.getRating(),beverage.getRating(),service.getRating(),orderid);
        new AsyncTask<String, Void, Void>() {
            boolean result=false;
            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                if (result){
                    Toast.makeText(getBaseContext(), "Feedback sent", Toast.LENGTH_SHORT).show();
                    finish();
                }
                else {
                    Toast.makeText(getBaseContext(), "Error while sending feedback, Try again", Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            protected Void doInBackground(String... strings) {
                result= RequestsPost.sendPost(strings[0], strings[1]);
                return null;
            }
        }.execute(getConnectionConfig()+"/api/waiter/clientFeedback",feedbackPojo);

    }

    @SuppressLint("StaticFieldLeak")
    public void onClickSkipFeedback(View view) {
        //triggered after using Button
        new AsyncTask<String, Void, Void>() {
            boolean result=false;
            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                if (result){
                    Toast.makeText(getBaseContext(), "Finalized", Toast.LENGTH_SHORT).show();
                    finish();
                }
                else {
                    Toast.makeText(getBaseContext(), "Error while finalizing, Try again", Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            protected Void doInBackground(String... strings) {
                result= RequestsPatch.patchRequest(strings[0]);
                return null;
            }
        }.execute(getConnectionConfig()+"/api/waiter/finalize?orderId="+orderid.toString());

    }
    String prepareRequest(int dish,int bevarage,int service,Long orderid){
        //prepares Json that contains feedback data
        String jsonOrderRequest="";
        FeedbackPojo feedbackPojo=new FeedbackPojo(FeedbackEnum.fromInt(service),FeedbackEnum.fromInt(bevarage),FeedbackEnum.fromInt(dish),orderid);
        ObjectMapper mapper = new ObjectMapper();
        try {
            jsonOrderRequest = mapper.writeValueAsString(feedbackPojo);
        } catch (JsonProcessingException e) {
            LOGGER.log(Level.SEVERE, e.toString());
        }
        return jsonOrderRequest;
    }
}
