package com.example.anidex.Database;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.anidex.Models.Anime;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "favoritesDb";
    private static final int DATABASE_VERSION = 1;

    private static final String TABLE_FAVORITES = "favorites";
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_TITLE = "title";
    private static final String COLUMN_REVIEW = "review";
    private static final String COLUMN_COMMENT = "comment";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_FAVORITES_TABLE = "CREATE TABLE " + TABLE_FAVORITES + "("
                + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + COLUMN_TITLE + " TEXT,"
                + COLUMN_REVIEW + " TEXT,"
                + COLUMN_COMMENT + " TEXT"
                + ")";
        db.execSQL(CREATE_FAVORITES_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_FAVORITES);
        onCreate(db);
    }

    public void addFavoriteAnime(Anime anime) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_TITLE, anime.getAttributes().getCanonicalTitle());
        values.put(COLUMN_REVIEW, anime.getUserReview());
        values.put(COLUMN_COMMENT, anime.getUserComment());

        db.insert(TABLE_FAVORITES, null, values);
        db.close();
    }

    public List<Anime> getAllFavoriteAnimes() {
        List<Anime> animeList = new ArrayList<>();
        String selectQuery = "SELECT * FROM " + TABLE_FAVORITES;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        int idIndex = cursor.getColumnIndex(COLUMN_ID);
        int titleIndex = cursor.getColumnIndex(COLUMN_TITLE);
        int reviewIndex = cursor.getColumnIndex(COLUMN_REVIEW);
        int commentIndex = cursor.getColumnIndex(COLUMN_COMMENT);

        if (idIndex == -1 || titleIndex == -1 || reviewIndex == -1 || commentIndex == -1) {
            // One of the columns doesn't exist in the table
            // Handle this error as needed
            cursor.close();
            db.close();
            return animeList; // Return the empty list or throw an exception
        }

        if (cursor.moveToFirst()) {
            do {
                Anime anime = new Anime();
                Anime.Attributes attributes = new Anime.Attributes();

                anime.setId(cursor.getString(idIndex));
                attributes.setCanonicalTitle(cursor.getString(titleIndex));
                anime.setUserReview(cursor.getString(reviewIndex));
                anime.setUserComment(cursor.getString(commentIndex));

                anime.setAttributes(attributes);

                animeList.add(anime);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return animeList;
    }

    public void updateFavoriteAnime(Anime anime) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_TITLE, anime.getAttributes().getCanonicalTitle());
        values.put(COLUMN_REVIEW, anime.getUserReview());
        values.put(COLUMN_COMMENT, anime.getUserComment());

        db.update(TABLE_FAVORITES, values, COLUMN_ID + " = ?", new String[] { String.valueOf(anime.getId()) });
        db.close();
    }

    public void deleteFavoriteAnime(Anime anime) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_FAVORITES, COLUMN_ID + " = ?", new String[] { String.valueOf(anime.getId()) });
        db.close();
    }


}

