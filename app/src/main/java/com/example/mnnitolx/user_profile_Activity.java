package com.example.mnnitolx;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.content.Intent;
import android.graphics.Point;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Display;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Iterator;

import static android.view.MotionEvent.*;

public class user_profile_Activity extends AppCompatActivity {

    FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    ArrayList<Student> arrayStudent;
    String  profile_hostel_name,profile_user_name;
    String  profile_user_room_no ,profile_user_mobile_number,user_profile_id;
    TextView profile_name,profile_email,profile_contact,profile_hostel,profile_room ;
    LinearLayout update_bt;
    /*-------------------------------------------*/
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile_);
        /*--------------object of test  field------------*/
        profile_name = (TextView)findViewById(R.id.single_profile_name);
        profile_email=(TextView)findViewById(R.id.single_profile_email);
        profile_contact=(TextView)findViewById(R.id.single_profile_mobile_no);
        profile_hostel =(TextView)findViewById(R.id.single_profile_hostel);
        profile_room=(TextView)findViewById(R.id.single_profile_room_no);
        update_bt=(LinearLayout)findViewById(R.id.Btn_update);

        /*---------------------------------sumit-bangar---------------------------*/
        final String user_search = MainActivity.myemail;
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference().child("STUDENT");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                arrayStudent = new ArrayList<Student>();
                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                    Student p = dataSnapshot1.getValue(Student.class);
                    arrayStudent.add(p);

                }

                Iterator<Student> itemIterator = arrayStudent.iterator();

                while (itemIterator.hasNext()) {
                    Student myitem = itemIterator.next();
                    String str1 = myitem.email;

                    if (str1 != null && str1.equals(user_search))
                    {
                        profile_hostel_name = myitem.hostel;
                        profile_user_room_no = myitem.room_no;
                        profile_user_name = myitem.fullname;
                        profile_user_mobile_number = myitem.contact;
                        user_profile_id=myitem.user_id;
                        profile_name.setText(profile_user_name);
                        profile_hostel.setText(profile_hostel_name);
                        profile_email.setText(user_search);
                        profile_contact.setText(profile_user_mobile_number);
                        profile_room.setText(profile_user_room_no);
                       // Toast.makeText(user_profile_Activity.this,myitem.toString(), Toast.LENGTH_SHORT).show();
                        break;
                    }
                }
            }


            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(user_profile_Activity.this, "seller information  not found", Toast.LENGTH_SHORT).show();
            }
        });

        /*-----------------------------------end----------------------------------*/
        update_bt.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
               Intent intent = new Intent(user_profile_Activity.this, profile_update_Activity.class);
                intent.putExtra("user_profile_id",user_profile_id);
                /*-----------------------new thing try-----------------------*/
                intent.putExtra("user_profile_mobile",profile_user_mobile_number);
                intent.putExtra("user_profile_hostel",profile_hostel_name);
                intent.putExtra("user_profile_room", profile_user_room_no);
                /*------------------------------------------------------------*/
                startActivity(intent);
            }
        });

    }
}
