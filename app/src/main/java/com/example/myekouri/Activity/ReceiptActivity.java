package com.example.myekouri.Activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;

import com.example.myekouri.Adapter.CartListAdapter;
import com.example.myekouri.Adapter.ReceiptAdapter;
import com.example.myekouri.Domain.CartListDomain;
import com.example.myekouri.Domain.ReceiptDomain;
import com.example.myekouri.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class ReceiptActivity extends AppCompatActivity {

    TextView receipt,date,time;
    TextView totalFeeTxt,chargeTxt, totalTxt, emptyTxt;
    FirebaseFirestore mStore;
    FirebaseAuth mAuth;


    RecyclerView recyclerViewReceipt;
    ReceiptAdapter receiptAdapter;
    List<ReceiptDomain> receiptDomainList;
    List<CartListDomain> cartListDomainList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_receipt);

        receipt = findViewById(R.id.receipt);
        date= findViewById(R.id.date);
        time = findViewById(R.id.time);

        mAuth = FirebaseAuth.getInstance();
        mStore = FirebaseFirestore.getInstance();

        Intent intent = getIntent();
        Double totalPriceProduct = Double.valueOf(intent.getStringExtra("TotalPrice"));
        totalFeeTxt.setText(String.valueOf(totalPriceProduct));

        recyclerViewReceipt = findViewById(R.id.receiptRecView);
        recyclerViewReceipt.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));

        receiptDomainList = new ArrayList<>();
        receiptAdapter = new ReceiptAdapter(this,receiptDomainList);
        recyclerViewReceipt.setAdapter(receiptAdapter);

        //Retrieve data from firebase
        DocumentReference documentReference = mStore.collection("Receipt").document(mAuth.getCurrentUser().getUid());
        documentReference.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException error) {
                date.setText(documentSnapshot.getString("currentDate"));
                time.setText(documentSnapshot.getString("currentTime"));
            }
        });


        mStore.collection("Cart").document(mAuth.getCurrentUser().getUid())
                .collection("users").get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if(task.isSuccessful()){
                            for(DocumentSnapshot documentSnapshot : task.getResult().getDocuments()){

                                String documentId = documentSnapshot.getId();

                                ReceiptDomain receiptDomain = documentSnapshot.toObject(ReceiptDomain.class);

                                receiptDomain.setDocumentId(documentId);

                                receiptDomainList.add(receiptDomain);
                                receiptAdapter.notifyDataSetChanged();
                            }

                        }

                    }
                });
    }


    }


