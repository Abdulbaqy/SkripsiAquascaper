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
import com.example.multiuserrealtimefragment.view.Login;
import com.google.firebase.auth.FirebaseAuth;

public class SetUp extends AppCompatActivity {

    private ProjectController projectController = new ProjectController();

    private EditText uploadSize, uploadAddress, uploadCity;
    private Button submitSetupBtn;
    private RadioButton RBsetupNatural, RBsetupIwagumi, RBsetupDutch;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_up);

        FirebaseAuth auth = FirebaseAuth.getInstance();
        uploadSize = findViewById(R.id.ETSetup_size);
        uploadAddress = findViewById(R.id.ETSetup_adress);
        uploadCity = findViewById(R.id.ETSetup_city);
        RBsetupNatural = findViewById(R.id.radioButtonNatural);
        RBsetupDutch = findViewById(R.id.radioButtonDutch);
        RBsetupIwagumi = findViewById(R.id.radioButtonIwagumi);
        submitSetupBtn = findViewById(R.id.submitBtnSetup);

        RBsetupIwagumi.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (compoundButton.isChecked()) {
                    RBsetupDutch.setChecked(false);
                    RBsetupNatural.setChecked(false);
                }
            }
        });

        RBsetupDutch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (compoundButton.isChecked()) {
                    RBsetupIwagumi.setChecked(false);
                    RBsetupNatural.setChecked(false);
                }
            }
        });

        RBsetupNatural.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (compoundButton.isChecked()) {
                    RBsetupIwagumi.setChecked(false);
                    RBsetupDutch.setChecked(false);
                }
            }
        });

        submitSetupBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String getDataSize = uploadSize.getText().toString();
                String getDataAddress = uploadAddress.getText().toString();
                String getDataCity = uploadCity.getText().toString();
                String style = "";
                if (RBsetupNatural.isChecked()) {
                    style = "Natural";
                } else if (RBsetupDutch.isChecked()) {
                    style = "Dutch";
                } else if (RBsetupIwagumi.isChecked()) {
                    style = "Iwagumi";
                }

                if(getDataSize.isEmpty()){
                    uploadSize.setError("Input Size!");
                }else if(getDataAddress.isEmpty()) {
                    uploadAddress.setError("Input Address!");
                }else if(getDataAddress.length() < 20) {
                    uploadAddress.setError("Address must be longer than 20 characters!");
                }else if(getDataCity.isEmpty()) {
                    uploadCity.setError("Input City!");
                }else if (style.isEmpty()) {
                    Toast.makeText(SetUp.this, "Choose Style!", Toast.LENGTH_SHORT).show();
                }else{
                    ModelProject project = new ModelProject(
                            getDataSize,
                            getDataAddress,
                            getDataCity,
                            style,
                            auth.getUid(),
                            PreferencesController.getDataName(SetUp.this),
                            "SetUp",
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
                            Toast.makeText(SetUp.this, "Data saved", Toast.LENGTH_SHORT).show();
                            finish();
                        }
                        @Override
                        public void onFailed() {
                            Toast.makeText(SetUp.this, "Data failed to save", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });
    }
}