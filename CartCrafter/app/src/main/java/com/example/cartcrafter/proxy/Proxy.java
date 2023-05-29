package com.example.cartcrafter.proxy;

import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.widget.Toast;

import com.example.cartcrafter.R;
import com.example.cartcrafter.activities.MenuActivity;
import com.example.cartcrafter.models.CategoryModel;
import com.example.cartcrafter.models.HttpResponse;
import com.example.cartcrafter.models.LoginModel;
import com.example.cartcrafter.models.ProductModel;
import com.example.cartcrafter.models.ProductReviewModel;
import com.example.cartcrafter.models.RegisterModel;
import com.example.cartcrafter.models.ReviewViewModel;
import com.example.cartcrafter.services.NotifySevice;
import com.example.cartcrafter.services.ValidationService;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.List;

public class Proxy {
    public static String token;

    /**
     * El método register devuelve un true o un false si ha conseguido envíar al servidor la operación de registro
     * es decir, si los datos introducidos por el usuario son correctos.
     *
     * @param model
     * @param callback
     * @return
     */
    public static boolean register(RegisterModel model, IOnTaskCompleted callback) {
        if (ValidationService.validatePassword(model.getPassword())) {
            HttpPostRequest<RegisterModel> httpRequest = new HttpPostRequest<>("api/account/register", model, callback);
            httpRequest.execute();
            return true;
        } else {
            // La fuerza de la contraseña no cumple el criterio de seguridad, no será enviada al servidor
            return false;
        }
    }

    public static boolean login(LoginModel model, IOnTaskCompleted callback) {
        if (ValidationService.validatePassword(model.getPassword())) {
            HttpPostRequest<LoginModel> httpRequest = new HttpPostRequest<>("api/account/login", model, callback);
            httpRequest.execute();
            return true;
        } else {
            // La fuerza de la contraseña no cumple el criterio de seguridad, no será enviada al servidor
            return false;
        }
    }

    /**
     * Método para recuperar categorias, se pueden recuperar por el id del padre o mandar el id en
     * nulo para recuperar las categorias principales
     *
     * @param idParent
     * @param callback
     * @return
     */
    public static void getCategories(String idParent, IOnTaskCompleted callback) {
        List<CategoryModel> categories = new ArrayList<>();
        HttpGetRequest httpRequest;
        if (idParent != null)
            httpRequest = new HttpGetRequest("api/categories/byParent/" + idParent, null, callback);
        else
            httpRequest = new HttpGetRequest("api/categories", null, callback);
        httpRequest.execute();
    }

    public static void getTypes(String idCategory, IOnTaskCompleted callback) {
        List<CategoryModel> types = new ArrayList<>();
        HttpGetRequest httpRequest;
        if (idCategory != null)
            httpRequest = new HttpGetRequest("api/productTypes/byCategory/" + idCategory, null, callback);
        else
            httpRequest = new HttpGetRequest("api/productTypes", null, callback);
        httpRequest.execute();
    }

    public static void getProducts(String idType, IOnTaskCompleted callback) {
        List<ProductModel> products = new ArrayList<>();
        HttpGetRequest httpRequest;
        if (idType != null)
            httpRequest = new HttpGetRequest("api/products/byProductType/" + idType, null, callback);
        else
            httpRequest = new HttpGetRequest("api/products", null, callback);
        httpRequest.execute();
    }

    public static void getProductFullData(String idProduct, IOnTaskCompleted callback) {
        List<ProductModel> products = new ArrayList<>();
        HttpGetRequest httpRequest;
        httpRequest = new HttpGetRequest("api/Products/fullData/" + idProduct, null, callback);
        httpRequest.execute();
    }

    /**
     * Método para recuperar tipos por id
     *
     * @param idParent
     * @return
     */
    public static List<CategoryModel> getTypes(String idParent) {
        List<CategoryModel> categories = new ArrayList<>();
        IOnTaskCompleted callback = new IOnTaskCompleted() {
            @Override
            public void onTaskCompleted(Object result) {
                Gson gson = new Gson();
                HttpResponse response = (HttpResponse) result;

                if (response != null && response.getResponseArray() != null) {
                    JsonArray jsonArray = response.getResponseArray();

                    for (JsonElement element : jsonArray) {
                        try {
                            CategoryModel category = gson.fromJson(element, CategoryModel.class);
                            categories.add(category);
                        } catch (Exception ex) {

                        }
                    }
                }
            }
        };
        HttpGetRequest httpRequest;
        httpRequest = new HttpGetRequest("api/ProductTypes/ByCategory/" + idParent, null, callback);
        httpRequest.execute();
        return categories;
    }


    public static void CategoryHasChilds(String idCategory, IOnTaskCompleted callback) {
        boolean response;
        HttpGetRequest httpRequest = new HttpGetRequest("api/categories/hasChilds/" + idCategory, null, callback);
        httpRequest.execute();
    }

    public static void sendReview(ProductReviewModel model, IOnTaskCompleted callback) {
        ReviewViewModel review = new ReviewViewModel();
        review.setProductId(model.getProductId());
        review.setRating(model.getRating());
        review.setText(model.getText());

        HttpPostRequest<ReviewViewModel> httpRequest = new HttpPostRequest<>("api/productreviews", review, callback);
        httpRequest.execute();
    }

    public static void deleteReview(String idProduct, IOnTaskCompleted callback) {
        HttpGetRequest httpRequest = new HttpGetRequest("api/productreviews/deletebyproductid/" + idProduct, null, callback);
        httpRequest.execute();
    }
}

