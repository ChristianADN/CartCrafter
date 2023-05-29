package com.example.cartcrafter.activities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.core.content.ContextCompat;

import com.example.cartcrafter.R;
import com.example.cartcrafter.models.HttpResponse;
import com.example.cartcrafter.models.LoginModel;
import com.example.cartcrafter.models.RegisterModel;
import com.example.cartcrafter.proxy.IOnTaskCompleted;
import com.example.cartcrafter.proxy.Proxy;
import com.example.cartcrafter.services.NotifySevice;

public class LoginActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
    }

    public void login(View view){
        EditText emailControl = findViewById(R.id.emailInput_LoginActivity);
        EditText passwordControl = findViewById(R.id.passwordInput_LoginActivity);

        ColorStateList color = ContextCompat.getColorStateList(this, R.color.dark_grey);
        emailControl.setBackgroundTintList(color);
        passwordControl.setBackgroundTintList(color);

        String email = emailControl.getText().toString();
        String password = passwordControl.getText().toString();

        IOnTaskCompleted onTaskCompleted = new IOnTaskCompleted() {
            @Override
            public void onTaskCompleted(Object result) {
                // Aquí proceso la respuesta del servidor
                HttpResponse response = (HttpResponse)result;
                Context context = getApplicationContext();
                if(response.getHttpCode()==200){
                    // Login correcto
                    String message = getString(R.string.login_ok);;
                    int duration = Toast.LENGTH_LONG;
                    NotifySevice.sendAlert(message,duration,context);

                    Proxy.token = response.getResponseObject().get("accessToken").toString();

                    // Redirigir a la actividad de menú
                    Intent menuIntent = new Intent(context, MenuActivity.class);
                    startActivity(menuIntent);

                    finish();
                }else{
                    String message = getString(R.string.password_error);;
                    int duration = Toast.LENGTH_LONG;
                    NotifySevice.sendAlert(message,duration,context);

                    // Cambio el color de fondo del editText a rojo para indicar el error
                    ColorStateList colorStateList = ColorStateList.valueOf(Color.RED);
                    emailControl.setBackgroundTintList(colorStateList);
                }
            }
        };

        // Llamo al método login, si éste devuelve un fasle significa que la contraseña es insegura
        // si la contraseña es insegura, no puede estar en el servidor, así que ahorro una petición
        LoginModel model = new LoginModel(email,password);
        if(!Proxy.login(model, onTaskCompleted)){
            Context context = getApplicationContext();
            String message = getString(R.string.login_error);;
            int duration = Toast.LENGTH_LONG;
            NotifySevice.sendAlert(message,duration,context);
        }
    }

    public void goToRegister(View view){
        Intent registerIntent = new Intent(this,RegisterActivity.class);
        startActivity(registerIntent);
    }
}
