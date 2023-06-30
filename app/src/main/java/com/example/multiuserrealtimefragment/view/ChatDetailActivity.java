package com.example.multiuserrealtimefragment.view;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.multiuserrealtimefragment.controller.PreferencesController;
import com.example.multiuserrealtimefragment.model.DatabaseCallback;
import com.example.multiuserrealtimefragment.R;
import com.example.multiuserrealtimefragment.controller.ChatController;
import com.example.multiuserrealtimefragment.model.ModelChat;
import com.example.multiuserrealtimefragment.model.ModelChatMessage;
import com.example.multiuserrealtimefragment.model.ModelProject;
import com.example.multiuserrealtimefragment.view.vendor_ui.Invoice;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ChatDetailActivity extends AppCompatActivity {

    ModelProject project;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vendor_chat_detail);

        FirebaseAuth auth = FirebaseAuth.getInstance();
        String userId = auth.getUid();

        String projectId = getIntent().getStringExtra("projectId");
        String projectName = getIntent().getStringExtra("projectName");
        String targetId = getIntent().getStringExtra("targetId");
        String role = PreferencesController.getDataAs(this);
        String vendorId, customerId;

        if (role.equals("vendor")) {
            vendorId = userId;
            customerId = targetId;
        } else {
            vendorId = targetId;
            customerId = userId;
        }

        TextView txtTargetName = findViewById(R.id.TVCard_username);
        TextView txtApprove = findViewById(R.id.TVCard_approve);

        ChatAdapter adapter = new ChatAdapter(userId);

        RecyclerView rvChat = findViewById(R.id.RV_Chat);
        rvChat.setLayoutManager(new LinearLayoutManager(this));
        rvChat.setAdapter(adapter);

        EditText etMessage = findViewById(R.id.ET_message);
        ImageView ivSend = findViewById(R.id.IV_Send);
        ivSend.setVisibility(View.GONE);
        ChatController chatController = new ChatController(projectId, customerId, vendorId);

        DatabaseReference databaseReferenceUser = FirebaseDatabase.getInstance()
                .getReference("Users")
                .child(targetId);
        databaseReferenceUser.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String name = snapshot.child("FullName").getValue(String.class);
                txtTargetName.setText(name + " - " + projectName);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {}
        });

        DatabaseReference databaseReferenceProject = FirebaseDatabase.getInstance()
                .getReference("Projects")
                .child(projectId);
        databaseReferenceProject.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                project = snapshot.getValue(ModelProject.class);
                project.setId(snapshot.getKey());
                ivSend.setVisibility(View.VISIBLE);

                if (role.equals("vendor") && project.getDataStatus().equals("Waiting")) {
                    txtApprove.setVisibility(View.VISIBLE);
                } else {
                    txtApprove.setVisibility(View.GONE);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {}
        });

        DatabaseReference databaseReferenceChat = FirebaseDatabase.getInstance()
                .getReference("Chats")
                .child(projectId)
                .child("customers")
                .child(customerId)
                .child(vendorId);
        databaseReferenceChat.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                ArrayList<ModelChatMessage> chats = new ArrayList<>();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    ModelChatMessage chat = dataSnapshot.getValue(ModelChatMessage.class);
                    chats.add(chat);
                }
                rvChat.removeAllViews();
                adapter.setChats(chats);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {}
        });

        ivSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String message = etMessage.getText().toString();
                if (message.isEmpty()) {
                    etMessage.setError("Harus diisi");
                } else {
                    if (role.equals("vendor")) {
                        FirebaseDatabase.getInstance()
                                .getReference("Chats")
                                .child(projectId)
                                .child("vendorId")
                                .setValue(userId);
                    }

                    ModelChatMessage chat = new ModelChatMessage(userId, message);
                    chatController.addChat(
                            chat,
                            new DatabaseCallback() {
                                @Override
                                public void onSuccess() {
                                    etMessage.setText("");
                                }

                                @Override
                                public void onFailed() {
                                    Toast.makeText(ChatDetailActivity.this, "Gagal mengirim pesan", Toast.LENGTH_SHORT).show();
                                }
                            }
                    );
                }
            }
        });

        if (role.equals("vendor")) {
            txtApprove.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(ChatDetailActivity.this, Invoice.class);
                    intent.putExtra("projectId", projectId);
                    intent.putExtra("vendorName", PreferencesController.getDataName(ChatDetailActivity.this));
                    startActivity(intent);
                }
            });
        }
    }
}