package com.example.project_patt;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class JobAdapter1 extends RecyclerView.Adapter<JobAdapter1.ViewHolder> {

    private ArrayList<Jobdetails> jobdetailslist; // Initialize this list

    private Context context;

    public JobAdapter1(Context context, ArrayList<Jobdetails> jobdetailslist) {
        this.context = context;
        this.jobdetailslist = jobdetailslist;
         // Initialize jobdetailslist
    }

    @NonNull
    @Override
    public JobAdapter1.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.viewholder_jobs1, parent, false);
        return new JobAdapter1.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull JobAdapter1.ViewHolder holder, int position) {
        Jobdetails category = jobdetailslist.get(position);

        holder.categoryName1.setText(category.getJob1());
        holder.categorySalary.setText(category.getSal());
        holder.categoryComname.setText(category.getcom());
        holder.categoryLocation.setText(category.getLoc());


    }

    @Override
    public int getItemCount() {
        return jobdetailslist.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView categoryName1;
        public TextView categorySalary;
        public TextView categoryComname;
        public TextView categoryLocation;
        public LinearLayout card; // Assuming card is a CardView

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            categoryName1 = itemView.findViewById(R.id.jobname1);
            categorySalary = itemView.findViewById(R.id.salary);
            categoryComname = itemView.findViewById(R.id.comname);
            categoryLocation = itemView.findViewById(R.id.location);
            card = itemView.findViewById(R.id.l1); // Assign your card view id
        }
    }
}
