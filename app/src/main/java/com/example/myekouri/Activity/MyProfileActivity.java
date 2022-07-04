package com.example.myekouri.Activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.myekouri.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class MyProfileActivity extends AppCompatActivity {
     EditText Name, Email,Address,PhoneNo;
     ImageView backBtn;
     FirebaseFirestore mStore;
     FirebaseAuth mAuth;
     String userId;
     Button updateBtn, logoutBtn;
     ImageView profileImg;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_profile);
        Name = findViewById(R.id.regName);
        Email = findViewById(R.id.regEmail);
        Address = findViewById(R.id.regAddress);
        PhoneNo = findViewById(R.id.regPhoneNo);
        profileImg = findViewById(R.id.profile_img);
        updateBtn = findViewById(R.id.updateBtn);
        mAuth = FirebaseAuth.getInstance();
        mStore = FirebaseFirestore.getInstance();

        userId = mAuth.getCurrentUser().getUid();



        //Retrieve data from firebase
        DocumentReference documentReference = mStore.collection("users").document(userId);
        documentReference.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException error) {
                Name.setText(documentSnapshot.getString("Name"));
                Email.setText(documentSnapshot.getString("Email"));
                Address.setText(documentSnapshot.getString("Address"));
                PhoneNo.setText(documentSnapshot.getString("PhoneNo"));
            }
        });


        backBtn = (ImageView) findViewById(R.id.back);

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MyProfileActivity.this, MainActivity.class));
            }
        });

        updateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateData();
            }
        });
    }

    private void updateData() {
        String name = Name.getText().toString();
        String email = Email.getText().toString();
        String address = Address.getText().toString();
        String phone = PhoneNo.getText().toString();
        DocumentReference documentReference = mStore.collection("users").document(userId);

        Map<String, Object> user = new HashMap<>();
        user.put("Name", name);
        user.put("Email", email);
        user.put("Address", address);
        user.put("PhoneNo", phone);
        documentReference.set(user)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Toast.makeText(MyProfileActivity.this, "Berjaya dikemaskini!", Toast.LENGTH_SHORT).show();
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(MyProfileActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

}