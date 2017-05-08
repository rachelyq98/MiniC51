package com.example.minic51;


import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by rachelliu on 2017-05-08.
 */

public class LoginAPI{

    private String userUrl;
    private String authToken;
    private String userId;
    private String url;
    private String username;
    private String password;
    private String valid;

    public LoginAPI (String username, String password){
        this.username = username;
        this.password = password;
        this.authToken="";
        this.userId="";
        this.url="";
        this.userUrl="";
    }

    public String getURL (){
        getUserInfo();
        url = "https://public-api.checkout51.com/v1/getOffers?page=1&platform=android&uuid=42e681ac-da61-4bc2-911f-79f5183745cb&token_version=2&authtoken=" + authToken + "&user_id=" + userId;
        return url;
    }

    private void getUserInfo (){
        try {
            String encodedPassword = URLEncoder.encode(password, "UTF-8");
            String encodedUser = URLEncoder.encode(username, "UTF-8");
            userUrl = "https://public-api.checkout51.com/v1/login?email=" + encodedUser +"&password=" + encodedPassword +"&platform=android";
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        try {
            runUserInfo();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void runUserInfo () throws  IOException{
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(userUrl)
                .build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                call.cancel();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final String jsonUserData = response.body().string();
                try {
                    JSONObject user = new JSONObject(jsonUserData);
                    valid = user.get("status").toString();
                    if (valid.equalsIgnoreCase("login_success")){
                        authToken = user.getString("token").toString();
                        userId = user.getString("user_id").toString();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
