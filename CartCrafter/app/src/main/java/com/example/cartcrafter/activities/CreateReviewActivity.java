package com.example.cartcrafter.activities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.cartcrafter.R;
import com.example.cartcrafter.models.ProductReviewModel;
import com.example.cartcrafter.models.HttpResponse;
import com.example.cartcrafter.proxy.IOnTaskCompleted;
import com.example.cartcrafter.proxy.Proxy;
import com.example.cartcrafter.services.NotifySevice;

public class CreateReviewActivity extends Activity {
    private String productId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_review);

        // Obtener los datos pasados por el intent
        productId = getIntent().getStringExtra("productId");
        String productName = getIntent().getStringExtra("productName");

        // Pintar el nombre del producto en el TextView
        TextView nameProductTextView = findViewById(R.id.nameProductTextView);
        nameProductTextView.setText(productName);
    }

    public void submitReview(View view) {
        EditText reviewText = findViewById(R.id.reviewEditText);
        RatingBar ratingBar = findViewById(R.id.ratingBar);

        float rating = ratingBar.getRating();
        String text = reviewText.getText().toString();

        // Crear el objeto ProductReviewModel con los datos de la reseña
        ProductReviewModel reviewModel = new ProductReviewModel();
        reviewModel.setRating((int)(rating*2));
        reviewModel.setText(text);
        reviewModel.setProductId(productId);

        // Enviar la reseña al servidor utilizando Proxy
        Proxy.sendReview(reviewModel, new IOnTaskCompleted() {
            @Override
            public void onTaskCompleted(Object result) {
                HttpResponse response = (HttpResponse) result;
                Context context = getApplicationContext();
                if (response.getHttpCode() == 201) {
                    // Reseña enviada correctamente
                    String message = getString(R.string.review_submit_success);
                    int duration = Toast.LENGTH_LONG;
                    NotifySevice.sendAlert(message, duration, context);

                    // Cerrar la actividad de creación de reseñas
                    setResult(RESULT_OK);
                    finish();
                } else {
                    // Error al enviar la reseña
                    String message = getString(R.string.review_submit_failed);
                    int duration = Toast.LENGTH_LONG;
                    NotifySevice.sendAlert(message, duration, context);
                }
            }
        });
    }
}
