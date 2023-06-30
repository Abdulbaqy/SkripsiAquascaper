package com.example.multiuserrealtimefragment.controller;

import androidx.annotation.NonNull;

import com.example.multiuserrealtimefragment.model.DatabaseCallback;
import com.example.multiuserrealtimefragment.model.ModelChat;
import com.example.multiuserrealtimefragment.model.ModelChatMessage;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.concurrent.TimeUnit;

public class ChatController {
    String projectId, customerId, vendorId;

    public ChatController(String projectId, String customerId, String vendorId) {
        this.projectId = projectId;
        this.customerId = customerId;
        this.vendorId = vendorId;
    }

    public void addChat(ModelChatMessage message, DatabaseCallback callback) {
        DatabaseReference reference = FirebaseDatabase.getInstance()
                .getReference("Chats")
                .child(projectId)
                .child("customers")
                .child(customerId)
                .child(vendorId);
        String timestamp = String.valueOf(TimeUnit.MILLISECONDS.toSeconds(System.currentTimeMillis()));
        reference.child(timestamp).setValue(message).addOnSuccessListener(new OnSuccessListener<Void>() {
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
