package com.example.mnnitolx;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class profile_update_Activity extends AppCompatActivity {

    String user_for_update;
    FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    String user_profile_mobile1,user_profile_hostel1,user_profile_room1;
    EditText mobile_no,room_no,hostel_name;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_update_);
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference=firebaseDatabase.getReference().child("STUDENT");
          mobile_no = (EditText)findViewById(R.id.mobile_number_update);
          room_no = (EditText)findViewById(R.id.room_number_update);
          hostel_name = (EditText)findViewById(R.id.hostel_update_profile);
        /*-------------------------------------newchange---------------------------*/
        user_profile_hostel1= getIntent().getStringExtra("user_profile_hostel");
        user_profile_mobile1= getIntent().getStringExtra("user_profile_mobile");
        user_profile_room1= getIntent().getStringExtra("user_profile_room");
        mobile_no.setText(user_profile_mobile1, TextView.BufferType.EDITABLE);
        room_no.setText(user_profile_room1, TextView.BufferType.EDITABLE);
        hostel_name.setText(user_profile_hostel1, TextView.BufferType.EDITABLE);
        /*-------------------------------------------------------------------------*/
        user_for_update= getIntent().getStringExtra("user_profile_id");
        Button bt1 = (Button)findViewById(R.id.update_profile
        );
        bt1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                String mobile = mobile_no.getText().toString().trim();
                String hostel = hostel_name.getText().toString().trim();
                String room = room_no.getText().toString().trim();
                if (TextUtils.isEmpty(mobile) || TextUtils.isEmpty(hostel))
                {
                    Toast.makeText(getApplicationContext(),"Fill all Blocks ",Toast.LENGTH_LONG).show();
                }
              else {
                    databaseReference.child(user_for_update).child("contact").setValue(mobile);
                    databaseReference.child(user_for_update).child("hostel").setValue(hostel);
                    databaseReference.child(user_for_update).child("room_no").setValue(room);
                    finish();
                   }
            }
        });
    }
}
