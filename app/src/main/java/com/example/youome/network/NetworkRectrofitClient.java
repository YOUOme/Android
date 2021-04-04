package com.example.youome.network;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class NetworkRectrofitClient {
    private final static String BASE_URL = "https://220.70.46.145:8000/";
    private final static String BASE_URL2 = "http://220.70.46.145:8001/";
    private static Retrofit retrofit = null;

    private NetworkRectrofitClient(){}
    public static Retrofit getClient(){
        if(retrofit == null){
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL2)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }
}
