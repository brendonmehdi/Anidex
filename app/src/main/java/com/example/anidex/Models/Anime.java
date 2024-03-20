package com.example.anidex.Models;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

public class Anime implements Parcelable {
    private String id;
    private String type;
    private String synopsis;
    private String canonicalTitle;
    private String posterImage;
    private List<String> genres;
    private String startDate;
    private String endDate;
    private String status;
    private int episodeCount;
    private int episodeLength;
    private double averageRating;
    private String ageRating;
    private String ageRatingGuide;
    private String youtubeVideoId;
    private String animeType;
    private String youtubeVideoUrl;
    private String coverImage;
    private String trailerVideoUrl;
    private int totalLength;
    private List<String> categories;
    private List<String> producers;

    public Anime() {

    }

    public String getId() {
        return id;
    }

    public Anime setId(String id) {
        this.id = id;
        return this;
    }

    public String getType() {
        return type;
    }

    public Anime setType(String type) {
        this.type = type;
        return this;
    }

    public String getSynopsis() {
        return synopsis;
    }

    public Anime setSynopsis(String synopsis) {
        this.synopsis = synopsis;
        return this;
    }

    public String getCanonicalTitle() {
        return canonicalTitle;
    }

    public Anime setCanonicalTitle(String canonicalTitle) {
        this.canonicalTitle = canonicalTitle;
        return this;
    }

    public String getPosterImage() {
        return posterImage;
    }

    public Anime setPosterImage(String posterImage) {
        this.posterImage = posterImage;
        return this;
    }

    public List<String> getGenres() {
        return genres;
    }

    public Anime setGenres(List<String> genres) {
        this.genres = genres;
        return this;
    }

    public String getStartDate() {
        return startDate;
    }

    public Anime setStartDate(String startDate) {
        this.startDate = startDate;
        return this;
    }

    public String getEndDate() {
        return endDate;
    }

    public Anime setEndDate(String endDate) {
        this.endDate = endDate;
        return this;
    }

    public String getStatus() {
        return status;
    }

    public Anime setStatus(String status) {
        this.status = status;
        return this;
    }

    public int getEpisodeCount() {
        return episodeCount;
    }

    public Anime setEpisodeCount(int episodeCount) {
        this.episodeCount = episodeCount;
        return this;
    }

    public int getEpisodeLength() {
        return episodeLength;
    }

    public Anime setEpisodeLength(int episodeLength) {
        this.episodeLength = episodeLength;
        return this;
    }

    public double getAverageRating() {
        return averageRating;
    }

    public Anime setAverageRating(double averageRating) {
        this.averageRating = averageRating;
        return this;
    }

    public String getAgeRating() {
        return ageRating;
    }

    public Anime setAgeRating(String ageRating) {
        this.ageRating = ageRating;
        return this;
    }

    public String getAgeRatingGuide() {
        return ageRatingGuide;
    }

    public Anime setAgeRatingGuide(String ageRatingGuide) {
        this.ageRatingGuide = ageRatingGuide;
        return this;
    }

    public String getYoutubeVideoId() {
        return youtubeVideoId;
    }

    public Anime setYoutubeVideoId(String youtubeVideoId) {
        this.youtubeVideoId = youtubeVideoId;
        return this;
    }

    public String getAnimeType() {
        return animeType;
    }

    public Anime setAnimeType(String animeType) {
        this.animeType = animeType;
        return this;
    }

    public String getYoutubeVideoUrl() {
        return youtubeVideoUrl;
    }

    public Anime setYoutubeVideoUrl(String youtubeVideoUrl) {
        this.youtubeVideoUrl = youtubeVideoUrl;
        return this;
    }

    public String getCoverImage() {
        return coverImage;
    }

    public Anime setCoverImage(String coverImage) {
        this.coverImage = coverImage;
        return this;
    }

    public String getTrailerVideoUrl() {
        return trailerVideoUrl;
    }

    public Anime setTrailerVideoUrl(String trailerVideoUrl) {
        this.trailerVideoUrl = trailerVideoUrl;
        return this;
    }

    public int getTotalLength() {
        return totalLength;
    }

    public Anime setTotalLength(int totalLength) {
        this.totalLength = totalLength;
        return this;
    }

    public List<String> getCategories() {
        return categories;
    }

    public Anime setCategories(List<String> categories) {
        this.categories = categories;
        return this;
    }

    public List<String> getProducers() {
        return producers;
    }

    public Anime setProducers(List<String> producers) {
        this.producers = producers;
        return this;
    }

    protected Anime(Parcel in) {
        id = in.readString();
        type = in.readString();
        synopsis = in.readString();
        canonicalTitle = in.readString();
        posterImage = in.readString();
        genres = in.createStringArrayList();
        startDate = in.readString();
        endDate = in.readString();
        status = in.readString();
        episodeCount = in.readInt();
        episodeLength = in.readInt();
        averageRating = in.readDouble();
        ageRating = in.readString();
        ageRatingGuide = in.readString();
        youtubeVideoId = in.readString();
        animeType = in.readString();
        youtubeVideoUrl = in.readString();
        coverImage = in.readString();
        trailerVideoUrl = in.readString();
        totalLength = in.readInt();
        categories = in.createStringArrayList();
        producers = in.createStringArrayList();
    }

    public static final Creator<Anime> CREATOR = new Creator<Anime>() {
        @Override
        public Anime createFromParcel(Parcel in) {
            return new Anime(in);
        }

        @Override
        public Anime[] newArray(int size) {
            return new Anime[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(type);
        dest.writeString(synopsis);
        dest.writeString(canonicalTitle);
        dest.writeString(posterImage);
        dest.writeStringList(genres);
        dest.writeString(startDate);
        dest.writeString(endDate);
        dest.writeString(status);
        dest.writeInt(episodeCount);
        dest.writeInt(episodeLength);
        dest.writeDouble(averageRating);
        dest.writeString(ageRating);
        dest.writeString(ageRatingGuide);
        dest.writeString(youtubeVideoId);
        dest.writeString(animeType);
        dest.writeString(youtubeVideoUrl);
        dest.writeString(coverImage);
        dest.writeString(trailerVideoUrl);
        dest.writeInt(totalLength);
        dest.writeStringList(categories);
        dest.writeStringList(producers);
    }

    // Getters and Setters
    // You would need to generate getters and setters for all fields
    // For brevity, they are not included here
}
