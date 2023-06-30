package com.example.multiuserrealtimefragment.view;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.multiuserrealtimefragment.R;
import com.example.multiuserrealtimefragment.model.ModelChat;
import com.example.multiuserrealtimefragment.model.ModelChatMessage;

import java.util.ArrayList;

public class ChatAdapter extends RecyclerView.Adapter<ChatAdapter.ViewHolder> {

    String userId;

    public ChatAdapter() {

    }

    public ChatAdapter(String userId) {
        this.userId = userId;
    }

    private ArrayList<ModelChatMessage> chats = new ArrayList<>();

    public void setChats(ArrayList<ModelChatMessage> chats) {
        this.chats.clear();
        this.chats.addAll(chats);
    }

    @NonNull
    @Override
    public ChatAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(
                LayoutInflater.from(parent.getContext()).inflate(R.layout.card_chat, parent, false)
        );
    }

    @Override
    public void onBindViewHolder(@NonNull ChatAdapter.ViewHolder holder, int position) {
        ModelChatMessage chat = chats.get(position);
        if (chat.getFromUserId().equals(userId)) {
            holder.cardUser.setVisibility(View.VISIBLE);
            holder.txtMessageUser.setText(chat.getMessage());
        } else {
            holder.cardTarget.setVisibility(View.VISIBLE);
            holder.txtMessageTarget.setText(chat.getMessage());
        }
    }

    @Override
    public int getItemCount() {
        return chats.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView txtMessageTarget, txtMessageUser;
        CardView cardUser, cardTarget;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            txtMessageUser = itemView.findViewById(R.id.TVCard_message_user);
            txtMessageTarget = itemView.findViewById(R.id.TVCard_message_target);
            cardTarget = itemView.findViewById(R.id.Card_Target);
            cardUser = itemView.findViewById(R.id.Card_User);
        }
    }
}
