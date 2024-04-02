package com.example.anidex;


import android.content.Context;

import com.example.anidex.Database.DatabaseHelper;
import com.example.anidex.Models.Anime;

import java.util.List;

public class FavoritesManager {
    private DatabaseHelper dbHelper;

    public FavoritesManager(Context context) {
        dbHelper = new DatabaseHelper(context);
    }

    public boolean isFavorite(String animeId) {
        List<Anime> favorites = dbHelper.getAllFavoriteAnimes();
        for (Anime anime : favorites) {
            if (anime.getId().equals(animeId)) {
                return true;
            }
        }
        return false;
    }

    public void addFavorite(Anime anime) {
        dbHelper.addFavoriteAnime(anime);
    }

    public void removeFavorite(String animeId) {
        Anime anime = new Anime();
        anime.setId(animeId);
        dbHelper.deleteFavoriteAnime(anime);
    }

    public List<Anime> getAllFavorites() {
        return dbHelper.getAllFavoriteAnimes();
    }
}
