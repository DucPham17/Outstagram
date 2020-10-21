package com.ducpham.outstagram;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SignUpCallback;

public class LoginActivity extends AppCompatActivity {

    public static final int REQUEST_CODE = 321;
    EditText username;
    EditText password;
    Button login;
    Button signup;
    public static final String TAG = "LOGIN";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        username = findViewById(R.id.username);
        password = findViewById(R.id.password);
        login = findViewById(R.id.login);
        signup = findViewById(R.id.signup);
        if(ParseUser.getCurrentUser() != null){
            goMainActivity();
        }
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String user = username.getText().toString();
                String pass = password.getText().toString();
                login(user,pass);
            }
        });
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onSignUp();
            }
        });
    }

    private void login(String user, String pass){
        Log.d(TAG,"In log in process");
        ParseUser.logInInBackground(user, pass, new LogInCallback() {
            @Override
            public void done(ParseUser user, ParseException e) {
                if(e != null){
                    Log.d(TAG,"Problems with login");
                    return;
                }
                goMainActivity();
            }
        });
    }

    private void goMainActivity(){
        Intent intent = new Intent(this,MainActivity.class);
        startActivity(intent);
    }

    private void onSignUp(){
        Intent intent = new Intent(this,SignUpActivity.class);
        startActivityForResult(intent,REQUEST_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        Log.d(TAG,"start");
        if(requestCode == REQUEST_CODE){
            Log.d(TAG,"start1");
            if(resultCode == RESULT_OK){
                Log.d(TAG,"start2");
                String curUserName = data.getStringExtra("username");
                String curPassword = data.getStringExtra("pass");
                ParseUser user = new ParseUser();
                user.setUsername(curUserName);
                user.setPassword(curPassword);
                user.signUpInBackground(new SignUpCallback() {
                    @Override
                    public void done(ParseException e) {
                        if(e != null){
                            Log.d(TAG,"Sign up error",e);
                        }
                    }
                });
                Log.d(TAG,"Sign up good");
                Toast.makeText(LoginActivity.this,"SignUp successful",Toast.LENGTH_SHORT).show();
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}