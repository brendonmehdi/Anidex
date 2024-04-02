package com.example.anidex.SearchFragment;

import com.example.anidex.Models.Anime;
import com.example.anidex.Models.Manga;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface KitsuService {
    // Endpoint for searching anime by name
    @GET("anime")
    Call<KitsuResponse<Anime>> searchAnime(
            @Query("filter[text]") String query,
            @Query("page[limit]") int limit
    );

    // Endpoint for searching manga by name
    @GET("manga")
    Call<KitsuResponse<Manga>> searchManga(
            @Query("filter[text]") String query,
            @Query("page[limit]") int limit
    );
}
