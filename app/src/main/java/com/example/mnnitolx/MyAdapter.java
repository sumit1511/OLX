package com.example.mnnitolx;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Iterator;

import static android.support.constraint.Constraints.TAG;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {

    Context context;
    ArrayList<Item> profiles;
    FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    ArrayList<Student> arrayStudent,search_array;
    String  hostel_name,seller_name;
    String  room_no ,mobile_number,seller_email_address;

    public MyAdapter(Context c , ArrayList<Item> p)
    {
        context = c;
        profiles = p;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(context).inflate(R.layout.add_grid_item,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, final int position) {

            holder.name.setText(profiles.get(position).getItem_name());
            Picasso.get().load(profiles.get(position).item_image).into(holder.profilePic);

           holder.parentLayout.setOnClickListener(new View.OnClickListener()
           {
               @Override
               public void onClick(View view) {


                   final String user_search = profiles.get(position).item_user_id;
                   /*---------------------------------sumit-bangar---------------------------*/
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

                               if (str1 != null && str1.equals(user_search)) {
                                   hostel_name = myitem.hostel;
                                   room_no = myitem.room_no;
                                   seller_name = myitem.fullname;
                                   mobile_number = myitem.contact;
                                   seller_email_address = myitem.email;
                                   break;
                               }
                           }
                       }


                       @Override
                       public void onCancelled(@NonNull DatabaseError databaseError) {
                           Toast.makeText(context, "seller information  not found", Toast.LENGTH_SHORT).show();
                       }
                   });

                   /*-----------------------------------end----------------------------------*/
                   Intent intent = new Intent(context, single_item_viewActivity.class);
                  /* intent.putExtra("hostel_name", hostel_name);
                   intent.putExtra("hostel_room_no", room_no);
                   intent.putExtra("seller_name", seller_name);
                   intent.putExtra("mobile_number", mobile_number);*/
                   intent.putExtra("seller_email_address", profiles.get(position).item_user_id);
                   intent.putExtra("item_url", profiles.get(position).item_image);
                   intent.putExtra("item_name", profiles.get(position).item_name);
                   intent.putExtra("item_price", profiles.get(position).item_price);
                   intent.putExtra("item_descreption", profiles.get(position).item_discreption);

                  // Toast.makeText(context,seller_name,Toast.LENGTH_LONG).show();
                   context.startActivity(intent);
               }
           });

    }

    @Override
    public int getItemCount() {
       // return 1;
       return profiles.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder
    {
        TextView name,email;
        ImageView profilePic;
        RelativeLayout parentLayout;
        public MyViewHolder(View itemView) {
            super(itemView);
            name = (TextView) itemView.findViewById(R.id.name_widget);
            profilePic = (ImageView) itemView.findViewById(R.id.imageview_widget);
            parentLayout = itemView.findViewById(R.id.parent_layout);
        }
    }
}

