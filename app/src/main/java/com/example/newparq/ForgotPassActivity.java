package com.example.newparq;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class ForgotPassActivity extends AppCompatActivity {

    private Button buttonPassReset;
    private EditText editTextPassResetEmail;
    private ProgressBar progressBar;
    private FirebaseAuth authProfile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_pass);

        editTextPassResetEmail=findViewById(R.id.editText_password_reset_email);
        buttonPassReset=findViewById(R.id.button_password_reset);
         progressBar=findViewById(R.id.progress);


         buttonPassReset.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View view) {
                 
                 String email= editTextPassResetEmail.getText().toString();
                 
                 if (TextUtils.isEmpty(email)){
                     Toast.makeText(ForgotPassActivity.this, "Please enter your registered email", 
                             Toast.LENGTH_SHORT).show();
                     editTextPassResetEmail.setError("Email is required");
                     editTextPassResetEmail.requestFocus();
                     
                     
                 }else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
                     Toast.makeText(ForgotPassActivity.this, "Please enter valid email",
                             Toast.LENGTH_SHORT).show();
                     editTextPassResetEmail.setError("Valid email required");
                     editTextPassResetEmail.requestFocus();
                 }else{
                     progressBar.setVisibility(View.VISIBLE);
                     resetPassword(email);
                 }


                 

             }
         });


    }

    private void resetPassword(String email) {
        authProfile=FirebaseAuth.getInstance();
        authProfile.sendPasswordResetEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()){
                    Toast.makeText(ForgotPassActivity.this, "Please check your email we have sent a password reset link",
                            Toast.LENGTH_SHORT).show();

                    Intent intent = new Intent(ForgotPassActivity.this, LoginActivity.class);
                    //to prevent user from returining to register activity on pressing back button after registration
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK
                            | Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                    finish();


                }else {
                    Toast.makeText(ForgotPassActivity.this, "Something went wrong", Toast.LENGTH_SHORT).show();
                }
                progressBar.setVisibility(View.GONE);


            }


        });
    }
}