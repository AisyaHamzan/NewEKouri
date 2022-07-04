package com.example.myekouri.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myekouri.Adapter.CartListAdapter;
import com.example.myekouri.Domain.CartListDomain;
import com.example.myekouri.Helper.ManagementCart;
import com.example.myekouri.Interface.ChangeNumberItemsListener;
import com.example.myekouri.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

public class CartListActivity extends AppCompatActivity {

   // private RecyclerView.Adapter adapter;
   // private RecyclerView recyclerViewList;
    private ManagementCart managementCart;
    TextView totalFeeTxt,chargeTxt, totalTxt, emptyTxt;
    private double tax;
    private ScrollView scrollView;

    FirebaseFirestore mStore;
    FirebaseAuth mAuth;

    RecyclerView recyclerViewList;
    CartListAdapter cartListAdapter;
    List<CartListDomain> cartListDomainList;
    CartListDomain cartListDomain = new CartListDomain();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart_list);

        mAuth = FirebaseAuth.getInstance();
        mStore = FirebaseFirestore.getInstance();
        recyclerViewList = findViewById(R.id.cartView);
        recyclerViewList.setLayoutManager(new LinearLayoutManager(this));

        cartListDomainList = new ArrayList<>();
        cartListAdapter = new CartListAdapter(this,cartListDomainList);
        recyclerViewList.setAdapter(cartListAdapter);

        mStore.collection("Cart").document(mAuth.getCurrentUser().getUid())
               .collection("users").get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if(task.isSuccessful()){
                            for(DocumentSnapshot documentSnapshot : task.getResult().getDocuments()){

                                String documentId = documentSnapshot.getId();

                                CartListDomain cartListDomain = documentSnapshot.toObject(CartListDomain.class);

                                cartListDomain.setDocumentId(documentId);

                                cartListDomainList.add(cartListDomain);
                                cartListAdapter.notifyDataSetChanged();
                            }
                            CalculateTotalProduct(cartListDomainList);
                        }
                    }
                });


        initView();
        initList();
        bottomNavigation();
        generateReceipt();

    }

    private void CalculateTotalProduct(List<CartListDomain> cartListDomainList) {
        double  totalPriceProduct = 0.0F;
        double charge = 5.50;
        double totalAllPrice = 0.0;
        for(CartListDomain cartListDomain : cartListDomainList){
            totalPriceProduct += cartListDomain.getTotalPrice();
            totalAllPrice += cartListDomain.getTotalPrice() + charge;

        }
        totalFeeTxt.setText("RM" +totalPriceProduct);
        totalTxt.setText("RM" +totalAllPrice);
        chargeTxt.setText("RM"+ charge);

    }


    private void generateReceipt() {
    TextView receipt;
    receipt = findViewById(R.id.receipt);

    receipt.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {

            Double totalPriceProduct = Double.valueOf(totalFeeTxt.getText().toString());
            Intent intent = new Intent(CartListActivity.this, ReceiptActivity.class);
            intent.putExtra("Total Price", totalPriceProduct);
            startActivity(intent);
        // startActivity(new Intent(CartListActivity.this,ReceiptActivity.class));

                String saveCurrentDate,saveCurrentTime;
                Calendar callForDate = Calendar.getInstance();

                SimpleDateFormat currentDate = new SimpleDateFormat("dd MM, yyyy");
                saveCurrentDate = currentDate.format(callForDate.getTime());

                SimpleDateFormat currentTime = new SimpleDateFormat("HH:mm:ss");
                saveCurrentTime = currentTime.format(callForDate.getTime());

                final HashMap<String,Object> receipt = new HashMap<>();

                receipt.put("currentDate",saveCurrentDate);
                receipt.put("currentTime",saveCurrentTime);


                mStore.collection("Receipt").document(mAuth.getCurrentUser().getUid())
                        .collection("users").add(receipt)
                        .addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                            @Override
                            public void onComplete(@NonNull Task<DocumentReference> task) {
                                Toast.makeText(CartListActivity.this,"Sedang menjana resit ",Toast.LENGTH_SHORT).show();
                                finish();
                            }
                        });
        }
    });
    }

    private void bottomNavigation() {
        FloatingActionButton floatingActionButton = findViewById(R.id.cartBtn);
        LinearLayout homeBtn = findViewById(R.id.homeBtn);
        LinearLayout logoutBtn = findViewById(R.id.logoutBtn);

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(CartListActivity.this, CartListActivity.class));
            }
        });
        homeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(CartListActivity.this, MainActivity.class));
            }
        });
        logoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(CartListActivity.this,LoginActivity.class));
            }
        });
    }

    private void initView() {
        recyclerViewList = findViewById(R.id.catRecView);
        totalFeeTxt = findViewById(R.id.totalFeeTxt);
        totalTxt = findViewById(R.id.totalTxt);
        chargeTxt = findViewById(R.id.chargeTxt);
        emptyTxt = findViewById(R.id.emptyTxt);
        scrollView = findViewById(R.id.scrollView3);
        recyclerViewList=findViewById(R.id.cartView);
    }

    private void initList() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerViewList.setLayoutManager(linearLayoutManager);
       /* adapter = new CartListAdapter(managementCart.getListCart(), this, new ChangeNumberItemsListener() {
            @Override
            public void changed() {
                CalculateCart();
            }
        });

        recyclerViewList.setAdapter(adapter);
        if (managementCart.getListCart().isEmpty()) {
            emptyTxt.setVisibility(View.VISIBLE);
            scrollView.setVisibility(View.GONE);
        } else {
            emptyTxt.setVisibility(View.GONE);
            scrollView.setVisibility(View.VISIBLE);
        }
    }*/

    }
}
