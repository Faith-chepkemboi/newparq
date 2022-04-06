package com.example.newparq;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class RegisterActivity extends AppCompatActivity {

    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://newparq-475c8-default-rtdb.firebaseio.com/");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        final EditText fullname = findViewById(R.id.fnames);
        final EditText phoneTxt = findViewById(R.id.pnumber);
        final EditText identyNumber = findViewById(R.id.idno);
        final EditText emaiil = findViewById(R.id.email);
        final EditText passwordTxtt = findViewById(R.id.password);
        final EditText conPassTxt = findViewById(R.id.conpass);
        final Button registerUser = findViewById(R.id.register);
        final ImageView backTo = findViewById(R.id.back_to_login);


        registerUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final String fullnameTxt = fullname.getText().toString();
                final String phone =phoneTxt.getText().toString();
                final String identyNumberTxt = identyNumber.getText().toString();
                final String email= emaiil.getText().toString();
                final String password = passwordTxtt.getText().toString();
                final  String conpass = conPassTxt.getText().toString();

                if (fullnameTxt.isEmpty() || password.isEmpty() || phone.isEmpty() || email.isEmpty() || identyNumberTxt.isEmpty() || conpass.isEmpty()){
                    Toast.makeText(RegisterActivity.this, "All fields are required", Toast.LENGTH_SHORT).show();

                }
                else if (!password.equals(conpass)){
                    Toast.makeText(RegisterActivity.this, "Password inserted not the same", Toast.LENGTH_SHORT).show();
                }
                else{
                    databaseReference.child("users").addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {

                            if(snapshot.hasChild(email)){
                                Toast.makeText(RegisterActivity.this, "Email already registered ", Toast.LENGTH_SHORT).show();
                            }else{
                                databaseReference.child("users").child(email).child("fullname").setValue(fullnameTxt);
                                databaseReference.child("users").child(email).child("phoneTxt").setValue(phone);
                                databaseReference.child("users").child(email).child("identyNumber").setValue(identyNumberTxt);
                                databaseReference.child("users").child(email).child("passwordTxtt").setValue(password);

                                Toast.makeText(RegisterActivity.this, "Registration successfull", Toast.LENGTH_SHORT).show();
                                finish();

                            }

                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });

                }



            }
        });





        backTo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Intent i = new Intent(RegisterActivity.this, LoginActivity.class);
                startActivity(i);
            }
        });



    }
}