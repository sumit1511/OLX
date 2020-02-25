package com.example.mnnitolx;

import android.support.annotation.NonNull;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Iterator;

public class user_sold_Activity extends AppCompatActivity {
    RecyclerView recyclerView;
    ArrayList<Item> arrayStudent,verified_item1,user_verified_item1,user_sold_item1;
    FirebaseDatabase firebaseDatabase;
    Item data;
    private DatabaseReference databaseReference;
    user_verified_MyAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_sold_);
        /*----------------------------back toolbar button---------------------*/
          ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setHomeButtonEnabled(true);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        /*----------------------------end--------------------------------------*/
        /*---------------------------------sumit-bangar---------------------------*/
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference=firebaseDatabase.getReference().child("ITEM");
        recyclerView = (RecyclerView)findViewById(R.id.usersold);
        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(this, 2);
        recyclerView.setLayoutManager(mLayoutManager);
        // recyclerView.setLayoutManager( new LinearLayoutManager(this));
        databaseReference.addValueEventListener(new ValueEventListener()
        {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                arrayStudent = new ArrayList<Item>();
                user_sold_item1 = new ArrayList<Item>();

                for(DataSnapshot dataSnapshot1: dataSnapshot.getChildren())
                {
                    Item p = dataSnapshot1.getValue(Item.class);
                    arrayStudent.add(p);

                }

                Iterator<Item> itemIterator = arrayStudent.iterator();
                while(itemIterator.hasNext())
                {
                    Item myitem = itemIterator.next();
                    String str1 = myitem.item_status;
                    String str2 = myitem.item_user_id;
                    String str3 = myitem.item_sold;
                    if (str1 != null && str1.equals("true") && str2 != null && str2.equals(MainActivity.myemail)&& str3!=null &&
                            str3.equals("yes")) {
                        user_sold_item1.add(myitem);
                    }


                }
                if(user_sold_item1.isEmpty())
                {

                    Item templat = new Item("","https://firebasestorage.googleapis.com/v0/b/mnnitolx-cf684.appspot.com/o/item%2F1552411706514.jpg?alt=media&token=95107ecb-ce4d-4bf9-8f9e-84347b404010",
                            "","","","","","");
                    user_sold_item1.add(templat);
                    adapter = new user_verified_MyAdapter(user_sold_Activity.this,user_sold_item1);
                    recyclerView.setAdapter(adapter);

                }
                else {
                    adapter = new user_verified_MyAdapter(user_sold_Activity.this, user_sold_item1);
                    recyclerView.setAdapter(adapter);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(user_sold_Activity.this, "Opsss.... Something is wrong", Toast.LENGTH_SHORT).show();
            }
        });

        /*-----------------------------------end----------------------------------*/
    }

}