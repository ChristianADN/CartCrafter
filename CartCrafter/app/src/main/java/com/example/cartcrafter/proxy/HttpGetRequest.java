package com.example.cartcrafter.proxy;

import android.os.AsyncTask;

import com.example.cartcrafter.models.HttpResponse;
import com.example.cartcrafter.models.ModelBase;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.URL;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;

public class HttpGetRequest extends AsyncTask<Void, Void, HttpResponse> {
    private String[] args;
    private String endpoint;
    private static String host = "https://192.168.1.130";
    private static String port = "7074";
    private IOnTaskCompleted callback;
    private HttpResponse httpResponse;

    public HttpGetRequest(String endpoint, String[] args, IOnTaskCompleted callback) {
        this.args = args;
        this.endpoint = endpoint;
        this.callback = callback;
    }

    @Override
    protected HttpResponse doInBackground(Void... params) {
        try {
            // Preparo los parámetros a enviar al servidor
            StringBuilder queryBuilder = new StringBuilder();
            if (args != null)
                for (String arg : args) {
                    if (queryBuilder.length() > 0) {
                        queryBuilder.append("&");
                    }
                    queryBuilder.append(arg);
                }

            //Declaro la respuesta
            httpResponse = new HttpResponse();
            int responseCode;

            // URL del endpoint
            String urlString;
            if (args != null)
                urlString = host + ":" + port + "/" + endpoint + "?" + queryBuilder.toString();
            else
                urlString = host + ":" + port + "/" + endpoint;
            URL url = new URL(urlString);

            // Creo el objeto Gson que necesitaré después para convertir la respuesta
            Gson gson = new Gson();

            // Saltamos la seguridad contra certificados autofirmados
            CustomTrustManager customTrustManager = new CustomTrustManager();
            SSLContext sslContext = SSLContext.getInstance("TLS");
            sslContext.init(null, new TrustManager[]{customTrustManager}, null);
            HostnameVerifier hostnameVerifier = new HostnameVerifier() {
                @Override
                public boolean verify(String hostname, SSLSession session) {
                    return true;
                }
            };

            // Crear la conexión HTTP
            HttpsURLConnection conn = (HttpsURLConnection) url.openConnection();
            conn.setRequestMethod("GET");

            // Añade el token, si lo hay
            if (Proxy.token != null)
                conn.setRequestProperty("Authorization", "Bearer " + Proxy.token.replace("\"",""));

            // Configurar el SSLContext personalizado en la conexión
            if (conn instanceof HttpsURLConnection) {
                ((HttpsURLConnection) conn).setSSLSocketFactory(sslContext.getSocketFactory());
                ((HttpsURLConnection) conn).setHostnameVerifier(hostnameVerifier);
            }

            httpResponse.setHttpCode(conn.getResponseCode());

            // Obtener la respuesta del servidor
            BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String line;
            StringBuilder response = new StringBuilder();
            while ((line = reader.readLine()) != null) {
                response.append(line);
            }

            // Cerrar las conexiones
            reader.close();
            conn.disconnect();

            // Procesar la respuesta
            JsonElement jsonElement=null;
            try {
                jsonElement = gson.fromJson(response.toString(), JsonObject.class);
            }catch(Exception ex){
                try {
                    jsonElement = gson.fromJson(response.toString(), JsonArray.class);
                }catch (Exception exc){
                    jsonElement = gson.fromJson(response.toString(), JsonPrimitive.class);
                }
            }
            httpResponse.setResponse(jsonElement);
            return httpResponse;
        } catch (Exception ex) {
            return httpResponse;
        }
    }

    @Override
    protected void onPostExecute(HttpResponse result) {
        // Llama al método de callback y pasa el resultado obtenido
        callback.onTaskCompleted(result);
    }
}
