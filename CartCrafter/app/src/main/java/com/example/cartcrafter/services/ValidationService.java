package com.example.cartcrafter.services;

public class ValidationService {
    public static boolean validatePassword(String password){
        // Expresión regular para verificar que la contraseña contiene números y letras
        String regexp = "^(?=.*[0-9])(?=.*[a-zA-Z]).*$";

        // Verificar si la contraseña cumple con la expresión regular
        boolean cumpleExpresion = password.matches(regexp);

        return password.length()>=8&&cumpleExpresion;
    }
}
