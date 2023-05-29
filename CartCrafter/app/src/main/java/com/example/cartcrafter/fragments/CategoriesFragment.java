package com.example.cartcrafter.fragments;

import android.content.Context;
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
import com.example.cartcrafter.activities.ProductSearchActivity;
import com.example.cartcrafter.adapters.CategoriesAdapter;
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

public class CategoriesFragment extends Fragment {

    // Declaración de Variables.
    private RecyclerView recyclerView;
    private CategoriesAdapter adapter;
    public Context context;

    private List<CategoryModel> originalCategories;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = LayoutInflater.from(getContext()).inflate(R.layout.fragment_search_category, container, false);
        recyclerView = view.findViewById(R.id.categories_fragment);
        recyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));
        context = getContext();

        showCategories(null);
        return view;
    }

    public void removeSearch(){
        setData(originalCategories);
    }
    public void search(String searchQuery) {
        // Obtengo la lista de elementos del Adapter
        if(originalCategories==null)
            originalCategories = adapter.getData();

        // Creo una nueva lista para almacenar los elementos filtrados
        List<CategoryModel> filteredElements = new ArrayList<>();

        // Realizo el filtrado de acuerdo al criterio de búsqueda
        for (CategoryModel category : originalCategories) {
            if (matchesSearchQuery(category,searchQuery)) {
                filteredElements.add(category);
            }
        }

        // Actualizo la lista de elementos en el Adapter
        setData(filteredElements);
    }
    public void setData(List<CategoryModel> categories){
        adapter = new CategoriesAdapter(categories, context, new CategoriesAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(CategoryModel item) {
                openCategory(item.getId());
            }
        });
        recyclerView.setAdapter(adapter);
    }
    private boolean matchesSearchQuery(CategoryModel category, String searchQuery) {
        String[] tokens = searchQuery.toLowerCase().split(" "); // Dividir la búsqueda en palabras clave

        // Verificar si alguna de las palabras clave coincide con el nombre del elemento
        for (String token : tokens) {
            if (!category.getName().toLowerCase().contains(token)) {
                return false; // Un criterio no coincide
            }
        }

        return true; // Los criterios coinciden
    }
    public void openCategory(String idCategory) {
        IOnTaskCompleted callback = new IOnTaskCompleted() {
            @Override
            public void onTaskCompleted(Object result) {
                Gson gson = new Gson();
                HttpResponse response = (HttpResponse) result;
                boolean hasChilds = response.getResponsePrimitive().getAsBoolean();


                if(hasChilds)
                    showCategories(idCategory);
                else
                    showTypes(idCategory);
            }
        };
        Proxy.CategoryHasChilds(idCategory, callback);

    }

    private void showCategories(String parentId) {

        IOnTaskCompleted callback = new IOnTaskCompleted() {
            @Override
            public void onTaskCompleted(Object result) {
                List<CategoryModel> categories = new ArrayList<CategoryModel>();
                Gson gson = new Gson();
                HttpResponse response = (HttpResponse) result;

                if (response != null && response.getResponseArray() != null) {
                    JsonArray jsonArray = response.getResponseArray();

                    // Obtengo la lista de CategoryModel del objeto JSON
                    for (JsonElement element : jsonArray) {

                        try {
                            CategoryModel category = gson.fromJson(element, CategoryModel.class);
                            categories.add(category);
                        }catch(Exception ex){
                            categories.size();
                        }
                    }
                }

                adapter = new CategoriesAdapter(categories, context, new CategoriesAdapter.OnItemClickListener() {
                    @Override
                    public void onItemClick(CategoryModel item) {
                        openCategory(item.getId());
                    }
                });
                recyclerView.setAdapter(adapter);
            }
        };
        Proxy.getCategories(parentId, callback);
    }
    private void showTypes(String idCategoy){
        ProductSearchActivity activity = (ProductSearchActivity)getActivity();
        activity.setCategoryParentId(idCategoy);
        activity.setTypeFragment(null);
    }
}