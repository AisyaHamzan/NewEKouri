package com.example.myekouri.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.myekouri.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;

public class LoginActivity extends AppCompatActivity {

    TextInputLayout Email, Password;
    Button callSignUp, loginBtn;
    String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
    ProgressDialog progressDialog;
    FirebaseAuth mAuth;
    FirebaseUser mUser;
    FirebaseFirestore mStore;
    String userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        callSignUp = findViewById(R.id.SignUp_Btn);
        Email = findViewById(R.id.regEmail);
        Password = findViewById(R.id.regPassword);
        loginBtn = findViewById(R.id.loginBtn);

//        userId = mAuth.getCurrentUser().getUid();

        progressDialog = new ProgressDialog(this);
        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();
        mStore = FirebaseFirestore.getInstance();

        callSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, SignUpActivity.class);
                startActivity(intent);
            }
        });


        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                performLogin();
            }
        });
    }

    private void performLogin() {
        String email = Email.getEditText().getText().toString();
        String password = Password.getEditText().getText().toString();


        if (!email.matches(emailPattern)) {
            Email.setError("Masukkan e-mel dengan betul");
        } else if (password.isEmpty() || password.length() < 6) {
            Password.setError("Masukkan kata laluan dengan betul");
        } else {
            progressDialog.setMessage("Sila tunggu untuk log masuk");
            progressDialog.setTitle("Log masuk");
            progressDialog.setCanceledOnTouchOutside(false);
            progressDialog.show();

            mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {
                        progressDialog.dismiss();
                        sendUserToNextActivity();
                        Toast.makeText(LoginActivity.this, "Log masuk berjaya ", Toast.LENGTH_SHORT).show();
                    } else {
                        progressDialog.dismiss();
                        Toast.makeText(LoginActivity.this, "" + task.getException(), Toast.LENGTH_SHORT);
                    }
                }
            });
        }
    }

    private void sendUserToNextActivity() {
        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }
}
