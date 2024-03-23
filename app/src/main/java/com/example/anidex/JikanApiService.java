package com.example.anidex;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface JikanApiService {
    @GET("anime/{id}/themes")
    Call<AnimeThemesResponse> getAnimeThemes(@Path("id") int animeId);

    @GET("anime")
    Call<AnimeSearchResponse> searchAnime(@Query("q") String query);

}
