package com.example.newparq;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {
//    ImageView got_reg;
//
//    EditText editTextEmail, editTextPassword;
//    Button button_login;
//    TextView forgotpasss;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        final EditText email = findViewById(R.id.email);
        final EditText password = findViewById(R.id.password);
        final Button button_login = findViewById(R.id.login);
        final TextView forgotpasss = findViewById(R.id.fogetpass);
        final ImageView gotoReg = findViewById(R.id.goto_reg);

        button_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final String emailTxt = email.getText().toString();
                final String passwordTxt = password.getText().toString();
//                final String loginbtn = button_login.getText().toString();
//                final String fogopass = forgotpasss.getText().toString();


                if (emailTxt.isEmpty()) {
                    Toast.makeText(LoginActivity.this, "Please enter your email", Toast.LENGTH_SHORT).show();
                } else if (passwordTxt.isEmpty()) {
                    Toast.makeText(LoginActivity.this, "Please enter your password", Toast.LENGTH_SHORT).show();

                } else {

                }

            }
        });


        gotoReg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Intent i = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(i);

            }
        });

        forgotpasss.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Intent i= new Intent(LoginActivity.this,ForgotPassActivity.class);
                startActivity(i);
            }
        });
    }
}
//
//    }
//
//    private void attempt_login() {
//    }
//}