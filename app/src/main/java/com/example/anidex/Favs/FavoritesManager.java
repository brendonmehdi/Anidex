package com.example.anidex.Favs;

import android.content.Context;
import android.util.Log;
import com.example.anidex.Database.DatabaseHelper;
import com.example.anidex.Models.Anime;
import com.example.anidex.Models.Manga;

import java.util.ArrayList;
import java.util.List;

public class FavoritesManager {
    private DatabaseHelper dbHelper;

    public FavoritesManager(Context context) {
        dbHelper = new DatabaseHelper(context);
    }

    public boolean isFavorite(String id, String type) {
        try {
            List<Object> favorites = dbHelper.getAllFavorites(type);
            for (Object item : favorites) {
                if (type.equals("anime") && item instanceof Anime && ((Anime) item).getId().equals(id)) {
                    return true;
                } else if (type.equals("manga") && item instanceof Manga && ((Manga) item).getId().equals(id)) {
                    return true;
                }
            }
        } catch (Exception e) {
            Log.e("FavoritesManager", "Error checking if item is favorite", e);
        }
        return false;
    }

    public void addFavorite(Object item, String type) {
        if (item == null || type == null) {
            Log.e("FavoritesManager", "Item or type is null in addFavorite method.");
            return;
        }
        dbHelper.addFavorite(item, type);
    }

    public void removeFavorite(String id, String type) {
        if (id == null || type == null) {
            Log.e("FavoritesManager", "ID or type is null in removeFavorite method.");
            return;
        }
        dbHelper.deleteFavorite(id, type);
    }

    // Method to update an anime's comment or any other detail
    public void updateFavoriteAnime(Anime anime) {
        dbHelper.updateFavoriteAnime(anime);
    }

    // Method to update a manga's comment or any other detail
    public void updateFavoriteManga(Manga manga) {
        dbHelper.updateFavoriteManga(manga);
    }

    // This method now uses the updated structure to fetch all favorites of a given type.
    public List<Object> getAllFavorites(String type) {
        return dbHelper.getAllFavorites(type);
    }
}
