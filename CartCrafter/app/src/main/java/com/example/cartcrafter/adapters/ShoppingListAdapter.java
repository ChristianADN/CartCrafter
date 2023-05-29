package com.example.cartcrafter.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cartcrafter.R;
import com.example.cartcrafter.models.ShoppingListModel;

import java.util.ArrayList;
import java.util.List;

public class ShoppingListAdapter extends RecyclerView.Adapter<ShoppingListAdapter.ViewHolder> {

    private List<ShoppingListModel> shoppingLists;
    private Context context;

    public ShoppingListAdapter(List<ShoppingListModel> shoppingLists, Context context) {
        this.shoppingLists = shoppingLists;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_list_list, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ShoppingListModel shoppingList = shoppingLists.get(position);
        holder.nameTextView.setText(shoppingList.getDateCreated()+"");
        holder.numProductsTextView.setText(String.valueOf(shoppingList.getProductCount()));
        holder.weightTextView.setText(shoppingList.getTotalWeight()+"Kg");

        // Funcionalidad del botón "Usar"
        holder.useButton.setOnClickListener(v -> {
            // Acciones a realizar cuando se hace clic en el botón "Usar"
            Toast.makeText(context, "Botón Usar clickeado", Toast.LENGTH_SHORT).show();
        });

        // Funcionalidad del botón "Editar"
        holder.editButton.setOnClickListener(v -> {
            // Acciones a realizar cuando se hace clic en el botón "Editar"
            Toast.makeText(context, "Botón Editar clickeado", Toast.LENGTH_SHORT).show();
        });

        // Funcionalidad del botón "Eliminar"
        holder.deleteButton.setOnClickListener(v -> {
            // Acciones a realizar cuando se hace clic en el botón "Eliminar"
            Toast.makeText(context, "Botón Eliminar clickeado", Toast.LENGTH_SHORT).show();
        });
    }

    @Override
    public int getItemCount() {
        return shoppingLists.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView nameTextView;
        public TextView numProductsTextView;
        public TextView weightTextView;
        public Button useButton;
        public Button editButton;
        public Button deleteButton;

        public ViewHolder(View itemView) {
            super(itemView);
            nameTextView = itemView.findViewById(R.id.name_list_item);
            numProductsTextView = itemView.findViewById(R.id.num_products_item);
            weightTextView = itemView.findViewById(R.id.weightTextView);
            useButton = itemView.findViewById(R.id.use_button);
            editButton = itemView.findViewById(R.id.edit_button);
            deleteButton = itemView.findViewById(R.id.delete_button);
        }
    }
}
