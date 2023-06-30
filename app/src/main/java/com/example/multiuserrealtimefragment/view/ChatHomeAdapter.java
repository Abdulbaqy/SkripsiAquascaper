package com.example.multiuserrealtimefragment.view;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.multiuserrealtimefragment.R;
import com.example.multiuserrealtimefragment.model.ModelChat;
import com.example.multiuserrealtimefragment.model.ModelProject;

import java.util.ArrayList;

public class ChatHomeAdapter extends RecyclerView.Adapter<ChatHomeAdapter.ViewHolder> {

    String role;

    public void setRole(String role) {
        this.role = role;
    }

    public ChatHomeAdapter() {

    }

    private ArrayList<ModelChat> chats = new ArrayList<>();

    public void setChats(ArrayList<ModelChat> chats) {
        this.chats.clear();
        this.chats.addAll(chats);
    }

    @NonNull
    @Override
    public ChatHomeAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(
                LayoutInflater.from(parent.getContext()).inflate(R.layout.card_chat_home, parent, false)
        );
    }

    @Override
    public void onBindViewHolder(@NonNull ChatHomeAdapter.ViewHolder holder, int position) {
        ModelChat chat = chats.get(position);
        holder.txtTitle.setText(chat.getProjectType() + " " + chat.getProjectSize() + " " + chat.getProjectStyle() + " Style " + chat.getProjectCity());
        holder.cardVendorSetup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(holder.itemView.getContext(), ChatDetailActivity.class);
                intent.putExtra("projectId", chat.getProjectId());
                intent.putExtra("projectName", chat.getProjectType() + " " + chat.getProjectSize() + " " + chat.getProjectStyle() + " Style");
                if (role.equals("vendor")) {
                    intent.putExtra("targetId", chat.getCustomerId());
                } else {
                    intent.putExtra("targetId", chat.getVendorId());
                }
                holder.itemView.getContext().startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return chats.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView txtTitle;
        CardView cardVendorSetup;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            txtTitle = itemView.findViewById(R.id.TVCard_size);
            cardVendorSetup = itemView.findViewById(R.id.cardVendorSetup);
        }
    }
}
