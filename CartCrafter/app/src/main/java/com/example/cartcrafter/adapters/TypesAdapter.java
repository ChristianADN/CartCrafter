package com.example.cartcrafter.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cartcrafter.R;
import com.example.cartcrafter.models.CategoryModel;
import com.example.cartcrafter.models.TypeModel;
import com.google.android.material.card.MaterialCardView;

import java.util.ArrayList;
import java.util.List;

public class TypesAdapter extends RecyclerView.Adapter<TypesAdapter.MyViewHolder> {
    final OnItemClickListener listener;
    private LayoutInflater layoutInflater;

    private List<TypeModel> types;
    private Context context;
    TextView name, description;

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = layoutInflater.from(parent.getContext())
                .inflate(R.layout.item_list_type, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.bindData(types.get(position));
    }

    @Override
    public int getItemCount() {
        return types.size();
    }

    public interface OnItemClickListener {
        void onItemClick(TypeModel item);
    }

    public TypesAdapter(List<TypeModel> itemList, Context context, OnItemClickListener onItemClickListener) {
        this.layoutInflater = LayoutInflater.from(context);
        this.context = context;
        if (itemList != null)
            this.types = itemList;
        else
            this.types = new ArrayList<TypeModel>();
        this.listener = onItemClickListener;
    }
    public List<TypeModel> getData() {
        return types;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        MaterialCardView materialCardView;

        MyViewHolder(View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.name_type_item);
            description = itemView.findViewById(R.id.description_type_item);
        }

        void bindData(final TypeModel item) {
            name.setText((item.getName()));
            description.setText(item.getDescription());
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listener.onItemClick(item);
                }
            });
        }
    }
}

