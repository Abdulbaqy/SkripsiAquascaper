package com.example.multiuserrealtimefragment.view.customer_ui;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import com.example.multiuserrealtimefragment.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class CustomerHomeFragment extends Fragment {

    TextView customerHomeName;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    FirebaseAuth auth = FirebaseAuth.getInstance();

    String name;

    @SuppressLint("MissingInflatedId")
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.customer_fragment_home, container, false);

        customerHomeName = root.findViewById(R.id.home_customerName);

        CardView setUpbtn = root.findViewById(R.id.SetupBtn);
        CardView maintenancebtn = root.findViewById(R.id.MaintenanceBtn);


        DatabaseReference reference = database.getReference().child("Users").child(auth.getUid());

        reference.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    name = snapshot.child("FullName").getValue().toString();
                    String name = snapshot.child("FullName").getValue(String.class);
                    customerHomeName.setText("Welcome " + name + "!");
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        setUpbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(requireActivity(), SetUp.class);
                startActivity(intent);
            }
        });

        maintenancebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(requireActivity(), Maintenance.class);
                startActivity(intent);
            }
        });
        return root;
    }
}