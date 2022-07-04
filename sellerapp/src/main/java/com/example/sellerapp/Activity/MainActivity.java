 package com.example.sellerapp.Activity;

 import androidx.annotation.NonNull;
 import androidx.appcompat.app.AppCompatActivity;
 import androidx.appcompat.widget.SearchView;
 import androidx.recyclerview.widget.LinearLayoutManager;
 import androidx.recyclerview.widget.RecyclerView;

 import android.content.Intent;
 import android.os.Bundle;
 import android.text.Editable;
 import android.text.TextWatcher;
 import android.view.View;
 import android.widget.Button;
 import android.widget.EditText;
 import android.widget.LinearLayout;
 import android.widget.Toast;

 import com.example.myekouri.Adapter.AllProductAdapter;
 import com.example.myekouri.Adapter.CategoryAdapter;
 import com.example.myekouri.Adapter.FreshAdapter;
 import com.example.myekouri.Adapter.PopularAdapter;
 import com.example.myekouri.Adapter.ViewProductAdapter;
 import com.example.myekouri.Domain.AllProductDomain;
 import com.example.myekouri.Domain.CategoryDomain;
 import com.example.myekouri.Domain.FreshDomain;
 import com.example.myekouri.Domain.PopularDomain;
 import com.example.myekouri.Domain.ViewProductDomain;
 import com.example.myekouri.R;
 import com.example.sellerapp.R;
 import com.google.android.gms.tasks.OnCompleteListener;
 import com.google.android.gms.tasks.Task;
 import com.google.android.material.floatingactionbutton.FloatingActionButton;
 import com.google.firebase.firestore.DocumentSnapshot;
 import com.google.firebase.firestore.FirebaseFirestore;
 import com.google.firebase.firestore.QueryDocumentSnapshot;
 import com.google.firebase.firestore.QuerySnapshot;

 import java.util.ArrayList;
 import java.util.List;

 public class MainActivity extends AppCompatActivity {
     RecyclerView.Adapter categoryAdapter, popularAdapter, freshAdapter;
     RecyclerView recyclerViewCategoryList, recyclerViewPopularList, recyclerViewFreshList;
     FirebaseFirestore mStore;

     //Fresh
     List<FreshDomain> freshDomainList;

     //Category
     List<CategoryDomain> categoryDomains;

     //Popular
     List<PopularDomain> popularDomainList;

     //SearchView
     private SearchView searchView;

     private List<AllProductDomain> allProductDomainList;
     private RecyclerView recyclerViewSearch;
     private AllProductAdapter allProductAdapter;


     @Override
     protected void onCreate(Bundle savedInstanceState) {
         super.onCreate(savedInstanceState);
         setContentView(R.layout.activity_main);

         bottomNavigation();
         mStore = FirebaseFirestore.getInstance();

         recyclerViewSearch = findViewById(R.id.searchRecView);
         recyclerViewSearch.setHasFixedSize(true);
         recyclerViewSearch.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL,false));
         allProductDomainList = new ArrayList<>();
         allProductAdapter = new AllProductAdapter(this,allProductDomainList);
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


         //fresh items
         recyclerViewFreshList = findViewById(R.id.freshRecView);
         recyclerViewFreshList.setLayoutManager(new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false));
         freshDomainList = new ArrayList<>();
         freshAdapter = new FreshAdapter(this, freshDomainList);
         recyclerViewFreshList.setAdapter(freshAdapter);

         mStore.collection("Fresh")
                 .get()
                 .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                     @Override
                     public void onComplete(@NonNull Task<QuerySnapshot> task) {
                         if (task.isSuccessful()) {
                             for (QueryDocumentSnapshot document : task.getResult()) {
                                 FreshDomain freshDomain = document.toObject(FreshDomain.class);
                                 freshDomainList.add(freshDomain);
                                 freshAdapter.notifyDataSetChanged();
                             }
                         } else {
                             Toast.makeText(MainActivity.this, "Error" + task.getException(), Toast.LENGTH_SHORT).show();
                         }
                     }
                 });


         //category items
         recyclerViewCategoryList = findViewById(R.id.catRecView);
         recyclerViewCategoryList.setLayoutManager(new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false));
         categoryDomains = new ArrayList<>();
         categoryAdapter = new CategoryAdapter(this, categoryDomains);
         recyclerViewCategoryList.setAdapter(categoryAdapter);

         mStore.collection("Category")
                 .get()
                 .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                     @Override
                     public void onComplete(@NonNull Task<QuerySnapshot> task) {
                         if (task.isSuccessful()) {
                             for (QueryDocumentSnapshot document : task.getResult()) {
                                 CategoryDomain categoryDomain = document.toObject(CategoryDomain.class);
                                 categoryDomains.add(categoryDomain);
                                 categoryAdapter.notifyDataSetChanged();
                             }
                         } else {
                             Toast.makeText(MainActivity.this, "Error" + task.getException(), Toast.LENGTH_SHORT).show();
                         }
                     }
                 });

         //popular items
         recyclerViewPopularList = findViewById(R.id.popRecView);
         recyclerViewPopularList.setLayoutManager(new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false));
         popularDomainList = new ArrayList<>();
         popularAdapter = new PopularAdapter(this, popularDomainList);
         recyclerViewPopularList.setAdapter(popularAdapter);

         mStore.collection("Popular")
                 .get()
                 .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                     @Override
                     public void onComplete(@NonNull Task<QuerySnapshot> task) {
                         if (task.isSuccessful()) {
                             for (QueryDocumentSnapshot documentSnapshot : task.getResult()) {
                                 PopularDomain popularDomain = documentSnapshot.toObject(PopularDomain.class);
                                 popularDomainList.add(popularDomain);
                                 popularAdapter.notifyDataSetChanged();
                             }
                         } else {
                             Toast.makeText(MainActivity.this, "Error" + task.getException(), Toast.LENGTH_SHORT).show();
                         }
                     }
                 });

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



     private void bottomNavigation() {
         FloatingActionButton floatingActionButton = findViewById(R.id.cartBtn);
         LinearLayout homeBtn = findViewById(R.id.homeBtn);
         LinearLayout profileBtn = findViewById(R.id.profileBtn);
         LinearLayout settingBtn = findViewById(R.id.settingbtn);
         LinearLayout logoutBtn = findViewById(R.id.logoutbtn);


         floatingActionButton.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View view) {
                 startActivity(new Intent(MainActivity.this, CartListActivity.class));
             }
         });
         homeBtn.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View view) {
                 startActivity(new Intent(MainActivity.this, MainActivity.class));
             }
         });

         profileBtn.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View view) {
                 startActivity(new Intent(MainActivity.this, MyProfileActivity.class));
             }
         });

         settingBtn.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View view) {
                 startActivity(new Intent(MainActivity.this, AllProductActivity.class));
             }
         });

         logoutBtn.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View view) {
                 startActivity(new Intent(MainActivity.this,LoginActivity.class));
             }
         });

     }

 }
