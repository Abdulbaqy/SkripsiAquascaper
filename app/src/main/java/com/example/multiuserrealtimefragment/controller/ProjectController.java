package com.example.multiuserrealtimefragment.controller;

import androidx.annotation.NonNull;

import com.example.multiuserrealtimefragment.model.DatabaseCallback;
import com.example.multiuserrealtimefragment.model.ModelChat;
import com.example.multiuserrealtimefragment.model.ModelProject;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class ProjectController {

    private DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Projects");

    public void addProject(ModelProject project, DatabaseCallback callback) {
        DatabaseReference projectReference = reference.push();

        DatabaseReference chatReference = FirebaseDatabase.getInstance().getReference("Chats").child(projectReference.getKey());
        ModelChat chat = new ModelChat(
                project.getId(),
                project.getDataType(),
                project.getDataSize(),
                project.getDataStyle(),
                project.getDataCity()
        );
        chatReference.setValue(chat);

        projectReference.setValue(project).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                callback.onSuccess();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                callback.onFailed();
            }
        });
    }

    public void takeProject(String projectId, ModelProject project, DatabaseCallback callback) {
        HashMap<String, Object> updates = new HashMap<>();
        updates.put("dataDetails", project.getDataDetails());
        updates.put("dataVendorId", project.getDataVendorId());
        updates.put("dataVendorName", project.getDataVendorName());
        updates.put("dataPrice", project.getDataPrice());
        updates.put("dataStatus", project.getDataStatus());

        reference.child(projectId)
                .updateChildren(updates)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                callback.onSuccess();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                callback.onFailed();
            }
        });
    }

    public void paidProject(String projectId, String imagePath) {
        reference.child(projectId).child("dataStatus").setValue("Paid");
        reference.child(projectId).child("dataTransfer").setValue(imagePath);
    }

    public void doneProject(String projectId, DatabaseCallback callback) {
        reference.child(projectId)
                .child("dataStatus").setValue("Done")
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        callback.onSuccess();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        callback.onFailed();
                    }
                });
    }
}
