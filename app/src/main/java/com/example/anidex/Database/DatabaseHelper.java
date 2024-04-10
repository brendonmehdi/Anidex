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
        values.put(COLUMN_COMMENT, comment);

        // insert with conflict resolution to avoid duplicate entries
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
        SQLiteDatabase db = this.getReadableDatabase(); // Use getReadableDatabase for data fetching
        Cursor cursor = db.query(TABLE_FAVORITES, null, COLUMN_TYPE + "=?", new String[]{type}, null, null, null);

        while (cursor.moveToNext()) {
            String externalId = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_EXTERNAL_ID));
            String title = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_TITLE));
            String comment = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_COMMENT)); // Ensure we are fetching the comment

            if ("anime".equals(type)) {
                Anime anime = new Anime();
                anime.setId(externalId);
                Anime.Attributes attributes = new Anime.Attributes();
                attributes.setCanonicalTitle(title);
                anime.setAttributes(attributes);
                anime.setUserComment(comment); // Set the comment here
                favoritesList.add(anime);
            } else if ("manga".equals(type)) {
                Manga manga = new Manga();
                manga.setId(externalId);
                Manga.Attributes attributes = new Manga.Attributes();
                attributes.setCanonicalTitle(title);
                manga.setAttributes(attributes);
                manga.setUserComment(comment); // Set the comment here
                favoritesList.add(manga);
            }
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



    public void updateFavorite(String externalId, String type, String comment) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(DatabaseHelper.COLUMN_COMMENT, comment);

        // Updating row
        int update = db.update(DatabaseHelper.TABLE_FAVORITES, values,
                DatabaseHelper.COLUMN_EXTERNAL_ID + "=? AND " +
                        DatabaseHelper.COLUMN_TYPE + "=?",
                new String[]{externalId, type});
        if (update > 0) {
            Log.d("DatabaseHelper", "Successfully updated comment for " + type + " with ID: " + externalId);
        } else {
            Log.e("DatabaseHelper", "Failed to update comment for " + type + " with ID: " + externalId);
        }
        db.close();
    }


}
