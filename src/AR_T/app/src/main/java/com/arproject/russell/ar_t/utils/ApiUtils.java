package com.arproject.russell.ar_t.utils;

public class ApiUtils {

    public static ApiInterface getApiService() {

        return ApiClient.retrofit().create(ApiInterface.class);
    }
}
