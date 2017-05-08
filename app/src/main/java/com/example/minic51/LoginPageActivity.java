package com.example.minic51;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class LoginPageActivity extends AppCompatActivity {

    public static String USER_NAME = "com.example.myfirstapp.USER";
    public static String PASSWORD = "com.example.myfirstapp.PASSWORD";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_page);
    }

    public void displayOffers (View view){
        Intent intent = new Intent(this, DisplayOffersActivity.class);
        EditText userEntry = (EditText)findViewById(R.id.nameEntry);
        EditText passwordEntry = (EditText) findViewById(R.id.passwordEntry);
        String user = userEntry.getText().toString();
        String password = passwordEntry.getText().toString();
        intent.putExtra(USER_NAME, user);
        intent.putExtra(PASSWORD, password);
        startActivity (intent);
    }
}
