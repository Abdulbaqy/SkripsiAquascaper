package com.example.multiuserrealtimefragment.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.multiuserrealtimefragment.R;
import com.example.multiuserrealtimefragment.controller.PreferencesController;
import com.example.multiuserrealtimefragment.controller.ProjectController;
import com.example.multiuserrealtimefragment.model.DatabaseCallback;
import com.example.multiuserrealtimefragment.model.ModelProject;
import com.example.multiuserrealtimefragment.view.customer_ui.CustomerPaymentConfirmation;

public class ProjectDetail extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_project_detail_customer);

        ModelProject project = getIntent().getParcelableExtra("project");
        if (project == null) {
            finish();
        } else {
            ProjectController projectController = new ProjectController();

            TextView txtTypeSizeStyle = findViewById(R.id.TVCard_Type_Size_Style);
            TextView txtCity = findViewById(R.id.TVCard_city);
            TextView txtDetailsHead = findViewById(R.id.TVCard_details_head);
            TextView txtDetails = findViewById(R.id.TVCard_details);
            TextView txtAddress = findViewById(R.id.TVCard_address);
            TextView txtPriceHead = findViewById(R.id.TVCard_price_head);
            TextView txtPrice = findViewById(R.id.TVCard_price);
            TextView txtStatus = findViewById(R.id.TVCard_status);
            TextView txtImage = findViewById(R.id.TVCard_image_head);
            TextView txtPaymentHead = findViewById(R.id.TVCard_payment_head);
            TextView txtVendorName = findViewById(R.id.TVCard_vendor);
            TextView txtVendorNameHead = findViewById(R.id.TVCard_vendor_head);
            LinearLayout layoutPayment = findViewById(R.id.TVCard_payment_layout);
            AppCompatButton btnPayment = findViewById(R.id.BTN_Payment);
            AppCompatButton btnDone = findViewById(R.id.BTN_Project_Done);
            ImageView imageTransfer = findViewById(R.id.Image_Bukti_Transfer);

            txtTypeSizeStyle.setText(project.getDataType() + " " + project.getDataSize() + " " + project.getDataStyle() + " Style");
            txtCity.setText(project.getDataCity());
            txtDetails.setText(project.getDataDetails());
            txtPrice.setText("Rp. " + project.getDataPrice());
            txtAddress.setText(project.getDataAddress());

            if (project.getDataPrice() == 0) {
                txtPriceHead.setVisibility(View.GONE);
                txtPrice.setVisibility(View.GONE);
            }

            String role = PreferencesController.getDataAs(this);
            if (role.equals("customer")) {
                if (project.getDataStatus().equals("Taken")) {
                    btnPayment.setVisibility(View.VISIBLE);
                } else if (project.getDataStatus().equals("Paid")) {
                    btnDone.setVisibility(View.VISIBLE);
                }
            }

            if (!project.getDataVendorName().isEmpty()) {
                txtVendorName.setText(project.getDataVendorName());
            } else {
                txtVendorName.setVisibility(View.GONE);
                txtVendorNameHead.setVisibility(View.GONE);
            }

            if (project.getDataDetails().isEmpty()) {
                txtDetailsHead.setVisibility(View.GONE);
                txtDetails.setVisibility(View.GONE);
            }

            String projectStatus = project.getDataStatus();
            if (projectStatus.equals("Waiting")) {
                txtStatus.setText("Mencari Vendor");
            } else if (projectStatus.equals("Taken")) {
                txtStatus.setText("Diambil oleh " + project.getDataVendorName());
            } else if (projectStatus.equals("Paid")) {
                txtStatus.setText("Telah dibayar oleh Customer");
            } else if (projectStatus.equals("Done")) {
                txtStatus.setText("Selesai");
            }

            if (!projectStatus.equals("Taken")) {
                txtPaymentHead.setVisibility(View.GONE);
                layoutPayment.setVisibility(View.GONE);
            }

            if (projectStatus.equals("Paid") || projectStatus.equals("Done")) {
                txtImage.setVisibility(View.VISIBLE);
                imageTransfer.setVisibility(View.VISIBLE);

                Glide.with(this)
                        .load(project.getDataTransfer())
                        .into(imageTransfer);
            }

            btnPayment.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(ProjectDetail.this, CustomerPaymentConfirmation.class);
                    intent.putExtra("projectId", project.getId());
                    startActivity(intent);
                }
            });

            btnDone.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    projectController.doneProject(project.getId(), new DatabaseCallback() {
                        @Override
                        public void onSuccess() {
                            btnPayment.setVisibility(View.GONE);
                            btnDone.setVisibility(View.GONE);
                            txtStatus.setText("Selesai");
                            Toast.makeText(ProjectDetail.this, "Berhasil menyelesaikan", Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void onFailed() {
                            Toast.makeText(ProjectDetail.this, "Gagal menyelesaikan", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            });
        }
    }
}