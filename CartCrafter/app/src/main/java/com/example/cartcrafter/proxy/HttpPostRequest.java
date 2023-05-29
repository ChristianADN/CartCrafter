package com.example.cartcrafter.proxy;

import android.os.AsyncTask;

import com.example.cartcrafter.models.HttpResponse;
import com.example.cartcrafter.models.ModelBase;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;

public class HttpPostRequest<T extends ModelBase> extends AsyncTask<Void, Void, HttpResponse> {
    private T model;
    private String endpoint;
    private static String host = "https://192.168.1.130";
    private static String port = "7074";
    private IOnTaskCompleted callback;
    private HttpResponse httpResponse;

    public HttpPostRequest(String endpoint, T model, IOnTaskCompleted callback) {
        this.model = model;
        this.endpoint = endpoint;
        this.callback = callback;
    }

    @Override
    protected HttpResponse doInBackground(Void... params) {
        try {

            //Declaro la respuesta
            httpResponse=new HttpResponse();
            int responseCode;

            // URL del endpoint
            String urlString = host + ":" + port + "/" + endpoint;
            URL url = new URL(urlString);

            // Convertir el objeto RegisterModel a JSON
            Gson gson = new Gson();
            String requestBody = gson.toJson(model);

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
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setDoOutput(true);

            // Añade el token, si lo hay
            if (Proxy.token != null)
                conn.setRequestProperty("Authorization", "Bearer " + Proxy.token.replace("\"",""));

            // Configurar el SSLContext personalizado en la conexión
            if (conn instanceof HttpsURLConnection) {
                ((HttpsURLConnection) conn).setSSLSocketFactory(sslContext.getSocketFactory());
                ((HttpsURLConnection) conn).setHostnameVerifier(hostnameVerifier);
            }

            OutputStream outputStream = conn.getOutputStream();
            outputStream.write(requestBody.getBytes());
            outputStream.flush();
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
            outputStream.close();
            conn.disconnect();

            // Procesar la respuesta
            JsonObject jsonObject = gson.fromJson(response.toString(), JsonObject.class);
            httpResponse.setResponse(jsonObject);
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
