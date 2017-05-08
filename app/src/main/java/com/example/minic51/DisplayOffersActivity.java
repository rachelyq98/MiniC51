package com.example.minic51;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import java.util.ArrayList;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by rachelliu on 2017-05-05.
 */

public class DisplayOffersActivity extends AppCompatActivity {

    private String url;
    private RecyclerView recyclerView;
    private OffersAdapter offerAdapter;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.offers_list);
        Intent intent = getIntent();
        String user = intent.getStringExtra(LoginPageActivity.USER_NAME);
        String password = intent.getStringExtra(LoginPageActivity.PASSWORD);
        LoginAPI login = new LoginAPI(user, password);
        this.url = login.getURL();
        offerAdapter = new OffersAdapter(this);
        recyclerView = (RecyclerView) findViewById(R.id.offer_recycler_view);
        recyclerView.setAdapter(offerAdapter);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        try {
            run();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void run() throws IOException {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(url)
                .build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                call.cancel();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final String jsonData = response.body().string();
                String name = "";
                String iconCode = "";
                try {
                    JSONObject obj = new JSONObject(jsonData);
                    JSONArray array = obj.getJSONArray("offers");
                    final ArrayList<Offer> offersList = new ArrayList<Offer>();
                    for (int i = 0; i < array.length(); i++) {
                        JSONObject newObj = array.getJSONObject(i);
                        name = newObj.getString("name");
                        iconCode = newObj.getString("image_b64");
                        offersList.add(new Offer(name, iconCode));
                    }
                    DisplayOffersActivity.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            offerAdapter.setOffers(offersList);
                        }
                    });
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

}