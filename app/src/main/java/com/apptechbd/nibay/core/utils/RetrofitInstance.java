package com.apptechbd.nibay.core.utils;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitInstance {
    private static final String BASE_URL_V1 = "https://nibay.co/api/v1/";

    //there's no v2 of the APIs at present just keeping it for future iteration
    private static final String BASE_URL_V2 = "https://nibay.co/api/v2/";
    private static Retrofit retrofitV1;
    private static Retrofit retrofitV2;

    public static Retrofit getRetrofitClient(String baseUrl) {
        if (baseUrl.equals(BASE_URL_V1)) {
            if (retrofitV1 == null) {
                retrofitV1 = createRetrofit(baseUrl);
            }
            return retrofitV1;
        } else if (baseUrl.equals(BASE_URL_V2)) {
            if (retrofitV2 == null) {
                retrofitV2 = createRetrofit(baseUrl);
            }
            return retrofitV2;
        } else {
            throw new IllegalArgumentException("Unsupported baseUrl: " + baseUrl);
        }
    }

    private static Retrofit createRetrofit(String baseUrl) {
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        httpClient.readTimeout(60, TimeUnit.SECONDS)
                .connectTimeout(60, TimeUnit.SECONDS)
                .writeTimeout(60, TimeUnit.SECONDS);
        httpClient.addInterceptor(logging);

        return new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .client(httpClient.build())
                .build();
    }
}
