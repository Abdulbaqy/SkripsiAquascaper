package com.example.multiuserrealtimefragment.view.vendor_ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.multiuserrealtimefragment.LoadingAlert;
import com.example.multiuserrealtimefragment.R;
import com.example.multiuserrealtimefragment.controller.ProjectController;
import com.example.multiuserrealtimefragment.model.DatabaseCallback;
import com.example.multiuserrealtimefragment.model.ModelProject;
import com.example.multiuserrealtimefragment.view.Login;
import com.google.firebase.auth.FirebaseAuth;

public class Invoice extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_invoice);

        FirebaseAuth auth = FirebaseAuth.getInstance();

        String projectId = getIntent().getStringExtra("projectId");
        String vendorName = getIntent().getStringExtra("vendorName");
        String vendorId = auth.getUid();

        AppCompatButton btnSubmit = findViewById(R.id.submit_invoice_btn);
        EditText edtDetails = findViewById(R.id.details_invoice);
        EditText edtPrice = findViewById(R.id.price_invoice);

        ProjectController projectController = new ProjectController();

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String price = edtPrice.getText().toString();
                String details = edtDetails.getText().toString();

                if (details.isEmpty()) {
                    edtDetails.setError("Input Details!");
                } else if (price.isEmpty()) {
                    edtPrice.setError("Input Price!");
                } else {
                    int priceInt = Integer.parseInt(price);

                    ModelProject modelProject = new ModelProject();
                    modelProject.setDataPrice(priceInt);
                    modelProject.setDataDetails(details);
                    modelProject.setDataVendorId(vendorId);
                    modelProject.setDataVendorName(vendorName);
                    modelProject.setDataStatus("Taken");
                    projectController.takeProject(projectId, modelProject, new DatabaseCallback() {
                        @Override
                        public void onSuccess() {
                            Toast.makeText(Invoice.this, "Success generated invoice!", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(Invoice.this, VendorActivity.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            startActivity(intent);
                        }

                        @Override
                        public void onFailed() {
                            Toast.makeText(Invoice.this, "Failed generated invoice!", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });
    }
}