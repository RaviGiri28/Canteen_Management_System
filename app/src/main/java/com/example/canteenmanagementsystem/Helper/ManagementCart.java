package com.example.canteenmanagementsystem.Helper;

import android.content.Context;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.canteenmanagementsystem.Domain.FoodDomain;
import com.example.canteenmanagementsystem.Interface.ChangeNumberItemsListener;

import java.util.ArrayList;

public class ManagementCart {
    private Context context;
    private TinyDB tinyDB;
    private int position;

    public ManagementCart(Context context) {
        this.context = context;
        this.tinyDB= new TinyDB(context);
    }
    public void insertFood(FoodDomain item){
        ArrayList<FoodDomain> listFood=getListCart();
                boolean existAlready=false;
                int n=0;
                for (int i=0;i<listFood.size();i++){
                    if (listFood.get(i).getTitle().equals(item.getTitle())){
                        existAlready=true;
                        n=i;
                        break;
                    }
                }
                if (existAlready){
                    listFood.get(n).setNumberInCart(item.getNumberInCart(listFood.get(position).getNumberInCart() - 1));
                }else{
                    listFood.add(item);
                }
                tinyDB.putListObject("CardList",listFood);
                Toast.makeText(context,"Added To Your Cart",Toast.LENGTH_SHORT).show();
    }
    public ArrayList<FoodDomain>getListCart(){
        return tinyDB.getListObject("CartList",new ArrayList<FoodDomain>());

    }
    public void plusNumberFood(@NonNull ArrayList<FoodDomain>listFood, int position, @NonNull ChangeNumberItemsListener changeNumberItemsListener){
        listFood.get(position).setNumberInCart(listFood.get(position).getNumberInCart() + 1);
        tinyDB.putListObject("CartList",listFood);
        changeNumberItemsListener.changed();
    }

    public void minusNumberFood(@NonNull ArrayList<FoodDomain>listFood, int position, ChangeNumberItemsListener changeNumberItemsListener){
        if (listFood.get(position).getNumberInCart() == 1) {
            listFood.remove(position);
        } else {
            listFood.get(position).setNumberInCart(listFood.get(position).getNumberInCart() - 1);
        }
        tinyDB.putListObject("CartList",listFood);
        changeNumberItemsListener.changed();
    }
    public int getTotalfee() {
        ArrayList<FoodDomain> listfood = getListCart();
        int fee = 0;
        for (int i = 0; i < listfood.size(); i++) {
            fee = fee + (listfood.get(i).getFee() * listfood.get(i).getNumberInCart());
        }
        return fee;
    }
}
