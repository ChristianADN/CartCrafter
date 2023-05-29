package com.example.cartcrafter.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.cartcrafter.R;
import com.example.cartcrafter.proxy.Proxy;

public class MenuActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
    }

    public void goToProductSearch(View view){
        Intent productSearchIntent = new Intent(this,ProductSearchActivity.class);
        startActivity(productSearchIntent);
    }
    public void logout(View view){
        Proxy.token=null;
        finish();
    }

    public void goToMyLists(View view){
        Intent myListsIntent = new Intent(this,MyListsActivity.class);
        startActivity(myListsIntent);
    }
}
