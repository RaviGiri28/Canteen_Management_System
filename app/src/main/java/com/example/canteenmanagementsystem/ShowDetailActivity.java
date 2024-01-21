package com.example.canteenmanagementsystem;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.canteenmanagementsystem.Domain.FoodDomain;
import com.example.canteenmanagementsystem.Helper.ManagementCart;

public class ShowDetailActivity extends AppCompatActivity {
    private TextView AddToCartBtn;
    private TextView titleTxt,feetxt,description,numberordertxt;
    private ImageView plusbtn,minusbtn,picfood;
    private FoodDomain object;
    int numberorder=1;
    private ManagementCart managementCart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_detail);

        managementCart=new ManagementCart(this);

        initView();
        getBundle();
    }

    private void getBundle() {
        object = (FoodDomain) getIntent().getSerializableExtra("object");
        int drawableResourceId=this.getResources().getIdentifier(object.getPic(),"drawable",this.getPackageName());
        Glide.with(this)
                .load(drawableResourceId)
                .into(picfood);
        titleTxt.setText(object.getTitle());
        feetxt.setText("₹"+object.getFee());
        description.setText(object.getDescription());
        numberordertxt.setText(String.valueOf(numberorder));
        plusbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                numberorder=numberorder+1;
                numberordertxt.setText(String.valueOf(numberorder));
            }
        });
                minusbtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (numberorder>1){
                            numberorder=numberorder-1;
                        }
                        numberordertxt.setText(String.valueOf(numberorder));
                    }
                });
                AddToCartBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        object.setNumberInCart(numberorder);
                        managementCart.insertFood(object);
                    }
                });
    }

    private void initView() {
        AddToCartBtn=findViewById(R.id.Addtocart);
        titleTxt=findViewById(R.id.title);
        feetxt=findViewById(R.id.pricetxt);
        description=findViewById(R.id.descriptiontxt);
        numberordertxt=findViewById(R.id.numberordertxt);
        plusbtn=findViewById(R.id.plus);
        minusbtn=findViewById(R.id.minus);
        picfood=findViewById(R.id.pizza);

    }
}