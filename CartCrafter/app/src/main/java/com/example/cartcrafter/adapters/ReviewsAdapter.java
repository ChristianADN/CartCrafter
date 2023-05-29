package com.example.cartcrafter.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cartcrafter.R;
import com.example.cartcrafter.models.ProductReviewModel;
import com.example.cartcrafter.models.ProductShopModel;
import com.google.android.material.card.MaterialCardView;

import java.util.ArrayList;
import java.util.List;

public class ReviewsAdapter extends RecyclerView.Adapter<ReviewsAdapter.MyViewHolder> {
    private LayoutInflater layoutInflater;

    private List<ProductReviewModel> productReviews;
    private Context context;
    TextView userName, reviewText;
    RatingBar ratingBar;

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = layoutInflater.from(parent.getContext())
                .inflate(R.layout.item_list_review, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.bindData(productReviews.get(position));
    }

    @Override
    public int getItemCount() {
        return productReviews.size();
    }

    public ReviewsAdapter(List<ProductReviewModel> itemList, Context context) {
        this.layoutInflater = LayoutInflater.from(context);
        this.context = context;
        if (itemList != null)
            this.productReviews = itemList;
        else
            this.productReviews = new ArrayList<ProductReviewModel>();
    }
    public List<ProductReviewModel> getData() {
        return productReviews;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        MaterialCardView materialCardView;

        MyViewHolder(View itemView) {
            super(itemView);
            userName = itemView.findViewById(R.id.user_name_review_item);
            reviewText = itemView.findViewById(R.id.review_text_review_item);
            ratingBar = itemView.findViewById(R.id.ratingBar_review_item);
        }

        void bindData(final ProductReviewModel item) {
            userName.setText(item.getUserName());
            reviewText.setText(item.getText());
            float rating = item.getRating() / 2.0f;  // Dividir por 2 para ajustar el valor
            ratingBar.setRating(rating);
        }
    }
}

