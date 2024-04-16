package com.example.anidex.Music;

import com.example.anidex.Music.AnimeSearchResponse;
import com.example.anidex.Music.AnimeThemesResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface JikanApiService {
//    making network requests to fetch anime information and themes
    @GET("anime/{id}/themes")
    Call<AnimeThemesResponse> getAnimeThemes(@Path("id") int animeId);

    @GET("anime")
    Call<AnimeSearchResponse> searchAnime(@Query("q") String query);




}
