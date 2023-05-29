package com.example.cartcrafter.models;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;

public class HttpResponse {
    private JsonElement response;
    private int httpCode;

    public HttpResponse(){}
    public HttpResponse(JsonElement response, int httpCode) {
        this.response = response;
        this.httpCode = httpCode;
    }

    public HttpResponse( int httpCode) {
        this.httpCode = httpCode;
    }

    public JsonPrimitive getResponsePrimitive() {
        return (JsonPrimitive) response;
    }
    public JsonObject getResponseObject() {
        return (JsonObject) response;
    }
    public JsonArray getResponseArray() {
        return (JsonArray) response;
    }

    public void setResponse(JsonElement response) {
        this.response = response;
    }

    public int getHttpCode() {
        return httpCode;
    }

    public void setHttpCode(int httpCode) {
        this.httpCode = httpCode;
    }
}
