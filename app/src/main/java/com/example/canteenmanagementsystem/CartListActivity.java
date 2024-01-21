package com.example.canteenmanagementsystem;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.example.canteenmanagementsystem.Adapter.CartListAdapter;
import com.example.canteenmanagementsystem.Helper.ManagementCart;
import com.example.canteenmanagementsystem.Interface.ChangeNumberItemsListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class CartListActivity extends AppCompatActivity {
    private RecyclerView.Adapter adapter;
    private RecyclerView recyclerViewList;
    private ManagementCart managementCart;
    TextView totalFeetxt, taxtxt, DeliveryTxt, totaltxt, emptytxt;
    private ScrollView scrollView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart_list);

        managementCart = new ManagementCart(this);

        initView();
        initList();
        CalclateCart();
        bottomNavigation();
    }

    private void bottomNavigation() {
        FloatingActionButton floatingActionButton = findViewById(R.id.cartbtn);
        ImageView homebtn = findViewById(R.id.Homebtn);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(CartListActivity.this, CartListActivity.class));
            }
        });
        homebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(CartListActivity.this, Content_Activity.class));
            }
        });
    }

    private void initView() {
        recyclerViewList = findViewById(R.id.recyclerview);
        totalFeetxt = findViewById(R.id.totalfeetxt);
        taxtxt = findViewById(R.id.Taxtxt);
        DeliveryTxt = findViewById(R.id.Deliverytxt);
        totaltxt = findViewById(R.id.totaltxt);
        emptytxt = findViewById(R.id.emptyTxt);
        scrollView = findViewById(R.id.scrollView3);
    }

    private void initList() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerViewList.setLayoutManager(linearLayoutManager);
        adapter = new CartListAdapter(managementCart.getListCart(), this, new ChangeNumberItemsListener() {

            @Override
            public void changed() {
                CalclateCart();
            }
        });
        recyclerViewList.setAdapter(adapter);
        if (managementCart.getListCart().isEmpty()) {
            emptytxt.setVisibility(View.VISIBLE);
            scrollView.setVisibility(View.GONE);
        } else {
            emptytxt.setVisibility(View.GONE);
            scrollView.setVisibility(View.VISIBLE);
        }
    }

    private void CalclateCart() {
        double percentTax = 0.02;
        double delivery = 10;

        int taxAmount = (int) Math.round((managementCart.getTotalfee() * percentTax));
        int totalAmount = (int) Math.round(managementCart.getTotalfee() + taxAmount + delivery);

        totalFeetxt.setText(String.format("₹%.2f", managementCart.getTotalfee()));
        taxtxt.setText(String.format("₹%d", taxAmount));
        DeliveryTxt.setText(String.format("₹%.2f", delivery));
        totaltxt.setText(String.format("₹%d", totalAmount));
    }
}