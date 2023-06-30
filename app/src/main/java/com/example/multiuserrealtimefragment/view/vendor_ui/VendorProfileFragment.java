package com.example.multiuserrealtimefragment.view.vendor_ui;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.fragment.app.Fragment;

import com.example.multiuserrealtimefragment.view.Login;
import com.example.multiuserrealtimefragment.R;
import com.example.multiuserrealtimefragment.controller.PreferencesController;
import com.google.android.material.switchmaterial.SwitchMaterial;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class VendorProfileFragment extends Fragment {

    TextView vendorProfileName, vendorProfileEmail, vendorProfileNumber, vendorProfileAs;
    Button logout;
    FirebaseDatabase databaseVendor;
    FirebaseAuth auth;
    String emailVendor, password, nameVendor, numberVendor, asVendor;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    Boolean nightMode;
    SwitchMaterial darkmode_switch;


    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.vendor_fragment_profile, container, false);

        sharedPreferences = getActivity().getSharedPreferences("MODE", Context.MODE_PRIVATE);
        nightMode = sharedPreferences.getBoolean("night", false);
        auth = FirebaseAuth.getInstance();
        databaseVendor = FirebaseDatabase.getInstance();
        vendorProfileName = root.findViewById(R.id.profile_vendor_name);
        vendorProfileEmail = root.findViewById(R.id.profile_vendor_email);
        vendorProfileNumber = root.findViewById(R.id.profile_vendor_number);
        vendorProfileAs = root.findViewById(R.id.profile_vendor_as);
        logout = root.findViewById(R.id.logoutBtn);
        darkmode_switch = root.findViewById(R.id.switch_dark_vendor);

        showUserData();

        darkmode_switch.setChecked(nightMode);

        darkmode_switch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                editor = sharedPreferences.edit();
                editor.putBoolean("night", isChecked);
                editor.apply();

                updateTheme(isChecked);
            }
        });

        DatabaseReference referenceVendor = databaseVendor.getReference().child("Users").child(auth.getUid());

        referenceVendor.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                nameVendor = snapshot.child("FullName").getValue().toString();
                numberVendor = snapshot.child("PhoneNumber").getValue().toString();
                emailVendor = snapshot.child("UserEmail").getValue().toString();
                asVendor = snapshot.child("as").getValue().toString();

                vendorProfileName.setText(nameVendor);
                vendorProfileNumber.setText(numberVendor);
                vendorProfileEmail.setText(emailVendor);
                vendorProfileAs.setText(asVendor);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getActivity());

                alertDialogBuilder.setTitle("Confirm Logout");
                alertDialogBuilder.setMessage("Are you sure you want to log out?");
                alertDialogBuilder.setCancelable(false);

                alertDialogBuilder.setPositiveButton("Yes", new DialogInterface.OnClickListener(){
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        PreferencesController.clearData(getActivity());
                        auth.signOut();

                        Intent intent = new Intent(getActivity(), Login.class);
                        startActivity(intent);

                        requireActivity().finish();
                    }
                });
                alertDialogBuilder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                });

                AlertDialog alertDialog = alertDialogBuilder.create();
                alertDialog.show();
            }
        });
        return root;
    }
    private void updateTheme(boolean nightMode) {
        if (nightMode) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        }

        requireActivity().recreate();
    }
    public void showUserData(){

        Bundle bundle = getArguments();
        if (bundle != null) {
            String nameUser = bundle.getString("FullName");
            String emailUser = bundle.getString("UserEmail");
            String numberUser = bundle.getString("PhoneNumber");
            String asUser = bundle.getString("as");

            vendorProfileName.setText(nameUser);
            vendorProfileEmail.setText(emailUser);
            vendorProfileNumber.setText(numberUser);
            vendorProfileAs.setText(asUser);
        }
    }
}