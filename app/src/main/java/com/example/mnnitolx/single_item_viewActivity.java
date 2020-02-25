package com.example.mnnitolx;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Iterator;

public class single_item_viewActivity extends AppCompatActivity {
    String seller_name,seller_mobile_numer,seller_email,single_item_name,single_item_Url ;
    LinearLayout whatsapp_bt,call_bt,email_bt;
    FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    ArrayList<Student> arrayStudent,search_array;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_item_view);

        /*-------------------------------------------------------------------------------------*/
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference().child("STUDENT");
        arrayStudent = new ArrayList<Student>();
        seller_email= getIntent().getStringExtra("seller_email_address");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot)
            {
                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren())
                {
                    Student p = dataSnapshot1.getValue(Student.class);
                    arrayStudent.add(p);
                }
                Iterator<Student> itemIterator = arrayStudent.iterator();

                while (itemIterator.hasNext()) {
                    Student myitem = itemIterator.next();
                    String str1 = myitem.email;

                    if (str1 != null && str1.equals(seller_email))
                    {
                        TextView person_hostel_name = (TextView)findViewById(R.id.single_item_seller_hostel);
                        person_hostel_name.setText(myitem.hostel);
                        TextView person_hostel_room_no = (TextView)findViewById(R.id.single_item_seller_room);
                        person_hostel_room_no.setText(myitem.room_no);
                        TextView person_name = (TextView)findViewById(R.id.single_item_seller_name);
                        person_name.setText(myitem.fullname);
                        seller_mobile_numer=myitem.contact;
                        break;
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        /*---------------------------------------------------------------------------------*/
        getIncomingIntent();
        whatsapp_bt=(LinearLayout)findViewById(R.id.Btn_whatsapp);
        whatsapp_bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                /*--------------------------*/
                try {
                    String text = "Hi sir, I want to buy "+single_item_name+" from MNNIT_OLX";// Replace with your message.

                    String toNumber = "91"+seller_mobile_numer;


                    Intent intent = new Intent(Intent.ACTION_VIEW);
                    intent.setData(Uri.parse("http://api.whatsapp.com/send?phone="+toNumber +"&text="+text));
                    startActivity(intent);
                }
                catch (Exception e){
                    e.printStackTrace();
                }
            }
        });

        email_bt=(LinearLayout)findViewById(R.id.Btn_email);
        email_bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent emailIntent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts(
                        "mailto",seller_email, null));
                emailIntent.putExtra(Intent.EXTRA_SUBJECT, "MNNITOLX");
                emailIntent.putExtra(Intent.EXTRA_TEXT, "Hi sir I want to buy "+single_item_name+" from MNNIT_OLX");
                startActivity(Intent.createChooser(emailIntent, "Send email..."));
            }
        });
        call_bt=(LinearLayout)findViewById(R.id.Btn_call);

        call_bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ActivityCompat.checkSelfPermission(single_item_viewActivity.this,
                        Manifest.permission.CALL_PHONE) == PackageManager.PERMISSION_GRANTED) {
                    //Creating intents for making a call
                    Intent callIntent = new Intent(Intent.ACTION_CALL);
                    callIntent.setData(Uri.parse("tel:"+seller_mobile_numer));
                    single_item_viewActivity.this.startActivity(callIntent);

                }else{
                    Toast.makeText(single_item_viewActivity.this, "You don't assign permission.", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
    private void getIncomingIntent(){
        if(getIntent().hasExtra("item_url") && getIntent().hasExtra("item_name")){
            single_item_Url = getIntent().getStringExtra("item_url");
            single_item_name = getIntent().getStringExtra("item_name");
            String single_item_price = getIntent().getStringExtra("item_price");
            String single_item_descreption = getIntent().getStringExtra("item_descreption");
            setImage(single_item_Url,single_item_name,single_item_price,single_item_descreption);
        }
    }


    private void setImage(String single_item_Url, String single_item_name1,String item_price1,String item_descreption1){

        ImageView item_url=(ImageView) findViewById(R.id.option_item_image);
        Picasso.get().load(single_item_Url).into(item_url);
        TextView item_name=(TextView)findViewById(R.id.single_item_name);
        item_name.setText(single_item_name1);
        TextView item_price=(TextView)findViewById(R.id.single_item_price);
        item_price.setText(item_price1);
        TextView item_descreption=(TextView)findViewById(R.id.single_item_descreption);
        item_descreption.setText(item_descreption1);
    }

}
