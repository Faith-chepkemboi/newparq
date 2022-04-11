package com.example.newparq;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;



import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class LoginActivity extends AppCompatActivity {
//    private FirebaseAuth mAuth;

    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://newparq-475c8-default-rtdb.firebaseio.com/");
//    ImageView got_reg;
//
//    EditText editTextEmail, editTextPassword;
//    Button button_login;
//    TextView forgotpasss;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
//         mAuth = FirebaseAuth.getInstance();

        final EditText phone= findViewById(R.id.phone);
        final EditText password = findViewById(R.id.password);
        final Button button_login = findViewById(R.id.login);
        final TextView forgotpasss = findViewById(R.id.fogetpass);
        final ImageView gotoReg = findViewById(R.id.goto_reg);

        button_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //        set titile
                getSupportActionBar().setTitle("Newparq");

                final String phoneTxt= phone.getText().toString();
                final String passwordTxt = password.getText().toString();
//                final String loginbtn = button_login.getText().toString();
//                final String fogopass = forgotpasss.getText().toString();


                if (phoneTxt.isEmpty()) {
                    Toast.makeText(LoginActivity.this, "Please enter your password", Toast.LENGTH_SHORT).show();
                } else if (passwordTxt.isEmpty()) {
                    Toast.makeText(LoginActivity.this, "Please enter your password", Toast.LENGTH_SHORT).show();


                } else{

                    databaseReference.child("sers").addListenerForSingleValueEvent(new ValueEventListener() {

                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {

                            //if email exists in db
                            if(snapshot.hasChild(phoneTxt)){
                                //matching password of user and match to one entered
                                final String getPassword = snapshot.child(phoneTxt).child("password").getValue(String.class);



                                if (getPassword.equals(passwordTxt)){
                                    Toast.makeText(LoginActivity.this, "Logged in", Toast.LENGTH_SHORT).show();
                                    finish();
                                }
                                else{
                                    Toast.makeText(LoginActivity.this, "Wrong password", Toast.LENGTH_SHORT).show();
                                }

                            }

                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });


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