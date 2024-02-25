package com.example.anidex;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface OpenAIApiService {
    @POST("chat/completions") // Correct endpoint path here
    Call<ApiResponse> uploadImage(@Body RequestBody image); // Adjust method signature as needed
}

