package com.example.anidex;

import com.example.anidex.Models.Anime;
import com.example.anidex.Models.Manga;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;
import java.util.List;

public interface KitsuService {
    // Endpoint for searching anime by name
    @GET("anime")
    Call<List<Anime>> searchAnime(
            @Query("filter%5Btext%5D") String query,
            @Query("page%5Blimit%5D") int limit
    );

    // Endpoint for searching manga by name
    @GET("manga")
    Call<List<Manga>> searchManga(
            @Query("filter%5Btext%5D") String query,
            @Query("page%5Blimit%5D") int limit
    );
}
