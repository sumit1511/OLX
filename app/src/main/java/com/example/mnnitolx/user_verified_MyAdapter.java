package com.example.mnnitolx;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class user_verified_MyAdapter extends RecyclerView.Adapter<user_verified_MyAdapter.MyViewHolder> {

    Context context;
    ArrayList<Item> profiles;
    FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;

    public user_verified_MyAdapter(Context c , ArrayList<Item> p)
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
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

            holder.name.setText(profiles.get(position).getItem_name());
            Picasso.get().load(profiles.get(position).item_image).into(holder.profilePic);

              // delete item by  long  press.
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference=firebaseDatabase.getReference().child("ITEM");
            final String delet_item=profiles.get(position).item_id;
            final String item_pending=profiles.get(position).item_status;
        final String item_sold=profiles.get(position).item_sold;
         holder.parentLayout.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                  if(item_pending!=null && item_pending.equals("true") && item_sold!=null && item_sold.equals("no")) {
                      Intent intent = new Intent(context, user_item_option_Activity.class);
                      intent.putExtra("delete_item_id", delet_item);
                      context.startActivity(intent);
                  }
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

