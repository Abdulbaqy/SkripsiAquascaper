package com.example.multiuserrealtimefragment.view.customer_ui;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.multiuserrealtimefragment.model.ModelProject;
import com.example.multiuserrealtimefragment.R;
import com.example.multiuserrealtimefragment.view.ProjectDetail;

import java.util.ArrayList;

public class ProjectAdapter extends RecyclerView.Adapter<ProjectAdapter.ViewHolder> {

    private ArrayList<ModelProject> projects = new ArrayList<>();

    public void setProjects(ArrayList<ModelProject> modelProjects) {
        projects.clear();
        projects.addAll(modelProjects);
    }

    public ArrayList<ModelProject> getProjects() {
        return projects;
    }

    @NonNull
    @Override
    public ProjectAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(
                LayoutInflater.from(parent.getContext()).inflate(R.layout.card_project_customer, parent, false)
        );
    }

    @Override
    public void onBindViewHolder(@NonNull ProjectAdapter.ViewHolder holder, int position) {
        ModelProject project = projects.get(position);
        holder.txtTypeSizeStyle.setText(project.getDataType() + " " + project.getDataSize() + " " + project.getDataStyle() + " Style");
        holder.txtCity.setText(project.getDataCity());
        if (project.getDataStatus().equals("Waiting")) {
            holder.txtPrice.setText("Mencari vendor");
        } else {
            holder.txtPrice.setText("Rp. " + project.getDataPrice());
        }

        holder.txtDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(holder.itemView.getContext(), ProjectDetail.class);
                intent.putExtra("project", project);
                holder.itemView.getContext().startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return projects.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView txtTypeSizeStyle, txtCity, txtPrice, txtDetails;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            txtTypeSizeStyle = itemView.findViewById(R.id.TVCard_size);
            txtCity = itemView.findViewById(R.id.TVCard_city);
            txtPrice = itemView.findViewById(R.id.TVCard_price);
            txtDetails = itemView.findViewById(R.id.TVCard_details);
        }
    }
}
