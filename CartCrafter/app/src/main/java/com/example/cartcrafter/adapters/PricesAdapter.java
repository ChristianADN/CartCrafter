package com.example.cartcrafter.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cartcrafter.R;
import com.example.cartcrafter.models.ProductShopModel;
import com.example.cartcrafter.models.TypeModel;
import com.google.android.material.card.MaterialCardView;

import java.util.ArrayList;
import java.util.List;

public class PricesAdapter extends RecyclerView.Adapter<PricesAdapter.MyViewHolder> {
    final OnItemClickListener listener;
    private LayoutInflater layoutInflater;

    private List<ProductShopModel> productShops;
    private Context context;
    TextView nameMarket, priceItem;
    Button addToCart;

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = layoutInflater.from(parent.getContext())
                .inflate(R.layout.item_list_price, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.bindData(productShops.get(position));
    }

    @Override
    public int getItemCount() {
        return productShops.size();
    }

    public interface OnItemClickListener {
        void onItemClick(ProductShopModel item);
    }
    public PricesAdapter(List<ProductShopModel> itemList, Context context, OnItemClickListener onItemClickListener) {
        this.layoutInflater = LayoutInflater.from(context);
        this.context = context;
        if (itemList != null)
            this.productShops = itemList;
        else
            this.productShops = new ArrayList<ProductShopModel>();
        this.listener = onItemClickListener;
    }
    public List<ProductShopModel> getData() {
        return productShops;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        MaterialCardView materialCardView;

        MyViewHolder(View itemView) {
            super(itemView);
            nameMarket = itemView.findViewById(R.id.name_market_item);
            priceItem = itemView.findViewById(R.id.price_item);
            addToCart = itemView.findViewById(R.id.addToListButton);
        }

        void bindData(final ProductShopModel item) {
            nameMarket.setText(item.getShop().getShopName());
            priceItem.setText(item.getPrice()+"€");
            addToCart.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listener.onItemClick(item);
                }
            });
        }
    }
}

