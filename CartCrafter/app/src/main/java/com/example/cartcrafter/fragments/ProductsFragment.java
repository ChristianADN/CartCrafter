package com.example.cartcrafter.fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cartcrafter.R;
import com.example.cartcrafter.activities.ProductActivity;
import com.example.cartcrafter.activities.ProductSearchActivity;
import com.example.cartcrafter.adapters.CategoriesAdapter;
import com.example.cartcrafter.adapters.ProductsAdapter;
import com.example.cartcrafter.models.CategoryModel;
import com.example.cartcrafter.models.HttpResponse;
import com.example.cartcrafter.models.ProductModel;
import com.example.cartcrafter.models.TypeModel;
import com.example.cartcrafter.proxy.IOnTaskCompleted;
import com.example.cartcrafter.proxy.Proxy;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;

import java.util.ArrayList;
import java.util.List;

public class ProductsFragment extends Fragment {

    // Declaración de Variables.
    private RecyclerView recyclerView;
    private ProductsAdapter adapter;
    public Context context;

    private List<ProductModel> originalProducts;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = LayoutInflater.from(getContext()).inflate(R.layout.fragment_search_product, container, false);
        recyclerView = view.findViewById(R.id.product_fragment);
        recyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));
        context = getContext();

        ProductSearchActivity activity = (ProductSearchActivity)getActivity();

        showProducts(activity.getTypeParentId());

        return view;
    }

    public void removeSearch(){
        setData(originalProducts);
    }
    public void search(String searchQuery) {
        // Obtengo la lista de elementos del Adapter
        if(originalProducts==null)
            originalProducts = adapter.getData();

        // Creo una nueva lista para almacenar los elementos filtrados
        List<ProductModel> filteredElements = new ArrayList<>();

        // Realizo el filtrado de acuerdo al criterio de búsqueda
        for (ProductModel product : originalProducts) {
            if (matchesSearchQuery(product,searchQuery)) {
                filteredElements.add(product);
            }
        }

        // Actualizo la lista de elementos en el Adapter
        setData(filteredElements);
    }
    public void setData(List<ProductModel> products){
        adapter = new ProductsAdapter(products, context, new ProductsAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(ProductModel item) {
                openProduct(item.getId());
            }
        });
        recyclerView.setAdapter(adapter);
    }
    private boolean matchesSearchQuery(ProductModel product, String searchQuery) {
        String[] tokens = searchQuery.toLowerCase().split(" "); // Dividir la búsqueda en palabras clave

        // Verificar si alguna de las palabras clave coincide con el nombre del elemento
        for (String token : tokens) {
            if (!product.getProductName().toLowerCase().contains(token)) {
                return false; // Un criterio no coincide
            }
        }

        return true; // Los criterios coinciden
    }


    public void openProduct(String idProduct) {
        Intent intent = new Intent(requireActivity(), ProductActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString("productId", idProduct);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    private void showProducts(String parentId) {

        IOnTaskCompleted callback = new IOnTaskCompleted() {
            @Override
            public void onTaskCompleted(Object result) {
                List<ProductModel> Products = new ArrayList<ProductModel>();
                Gson gson = new Gson();
                HttpResponse response = (HttpResponse) result;

                if (response != null && response.getResponseArray() != null) {
                    JsonArray jsonArray = response.getResponseArray();

                    // Obtengo la lista de ProductModel del objeto JSON
                    for (JsonElement element : jsonArray) {

                        try {
                            ProductModel Product = gson.fromJson(element, ProductModel.class);
                            Products.add(Product);
                        }catch(Exception ex){
                        }
                    }
                }

                adapter = new ProductsAdapter(Products, context, new ProductsAdapter.OnItemClickListener() {
                    @Override
                    public void onItemClick(ProductModel item) {
                        openProduct(item.getId());
                    }
                });
                recyclerView.setAdapter(adapter);
            }
        };
        Proxy.getProducts(parentId, callback);
    }
}