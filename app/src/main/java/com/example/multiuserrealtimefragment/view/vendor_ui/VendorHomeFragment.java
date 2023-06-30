package com.example.multiuserrealtimefragment.view.vendor_ui;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.multiuserrealtimefragment.R;
import com.example.multiuserrealtimefragment.model.ModelProject;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class VendorHomeFragment extends Fragment {

    TextView vendorHomeName;
    HomeAdapter adapter = new HomeAdapter();
    String name;

    RecyclerView recyclerView;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    FirebaseAuth auth = FirebaseAuth.getInstance();


    @SuppressLint("MissingInflatedId")
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.vendor_fragment_home, container, false);
        recyclerView = root.findViewById(R.id.RV_ProjectVendor);
        vendorHomeName = root.findViewById(R.id.home_vendorName);


        DatabaseReference reference = database.getReference().child("Users").child(auth.getUid());

        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                name = snapshot.child("FullName").getValue().toString();
                String name = snapshot.child("FullName").getValue(String.class);
                vendorHomeName.setText("Welcome " + name + "!");
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);

        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Projects");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                ArrayList<ModelProject> projects = new ArrayList<>();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    ModelProject project = dataSnapshot.getValue(ModelProject.class);
                    project.setId(dataSnapshot.getKey());
                    if (project.getDataStatus().equals("Waiting")) {
                        projects.add(project);
                    }
                }
                recyclerView.removeAllViews();
                adapter.setProjects(projects);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

}