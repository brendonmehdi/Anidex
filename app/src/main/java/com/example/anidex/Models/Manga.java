package com.example.anidex.Models;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

public class Manga implements Parcelable {
    private String id;
    private String type;
    private String synopsis;
    private String canonicalTitle;
    private String posterImage;
    private List<String> genres;
    private String startDate;
    private String endDate;
    private String status;
    private int chapterCount;
    private double averageRating;
    private String ageRating;
    private String ageRatingGuide;
    private String youtubeVideoId;
    private String mangaType;
    private String youtubeVideoUrl;
    private String coverImage;
    private String trailerVideoUrl;
    private String volumeCount;
    private String volumeTitle;
    private List<String> authors;
    private List<String> artists;
    private String serialization;
    private List<String> characters;
    private List<String> staff;
    private String franchise;
    private List<String> publishers;

    public Manga() {

    }

    public Manga(String id, String type, String synopsis, String canonicalTitle, String posterImage, List<String> genres, String startDate, String endDate, String status, int chapterCount, double averageRating, String ageRating, String ageRatingGuide, String youtubeVideoId, String mangaType, String youtubeVideoUrl, String coverImage, String trailerVideoUrl, String volumeCount, String volumeTitle, List<String> authors, List<String> artists, String serialization, List<String> characters, List<String> staff, String franchise, List<String> publishers) {
        this.id = id;
        this.type = type;
        this.synopsis = synopsis;
        this.canonicalTitle = canonicalTitle;
        this.posterImage = posterImage;
        this.genres = genres;
        this.startDate = startDate;
        this.endDate = endDate;
        this.status = status;
        this.chapterCount = chapterCount;
        this.averageRating = averageRating;
        this.ageRating = ageRating;
        this.ageRatingGuide = ageRatingGuide;
        this.youtubeVideoId = youtubeVideoId;
        this.mangaType = mangaType;
        this.youtubeVideoUrl = youtubeVideoUrl;
        this.coverImage = coverImage;
        this.trailerVideoUrl = trailerVideoUrl;
        this.volumeCount = volumeCount;
        this.volumeTitle = volumeTitle;
        this.authors = authors;
        this.artists = artists;
        this.serialization = serialization;
        this.characters = characters;
        this.staff = staff;
        this.franchise = franchise;
        this.publishers = publishers;
    }

    protected Manga(Parcel in) {
        id = in.readString();
        type = in.readString();
        synopsis = in.readString();
        canonicalTitle = in.readString();
        posterImage = in.readString();
        genres = in.createStringArrayList();
        startDate = in.readString();
        endDate = in.readString();
        status = in.readString();
        chapterCount = in.readInt();
        averageRating = in.readDouble();
        ageRating = in.readString();
        ageRatingGuide = in.readString();
        youtubeVideoId = in.readString();
        mangaType = in.readString();
        youtubeVideoUrl = in.readString();
        coverImage = in.readString();
        trailerVideoUrl = in.readString();
        volumeCount = in.readString();
        volumeTitle = in.readString();
        authors = in.createStringArrayList();
        artists = in.createStringArrayList();
        serialization = in.readString();
        characters = in.createStringArrayList();
        staff = in.createStringArrayList();
        franchise = in.readString();
        publishers = in.createStringArrayList();
    }

    public static final Creator<Manga> CREATOR = new Creator<Manga>() {
        @Override
        public Manga createFromParcel(Parcel in) {
            return new Manga(in);
        }

        @Override
        public Manga[] newArray(int size) {
            return new Manga[size];
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
        dest.writeInt(chapterCount);
        dest.writeDouble(averageRating);
        dest.writeString(ageRating);
        dest.writeString(ageRatingGuide);
        dest.writeString(youtubeVideoId);
        dest.writeString(mangaType);
        dest.writeString(youtubeVideoUrl);
        dest.writeString(coverImage);
        dest.writeString(trailerVideoUrl);
        dest.writeString(volumeCount);
        dest.writeString(volumeTitle);
        dest.writeStringList(authors);
        dest.writeStringList(artists);
        dest.writeString(serialization);
        dest.writeStringList(characters);
        dest.writeStringList(staff);
        dest.writeString(franchise);
        dest.writeStringList(publishers);
    }

    public String getId() {
        return id;
    }

    public Manga setId(String id) {
        this.id = id;
        return this;
    }

    public String getType() {
        return type;
    }

    public Manga setType(String type) {
        this.type = type;
        return this;
    }

    public String getSynopsis() {
        return synopsis;
    }

    public Manga setSynopsis(String synopsis) {
        this.synopsis = synopsis;
        return this;
    }

    public String getCanonicalTitle() {
        return canonicalTitle;
    }

    public Manga setCanonicalTitle(String canonicalTitle) {
        this.canonicalTitle = canonicalTitle;
        return this;
    }

    public String getPosterImage() {
        return posterImage;
    }

    public Manga setPosterImage(String posterImage) {
        this.posterImage = posterImage;
        return this;
    }

    public List<String> getGenres() {
        return genres;
    }

    public Manga setGenres(List<String> genres) {
        this.genres = genres;
        return this;
    }

    public String getStartDate() {
        return startDate;
    }

    public Manga setStartDate(String startDate) {
        this.startDate = startDate;
        return this;
    }

    public String getEndDate() {
        return endDate;
    }

    public Manga setEndDate(String endDate) {
        this.endDate = endDate;
        return this;
    }

    public String getStatus() {
        return status;
    }

    public Manga setStatus(String status) {
        this.status = status;
        return this;
    }

    public int getChapterCount() {
        return chapterCount;
    }

    public Manga setChapterCount(int chapterCount) {
        this.chapterCount = chapterCount;
        return this;
    }

    public double getAverageRating() {
        return averageRating;
    }

    public Manga setAverageRating(double averageRating) {
        this.averageRating = averageRating;
        return this;
    }

    public String getAgeRating() {
        return ageRating;
    }

    public Manga setAgeRating(String ageRating) {
        this.ageRating = ageRating;
        return this;
    }

    public String getAgeRatingGuide() {
        return ageRatingGuide;
    }

    public Manga setAgeRatingGuide(String ageRatingGuide) {
        this.ageRatingGuide = ageRatingGuide;
        return this;
    }

    public String getYoutubeVideoId() {
        return youtubeVideoId;
    }

    public Manga setYoutubeVideoId(String youtubeVideoId) {
        this.youtubeVideoId = youtubeVideoId;
        return this;
    }

    public String getMangaType() {
        return mangaType;
    }

    public Manga setMangaType(String mangaType) {
        this.mangaType = mangaType;
        return this;
    }

    public String getYoutubeVideoUrl() {
        return youtubeVideoUrl;
    }

    public Manga setYoutubeVideoUrl(String youtubeVideoUrl) {
        this.youtubeVideoUrl = youtubeVideoUrl;
        return this;
    }

    public String getCoverImage() {
        return coverImage;
    }

    public Manga setCoverImage(String coverImage) {
        this.coverImage = coverImage;
        return this;
    }

    public String getTrailerVideoUrl() {
        return trailerVideoUrl;
    }

    public Manga setTrailerVideoUrl(String trailerVideoUrl) {
        this.trailerVideoUrl = trailerVideoUrl;
        return this;
    }

    public String getVolumeCount() {
        return volumeCount;
    }

    public Manga setVolumeCount(String volumeCount) {
        this.volumeCount = volumeCount;
        return this;
    }

    public String getVolumeTitle() {
        return volumeTitle;
    }

    public Manga setVolumeTitle(String volumeTitle) {
        this.volumeTitle = volumeTitle;
        return this;
    }

    public List<String> getAuthors() {
        return authors;
    }

    public Manga setAuthors(List<String> authors) {
        this.authors = authors;
        return this;
    }

    public List<String> getArtists() {
        return artists;
    }

    public Manga setArtists(List<String> artists) {
        this.artists = artists;
        return this;
    }

    public String getSerialization() {
        return serialization;
    }

    public Manga setSerialization(String serialization) {
        this.serialization = serialization;
        return this;
    }

    public List<String> getCharacters() {
        return characters;
    }

    public Manga setCharacters(List<String> characters) {
        this.characters = characters;
        return this;
    }

    public List<String> getStaff() {
        return staff;
    }

    public Manga setStaff(List<String> staff) {
        this.staff = staff;
        return this;
    }

    public String getFranchise() {
        return franchise;
    }

    public Manga setFranchise(String franchise) {
        this.franchise = franchise;
        return this;
    }

    public List<String> getPublishers() {
        return publishers;
    }

    public Manga setPublishers(List<String> publishers) {
        this.publishers = publishers;
        return this;
    }
}
