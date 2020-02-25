package com.example.mnnitolx;

import android.support.annotation.NonNull;
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

public class user_item_option_Activity extends AppCompatActivity {

    ArrayList<Item> arrayStudent,search_array;
    String mykey,item_id;
    TextView item_name,item_price,item_descreption;
    ImageView option_img;
    Item sumit;
    LinearLayout bt_delete,bt_sold;
    FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_item_option_);
        item_name=(TextView)findViewById(R.id.option_item_name);
        item_price=(TextView)findViewById(R.id.option_item_price);
        item_descreption=(TextView)findViewById(R.id.option_item_descreption);
        option_img=(ImageView)findViewById(R.id.option_item_image);

        bt_delete=(LinearLayout)findViewById(R.id.Btn_delete);
        bt_sold=(LinearLayout)findViewById(R.id.Btn_sold);
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference().child("ITEM");
       // if(getIntent().hasExtra("delete_item_id"))

            mykey=getIntent().getStringExtra("delete_item_id");

       // Toast.makeText(user_item_option_Activity.this, ""+mykey, Toast.LENGTH_SHORT).show();
        arrayStudent = new ArrayList<Item>();
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot)
            {
                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren())
                {
                    Item p = dataSnapshot1.getValue(Item.class);
                    arrayStudent.add(p);
                }
                Iterator<Item> itemIterator = arrayStudent.iterator();

                while (itemIterator.hasNext()) {
                    Item myitem = itemIterator.next();
                    String str1 = myitem.item_id;

                    if (str1 != null && str1.equals(mykey))
                    {
                        item_name.setText(myitem.item_name);
                        item_price.setText(myitem.item_price);
                        item_descreption.setText(myitem.item_discreption);
                        item_id=myitem.item_id;
                        Picasso.get().load(myitem.item_image).into(option_img);
                        sumit=myitem;
                        break;
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        bt_sold.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                databaseReference.child(item_id).child("item_sold").setValue("yes");
                finish();
            }
        });
        bt_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                databaseReference.child(item_id).removeValue();
                finish();
            }
        });
    }

}
