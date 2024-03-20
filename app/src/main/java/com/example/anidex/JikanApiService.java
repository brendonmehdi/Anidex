package com.example.anidex;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface JikanApiService {
    @GET("anime/{id}/themes")
    Call<AnimeThemesResponse> getAnimeThemes(@Path("id") int animeId);
}
