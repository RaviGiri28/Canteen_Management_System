package com.example.canteenmanagementsystem.Adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.canteenmanagementsystem.Domain.CategoryDomain;
import com.example.canteenmanagementsystem.R;

import java.util.ArrayList;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.ViewHolder>{
    ArrayList<CategoryDomain>categoryDomains;

    public CategoryAdapter(ArrayList<CategoryDomain> categoryDomains) {
        this.categoryDomains = categoryDomains;
    }

    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View inflate=LayoutInflater.from(parent.getContext()).inflate(R.layout.viewholder_category,parent,false);
        return new ViewHolder(inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryAdapter.ViewHolder holder, int position) {
    holder.CategoryName.setText(categoryDomains.get(position).getTitle());
    String picUrl="";
    switch (position){
        case 0:{
            picUrl="cat_1";
            holder.MainLayout.setBackground(ContextCompat.getDrawable(holder.itemView.getContext(),R.drawable.cat_background1));
        break;
        }
        case 1:{
            picUrl="cat_2";
            holder.MainLayout.setBackground(ContextCompat.getDrawable(holder.itemView.getContext(),R.drawable.cat_background2));
        break;
        }
        case 2:{
            picUrl="cat_3";
            holder.MainLayout.setBackground(ContextCompat.getDrawable(holder.itemView.getContext(),R.drawable.cat_background3));
        break;
        }
        case 3:{
            picUrl="cat_4";
            holder.MainLayout.setBackground(ContextCompat.getDrawable(holder.itemView.getContext(),R.drawable.cat_background4));
        break;
        }
        case 4:{
            picUrl="cat_5";
            holder.MainLayout.setBackground(ContextCompat.getDrawable(holder.itemView.getContext(),R.drawable.cat_background5));
        break;
        }
    }
    int drawableResourceId=holder.itemView.getContext().getResources().getIdentifier(picUrl,"drawable", holder.itemView.getContext().getPackageName());
    Glide.with(holder.itemView.getContext())
            .load(drawableResourceId)
            .into(holder.CategoryPic);

    }

    @Override
    public int getItemCount() {
        return categoryDomains.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView CategoryName;
        ImageView CategoryPic;
        ConstraintLayout MainLayout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            CategoryName=itemView.findViewById(R.id.CategoryName);
            CategoryPic=itemView.findViewById(R.id.CategoryPic);
            MainLayout=itemView.findViewById(R.id.MainLayout);
        }
    }
    }
