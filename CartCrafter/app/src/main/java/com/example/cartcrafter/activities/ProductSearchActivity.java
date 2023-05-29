package com.example.cartcrafter.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.RadioButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.cartcrafter.R;
import com.example.cartcrafter.fragments.CategoriesFragment;
import com.example.cartcrafter.fragments.ProductsFragment;
import com.example.cartcrafter.fragments.TypesFragment;

public class ProductSearchActivity extends AppCompatActivity {
    private String categoryParentId;
    private String typeParentId;

    private CategoriesFragment categoriesFragment;
    private TypesFragment typesFragment;
    private ProductsFragment productsFragment;


    private RadioButton radioCategory;
    private RadioButton radioType;
    private RadioButton radioProduct;
    public String getCategoryParentId() {
        String aux = categoryParentId;
        categoryParentId=null;
        return aux;
    }

    public void setCategoryParentId(String categoryParentId) {
        this.categoryParentId = categoryParentId;
    }

    public void search(View view){
        EditText searchText = (EditText)findViewById(R.id.searchInput_ProductSearchActivity);
        String searchQuery = searchText.getText().toString();
        if(radioCategory.isChecked())
            categoriesFragment.search(searchQuery);
        else if(radioType.isChecked())
            typesFragment.search(searchQuery);
        else if(radioProduct.isChecked())
            productsFragment.search(searchQuery);
    }

    public String getTypeParentId() {
        String aux = typeParentId;
        typeParentId=null;
        return aux;
    }

    public void setTypeParentId(String typeParentId) {
        this.typeParentId = typeParentId;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getSupportActionBar().hide();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_search);
        radioCategory = findViewById(R.id.radioCat_ProductSearchActivity);
        radioType = findViewById(R.id.radioTipo_ProductSearchActivity);
        radioProduct = findViewById(R.id.radioProd_ProductSearchActivity);
    }

    public void setCategoryFragment(View view){
        radioCategory.setChecked(true);
        categoriesFragment = new CategoriesFragment();
        replaceFragment(categoriesFragment);
    }
    public void setTypeFragment(View view){
        radioType.setChecked(true);
        typesFragment = new TypesFragment();
        replaceFragment(typesFragment);
    }
    public void setProductFragment(View view){
        radioProduct.setChecked(true);
        productsFragment = new ProductsFragment();
        replaceFragment(productsFragment);
    }

    /**
     * Método que reemplaza el frgament por otro
     * @param fragment - Fragment que queremos que reemplaze al actual.
     */
    private void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.search_product_frame, fragment);
        fragmentTransaction.commit();
    }

}
