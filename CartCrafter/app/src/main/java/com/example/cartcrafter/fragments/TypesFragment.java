package com.example.cartcrafter.fragments;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cartcrafter.R;
import com.example.cartcrafter.activities.ProductSearchActivity;
import com.example.cartcrafter.adapters.CategoriesAdapter;
import com.example.cartcrafter.adapters.TypesAdapter;
import com.example.cartcrafter.models.CategoryModel;
import com.example.cartcrafter.models.HttpResponse;
import com.example.cartcrafter.models.TypeModel;
import com.example.cartcrafter.proxy.IOnTaskCompleted;
import com.example.cartcrafter.proxy.Proxy;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;

import java.util.ArrayList;
import java.util.List;

public class TypesFragment extends Fragment {

    // Declaración de Variables.
    private RecyclerView recyclerView;
    private TypesAdapter adapter;
    public Context context;

    private List<TypeModel> originalTypes;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = LayoutInflater.from(getContext()).inflate(R.layout.fragment_search_type, container, false);
        recyclerView = view.findViewById(R.id.types_fragment);
        recyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));
        context = getContext();

        ProductSearchActivity activity = (ProductSearchActivity)getActivity();

        showTypes(activity.getCategoryParentId());

        return view;
    }

    public void removeSearch(){
        setData(originalTypes);
    }
    public void search(String searchQuery) {
        // Obtengo la lista de elementos del Adapter
        if(originalTypes==null)
            originalTypes = adapter.getData();

        // Creo una nueva lista para almacenar los elementos filtrados
        List<TypeModel> filteredElements = new ArrayList<>();

        // Realizo el filtrado de acuerdo al criterio de búsqueda
        for (TypeModel type : originalTypes) {
            if (matchesSearchQuery(type,searchQuery)) {
                filteredElements.add(type);
            }
        }

        // Actualizo la lista de elementos en el Adapter
        setData(filteredElements);
    }
    public void setData(List<TypeModel> types){
        adapter = new TypesAdapter(types, context, new TypesAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(TypeModel item) {
                openType(item.getId());
            }
        });
        recyclerView.setAdapter(adapter);
    }
    private boolean matchesSearchQuery(TypeModel type, String searchQuery) {
        String[] tokens = searchQuery.toLowerCase().split(" "); // Dividir la búsqueda en palabras clave

        // Verificar si alguna de las palabras clave coincide con el nombre del elemento
        for (String token : tokens) {
            if (!type.getName().toLowerCase().contains(token)) {
                return false; // Un criterio no coincide
            }
        }

        return true; // Todos los criterios coinciden
    }


    public void openType(String idType) {
        ProductSearchActivity activity = (ProductSearchActivity)getActivity();
        activity.setTypeParentId(idType);
        activity.setProductFragment(null);
    }

    private void showTypes(String parentId) {

        IOnTaskCompleted callback = new IOnTaskCompleted() {
            @Override
            public void onTaskCompleted(Object result) {
                List<TypeModel> types = new ArrayList<TypeModel>();
                Gson gson = new Gson();
                HttpResponse response = (HttpResponse) result;

                if (response != null && response.getResponseArray() != null) {
                    JsonArray jsonArray = response.getResponseArray();

                    // Obtengo la lista de TypeModel del objeto JSON
                    for (JsonElement element : jsonArray) {

                        try {
                            TypeModel type = gson.fromJson(element, TypeModel.class);
                            types.add(type);
                        }catch(Exception ex){
                            types.size();
                        }
                    }
                }

                adapter = new TypesAdapter(types, context, new TypesAdapter.OnItemClickListener() {
                    @Override
                    public void onItemClick(TypeModel item) {
                        openType(item.getId());
                    }
                });
                recyclerView.setAdapter(adapter);
            }
        };
        Proxy.getTypes(parentId, callback);
    }
}