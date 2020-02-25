package com.example.mnnitolx;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class user_sold_MyAdapter extends RecyclerView.Adapter<user_sold_MyAdapter.MyViewHolder> {

    Context context;
    ArrayList<Item> profiles;

    public user_sold_MyAdapter(Context c , ArrayList<Item> p)
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
        public MyViewHolder(View itemView) {
            super(itemView);
            name = (TextView) itemView.findViewById(R.id.name_widget);
            profilePic = (ImageView) itemView.findViewById(R.id.imageview_widget);
        }
    }
}

