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

    private interface UserInfoCallBack {

        void onSuccess(String authToken, String userId);
        void onFailure(Exception e);
    }

    public interface UrlCallback {

        void onSuccess(String url);
        void onFailure(Exception e);
    }

    private String userUrl;
    private String username;
    private String password;

    public LoginAPI (String username, String password){
        this.username = username;
        this.password = password;
        this.userUrl="";
    }

    public void getURL (final UrlCallback callback){
        getUserInfo(new UserInfoCallBack() {
            @Override
            public void onSuccess(String authToken, String userId) {
                String url = "https://public-api.checkout51.com/v1/getOffers?page=1&platform=android&uuid=42e681ac-da61-4bc2-911f-79f5183745cb&token_version=2&authtoken=" + authToken + "&user_id=" + userId;
                callback.onSuccess(url);
            }

            @Override
            public void onFailure(Exception e) {
                callback.onFailure(e);
            }
        });
    }

    private void getUserInfo (final UserInfoCallBack callback){
        try {
            String encodedPassword = URLEncoder.encode(password, "UTF-8");
            String encodedUser = URLEncoder.encode(username, "UTF-8");
            userUrl = "https://public-api.checkout51.com/v1/login?email=" + encodedUser +"&password=" + encodedPassword +"&platform=android";
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        try {
            runUserInfo(callback);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void runUserInfo (final UserInfoCallBack callback) throws  IOException{

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
                    String valid = user.get("status").toString();
                    if (valid.equalsIgnoreCase("login_success")){
                        String authToken = user.getString("token");
                        String userId = user.getString("user_id");

                        callback.onSuccess(authToken, userId);
                    } else {

                        callback.onFailure(new Exception("Login Failure"));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();

                    callback.onFailure(e);
                }
            }
        });
    }
}
