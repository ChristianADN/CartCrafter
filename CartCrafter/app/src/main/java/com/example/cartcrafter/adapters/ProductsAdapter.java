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
import com.example.cartcrafter.models.ProductModel;
import com.google.android.material.card.MaterialCardView;

import java.util.ArrayList;
import java.util.List;

public class ProductsAdapter extends RecyclerView.Adapter<ProductsAdapter.MyViewHolder> {
    final OnItemClickListener listener;
    private LayoutInflater layoutInflater;

    private List<ProductModel> products;
    private Context context;
    TextView name, description;

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = layoutInflater.from(parent.getContext())
                .inflate(R.layout.item_list_product, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.bindData(products.get(position));
    }

    @Override
    public int getItemCount() {
        return products.size();
    }

    public interface OnItemClickListener {
        void onItemClick(ProductModel item);
    }
    public List<ProductModel> getData() {
        return products;
    }

    public ProductsAdapter(List<ProductModel> itemList, Context context, OnItemClickListener onItemClickListener) {
        this.layoutInflater = LayoutInflater.from(context);
        this.context = context;
        if (itemList != null)
            this.products = itemList;
        else
            this.products = new ArrayList<ProductModel>();
        this.listener = onItemClickListener;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        MaterialCardView materialCardView;

        MyViewHolder(View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.name_product_item);
            description = itemView.findViewById(R.id.description_product_item);
        }

        void bindData(final ProductModel item) {
            name.setText((item.getProductName()));
            description.setText(item.getProductDescription());
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listener.onItemClick(item);
                }
            });
        }
    }
}

