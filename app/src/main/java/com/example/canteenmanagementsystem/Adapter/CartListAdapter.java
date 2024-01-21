package com.example.canteenmanagementsystem.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.canteenmanagementsystem.CartListActivity;
import com.example.canteenmanagementsystem.Domain.FoodDomain;
import com.example.canteenmanagementsystem.Helper.ManagementCart;
import com.example.canteenmanagementsystem.Interface.ChangeNumberItemsListener;
import com.example.canteenmanagementsystem.R;

import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.List;

public class CartListAdapter extends RecyclerView.Adapter<CartListAdapter.ViewHolder> {
    private ArrayList<FoodDomain> foodDomains;
    private ManagementCart managementCart;
    private ChangeNumberItemsListener changeNumberItemsListener;

    public CartListAdapter(ArrayList<FoodDomain> foodDomains, Context context, ChangeNumberItemsListener changeNumberItemsListener) {
        this.foodDomains = foodDomains;
        this.managementCart = new ManagementCart(context);
        this.changeNumberItemsListener = changeNumberItemsListener;
    }

    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.viewholder_cart, parent, false);
        return new ViewHolder(inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.title.setText(foodDomains.get(holder.getAdapterPosition()).getTitle());
        holder.feeEachItem.setText(String.valueOf(foodDomains.get(holder.getAdapterPosition()).getFee()));
        holder.totalEachItem.setText(String.valueOf(Math.round((foodDomains.get(holder.getAdapterPosition()).getNumberInCart() - 1 + foodDomains.get(holder.getAdapterPosition()).getFee()) * 100) / 100));
        holder.num.setText(String.valueOf(foodDomains.get(holder.getAdapterPosition()).getNumberInCart() - 1));

        int drawableResourceId = holder.itemView.getContext().getResources().getIdentifier(foodDomains.get(holder.getAdapterPosition()).getPic(), "drawable", holder.itemView.getContext().getPackageName());
        Glide.with(holder.itemView.getContext())
                .load(drawableResourceId)
                .into(holder.pic);

        holder.plusItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int adapterPosition = holder.getAdapterPosition();
                managementCart.plusNumberFood(foodDomains, adapterPosition, new ChangeNumberItemsListener() {
                    @Override
                    public void changed() {
                        notifyDataSetChanged();
                        changeNumberItemsListener.changed();
                    }
                });
            }
        });

        holder.minusItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int adapterPosition = holder.getAdapterPosition();
                managementCart.minusNumberFood(foodDomains, adapterPosition, new ChangeNumberItemsListener() {
                    @Override
                    public void changed() {
                        notifyDataSetChanged();
                        changeNumberItemsListener.changed();
                    }
                });
            }
        });
    }

    @Override
    public int getItemCount() {
        return foodDomains.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView title, feeEachItem, totalEachItem, num;
        ImageView pic, plusItem, minusItem;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.titletxt);
            feeEachItem = itemView.findViewById(R.id.feeEachItem);
            pic = itemView.findViewById(R.id.piccart);
            totalEachItem = itemView.findViewById(R.id.totalEachItem);
            num = itemView.findViewById(R.id.numberItemtxt);
            plusItem = itemView.findViewById(R.id.pluscardbtn);
            minusItem = itemView.findViewById(R.id.minuscardbtn);
        }
    }
}
