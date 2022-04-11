package com.example.newparq;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthEmailException;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class RegisterActivity extends AppCompatActivity {

    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://newparq-475c8-default-rtdb.firebaseio.com/");
    ProgressBar progressBar;
    EditText password;
    EditText email;
    private static final String TAG ="RegisterActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
//        set titile
        Toast.makeText(this, "You can Now Register", Toast.LENGTH_SHORT).show();


        final EditText fullname = findViewById(R.id.fnames);
        final EditText phone = findViewById(R.id.pnumber);
        final EditText identyNumber = findViewById(R.id.idno);
        final EditText email = findViewById(R.id.email);
        final EditText password = findViewById(R.id.password);
        final EditText conPassword = findViewById(R.id.conpass);

        final Button buttonRegister = findViewById(R.id.register);
        final ProgressBar progressBar=findViewById(R.id.progress);
        final ImageView backTo = findViewById(R.id.back_to_login);


        buttonRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final String fullnameTxt = fullname.getText().toString();
                final String phoneTxt = phone.getText().toString();

                final String identyNumberTxt = identyNumber.getText().toString();
                final String emailTxt = email.getText().toString();
                final String passwordTxt = password.getText().toString();
                final String conpassTxt = conPassword.getText().toString();


                if (fullnameTxt.isEmpty() || passwordTxt.isEmpty() || phoneTxt.isEmpty() || emailTxt.isEmpty() || identyNumberTxt.isEmpty() || conpassTxt.isEmpty()) {
                    Toast.makeText(RegisterActivity.this, "All fields are required", Toast.LENGTH_SHORT).show();
                } else if (TextUtils.isEmpty(fullnameTxt)) {
                    Toast.makeText(RegisterActivity.this, "Please enter full names", Toast.LENGTH_SHORT).show();
                    fullname.setError("Full Name is required");
                    fullname.requestFocus();

                } else if (phoneTxt.length() != 10) {
                    Toast.makeText(RegisterActivity.this, "Please re_enter your mobile Number", Toast.LENGTH_SHORT).show();
                    phone.setError("Mobile Number Should be 10 digits");
                    phone.requestFocus();
                } else if (TextUtils.isEmpty(conpassTxt)) {
                    Toast.makeText(RegisterActivity.this, "Please comfirm your password", Toast.LENGTH_SHORT).show();

                    conPassword.setError("Password confirmation is required");
                    conPassword.requestFocus();
                } else if (!passwordTxt.equals(conpassTxt)) {
                    Toast.makeText(RegisterActivity.this, "Password inserted not the same", Toast.LENGTH_SHORT).show();
                    conPassword.setError("password confirmation is required");
                    conPassword.requestFocus();

//                    clear entered password
                    password.clearComposingText();
                    conPassword.clearComposingText();

                } else if (!Patterns.EMAIL_ADDRESS.matcher(emailTxt).matches()) {
                    email.setError("Please insert a valid email");
                    email.requestFocus();
                    return;
                } else if (passwordTxt.length() < 6) {
                    password.setError("Minumum password length should be 6 charcters");
                    password.requestFocus();
                    return;

                }


//
//                else{
//                    prog.setVisibility(View.VISIBLE);
//
//                    databaseReference.child("sers").addListenerForSingleValueEvent(new ValueEventListener() {
//                        @Override
//                        public void onDataChange(@NonNull DataSnapshot snapshot) {
//
//
//                            if(snapshot.hasChild(phoneTxt)){
//                                Toast.makeText(RegisterActivity.this, "phone already registered ", Toast.LENGTH_SHORT).show();
//
//
//                            }else{
//                                databaseReference.child("sers").child(phoneTxt).child("fullname").setValue(fullnameTxt);
//                                databaseReference.child("sers").child(phoneTxt).child("email").setValue(emailTxt);
//                                databaseReference.child("sers").child(phoneTxt).child("identyNumber").setValue(identyNumberTxt);
//                                databaseReference.child("sers").child(phoneTxt).child("password").setValue(passwordTxt);
//
////                                Toast.makeText(RegisterActivity.this, "Registration successfull", Toast.LENGTH_SHORT).show();
////                                finish();
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
//                }
//
//
//
//            }
//        });
                else {
                    progressBar.setVisibility(View.VISIBLE);
                    registerUser(fullnameTxt, phoneTxt, identyNumberTxt, emailTxt, passwordTxt);
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

    private void registerUser(String fullnameTxt, String phoneTxt, String identyNumberTxt, String emailTxt, String passwordTxt) {
        FirebaseAuth auth = FirebaseAuth.getInstance();
        auth.createUserWithEmailAndPassword(emailTxt,passwordTxt).addOnCompleteListener(RegisterActivity.this,
                new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(RegisterActivity.this, "Registration Succesful", Toast.LENGTH_SHORT).show();
//         the user verification code
                            FirebaseUser firebaseUser = auth.getCurrentUser();
//code
                            firebaseUser.sendEmailVerification();

//                            opening user profile
                            Intent intent = new Intent(RegisterActivity.this, UserProfileActivity.class);
                            //to prevent user from returining to register activity on pressing back button after registration
                            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(intent);
                            finish();      //closes the register activity


                        } else {
                            try {
                                throw task.getException();
                            } catch (FirebaseAuthWeakPasswordException e) {
                                password.setError("weak password");
                                password.requestFocus();
                            } catch (FirebaseAuthInvalidCredentialsException e) {
                                password.setError("Email already used or invalid.kindly re_enter");
                                password.requestFocus();
                            } catch (FirebaseAuthUserCollisionException e) {
                                password.setError("user already registered with this email.use another email");
                                password.requestFocus();

                            } catch (Exception e) {
                                Log.e(TAG, e.getMessage());
                                Toast.makeText(RegisterActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                                progressBar.setVisibility(View.GONE);
                            }
                        }
                    }

                });


    }
}