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
    private static final int DATABASE_VERSION = 5;

    private static final String TABLE_FAVORITES = "favorites";
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_EXTERNAL_ID = "external_id";
    private static final String COLUMN_TITLE = "title";
    private static final String COLUMN_TYPE = "type";
    private static final String COLUMN_COMMENT = "comment"; // Added this line for the comment column

    private static final String COLUMN_SYNOPSIS = "synopsis";
    private static final String COLUMN_AVERAGE_RATING = "averageRating";
    private static final String COLUMN_START_DATE = "startDate";
    private static final String COLUMN_END_DATE = "endDate";
    private static final String COLUMN_EPISODE_COUNT = "episodeCount";
    private static final String COLUMN_POSTER_IMAGE_URL = "posterImageUrl";
    private static final String COLUMN_WATCH_STATUS = "watchStatus";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_FAVORITES_TABLE = "CREATE TABLE " + TABLE_FAVORITES + "("
                + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + COLUMN_EXTERNAL_ID + " TEXT UNIQUE,"
                + COLUMN_TITLE + " TEXT,"
                + COLUMN_TYPE + " TEXT,"
                + COLUMN_COMMENT + " TEXT,"
                + COLUMN_SYNOPSIS + " TEXT,"
                + COLUMN_AVERAGE_RATING + " TEXT,"
                + COLUMN_START_DATE + " TEXT,"
                + COLUMN_END_DATE + " TEXT,"
                + COLUMN_EPISODE_COUNT + " INTEGER,"
                + COLUMN_POSTER_IMAGE_URL + " TEXT,"
                + COLUMN_WATCH_STATUS + " TEXT" // Add this line
                + ")";
        db.execSQL(CREATE_FAVORITES_TABLE);
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
//to update db
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_FAVORITES);
        onCreate(db);
    }



    public void addFavoriteAnime(Anime anime) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_EXTERNAL_ID, anime.getId());
        values.put(COLUMN_TITLE, anime.getAttributes().getCanonicalTitle());
        values.put(COLUMN_TYPE, "anime");
        values.put(COLUMN_COMMENT, anime.getUserComment());
        // Add the new fields
        values.put(COLUMN_SYNOPSIS, anime.getAttributes().getSynopsis());
        values.put(COLUMN_AVERAGE_RATING, anime.getAttributes().getAverageRating());
        values.put(COLUMN_START_DATE, anime.getAttributes().getStartDate());
        values.put(COLUMN_END_DATE, anime.getAttributes().getEndDate());
        values.put(COLUMN_EPISODE_COUNT, anime.getAttributes().getEpisodeCount());
        values.put(COLUMN_POSTER_IMAGE_URL, anime.getAttributes().getPosterImage().getLarge());

        values.put(COLUMN_WATCH_STATUS, "toWatch");

        long result = db.insertWithOnConflict(TABLE_FAVORITES, null, values, SQLiteDatabase.CONFLICT_REPLACE);
        if (result == -1) Log.e("DatabaseHelper", "Failed to insert anime into favorites.");
        db.close();
    }

    public void addFavoriteManga(Manga manga) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_EXTERNAL_ID, manga.getId());
        values.put(COLUMN_TITLE, manga.getAttributes().getCanonicalTitle());
        values.put(COLUMN_TYPE, "manga");
        values.put(COLUMN_COMMENT, manga.getUserComment());
        // Assume Manga has similar fields; adjust as necessary
        values.put(COLUMN_SYNOPSIS, manga.getAttributes().getSynopsis());
        values.put(COLUMN_AVERAGE_RATING, manga.getAttributes().getAverageRating());
        values.put(COLUMN_START_DATE, manga.getAttributes().getStartDate());
        values.put(COLUMN_END_DATE, manga.getAttributes().getEndDate());
        values.put(COLUMN_EPISODE_COUNT, manga.getAttributes().getChapterCount()); // Use chapter count for manga
        values.put(COLUMN_POSTER_IMAGE_URL, manga.getAttributes().getPosterImage().getLarge());

        values.put(COLUMN_WATCH_STATUS, "toWatch");

        long result = db.insertWithOnConflict(TABLE_FAVORITES, null, values, SQLiteDatabase.CONFLICT_REPLACE);
        if (result == -1) Log.e("DatabaseHelper", "Failed to insert manga into favorites.");
        db.close();
    }


    public List<Object> getAllFavorites(String type) {
        List<Object> favoritesList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase(); // Use getReadableDatabase for data fetching
        Cursor cursor = db.query(TABLE_FAVORITES, null, COLUMN_TYPE + "=?", new String[]{type}, null, null, null);

        while (cursor.moveToNext()) {
            String externalId = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_EXTERNAL_ID));
            String title = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_TITLE));
            String comment = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_COMMENT));
            String synopsis = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_SYNOPSIS));
            String averageRating = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_AVERAGE_RATING));
            String startDate = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_START_DATE));
            String endDate = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_END_DATE));
            int episodeCount = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_EPISODE_COUNT));
            String posterImageUrl = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_POSTER_IMAGE_URL));
            String watchStatus = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_WATCH_STATUS));

            if ("anime".equals(type)) {
                Anime anime = new Anime();
                Anime.Attributes attributes = new Anime.Attributes();
                attributes.setCanonicalTitle(title);
                attributes.setSynopsis(synopsis);
                attributes.setAverageRating(averageRating);
                attributes.setStartDate(startDate);
                attributes.setEndDate(endDate);
                attributes.setEpisodeCount(episodeCount);
                Anime.PosterImage posterImage = new Anime.PosterImage();
                posterImage.setLarge(posterImageUrl);
                attributes.setPosterImage(posterImage);

                anime.setId(externalId);
                anime.setAttributes(attributes);
                anime.setUserComment(comment);
                anime.setWatchStatus(watchStatus);
                favoritesList.add(anime);
            } else if ("manga".equals(type)) {
                Manga manga = new Manga();
                Manga.Attributes attributes = new Manga.Attributes();
                attributes.setCanonicalTitle(title);
                attributes.setSynopsis(synopsis);
                attributes.setAverageRating(averageRating);
                attributes.setStartDate(startDate);
                attributes.setEndDate(endDate);
                attributes.setChapterCount(episodeCount); // Assuming episodeCount is used for chapter count in manga
                Manga.PosterImage posterImage = new Manga.PosterImage();
                posterImage.setLarge(posterImageUrl);
                attributes.setPosterImage(posterImage);

                manga.setId(externalId);
                manga.setAttributes(attributes);
                manga.setUserComment(comment);
                manga.setWatchStatus(watchStatus);
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

    public void updateAnimeStatus(String id, String status) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_WATCH_STATUS, status); // COLUMN_WATCH_STATUS is the name of the column storing the watch status

        db.update(TABLE_FAVORITES, values, COLUMN_EXTERNAL_ID + "=? AND " + COLUMN_TYPE + "=?", new String[]{id, "anime"});
        db.close();
    }

    public void updateMangaStatus(String id, String status) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_WATCH_STATUS, status);

        db.update(TABLE_FAVORITES, values, COLUMN_EXTERNAL_ID + "=? AND " + COLUMN_TYPE + "=?", new String[]{id, "manga"});
        db.close();
    }



}
