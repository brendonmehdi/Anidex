package com.example.anidex.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.anidex.Models.Anime;
import com.example.anidex.Models.Manga;
import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "favoritesDb";
    private static final int DATABASE_VERSION = 3; // Incremented to 3 for the new schema

    private static final String TABLE_FAVORITES = "favorites";
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_EXTERNAL_ID = "external_id"; // External ID from API
    private static final String COLUMN_TITLE = "title";
    private static final String COLUMN_TYPE = "type";
    private static final String COLUMN_COMMENT = "comment"; // Added this line for the comment column

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_FAVORITES_TABLE = "CREATE TABLE " + TABLE_FAVORITES + "("
                + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + COLUMN_EXTERNAL_ID + " TEXT UNIQUE," // Ensure uniqueness for external ID
                + COLUMN_TITLE + " TEXT,"
                + COLUMN_TYPE + " TEXT,"
                + COLUMN_COMMENT + " TEXT" // Added this line to create the comment column
                + ")";
        db.execSQL(CREATE_FAVORITES_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // This will simply drop the table and recreate it.
        // Consider a migration strategy to preserve existing data if necessary.
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_FAVORITES);
        onCreate(db);
    }

    public void addFavorite(Object favorite, String type) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        String externalId = favorite instanceof Anime ? ((Anime) favorite).getId() : ((Manga) favorite).getId();
        String title = favorite instanceof Anime ? ((Anime) favorite).getAttributes().getCanonicalTitle() : ((Manga) favorite).getAttributes().getCanonicalTitle();
        String comment = favorite instanceof Anime ? ((Anime) favorite).getUserComment() : ((Manga) favorite).getUserComment();

        values.put(COLUMN_EXTERNAL_ID, externalId);
        values.put(COLUMN_TITLE, title);
        values.put(COLUMN_TYPE, type);
        values.put(COLUMN_COMMENT, comment); // Ensure you're capturing comments correctly.

        // Use insert with conflict resolution to avoid duplicate entries
        long result = db.insertWithOnConflict(TABLE_FAVORITES, null, values, SQLiteDatabase.CONFLICT_REPLACE);
        if (result == -1) {
            Log.e("DatabaseHelper", "Failed to insert manga into favorites.");
        } else {
            Log.d("DatabaseHelper", "Manga inserted into favorites successfully.");
        }
        db.close();
    }

    public List<Object> getAllFavorites(String type) {
        List<Object> favoritesList = new ArrayList<>();
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.query(TABLE_FAVORITES, null, COLUMN_TYPE + "=?", new String[]{type}, null, null, null);

        int externalIdIndex = cursor.getColumnIndex(COLUMN_EXTERNAL_ID);
        int titleIndex = cursor.getColumnIndex(COLUMN_TITLE);

        // Verify that both indexes are valid
        if (externalIdIndex != -1 && titleIndex != -1) {
            if (cursor.moveToFirst()) {
                do {
                    String externalId = cursor.getString(externalIdIndex);
                    String title = cursor.getString(titleIndex);

                    if ("anime".equals(type)) {
                        Anime anime = new Anime();
                        anime.setId(externalId);
                        Anime.Attributes attributes = new Anime.Attributes();
                        attributes.setCanonicalTitle(title);
                        anime.setAttributes(attributes);
                        favoritesList.add(anime);
                    } else if ("manga".equals(type)) {
                        Manga manga = new Manga();
                        manga.setId(externalId);
                        Manga.Attributes attributes = new Manga.Attributes();
                        attributes.setCanonicalTitle(title);
                        manga.setAttributes(attributes);
                        favoritesList.add(manga);
                    }
                } while (cursor.moveToNext());
            }
        } else {
            // Handle the case where the columns were not found
            Log.e("DatabaseHelper", "Required columns not found when fetching favorites.");
        }
        cursor.close();
        db.close();
        return favoritesList;
    }


    public void deleteFavorite(String externalId, String type) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_FAVORITES, COLUMN_EXTERNAL_ID + "=? AND " + COLUMN_TYPE + "=?", new String[]{externalId, type});
        db.close();
    }

    // Updates an anime in the database
    public void updateFavoriteAnime(Anime anime) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_TITLE, anime.getAttributes().getCanonicalTitle());
        values.put(COLUMN_COMMENT, anime.getUserComment());
        // Add more fields to update as needed

        db.update(TABLE_FAVORITES, values, COLUMN_ID + " = ? AND " + COLUMN_TYPE + " = ?", new String[]{anime.getId(), "anime"});
        db.close();
    }

    // Updates a manga in the database
    public void updateFavoriteManga(Manga manga) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_TITLE, manga.getAttributes().getCanonicalTitle());
        values.put(COLUMN_COMMENT, manga.getUserComment());
        // Add more fields to update as needed

        db.update(TABLE_FAVORITES, values, COLUMN_ID + " = ? AND " + COLUMN_TYPE + " = ?", new String[]{manga.getId(), "manga"});
        db.close();
    }

}
