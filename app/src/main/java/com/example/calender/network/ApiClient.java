package com.example.calender.network;

import android.content.Context;



import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClient {

    static Context context;
    public static final String BASE_URL = "http://88.99.49.132/prixapi/";
    private static Retrofit retrofitClient;
    private static ApiClient mInstance;



    ApiClient() {
        if (retrofitClient == null) {

            OkHttpClient.Builder builder = new OkHttpClient().newBuilder();
            /*builder.readTimeout(1, TimeUnit.MINUTES);
            builder.connectTimeout(1, TimeUnit.MINUTES);
            builder.writeTimeout(1, TimeUnit.MINUTES);*/

            retrofitClient = new Retrofit.Builder()
                    .client(builder.build())
                    .addConverterFactory(GsonConverterFactory.create())
                    .baseUrl(BASE_URL)
                    .build();
        }
    }


    public static synchronized ApiClient getInstance(){
        if(mInstance==null){
            mInstance = new ApiClient();
        }
        return mInstance;
    }
    public Api getApi(){
        return retrofitClient.create(Api.class);
    }
}

