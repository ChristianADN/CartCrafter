package com.example.cartcrafter.activities;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cartcrafter.R;
import com.example.cartcrafter.adapters.ShoppingListAdapter;
import com.example.cartcrafter.models.ShoppingListModel;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MyListsActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private ShoppingListAdapter adapter;
    private List<ShoppingListModel> shoppingLists;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_lists);
        getSupportActionBar().hide();

        // Configurar el RecyclerView
        recyclerView = findViewById(R.id.lists_recicler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);

        // Crear una lista de ejemplo de compras
        shoppingLists = createSampleShoppingLists();

        // Crear y configurar el adaptador
        adapter = new ShoppingListAdapter(shoppingLists, this);
        recyclerView.setAdapter(adapter);
    }

    private List<ShoppingListModel> createSampleShoppingLists() {
        List<ShoppingListModel> shoppingLists = new ArrayList<>();

        // Crear objetos ShoppingList de ejemplo
        Date dateCreated1 = new Date();
        ShoppingListModel list1 = new ShoppingListModel(dateCreated1, 10, 5);
        ShoppingListModel list2 = new ShoppingListModel(dateCreated1, 5, 2);
        ShoppingListModel list3 = new ShoppingListModel(dateCreated1, 8, 4);

        // Agregar las listas a la lista principal
        shoppingLists.add(list1);
        shoppingLists.add(list2);
        shoppingLists.add(list3);

        return shoppingLists;
    }
}
