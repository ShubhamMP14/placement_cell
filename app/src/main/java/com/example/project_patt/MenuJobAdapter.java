package com.example.project_patt;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MenuJobAdapter extends RecyclerView.Adapter<MenuJobAdapter.ViewHolder> {
    private ArrayList<menujobmodel> categoryDomains;
    private Context context;


    public MenuJobAdapter(Context context, ArrayList<menujobmodel> categoryDomains) {
        this.context = context;
        this.categoryDomains = categoryDomains;
    }

    @NonNull
    @Override
    public MenuJobAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.viewholder_menu_jobs, parent, false);
        return new MenuJobAdapter.ViewHolder(view);
    }




    @Override
    public void onBindViewHolder(@NonNull MenuJobAdapter.ViewHolder holder, int position) {
        menujobmodel category = categoryDomains.get(holder.getAdapterPosition());

        holder.categoryName1.setText(category.getJob1());
        holder.categorySalary.setText(category.getSal());
        holder.categoryComname.setText(category.getcom());
        holder.categoryLocation.setText(category.getLoc());



    }

    @Override
    public int getItemCount() {
        return categoryDomains.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView categoryName1;
        public TextView categorySalary;
        public TextView categoryComname;
        public TextView categoryLocation;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            categoryName1 = itemView.findViewById(R.id.jobname1);
            categorySalary = itemView.findViewById(R.id.salary);
            categoryComname=itemView.findViewById(R.id.comname);
            categoryLocation=itemView.findViewById(R.id.location);
        }
    }

}

