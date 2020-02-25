package com.example.mnnitolx;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Register extends AppCompatActivity {

     EditText fullname,registration_no,contact,email,password,room_no,hostel;
     LinearLayout register;
     ProgressBar progressBar_reg;
     FirebaseAuth auth;
     @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        //Get Firebase auth instance
        auth = FirebaseAuth.getInstance();
        register=(LinearLayout)findViewById(R.id.Btn_Signup);
        fullname = (EditText) findViewById(R.id.full_name);
        registration_no = (EditText) findViewById(R.id.registration_no);
        contact = (EditText) findViewById(R.id.contact_no);
        email = (EditText) findViewById(R.id.email_add);
        password = (EditText) findViewById(R.id.password);
        room_no = (EditText) findViewById(R.id.room_no);
        hostel = (EditText) findViewById(R.id.hostel_name);
        progressBar_reg = (ProgressBar) findViewById(R.id.Register_Progress_Bar);





        register.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {

                String email1 = email.getText().toString().trim();
                String password1 = password.getText().toString().trim();
                String fullname1= fullname.getText().toString().trim();
                String mobile= contact.getText().toString().trim();
                if (TextUtils.isEmpty(email1)) {
                    Toast.makeText(getApplicationContext(), "Enter email address!", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (TextUtils.isEmpty(password1)) {
                    Toast.makeText(getApplicationContext(), "Enter password!", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (password1.length() < 6) {
                    Toast.makeText(getApplicationContext(), "Password too short, enter minimum 6 characters!", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(fullname1)) {
                    Toast.makeText(getApplicationContext(), "Enter Your Name!", Toast.LENGTH_SHORT).show();
                    return;
                }
                /*
                if (TextUtils.isEmpty(mobile)) {
                    Toast.makeText(getApplicationContext(), "Enter Your Mobile Number!", Toast.LENGTH_SHORT).show();
                    return;
                }
                 */
                progressBar_reg.setVisibility(View.VISIBLE);
                //create user
                auth.createUserWithEmailAndPassword(email1, password1)
                        .addOnCompleteListener(Register.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                Toast.makeText(Register.this, "createUserWithEmail:onComplete:" + task.isSuccessful(), Toast.LENGTH_SHORT).show();
                                datainsert();
                              //  progressBar.setVisibility(View.GONE);
                                // If sign in fails, display a message to the user. If sign in succeeds
                                // the auth state listener will be notified and logic to handle the
                                // signed in user can be handled in the listener.
                                if (!task.isSuccessful()) {
                                    Toast.makeText(Register.this, "Authentication failed." + task.getException(),
                                            Toast.LENGTH_SHORT).show();
                                } else {
                                    startActivity(new Intent(Register.this, MainActivity.class));
                                    finish();
                                }
                            }
                        });

            }
        });
    }

     @Override
    protected void onResume() {
        super.onResume();
     progressBar_reg.setVisibility(View.GONE);
         //finish();
    }
    private void datainsert()
    {
        String fullname1 = fullname.getText().toString().trim();
        String registration_no1  = registration_no.getText().toString().trim();
        String contact1 = contact.getText().toString().trim();
        String email1 = email.getText().toString().trim();
        String password1 = password.getText().toString().trim();
        String room_no1 = room_no.getText().toString().trim();
        String hostel_name1 = hostel.getText().toString().trim();
        FirebaseDatabase database=FirebaseDatabase.getInstance();
        final DatabaseReference ref =  database.getReference("STUDENT");
        final String id= ref.push().getKey();
        Student data=new Student(id,fullname1,registration_no1,contact1,email1,password1,room_no1,hostel_name1);
        ref.child(id).setValue(data);

    }
    }
