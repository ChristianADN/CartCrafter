package com.example.cartcrafter.activities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.core.content.ContextCompat;

import com.example.cartcrafter.R;
import com.example.cartcrafter.models.HttpResponse;
import com.example.cartcrafter.models.RegisterModel;
import com.example.cartcrafter.proxy.HttpPostRequest;
import com.example.cartcrafter.proxy.IOnTaskCompleted;
import com.example.cartcrafter.proxy.Proxy;
import com.example.cartcrafter.services.NotifySevice;

public class RegisterActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
    }
    public void register(View view){
        EditText userNameControl = findViewById(R.id.userInput_RegisterActivity);
        EditText emailControl = findViewById(R.id.emailInput_RegisterActivity);
        EditText passwordControl = findViewById(R.id.passwordInput_RegisterActivity);
        EditText repeatPasswordControl = findViewById(R.id.repearPasswordInput_RegisterActivity);

        ColorStateList color = ContextCompat.getColorStateList(this, R.color.dark_grey);
        userNameControl.setBackgroundTintList(color);
        emailControl.setBackgroundTintList(color);
        passwordControl.setBackgroundTintList(color);
        repeatPasswordControl.setBackgroundTintList(color);

        String userName = userNameControl.getText().toString();
        String email = emailControl.getText().toString();
        String password = passwordControl.getText().toString();

        IOnTaskCompleted onTaskCompleted = new IOnTaskCompleted() {
            @Override
            public void onTaskCompleted(Object result) {
                // Aquí proceso la respuesta del servidor
                HttpResponse response = (HttpResponse)result;
                Context context = getApplicationContext();
                if(response.getHttpCode()==200){
                    // Registro confirmado
                    String message = getString(R.string.register_ok);;
                    int duration = Toast.LENGTH_LONG;
                    NotifySevice.sendAlert(message,duration,context);

                    setResult(RESULT_OK);
                    finish();
                }else{
                    String message = getString(R.string.register_error);;
                    int duration = Toast.LENGTH_LONG;
                    NotifySevice.sendAlert(message,duration,context);

                    // Cambio el color de fondo del editText a rojo para indicar el error
                    ColorStateList colorStateList = ColorStateList.valueOf(Color.RED);
                    emailControl.setBackgroundTintList(colorStateList);
                }
            }
        };

        // Compruebo que las contraseñas coinciden
        String pass = passwordControl.getText().toString();
        String rePass = repeatPasswordControl.getText().toString();
        if(!pass.equals(rePass)){
            Context context = getApplicationContext();
            String message = getString(R.string.password_dont_match);
            int duration = Toast.LENGTH_LONG;
            NotifySevice.sendAlert(message,duration,context);

            // Cambio el color de fondo del editText a rojo para indicar el error
            ColorStateList colorStateList = ColorStateList.valueOf(Color.RED);
            passwordControl.setBackgroundTintList(colorStateList);
            repeatPasswordControl.setBackgroundTintList(colorStateList);
            return;
        }

        // Llamo al método register, si éste devuelve un fasle significa que la contraseña es insegura
        RegisterModel model = new RegisterModel(userName,email,password);
        if(!Proxy.register(model, onTaskCompleted)){
            Context context = getApplicationContext();
            String message = getString(R.string.password_strength_error);
            int duration = Toast.LENGTH_LONG;
            NotifySevice.sendAlert(message,duration,context);

            // Cambio el color de fondo del editText a rojo para indicar el error
            ColorStateList colorStateList = ColorStateList.valueOf(Color.RED);
            passwordControl.setBackgroundTintList(colorStateList);
        }
    }
}
