package com.example.multiuserrealtimefragment.view.vendor_ui;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.multiuserrealtimefragment.model.ModelProject;
import com.example.multiuserrealtimefragment.R;
import com.example.multiuserrealtimefragment.view.ChatDetailActivity;

import java.util.ArrayList;

public class HomeAdapter extends RecyclerView.Adapter<HomeAdapter.ViewHolder> {

    private ArrayList<ModelProject> projects = new ArrayList<>();

    public void setProjects(ArrayList<ModelProject> projects) {
        this.projects.clear();
        this.projects = projects;
    }

    @NonNull
    @Override
    public HomeAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(
                LayoutInflater.from(parent.getContext()).inflate(R.layout.card_vendor_setup, parent, false)
        );
    }

    @Override
    public void onBindViewHolder(@NonNull HomeAdapter.ViewHolder holder, int position) {
        ModelProject project = projects.get(position);
        holder.txtTypeSizeStyle.setText(project.getDataType() + " " + project.getDataSize() + " " + project.getDataStyle() + " Style");
        holder.txtCity.setText(project.getDataCity());

        holder.cardVendorSetup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(holder.itemView.getContext(), ChatDetailActivity.class);
                intent.putExtra("projectId", project.getId());
                intent.putExtra("projectName", project.getDataType() + " " + project.getDataSize() + " " + project.getDataStyle() + " Style");
                intent.putExtra("targetId", project.getDataCustomerId());
                holder.itemView.getContext().startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return projects.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView txtTypeSizeStyle, txtCity;
        CardView cardVendorSetup;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            txtTypeSizeStyle = itemView.findViewById(R.id.TVCard_size);
            txtCity = itemView.findViewById(R.id.TVCard_city);
            cardVendorSetup = itemView.findViewById(R.id.cardVendorSetup);
        }
    }
}
