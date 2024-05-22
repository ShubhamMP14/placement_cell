package com.example.project_patt;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class JobaddAdapter extends RecyclerView.Adapter<JobaddAdapter.ViewHolder> {
    private final List<Add_job> productList;


    public JobaddAdapter(List<Add_job> productList) {
        this.productList = productList;
    }



    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.viewholder_jobs1, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Add_job job = productList.get(position);
        // Bind the data to the ViewHolder views

        holder.jobNameTextView.setText(job.getJobName());
        holder.companyNameTextView.setText(job.getCompanyName());
        holder.salaryTextView.setText(job.getSalary());
        holder.locationTextView.setText(job.getLocation());

      /* holder.addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get the selected product

                Product selectedProduct = productList.get(holder.getAdapterPosition());

                Log.d("ProductAdapter", "Button Clicked " + selectedProduct.getProduct_name());
                // Get the current selected items list from the ViewModel
                List<Product> currentSelectedItems = selectedItemsViewModel.getSelectedItems().getValue();

                if (currentSelectedItems == null) {
                    currentSelectedItems = new ArrayList<>();
                }
                // Add the selected product to the list (if not null)
                currentSelectedItems.add(selectedProduct);

                Log.d("total items", currentSelectedItems.toString());
                // Update the selected items list in the ViewModel
                selectedItemsViewModel.setSelectedItems(currentSelectedItems);

                Toast.makeText(v.getContext(), "Item added to cart " + currentSelectedItems, Toast.LENGTH_SHORT).show();
            }
        });
*/
       /*holder.addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = holder.getAdapterPosition();
                if (position != RecyclerView.NO_POSITION && position <productList.size()) {
                    Product selectedFood = productList.get(position);
                    if (selectedFood != null) {
                        // Create a new instance of ShowDetailFragment with the selected FoodDomain
                        ShowDetailFragmenttwo showDetailFragment = ShowDetailFragmenttwo.newInstance(selectedFood);

                        // Use FragmentTransaction to display the fragment
                        FragmentTransaction fragmentTransaction = ((AppCompatActivity) holder.itemView.getContext()).getSupportFragmentManager().beginTransaction();
                        fragmentTransaction.replace(R.id.fragment_container, showDetailFragment); // Use your container view id
                        fragmentTransaction.addToBackStack(null); // Add to the back stack if needed
                        fragmentTransaction.commit();
                    }
                }
            }
        });*/

    }

    @Override
    public int getItemCount() {
        return productList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView categoryName1;
        public TextView categorySalary;
        public TextView categoryComname;
        public TextView categoryLocation;
        public TextView jobNameTextView, companyNameTextView, salaryTextView, locationTextView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            categoryName1 = itemView.findViewById(R.id.jobname1);
            categorySalary = itemView.findViewById(R.id.salary);
            categoryComname = itemView.findViewById(R.id.comname);
            categoryLocation = itemView.findViewById(R.id.location);
            jobNameTextView = itemView.findViewById(R.id.jobname1);
            companyNameTextView = itemView.findViewById(R.id.salary);
            salaryTextView = itemView.findViewById(R.id.comname);
            locationTextView = itemView.findViewById(R.id.location);
        }
    }
}