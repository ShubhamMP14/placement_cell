package Search;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.example.project_patt.Add_job;
import com.example.project_patt.R;
import com.example.project_patt.ShowDetailsFragment;

import java.util.ArrayList;
import java.util.List;

public class SerachResultAdapter extends RecyclerView.Adapter<SerachResultAdapter.ViewHolder> implements Filterable {

    private List<Add_job> itemList;
    private List<Add_job> itemListFull;

    public SerachResultAdapter(List<Add_job> itemList) {
        this.itemList = itemList;
        this.itemListFull = new ArrayList<>(itemList);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.viewholder_jobs1, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Add_job item = itemList.get(position);
        holder.bind(item);
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    @Override
    public Filter getFilter() {
        return itemFilter;
    }

    private Filter itemFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<Add_job> filteredList = new ArrayList<>();

            if (constraint == null || constraint.length() == 0) {
                filteredList.addAll(itemListFull);
            } else {
                String filterPattern = constraint.toString().toLowerCase().trim();

                for (Add_job item : itemListFull) {
                    if (item.getJobName().toLowerCase().contains(filterPattern)) {
                        filteredList.add(item);
                    }
                }
            }

            FilterResults results = new FilterResults();
            results.values = filteredList;
            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            itemList.clear();
            itemList.addAll((List<Add_job>) results.values);
            notifyDataSetChanged();
        }
    };

    public void updateList(List<Add_job> newList) {
        itemList.clear();
        itemList.addAll(newList);
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView jobNameTextView;
        TextView salary;
        TextView comname;
        TextView location;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            jobNameTextView = itemView.findViewById(R.id.jobname1);
            salary = itemView.findViewById(R.id.salary);
            comname = itemView.findViewById(R.id.comname);
            location = itemView.findViewById(R.id.location);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION && position < itemList.size()) {
                        Add_job selectedJob = itemList.get(position);
                        if (selectedJob != null) {
                            // Create a new instance of ShowDetailFragment with the selected job
                            ShowDetailsFragment showDetailFragment = ShowDetailsFragment.newInstance(selectedJob);

                            // Use FragmentTransaction to display the fragment
                            FragmentTransaction fragmentTransaction = ((AppCompatActivity) v.getContext()).getSupportFragmentManager().beginTransaction();
                            fragmentTransaction.replace(R.id.fragment_container, showDetailFragment); // Use your container view id
                            fragmentTransaction.addToBackStack(null); // Add to the back stack if needed
                            fragmentTransaction.commit();
                        }
                    }

                }
            });
        }

        public void bind(Add_job item) {
            jobNameTextView.setText(item.getJobName());
            salary.setText(item.getSalary());
            comname.setText(item.getCompanyName());
            location.setText(item.getLocation());
        }

    }
}
