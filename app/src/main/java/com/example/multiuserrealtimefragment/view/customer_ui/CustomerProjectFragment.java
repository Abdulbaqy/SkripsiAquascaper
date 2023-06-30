package com.example.multiuserrealtimefragment.view.customer_ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.multiuserrealtimefragment.model.ModelProject;
import com.example.multiuserrealtimefragment.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class CustomerProjectFragment extends Fragment {

    ProjectAdapter adapter = new ProjectAdapter();
    FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();

    RecyclerView recyclerView;
    DatabaseReference databaseReference;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.customer_fragment_project, container, false);
        recyclerView = root.findViewById(R.id.RV_ProjectVendor);
        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);

        databaseReference = FirebaseDatabase.getInstance().getReference("Projects");
        fetchProject();
    }

    @Override
    public void onStart() {
        super.onStart();
        fetchProject();
    }

    private void fetchProject() {
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                ArrayList<ModelProject> projects = new ArrayList<>();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    ModelProject project = dataSnapshot.getValue(ModelProject.class);
                    project.setId(dataSnapshot.getKey());
                    if (firebaseAuth.getUid().equals(project.getDataCustomerId())) {
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