package com.example.mnnitolx;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Iterator;

public class Home_Activity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    public static final int REQUEST_CALL =1;
    private FirebaseAuth auth;
    RecyclerView recyclerView;
    ArrayList<Item> arrayStudent,verified_item1,user_verified_item1,user_peding_item1;
    FirebaseDatabase firebaseDatabase;
    Item data;
    int user_count=0,user_pending_count=0,user_verified_count=0;
    private DatabaseReference databaseReference;
    MyAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_);
        makecall();
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
              // finish();
                startActivity(new Intent(Home_Activity.this, Add_item.class));
                finish();
              //  Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                      //  .setAction("Action", null).show();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        /*---------------------------------sumit-bangar---------------------------*/
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference=firebaseDatabase.getReference().child("ITEM");
        recyclerView = (RecyclerView)findViewById(R.id.recyclerView);
        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(this, 2);
        recyclerView.setLayoutManager(mLayoutManager);
       // recyclerView.setLayoutManager( new LinearLayoutManager(this));
        databaseReference.addValueEventListener(new ValueEventListener()
        {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                arrayStudent = new ArrayList<Item>();
                verified_item1 = new ArrayList<Item>();
                user_verified_item1 = new ArrayList<Item>();
                user_peding_item1 = new ArrayList<Item>();
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
                    String str3 = myitem.item_sold;
                    if(str1 != null && str1.equals("true") && str3!=null && str3.equals("no"))
                    {
                        verified_item1.add(myitem);

                    }

                }
                    adapter = new MyAdapter(Home_Activity.this, verified_item1);
                    recyclerView.setAdapter(adapter);
                    user_count=1;
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(Home_Activity.this, "Opsss.... Something is wrong", Toast.LENGTH_SHORT).show();
            }
        });

        /*-----------------------------------end----------------------------------*/
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
           // super.onBackPressed();
            new AlertDialog.Builder(this).setIcon(android.R.drawable.ic_dialog_alert).setTitle("Exit")
                    .setMessage("Are you sure you want to exit?")
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            finish();
                        }
                    }).setNegativeButton("No", null).show();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home_, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera)
        {
            startActivity(new Intent(Home_Activity.this, user_profile_Activity.class));
        }
        else if (id == R.id.nav_gallery)
        {


            startActivity(new Intent(Home_Activity.this, user_verified_item_Activity.class));


        }
        else if (id == R.id.nav_slideshow)
        {


            startActivity(new Intent(Home_Activity.this, user_pending_Activity.class));

        } else if (id == R.id.nav_manage) {
            startActivity(new Intent(Home_Activity.this, user_sold_Activity.class));

        } else if (id == R.id.nav_share) {
            FirebaseAuth.getInstance().signOut();
            finish();
            startActivity(new Intent(Home_Activity.this, MainActivity.class));

        } else if (id == R.id.nav_send)
        {
              //chat  activity  will be open  from here.....
            startActivity(new Intent(Home_Activity.this, AllChat.class));
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    /*---------------------------------------------phone call  permission ---------------*/
    private void makecall()
    {
        if(ContextCompat.checkSelfPermission(Home_Activity.this, Manifest.permission.CALL_PHONE)!= PackageManager.PERMISSION_GRANTED)
        {
            ActivityCompat.requestPermissions(Home_Activity.this,new String[]{Manifest.permission.CALL_PHONE},REQUEST_CALL);
        }
    }
    @Override
   public void onRequestPermissionsResult(int requestCode,
                                    String permissions[], int[] grantResults) {
        switch (requestCode) {
            case REQUEST_CALL : {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                   // Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + "+918511812660"));

                    if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) == PackageManager.PERMISSION_GRANTED) {
                       // startActivity(intent);
                    }
                }
            }
        }
    }
       /*----------------------------sing out-----------------------*/

}
