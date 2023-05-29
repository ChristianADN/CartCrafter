package com.example.cartcrafter.services;

import android.content.Context;
import android.view.Gravity;
import android.widget.Toast;

public class NotifySevice {
    public static void sendAlert(String message,int duration,Context context){
        Toast toast = Toast.makeText(context, message, duration);
        toast.setGravity(Gravity.CENTER, 0, 0);
        Toast.makeText(context, message, duration).show();
    }
}
