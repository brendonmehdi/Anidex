package com.example.anidex.Favs;

import android.content.Context;
import android.util.Log;
import com.example.anidex.Database.DatabaseHelper;
import com.example.anidex.Models.Anime;
import com.example.anidex.Models.Manga;

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

        // Checks type and calls method
        if (item instanceof Anime && "anime".equals(type)) {
            dbHelper.addFavoriteAnime((Anime) item);
        } else if (item instanceof Manga && "manga".equals(type)) {
            dbHelper.addFavoriteManga((Manga) item);
        } else {
            Log.e("FavoritesManager", "Unsupported type or mismatch between type and instance.");
        }
    }

    public void removeFavorite(String id, String type) {
        if (id == null || type == null) {
            Log.e("FavoritesManager", "ID or type is null in removeFavorite method.");
            return;
        }
        dbHelper.deleteFavorite(id, type);
    }

    public void updateFavoriteAnime(Anime anime) {
        if (anime == null) {
            Log.e("FavoritesManager", "Anime is null in updateFavoriteAnime method.");
            return;
        }

        dbHelper.updateFavorite(anime.getId(), "anime", anime.getUserComment());
    }



    // Method to update a favorite manga, including its comment
    public void updateFavoriteManga(Manga manga) {
        if (manga == null) {
            Log.e("FavoritesManager", "Manga is null in updateFavoriteManga method.");
            return;
        }

        dbHelper.updateFavorite(manga.getId(), "manga", manga.getUserComment());
    }

    // New method to update the watch status of an Anime or Manga
    public void updateWatchStatus(String id, String type, String status) {
        if (id == null || type == null || status == null) {
            Log.e("FavoritesManager", "One of the parameters is null in updateWatchStatus method.");
            return;
        }

        if ("anime".equals(type)) {
            dbHelper.updateAnimeStatus(id, status);
        } else if ("manga".equals(type)) {
            dbHelper.updateMangaStatus(id, status);
        } else {
            Log.e("FavoritesManager", "Unsupported type in updateWatchStatus method.");
        }
    }





    public List<Object> getAllFavorites(String type) {
        return dbHelper.getAllFavorites(type);
    }
}
