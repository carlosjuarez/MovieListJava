package com.juvcarl.batch.mcs.movielistjava.adapter;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;

import com.juvcarl.batch.mcs.movielistjava.R;
import com.juvcarl.batch.mcs.movielistjava.databinding.RecyclerviewItemBinding;
import com.juvcarl.batch.mcs.movielistjava.repository.model.Datum;

import java.util.ArrayList;
import java.util.List;

public class RecyclerViewItemAdapter extends RecyclerView.Adapter<RecyclerViewItemAdapter.ViewHolder> implements Filterable {

    private List<Datum> filteredItems;
    private List<Datum> items;
    private Context context;

    public RecyclerViewItemAdapter(List<Datum> items, Context context){
        this.context = context;
        this.items = items;
        this.filteredItems = items;
    }

    public void addData(List<Datum> data){
        this.items = data;
        this.filteredItems = items;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        RecyclerviewItemBinding binding = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.recyclerview_item,parent,false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Datum datum = filteredItems.get(position);
        holder.binding.setData(datum);
    }

    @Override
    public int getItemCount() {
        return filteredItems.size();
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String charString = charSequence.toString();
                if (charString.isEmpty()) {
                    filteredItems = items;
                } else {
                    List<Datum> listFilter = new ArrayList<>();
                    for (Datum row : items) {

                        // name match condition. this might differ depending on your requirement
                        // here we are looking for name or phone number match
                        if (row.getGenre().toLowerCase().contains(charString.toLowerCase()) || row.getTitle().toLowerCase().contains(charString.toLowerCase())) {
                            listFilter.add(row);
                        }
                    }

                    filteredItems = listFilter;
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = filteredItems;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                filteredItems = (ArrayList<Datum>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        final RecyclerviewItemBinding binding;

        public ViewHolder(RecyclerviewItemBinding binding){
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
