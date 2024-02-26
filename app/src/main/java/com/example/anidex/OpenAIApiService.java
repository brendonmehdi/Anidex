package com.example.anidex;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

/**
 * Defines the communication with OpenAI's API for image analysis.
 */
public interface OpenAIApiService {
    /**
     * Makes a POST request to the specified endpoint with an image for analysis.
     * @param image The image data to be analyzed, wrapped in a RequestBody for HTTP transport.
     * @return A Call object wrapping ApiResponse which contains the analysis result.
     */
    @POST("chat/completions") // Specifies the API endpoint for image analysis.
    Call<ApiResponse> uploadImage(@Body RequestBody image); // Defines a method to upload an image.
}
