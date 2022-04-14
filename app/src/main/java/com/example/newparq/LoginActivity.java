package com.example.newparq;

import static java.util.regex.Pattern.matches;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;



import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class LoginActivity extends AppCompatActivity {
private EditText editTextpassword, editTextemail;
private ProgressBar progressBar;
private FirebaseAuth authProfile;
private  ImageView goto_reg;
private  TextView forgotpass;
private TextView remMe;
private Button button_login;

    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://newparq-475c8-default-rtdb.firebaseio.com/");


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

//        getSupportActionBar().setTitle("Login");
        authProfile = FirebaseAuth.getInstance();

         editTextemail= findViewById(R.id.email);
         editTextpassword = findViewById(R.id.password);
//        final Button button_login = findViewById(R.id.login);


          progressBar=findViewById(R.id.progress);

          authProfile = FirebaseAuth.getInstance();

          //login user
        Button button_login =findViewById(R.id.login);
        button_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String textEmail = editTextemail.getText().toString();
                String textPass = editTextpassword.getText().toString();

                if (TextUtils.isEmpty(textEmail)){
                    Toast.makeText(LoginActivity.this, "Please enter your email", Toast.LENGTH_SHORT).show();
                    editTextemail.setError("Email is required");
                    editTextemail.requestFocus();
                }else if (!Patterns.EMAIL_ADDRESS.matcher(textEmail).matches()){
                    Toast.makeText(LoginActivity.this, "please re_enter your email", Toast.LENGTH_SHORT).show();
                    editTextemail.setError("invalid email");
                    editTextemail.requestFocus();
                    
                }else if (TextUtils.isEmpty(textPass)){
                    Toast.makeText(LoginActivity.this, "please enter password", Toast.LENGTH_SHORT).show();
                    editTextpassword.setError("password id required");
                    editTextpassword.requestFocus();

                }else {
                    progressBar.setVisibility(View.VISIBLE);
                    loginUser(textEmail,textPass);
                }
            }
        });





//        button_login.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                //        set titile
////                getSupportActionBar().setTitle("Newparq");
//
//                final String phoneTxt= editTextEmail.getText().toString();
//                final String passwordTxt = password.getText().toString();
////                final String loginbtn = button_login.getText().toString();
////                final String fogopass = forgotpasss.getText().toString();
//
//
//                if (phoneTxt.isEmpty()) {
//                    Toast.makeText(LoginActivity.this, "Please enter your password", Toast.LENGTH_SHORT).show();
//                } else if (passwordTxt.isEmpty()) {
//                    Toast.makeText(LoginActivity.this, "Please enter your password", Toast.LENGTH_SHORT).show();
//
//
//                } else{
//
//                    databaseReference.child("sers").addListenerForSingleValueEvent(new ValueEventListener() {
//
//                        @Override
//                        public void onDataChange(@NonNull DataSnapshot snapshot) {
//
//                            //if email exists in db
//                            if(snapshot.hasChild(phoneTxt)){
//                                //matching password of user and match to one entered
//                                final String getPassword = snapshot.child(phoneTxt).child("password").getValue(String.class);
//
//
//
//                                if (getPassword.equals(passwordTxt)){
//                                    Toast.makeText(LoginActivity.this, "Logged in", Toast.LENGTH_SHORT).show();
//                                    finish();
//                                }
//                                else{
//                                    Toast.makeText(LoginActivity.this, "Wrong password", Toast.LENGTH_SHORT).show();
//                                }
//
//                            }
//
//                        }
//
//                        @Override
//                        public void onCancelled(@NonNull DatabaseError error) {
//
//                        }
//                    });
//
//
//                }
//
//            }
//        });

        ImageView gotoReg = findViewById(R.id.goto_reg);
        gotoReg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Intent i = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(i);

            }
        });
        TextView forgotpass = findViewById(R.id.fogetpass);
        forgotpass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Intent i= new Intent(LoginActivity.this,ForgotPassActivity.class);
                startActivity(i);
            }
        });
    }

    private void loginUser(String textEmail, String textPass) {
        authProfile.signInWithEmailAndPassword(textEmail,textPass).addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){
                    Toast.makeText(LoginActivity.this, "You are logged in", Toast.LENGTH_SHORT).show();
                    final Intent intent = new Intent(LoginActivity.this,HomeActivity.class);
                    startActivity(intent);

                }else {
                    Toast.makeText(LoginActivity.this, "Something went wrong", Toast.LENGTH_SHORT).show();

                }
                progressBar.setVisibility(View.GONE);


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