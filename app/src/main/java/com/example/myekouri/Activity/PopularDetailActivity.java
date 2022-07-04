package com.example.myekouri.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.myekouri.Domain.FoodDomain;
import com.example.myekouri.Domain.PopularDomain;
import com.example.myekouri.Domain.ViewProductDomain;
import com.example.myekouri.Helper.ManagementCart;
import com.example.myekouri.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;

public class PopularDetailActivity extends AppCompatActivity {

    TextView addToCartBtn;
    TextView titleTxt, feeTxt, descriptionTxt, numberOrderTxt;
    ImageView plusBtn, minusBtn, picFood, backBtn;
    int numberOrder = 1;
    double totalPrice;
    private ManagementCart managementCart;

    FirebaseFirestore mStore;
    FirebaseAuth mAuth;

    PopularDomain popularDomain = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_popular_detail);
        mStore = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();

        managementCart = new ManagementCart(this);

        Object object = getIntent().getSerializableExtra("popdetail");
        if (object instanceof PopularDomain) {
            popularDomain = (PopularDomain) object;



        }


        addToCartBtn = findViewById(R.id.addToCartBtn);
        titleTxt = findViewById(R.id.titleTxt);
        feeTxt = findViewById(R.id.priceTxt);
        descriptionTxt = findViewById(R.id.descriptionTxt);
        numberOrderTxt = findViewById(R.id.numberOrderTxt);
        plusBtn = findViewById(R.id.plusBtn);
        minusBtn = findViewById(R.id.minusBtn);
        picFood = findViewById(R.id.picfood);
        backBtn = findViewById(R.id.back);

        if (popularDomain != null) {
            Glide.with(getApplicationContext()).load(popularDomain.getImage()).into(picFood);
            titleTxt.setText(popularDomain.getName());
            feeTxt.setText("RM" + popularDomain.getPrice());
            descriptionTxt.setText(popularDomain.getDescription());
            numberOrderTxt.setText(String.valueOf(numberOrder));
            totalPrice = popularDomain.getPrice() * numberOrder;

        }


        plusBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(numberOrder < 10) {
                    numberOrder++;
                    numberOrderTxt.setText(String.valueOf(numberOrder));
                    totalPrice = popularDomain.getPrice() * numberOrder;
                }
            }
        });

        minusBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (numberOrder > 1) {
                    numberOrder--;
                    numberOrderTxt.setText(String.valueOf(numberOrder));
                    totalPrice = popularDomain.getPrice() * numberOrder;
                }
            }
        });

        addToCartBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addedToCart();
            }
        });

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(PopularDetailActivity.this,MainActivity.class));
            }
        });
    }


    private void addedToCart() {
        String saveCurrentDate,saveCurrentTime;
        Calendar callForDate = Calendar.getInstance();

        SimpleDateFormat currentDate = new SimpleDateFormat("dd MM, yyyy");
        saveCurrentDate = currentDate.format(callForDate.getTime());

        SimpleDateFormat currentTime = new SimpleDateFormat("HH:mm:ss");
        saveCurrentTime = currentTime.format(callForDate.getTime());

        final HashMap<String,Object> cart = new HashMap<>();

        cart.put("Image",popularDomain.getImage());
        cart.put("ProductName",popularDomain.getName());
        cart.put("ProductPrice",feeTxt.getText().toString());
        cart.put("currentDate",saveCurrentDate);
        cart.put("currentTime",saveCurrentTime);
        cart.put("totalQuantity",numberOrderTxt.getText().toString());
        cart.put("totalPrice",totalPrice);



        mStore.collection("Cart").document(mAuth.getCurrentUser().getUid())
                .collection("users").add(cart)
                .addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentReference> task) {
                        Toast.makeText(PopularDetailActivity .this,"Ditambah ke dalam troli",Toast.LENGTH_SHORT).show();
                        finish();
                    }
                });
    }
}