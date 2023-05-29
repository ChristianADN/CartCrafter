package com.example.cartcrafter.activities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cartcrafter.R;
import com.example.cartcrafter.adapters.CategoriesAdapter;
import com.example.cartcrafter.adapters.PricesAdapter;
import com.example.cartcrafter.adapters.ReviewsAdapter;
import com.example.cartcrafter.fragments.CategoriesFragment;
import com.example.cartcrafter.fragments.ProductsFragment;
import com.example.cartcrafter.fragments.TypesFragment;
import com.example.cartcrafter.models.CategoryModel;
import com.example.cartcrafter.models.HttpResponse;
import com.example.cartcrafter.models.ProductFullDataModel;
import com.example.cartcrafter.models.ProductShopModel;
import com.example.cartcrafter.proxy.IOnTaskCompleted;
import com.example.cartcrafter.proxy.Proxy;
import com.example.cartcrafter.services.NotifySevice;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.List;

public class ProductActivity extends AppCompatActivity {
    private Context context;
    private String productId;
    private RecyclerView reviewsRecyclerView, pricesRecyclerView;
    ProductFullDataModel model;
    private PricesAdapter pricesAdapter;
    private ReviewsAdapter reviewsAdapter;
    public TextView nameTextView, descriptionTextView;
    public RatingBar totalScoreRatingBar;
    private ActivityResultLauncher<Intent> createReviewResultLauncher;
    private Button createReviewButton;
    private Button deleteReviewButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getSupportActionBar().hide();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product);
        context = this;

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            productId = bundle.getString("productId");
        }
        nameTextView = findViewById(R.id.nameProductTextView);
        descriptionTextView = findViewById(R.id.productDescriptionTextView);
        totalScoreRatingBar = findViewById(R.id.ratingBarAverage_product);

        pricesRecyclerView = findViewById(R.id.pricesReciclerView);
        pricesRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        reviewsRecyclerView = findViewById(R.id.reviewsRecilerView);
        reviewsRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        createReviewButton = findViewById(R.id.createReview_activity_product);
        deleteReviewButton = findViewById(R.id.deleteReview_activity_product);

        fillRecyclers(productId);

        createReviewResultLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {
                        if (result.getResultCode() == Activity.RESULT_OK) {
                            fillRecyclers(productId);
                        }
                    }
                });
    }

    private void fillRecyclers(String productId) {

        IOnTaskCompleted callback = new IOnTaskCompleted() {
            @Override
            public void onTaskCompleted(Object result) {
                Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ss").create();
                HttpResponse response = (HttpResponse) result;

                if (response != null && response.getResponseObject() != null) {
                    JsonElement jsonElement = response.getResponseObject();
                    model = gson.fromJson(jsonElement, ProductFullDataModel.class);
                }

                pricesAdapter = new PricesAdapter(model.getPrices(), context, new PricesAdapter.OnItemClickListener() {
                    @Override
                    public void onItemClick(ProductShopModel item) {
                        return;
                    }
                });
                reviewsAdapter = new ReviewsAdapter(model.getReviews(), context);

                nameTextView.setText(model.getProduct().getProductName());
                descriptionTextView.setText(model.getProduct().getProductDescription());
                totalScoreRatingBar.setRating(model.getAverageRating() / 2);
                pricesRecyclerView.setAdapter(pricesAdapter);
                reviewsRecyclerView.setAdapter(reviewsAdapter);
                if (model.isCurrentUserHasReview()) {
                    createReviewButton.setVisibility(View.GONE);
                    deleteReviewButton.setVisibility(View.VISIBLE);
                } else {
                    createReviewButton.setVisibility(View.VISIBLE);
                    deleteReviewButton.setVisibility(View.GONE);
                }
            }
        };
        Proxy.getProductFullData(productId, callback);
    }

    public void createReview(View view) {
        Intent intent = new Intent(this, CreateReviewActivity.class);
        intent.putExtra("productId", productId);
        intent.putExtra("productName", model.getProduct().getProductName());
        createReviewResultLauncher.launch(intent);
    }

    public void deleteReview(View view) {
        // Enviar la solicitud de eliminación al servidor utilizando Proxy
        Proxy.deleteReview(productId, new IOnTaskCompleted() {
            @Override
            public void onTaskCompleted(Object result) {
                HttpResponse response = (HttpResponse) result;
                Context context = getApplicationContext();
                if (response.getHttpCode() == 200) {
                    // Reseña eliminada correctamente
                    String message = getString(R.string.review_delete_success);
                    int duration = Toast.LENGTH_LONG;
                    NotifySevice.sendAlert(message, duration, context);

                    fillRecyclers(productId);
                } else {
                    // Error al eliminar la reseña
                    String message = getString(R.string.review_delete_failed);
                    int duration = Toast.LENGTH_LONG;
                    NotifySevice.sendAlert(message, duration, context);
                }
            }
        });
    }
}