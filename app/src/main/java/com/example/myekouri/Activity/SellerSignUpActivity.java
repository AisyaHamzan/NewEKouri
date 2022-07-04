package com.example.myekouri.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.myekouri.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class SellerSignUpActivity extends AppCompatActivity {

    TextInputLayout Name, Email, Address, PhoneNo, Username, Password, ConfirmPassword;
    Button SignUpBtn, ToLoginBtn;
    ImageView profileImg;
    String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
    ProgressDialog progressDialog;
    FirebaseAuth mAuth;
    FirebaseUser mUser;
    FirebaseFirestore mStore;
    String userId;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seller_sign_up);

        profileImg = findViewById(R.id.profile_img);
        Name = findViewById(R.id.regName);
        Email = findViewById(R.id.regEmail);
        Address = findViewById(R.id.regAddress);
        PhoneNo = findViewById(R.id.regPhoneNo);
        Username = findViewById(R.id.regUsername);
        Password = findViewById(R.id.regPassword);
        ConfirmPassword = findViewById(R.id.confirPassword);
        SignUpBtn = findViewById(R.id.SignUp_Btn);
        ToLoginBtn = findViewById(R.id.ToLogin_Btn);
        progressDialog = new ProgressDialog(this);
        mAuth = FirebaseAuth.getInstance();
        mStore = FirebaseFirestore.getInstance();
        mUser = mAuth.getCurrentUser();

        //To Login button
        ToLoginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SellerSignUpActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });


        //save data in Firebase on button click
        SignUpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                performAuth();

            }
        });
    }


    private void performAuth() {
        String name = Name.getEditText().getText().toString();
        String email = Email.getEditText().getText().toString();
        String address = Address.getEditText().getText().toString();
        String phone = PhoneNo.getEditText().getText().toString();
        String username = Username.getEditText().getText().toString();
        String password = Password.getEditText().getText().toString();
        String confirmPassword = ConfirmPassword.getEditText().getText().toString();

        if(!email.matches(emailPattern)){
            Email.setError("Masukkan e-mel dengan betul");
        }else if(password.isEmpty() || password.length()<6){
            Password.setError("Masukkan kata laluan dengan betul");
        }else if(!password.equals(confirmPassword)){
            ConfirmPassword.setError("Kata laluan tidak serasi");
        }else{
            progressDialog.setMessage("Sila tunggu untuk pendaftaran");
            progressDialog.setTitle("Pendaftaran akaun");
            progressDialog.setCanceledOnTouchOutside(false);
            progressDialog.show();

            mAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful())
                    {
                        progressDialog.dismiss();
                        sendUserToNextActivity();
                        Toast.makeText(SellerSignUpActivity.this,"Maklumat akaun sudah didaftarkan", Toast. LENGTH_SHORT).show();
                        userId = mAuth.getCurrentUser().getUid();
                        DocumentReference documentReference = mStore.collection("users").document(userId);
                        Map<String,Object> user = new HashMap<>();
                        user.put("Name",name);
                        user.put("Email",email);
                        user.put("Address",address);
                        user.put("PhoneNo",phone);
                        user.put("Username",username);
                        user.put("accountType","Seller");
                        user.put("Online","True");

                        documentReference.set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {
                                // Log.d("CHECKED","onSuccess: Pengguna akaun telah didaftarkan buat "+userId);
                                startActivity(new Intent(getApplicationContext(),LoginActivity.class));
                            }
                        });


                    }else{
                        progressDialog.dismiss();
                        Toast.makeText(SellerSignUpActivity.this,""+task.getException(),Toast.LENGTH_SHORT);
                    }
                }
            });


        }
    }

    private void sendUserToNextActivity() {
        Intent intent= new Intent(SellerSignUpActivity.this,LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);

    }
}