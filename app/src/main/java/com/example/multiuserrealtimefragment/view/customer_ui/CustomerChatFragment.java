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

import com.example.multiuserrealtimefragment.R;
import com.example.multiuserrealtimefragment.controller.PreferencesController;
import com.example.multiuserrealtimefragment.model.ModelChat;
import com.example.multiuserrealtimefragment.view.ChatHomeAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class CustomerChatFragment extends Fragment {

    ChatHomeAdapter adapter = new ChatHomeAdapter();
    RecyclerView recyclerView;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.customer_fragment_chat, container, false);
        recyclerView = root.findViewById(R.id.RV_Chat);
        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        adapter.setRole(PreferencesController.getDataAs(getContext()));
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);

        String customerId = FirebaseAuth.getInstance().getUid();

        DatabaseReference databaseReferenceChats = FirebaseDatabase.getInstance().getReference("Chats");
        databaseReferenceChats.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                ArrayList<ModelChat> chats = new ArrayList<>();
                for (DataSnapshot projectSnapshot : snapshot.getChildren()) {
                    for (DataSnapshot customerSnapshot : projectSnapshot.child("customers").getChildren()) {
                        String customerIdFromDatabase = customerSnapshot.getKey();
                        if (customerIdFromDatabase.equals(customerId)) {
                            for (DataSnapshot vendorSnapshot : customerSnapshot.getChildren()) {
                                ModelChat chat = projectSnapshot.getValue(ModelChat.class);
                                chat.setProjectId(projectSnapshot.getKey());
                                chat.setCustomerId(customerIdFromDatabase);
                                chat.setVendorId(vendorSnapshot.getKey());
                                chats.add(chat);
                            }
                        }
                    }
                }
                recyclerView.removeAllViews();
                adapter.setChats(chats);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}