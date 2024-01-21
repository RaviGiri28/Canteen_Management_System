package com.example.canteenmanagementsystem;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.canteenmanagementsystem.Adapter.CategoryAdapter;
import com.example.canteenmanagementsystem.Adapter.PopularAdapter;
import com.example.canteenmanagementsystem.Domain.CategoryDomain;
import com.example.canteenmanagementsystem.Domain.FoodDomain;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;

public class Content_Activity extends AppCompatActivity {


   private RecyclerView.Adapter adapter,adapter2;
    private RecyclerView recyclerViewCategoryList,recyclerViewPopularList;
    FirebaseAuth auth;
    FirebaseUser user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_content);
        auth=FirebaseAuth.getInstance();
        user= auth.getCurrentUser();

        recyclerViewCategoryList();
        recyclerViewPopular();
        bottomNavigation();
   }
    private void bottomNavigation(){
        FloatingActionButton floatingActionButton=findViewById(R.id.cartbtn);
        ImageView homebtn=findViewById(R.id.Homebtn);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Content_Activity .this,CartListActivity.class));
            }
        });
        homebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Content_Activity .this,Content_Activity.class));
            }
        });
    }

    private void recyclerViewCategoryList() {
      LinearLayoutManager linearLayoutManager=new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false);
       recyclerViewCategoryList=findViewById(R.id.recyclerview);
        recyclerViewCategoryList.setLayoutManager(linearLayoutManager);
        recyclerViewCategoryList.setLayoutManager(linearLayoutManager);

        ArrayList<CategoryDomain> category= new ArrayList<>();
        category.add(new CategoryDomain("Pizza", "cat_1"));
        category.add(new CategoryDomain("Burger", "cat_2"));
        category.add(new CategoryDomain("HotDog", "cat_3"));
        category.add(new CategoryDomain("Drinks", "cat_4"));
        category.add(new CategoryDomain("Donuts", "cat_5"));

        adapter=new CategoryAdapter(category);
        recyclerViewCategoryList.setAdapter(adapter);
    }
    private void recyclerViewPopular(){
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false);
        recyclerViewPopularList=findViewById(R.id.recyclerview2);
        recyclerViewPopularList.setLayoutManager(linearLayoutManager);
        ArrayList<FoodDomain> foodList=new ArrayList<>();
        foodList.add(new FoodDomain("Pepperoni pizza","pizza1","Slices Pepperoni,Mozzerella Cheese,Fresh Oregano, Ground black Pepper,Pizza Sauce",99));
        foodList.add(new FoodDomain("Cheese Burger","burger","Beef, Gouda Cheese,Special Sauce,Lettuce, Tomato",49));
        foodList.add(new FoodDomain("Vegetable pizza","pizza2","Olive oil,Vegetable Oil,Pitted Kalamata, Cherry Tomatoes,Fresh Oregano, Basil",150));

        adapter2=new PopularAdapter(foodList);
        recyclerViewPopularList.setAdapter(adapter2);
    }
}