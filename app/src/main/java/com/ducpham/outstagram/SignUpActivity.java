package com.ducpham.outstagram;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class SignUpActivity extends AppCompatActivity {
    EditText usernameSignUp;
    EditText passwordSignUp;
    Button signupS;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        usernameSignUp = findViewById(R.id.usernameSignUp);
        passwordSignUp = findViewById(R.id.passwordSignUp);
        signupS = findViewById(R.id.signupS);

        signupS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(usernameSignUp.getText().toString().isEmpty()){
                    Toast.makeText(SignUpActivity.this,"Username cannot be empty",Toast.LENGTH_SHORT).show();
                    return;
                }
                if(passwordSignUp.getText().toString().isEmpty()){
                    Toast.makeText(SignUpActivity.this,"Password cannot be empty",Toast.LENGTH_SHORT).show();
                    return;
                }
                Intent intent = new Intent();
                intent.putExtra("username",usernameSignUp.getText().toString());
                intent.putExtra("pass",passwordSignUp.getText().toString());
                usernameSignUp.setText("");
                passwordSignUp.setText("");
                setResult(RESULT_OK,intent);
                finish();
            }
        });
    }
}