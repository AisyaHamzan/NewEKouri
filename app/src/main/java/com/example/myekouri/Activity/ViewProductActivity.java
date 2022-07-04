package com.example.myekouri.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.myekouri.Adapter.ViewProductAdapter;
import com.example.myekouri.Domain.ViewProductDomain;
import com.example.myekouri.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class ViewProductActivity extends AppCompatActivity {

    FirebaseFirestore mStore;
    RecyclerView recyclerView;
    ViewProductAdapter viewProductAdapter;
    List<ViewProductDomain> viewProductDomainList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_product);

        mStore = FirebaseFirestore.getInstance();
        String category = getIntent().getStringExtra("Category");
        recyclerView = findViewById(R.id.proRecView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));


        viewProductDomainList = new ArrayList<>();
        viewProductAdapter = new ViewProductAdapter(this,viewProductDomainList);
        recyclerView.setAdapter(viewProductAdapter);


        //// getting ikan
        if(category != null && category.equalsIgnoreCase("Ikan")) {
            mStore.collection("Product").whereEqualTo("Category", "Ikan")
                    .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                @Override
                public void onComplete(@NonNull Task<QuerySnapshot> task) {

                    for (DocumentSnapshot documentSnapshot : task.getResult().getDocuments()) {
                        ViewProductDomain viewProductDomain = documentSnapshot.toObject(ViewProductDomain.class);
                        viewProductDomainList.add(viewProductDomain);
                        viewProductAdapter.notifyDataSetChanged();
                    }
                }
            });
        }
            //// getting penjagaan diri
            if(category != null && category.equalsIgnoreCase("Penjagaan Diri")){
                mStore.collection("Product").whereEqualTo("Category","Penjagaan Diri")
                        .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {

                        for(DocumentSnapshot documentSnapshot : task.getResult().getDocuments()){
                            ViewProductDomain viewProductDomain = documentSnapshot.toObject(ViewProductDomain.class);
                            viewProductDomainList.add(viewProductDomain);
                            viewProductAdapter.notifyDataSetChanged();
                        }
                    }
                });

        }
        //// getting segera
        if(category != null && category.equalsIgnoreCase("Makanan Segera")){
            mStore.collection("Product").whereEqualTo("Category","Makanan Segera")
                    .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                @Override
                public void onComplete(@NonNull Task<QuerySnapshot> task) {

                    for(DocumentSnapshot documentSnapshot : task.getResult().getDocuments()){
                        ViewProductDomain viewProductDomain = documentSnapshot.toObject(ViewProductDomain.class);
                        viewProductDomainList.add(viewProductDomain);
                        viewProductAdapter.notifyDataSetChanged();
                    }
                }
            });

        }
        //// getting asasi
        if(category != null && category.equalsIgnoreCase("Barangan Asasi")){
            mStore.collection("Product").whereEqualTo("Category","Barangan Asasi")
                    .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                @Override
                public void onComplete(@NonNull Task<QuerySnapshot> task) {

                    for(DocumentSnapshot documentSnapshot : task.getResult().getDocuments()){
                        ViewProductDomain viewProductDomain = documentSnapshot.toObject(ViewProductDomain.class);
                        viewProductDomainList.add(viewProductDomain);
                        viewProductAdapter.notifyDataSetChanged();
                    }
                }
            });

        }
        //// getting sayuran
        if(category != null && category.equalsIgnoreCase("Sayuran")){
            mStore.collection("Product").whereEqualTo("Category","Sayuran")
                    .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                @Override
                public void onComplete(@NonNull Task<QuerySnapshot> task) {

                    for(DocumentSnapshot documentSnapshot : task.getResult().getDocuments()){
                        ViewProductDomain viewProductDomain = documentSnapshot.toObject(ViewProductDomain.class);
                        viewProductDomainList.add(viewProductDomain);
                        viewProductAdapter.notifyDataSetChanged();
                    }
                }
            });

        }

    }
}