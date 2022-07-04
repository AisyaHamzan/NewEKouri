package com.example.myekouri.Activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.myekouri.Adapter.AllProductAdapter;
import com.example.myekouri.Adapter.CartListAdapter;
import com.example.myekouri.Adapter.CategoryAdapter;
import com.example.myekouri.Adapter.ViewProductAdapter;
import com.example.myekouri.Domain.AllProductDomain;
import com.example.myekouri.Domain.CartListDomain;
import com.example.myekouri.Domain.CategoryDomain;
import com.example.myekouri.Domain.FreshDomain;
import com.example.myekouri.Domain.ViewProductDomain;
import com.example.myekouri.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class AllProductActivity extends AppCompatActivity {

    FirebaseFirestore mStore;
    RecyclerView allProRecView;
    AllProductAdapter allProductAdapter;
    List<AllProductDomain> allProductDomainList;
    SearchView searchView;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_product);

        bottomNavigation();

        allProRecView = findViewById(R.id.allProRecView);
        allProRecView.setHasFixedSize(true);
        allProRecView.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));

        mStore = FirebaseFirestore.getInstance();
        allProductDomainList = new ArrayList<>();
        allProductAdapter = new AllProductAdapter(AllProductActivity.this,allProductDomainList);

        allProRecView.setAdapter(allProductAdapter);

        searchView = findViewById(R.id.search);
        searchView.clearFocus();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                filterList(newText);
                return true;
            }
        });

        EventChangeListener();


    }

    private void filterList(String text) {
        List<AllProductDomain> filteredList = new ArrayList<>();
        for( AllProductDomain allProductDomain : allProductDomainList){
            if(allProductDomain.getName().toLowerCase().contains(text.toLowerCase())){
                filteredList.add(allProductDomain);
            }
        }
        if(filteredList.isEmpty()){
            Toast.makeText(this,"Tiada produk ditemui",Toast.LENGTH_SHORT).show();
        }else{
            allProductAdapter.setFilteredList(filteredList);
        }
    }

    private void EventChangeListener() {
        mStore.collection("Product").orderBy("Name", Query.Direction.ASCENDING)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            Log.v("Error","Found"+task.getResult().size());
                            for (QueryDocumentSnapshot doc : task.getResult()) {
                                allProductDomainList.add(doc.toObject(AllProductDomain.class));
                            }
                        }
                        allProductAdapter = new AllProductAdapter(AllProductActivity.this,allProductDomainList);
                        allProRecView.setAdapter(allProductAdapter);
                    }
                });
    }

    private void bottomNavigation() {
            FloatingActionButton floatingActionButton = findViewById(R.id.addBtn);
            LinearLayout homeBtn = findViewById(R.id.homeBtn);
            LinearLayout profileBtn = findViewById(R.id.profileBtn);
            LinearLayout settingBtn = findViewById(R.id.settingbtn);
            LinearLayout logoutBtn = findViewById(R.id.logoutBtn);


            floatingActionButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    startActivity(new Intent(AllProductActivity.this, AddProductActivity.class));
                }
            });
            homeBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    startActivity(new Intent(AllProductActivity.this, MainActivity.class));
                }
            });

            profileBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    startActivity(new Intent(AllProductActivity.this, MyProfileActivity.class));
                }
            });

            settingBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    startActivity(new Intent(AllProductActivity.this, AllProductActivity.class));
                }
            });
        logoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(AllProductActivity.this,LoginActivity.class));
            }
        });


        }
}
