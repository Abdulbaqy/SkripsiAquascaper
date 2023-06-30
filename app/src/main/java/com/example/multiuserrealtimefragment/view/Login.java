package com.example.multiuserrealtimefragment.view;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;

import com.example.multiuserrealtimefragment.LoadingAlert;
import com.example.multiuserrealtimefragment.R;
import com.example.multiuserrealtimefragment.controller.PreferencesController;
import com.example.multiuserrealtimefragment.view.customer_ui.CustomerActivity;
import com.example.multiuserrealtimefragment.view.vendor_ui.VendorActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Login extends AppCompatActivity {
    EditText password, email;
    Button loginBtn, gotoRegister;
    Switch active;
    FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mAuth = FirebaseAuth.getInstance();
        email = findViewById(R.id.ET_email);
        password = findViewById(R.id.ET_LoginPassword);
        loginBtn = findViewById(R.id.signInBtn);
        gotoRegister = findViewById(R.id.goToRegisterBtn);
//        active = findViewById(R.id.active);

        LoadingAlert loadingAlert = new LoadingAlert(Login.this);

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String Email = email.getText().toString();
                String Password = password.getText().toString();

                if (validateInput()) {
                    // Start user registration process

                    loadingAlert.startAlertDialog();
                mAuth.signInWithEmailAndPassword(Email, Password)
                        .addOnCompleteListener(Login.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    loadingAlert.closeAlertDialog();
                                    // Login berhasil
                                    FirebaseUser user = mAuth.getCurrentUser();
                                    String userId = user.getUid();

                                    // Lakukan pengecekan as dan alihkan ke aktivitas yang sesuai
                                    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("Users").child(userId);
                                    databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                                        @Override
                                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                            if (dataSnapshot.exists()) {
                                                String userRole = dataSnapshot.child("as").getValue(String.class);
                                                String nameFromDB = dataSnapshot.child("FullName").getValue(String.class);

                                                if (userRole.equals("customer")) {
                                                    PreferencesController.setDataLogin(getApplicationContext(), "customer", nameFromDB);
                                                    Intent intent = new Intent(Login.this, CustomerActivity.class);
                                                    startActivity(intent);
                                                } else if (userRole.equals("vendor")) {
                                                    PreferencesController.setDataLogin(getApplicationContext(), "vendor", nameFromDB);
                                                    startActivity(new Intent(Login.this, VendorActivity.class));
                                                }
                                            }
                                        }
                                        @Override
                                        public void onCancelled(@NonNull DatabaseError error) {
                                            Toast.makeText(Login.this, "Error: " + error.getMessage(), Toast.LENGTH_SHORT).show();
                                        }
                                    });
                                } else {
                                    // Login gagal
                                    loadingAlert.closeAlertDialog();
                                    Toast.makeText(Login.this, "Sign in failed, wrong email or password!", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                        }
            }
        });

        gotoRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),Register.class));
            }
        });

    }


    private boolean validateInput() {
        String emailText = email.getText().toString();
        String passwordText = password.getText().toString();

        if (emailText.isEmpty()) {
            email.setError("Field Cannot Be Empty");
            return false;
        }
        if (passwordText.isEmpty()) {
            password.setError("Field Cannot Be Empty");
            return false;
        }
        return true;
    }


    @Override
    protected void onStart() {
        super.onStart();
        if (PreferencesController.getDataLogin(this) && mAuth.getUid() != null){
            if (PreferencesController.getDataAs(this).equals("customer")){
                startActivity(new Intent(Login.this, CustomerActivity.class));
                finish();
            }else if(PreferencesController.getDataAs(this).equals("vendor")){
                startActivity(new Intent(Login.this, VendorActivity.class));
                finish();
            }
        }
    }
}