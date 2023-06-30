package com.example.multiuserrealtimefragment.view.vendor_ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.multiuserrealtimefragment.NotificationUtils;
import com.example.multiuserrealtimefragment.R;
import com.example.multiuserrealtimefragment.model.ModelProject;
import com.example.multiuserrealtimefragment.view.customer_ui.ProjectAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class VendorProjectFragment extends Fragment {

    ProjectAdapter adapter = new ProjectAdapter();
    FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
    NotificationUtils notificationUtils;

    RecyclerView recyclerView;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.vendor_fragment_project, container, false);
        recyclerView = root.findViewById(R.id.RV_ProjectVendor);
        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        notificationUtils = new NotificationUtils(getContext().getApplicationContext());

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);

        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Projects");
        ArrayList<ModelProject> projects = new ArrayList<>();
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    ModelProject project = dataSnapshot.getValue(ModelProject.class);
                    project.setId(dataSnapshot.getKey());
                    if (firebaseAuth.getUid().equals(project.getDataVendorId()) && (project.getDataStatus().equals("Paid") || project.getDataStatus().equals("Done"))) {
                        projects.add(project);
                    }
                }

                if (!adapter.getProjects().isEmpty()) {
                    ArrayList<ModelProject> newData = new ArrayList<>(projects);
                    for (ModelProject dataAdapter : adapter.getProjects()) {
                        newData.removeIf(
                                e -> dataAdapter.getId().equals(e.getId())
                                        && dataAdapter.getDataStatus().equals(e.getDataStatus())
                        );
                    }

                    if (!newData.isEmpty()) {
                        // Data baru dimunculkan notifikasi
                        String projectStatus = newData.get(0).getDataStatus();
                        showNotification(projectStatus);
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

    void showNotification(String projectStatus) {
        if (projectStatus.equals("Paid")) {
            notificationUtils.showProjectPaid();
        } else if (projectStatus.equals("Done")) {
            notificationUtils.showProjectDone();
        }
    }
}