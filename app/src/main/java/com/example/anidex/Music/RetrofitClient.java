package com.example.anidex.Music;

import com.example.anidex.Music.JikanApiService;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
//Provides a Retrofit instance configured to communicate with the Jikan API.
public class RetrofitClient {
    private static Retrofit retrofit = null;
    private static final String BASE_URL = "https://api.jikan.moe/v4/";

    public static JikanApiService getService() {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit.create(JikanApiService.class);
    }
}

