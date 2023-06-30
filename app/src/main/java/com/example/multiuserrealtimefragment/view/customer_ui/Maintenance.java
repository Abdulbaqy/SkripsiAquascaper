package com.example.multiuserrealtimefragment.view.customer_ui;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import com.example.multiuserrealtimefragment.LoadingAlert;
import com.example.multiuserrealtimefragment.model.DatabaseCallback;
import com.example.multiuserrealtimefragment.model.ModelProject;
import com.example.multiuserrealtimefragment.controller.ProjectController;
import com.example.multiuserrealtimefragment.R;
import com.example.multiuserrealtimefragment.controller.PreferencesController;
import com.google.firebase.auth.FirebaseAuth;

public class Maintenance extends AppCompatActivity {

    private ProjectController projectController = new ProjectController();

    private EditText uploadMaintenanceSize, uploadMaintenanceAddress, uploadMaintenanceCity;
    private Button submitMaintenanceBtn;
    private RadioButton RBlow, RBmid, RBhigh;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maintenance);

        FirebaseAuth auth = FirebaseAuth.getInstance();
        uploadMaintenanceSize = findViewById(R.id.ETMaintenance_size);
        uploadMaintenanceAddress = findViewById(R.id.ETMaintenance_address);
        uploadMaintenanceCity = findViewById(R.id.ETMaintenance_city);
        RBlow = findViewById(R.id.radioButtonLow);
        RBmid = findViewById(R.id.radioButtonMid);
        RBhigh = findViewById(R.id.radioButtonHigh);
        submitMaintenanceBtn = findViewById(R.id.submitBtnMaintenance);

        RBlow.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (compoundButton.isChecked()) {
                    RBmid.setChecked(false);
                    RBhigh.setChecked(false);
                }
            }
        });

        RBmid.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (compoundButton.isChecked()) {
                    RBlow.setChecked(false);
                    RBhigh.setChecked(false);
                }
            }
        });

        RBhigh.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (compoundButton.isChecked()) {
                    RBlow.setChecked(false);
                    RBmid.setChecked(false);
                }
            }
        });

        submitMaintenanceBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String getDataSizeMaintenance = uploadMaintenanceSize.getText().toString();
                String getDataAddressMaintenance = uploadMaintenanceAddress.getText().toString();
                String getDataCityMaintenance = uploadMaintenanceCity.getText().toString();
                String style = "";
                if (RBhigh.isChecked()) {
                    style = "High";
                } else if (RBmid.isChecked()) {
                    style = "Mid";
                } else if (RBlow.isChecked()) {
                    style = "Low";
                }

                if (getDataSizeMaintenance.isEmpty()) {
                    uploadMaintenanceSize.setError("Input Size!");
                } else if (getDataAddressMaintenance.isEmpty()) {
                    uploadMaintenanceAddress.setError("Input Address!");
                } else if (getDataCityMaintenance.isEmpty()) {
                    uploadMaintenanceCity.setError("Input City!");
                }else if(getDataAddressMaintenance.length() < 20) {
                    uploadMaintenanceAddress.setError("Address must be longer than 20 characters!");
                }else if (style.isEmpty()) {
                    Toast.makeText(Maintenance.this, "Choose package!", Toast.LENGTH_SHORT).show();
                } else {
                    ModelProject project = new ModelProject(
                            getDataSizeMaintenance,
                            getDataAddressMaintenance,
                            getDataCityMaintenance,
                            style,
                            auth.getUid(),
                            PreferencesController.getDataName(Maintenance.this),
                            "Maintenance",
                            "Waiting",
                            "",
                            "",
                            "",
                            0,
                            ""
                    );
                    projectController.addProject(project, new DatabaseCallback() {
                        @Override
                        public void onSuccess() {
                            Toast.makeText(Maintenance.this, "Data saved", Toast.LENGTH_SHORT).show();
                            finish();
                        }

                        @Override
                        public void onFailed() {
                            Toast.makeText(Maintenance.this, "Data failed to save", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });
    }
}