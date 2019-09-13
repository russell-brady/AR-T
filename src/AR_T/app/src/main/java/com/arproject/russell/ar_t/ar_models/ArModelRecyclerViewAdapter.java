package com.arproject.russell.ar_t.ar_models;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.arproject.russell.ar_t.R;
import com.arproject.russell.ar_t.models.ARModel;

import java.util.ArrayList;

public class ArModelRecyclerViewAdapter extends RecyclerView.Adapter<ArModelRecyclerViewAdapter.ViewHolder> {

    private ArrayList<ARModel> arModels;
    private ItemClickListener itemClickListener;

    ArModelRecyclerViewAdapter(ArrayList<ARModel> arModels, ItemClickListener itemClickListener) {
        this.arModels = arModels;
        this.itemClickListener = itemClickListener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_armodel, parent, false);
        return new ViewHolder(view, itemClickListener);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        ARModel model = arModels.get(position);
        holder.setModelName(model.getModelName());
        holder.setModelDesc(model.getModelDesc());
    }

    @Override
    public int getItemCount() {
        return arModels.size();
    }

    public void setList(ArrayList<ARModel> arModels) {
        this.arModels = arModels;
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        void setModelName(String modelName) {
            this.modelName.setText(modelName);
        }

        void setModelDesc(String modelDesc) {
            this.modelDesc.setText(modelDesc);
        }

        private TextView modelName;
        private TextView modelDesc;
        private CardView arModelItem;

        ViewHolder(View itemView, ItemClickListener itemClickListener) {
            super(itemView);
            modelName = itemView.findViewById(R.id.modelName);
            modelDesc = itemView.findViewById(R.id.modelDesc);
            arModelItem = itemView.findViewById(R.id.arModelItem);

            arModelItem.setOnClickListener(view -> itemClickListener.onItemClicked(getAdapterPosition()));
        }
    }
}
