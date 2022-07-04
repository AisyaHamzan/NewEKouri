package com.example.myekouri.Activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.nfc.Tag;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.myekouri.Domain.AllProductDomain;
import com.example.myekouri.Domain.CategoryDomain;
import com.example.myekouri.Domain.FreshDomain;
import com.example.myekouri.Domain.PopularDomain;
import com.example.myekouri.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class AddProductActivity extends AppCompatActivity {

    private static final int PICK_IMAGE_REQUEST = 1;

    EditText Name, Brand, Desc, Price, Category;
    Button updateBtn;
    ProgressDialog progressDialog;
    FirebaseAuth mAuth;
    FirebaseUser mUser;
    FirebaseFirestore mStore;
    String proId;
    ImageView ImageProduct;
    ProgressBar progressBar;

    Uri ImageUri;

    AllProductDomain allProductDomain = null;

    //arraylist to hold category
    ArrayList<CategoryDomain> categoryDomains;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        Object object = getIntent().getSerializableExtra("productInfo");
        if (object instanceof AllProductDomain) {
            allProductDomain = (AllProductDomain) object;

        }

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_product);
        Name = findViewById(R.id.proName);
        Brand = findViewById(R.id.proBrand);
        Desc = findViewById(R.id.proDesc);
        Price = findViewById(R.id.proPrice);
        Category = findViewById(R.id.proCategory);
        ImageProduct = findViewById(R.id.proImg);
        progressBar = findViewById(R.id.progress_bar);
        updateBtn = findViewById(R.id.updateBtn);
        progressDialog = new ProgressDialog(this);
        mAuth = FirebaseAuth.getInstance();
        mStore = FirebaseFirestore.getInstance();
        mUser = mAuth.getCurrentUser();

        if (allProductDomain != null) {
            Glide.with(getApplicationContext()).load(allProductDomain.getImage()).into(ImageProduct);
            Name.setText(allProductDomain.getName());
            Brand.setText(allProductDomain.getBrand());
            Desc.setText(allProductDomain.getDescription());
            Price.setText("RM" + allProductDomain.getPrice());
            Category.setText(allProductDomain.getCategory());


        }




        ImageProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openFileChooser();
            }
        });


        //save data in Firebase on button click
        updateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //input data
                String name = Name.getText().toString();
                String brand = Brand.getText().toString();
                String description = Desc.getText().toString();
                Integer price = Integer.valueOf(Price.getText().toString());
                String category = Category.getText().toString();

                //function call to upload data
                uploadData(name, brand, description, price, category);

            }
        });
    }




    private void openFileChooser() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, PICK_IMAGE_REQUEST);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData()!= null){
            ImageUri=data.getData();

           ImageProduct.setImageURI(ImageUri);
        }
    }

    private void uploadData(String name, String brand, String description, Integer price, String category) {
        progressDialog.setMessage("Tambah dan Kemaskini Produk");
        progressDialog.setTitle("Penambahan dan Pengemaskinian Produk");
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();

        //random id for each product
        String proId = UUID.randomUUID().toString();
        Map<String,Object> product = new HashMap<>();
        product.put("Name",name);
        product.put("Brand",brand);
        product.put("Description",description);
        product.put("Price",price);
        product.put("Category",category);

        //add data
        mStore.collection("Product").document(proId).set(product)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        //will be called when data is successfully stored
                        progressDialog.dismiss();
                        Toast.makeText(AddProductActivity.this,"Memuatnaik produk",Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        ///will be called when data has error
                        progressDialog.dismiss();
                        Toast.makeText(AddProductActivity.this,e.getMessage(),Toast.LENGTH_SHORT).show();
                    }
                });
    }


}

