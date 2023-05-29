package com.example.cartcrafter.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cartcrafter.R;
import com.example.cartcrafter.models.CategoryModel;
import com.google.android.material.card.MaterialCardView;

import java.util.ArrayList;
import java.util.List;

public class CategoriesAdapter extends RecyclerView.Adapter<CategoriesAdapter.MyViewHolder> {
    final OnItemClickListener listener;
    private LayoutInflater layoutInflater;

    private List<CategoryModel> categories;
    private Context context;
    TextView name, description;

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = layoutInflater.from(parent.getContext())
                .inflate(R.layout.item_list_category, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.bindData(categories.get(position));
    }

    @Override
    public int getItemCount() {
        return categories.size();
    }

    public interface OnItemClickListener {
        void onItemClick(CategoryModel item);
    }

    public CategoriesAdapter(List<CategoryModel> itemList, Context context, OnItemClickListener onItemClickListener) {
        this.layoutInflater = LayoutInflater.from(context);
        this.context = context;
        if (itemList != null)
            this.categories = itemList;
        else
            this.categories = new ArrayList<CategoryModel>();
        this.listener = onItemClickListener;
    }

    public List<CategoryModel> getData() {
        return categories;
    }
    public class MyViewHolder extends RecyclerView.ViewHolder {
        MaterialCardView materialCardView;

        MyViewHolder(View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.name_category_item);
            description = itemView.findViewById(R.id.description_category_item);
        }

        void bindData(final CategoryModel item) {
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

