package com.example.anidex.Models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Manga implements Parcelable {
    private String id;
    private String type;
    private Attributes attributes;
    private String userReview; // New field for user review
    private String userComment; // New field for user comment

    // Standard getters and setters for the new fields
    public String getUserReview() {
        return userReview;
    }

    public void setUserReview(String userReview) {
        this.userReview = userReview;
    }

    public String getUserComment() {
        return userComment;
    }

    public void setUserComment(String userComment) {
        this.userComment = userComment;
    }

    public Manga() {
    }

    protected Manga(Parcel in) {
        id = in.readString();
        type = in.readString();
        attributes = in.readParcelable(Attributes.class.getClassLoader());
        userReview = in.readString(); // Read user review from parcel
        userComment = in.readString(); // Read user comment from parcel
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

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Attributes getAttributes() {
        return attributes;
    }

    public void setAttributes(Attributes attributes) {
        this.attributes = attributes;
    }

    public static class Attributes implements Parcelable {
        @SerializedName("canonicalTitle")
        private String canonicalTitle;

        @SerializedName("posterImage")
        private PosterImage posterImage;

        @SerializedName("subtype")
        private String subType;

        @SerializedName("popularityRank")
        private int popularityRank;

        @SerializedName("ratingRank")
        private int ratingRank;

        @SerializedName("createdAt")
        private String createdAt;

        @SerializedName("synopsis")
        private String synopsis;

        @SerializedName("averageRating")
        private String averageRating;

        @SerializedName("startDate")
        private String startDate;

        @SerializedName("endDate")
        private String endDate;

        @SerializedName("chapterCount")
        private int chapterCount;

        @SerializedName("volumeCount")
        private int volumeCount;

        public String getCanonicalTitle() {
            return canonicalTitle;
        }

        public void setCanonicalTitle(String canonicalTitle) {
            this.canonicalTitle = canonicalTitle;
        }

        public PosterImage getPosterImage() {
            return posterImage;
        }

        public void setPosterImage(PosterImage posterImage) {
            this.posterImage = posterImage;
        }

        public String getSubType() {
            return subType;
        }

        public void setSubType(String subType) {
            this.subType = subType;
        }

        public int getPopularityRank() {
            return popularityRank;
        }

        public void setPopularityRank(int popularityRank) {
            this.popularityRank = popularityRank;
        }

        public int getRatingRank() {
            return ratingRank;
        }

        public void setRatingRank(int ratingRank) {
            this.ratingRank = ratingRank;
        }

        public String getCreatedAt() {
            return createdAt;
        }

        public void setCreatedAt(String createdAt) {
            this.createdAt = createdAt;
        }

        public String getSynopsis() {
            return synopsis;
        }

        public void setSynopsis(String synopsis) {
            this.synopsis = synopsis;
        }

        public String getAverageRating() {
            return averageRating;
        }

        public void setAverageRating(String averageRating) {
            this.averageRating = averageRating;
        }

        public String getStartDate() {
            return startDate;
        }

        public void setStartDate(String startDate) {
            this.startDate = startDate;
        }

        public String getEndDate() {
            return endDate;
        }

        public void setEndDate(String endDate) {
            this.endDate = endDate;
        }

        public int getChapterCount() {
            return chapterCount;
        }

        public void setChapterCount(int chapterCount) {
            this.chapterCount = chapterCount;
        }

        public int getVolumeCount() {
            return volumeCount;
        }

        public void setVolumeCount(int volumeCount) {
            this.volumeCount = volumeCount;
        }

        protected Attributes(Parcel in) {
            canonicalTitle = in.readString();
            posterImage = in.readParcelable(PosterImage.class.getClassLoader());
            subType = in.readString();
            popularityRank = in.readInt();
            ratingRank = in.readInt();
            createdAt = in.readString();
            synopsis = in.readString();
            averageRating = in.readString();
            startDate = in.readString();
            endDate = in.readString();
            chapterCount = in.readInt();
            volumeCount = in.readInt();
        }

        public static final Creator<Attributes> CREATOR = new Creator<Attributes>() {
            @Override
            public Attributes createFromParcel(Parcel in) {
                return new Attributes(in);
            }

            @Override
            public Attributes[] newArray(int size) {
                return new Attributes[size];
            }
        };

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(canonicalTitle);
            dest.writeParcelable(posterImage, flags);
            dest.writeString(subType);
            dest.writeInt(popularityRank);
            dest.writeInt(ratingRank);
            dest.writeString(createdAt);
            dest.writeString(synopsis);
            dest.writeString(averageRating);
            dest.writeString(startDate);
            dest.writeString(endDate);
            dest.writeInt(chapterCount);
            dest.writeInt(volumeCount);
        }

        public void readFromParcel(Parcel source) {
            canonicalTitle = source.readString();
            posterImage = source.readParcelable(PosterImage.class.getClassLoader());
        }

        public Attributes() {
        }
    }

    public static class PosterImage implements Parcelable {
        private String tiny;
        private String small;
        private String medium;
        private String large;
        private String original;

        public String getTiny() {
            return tiny;
        }

        public void setTiny(String tiny) {
            this.tiny = tiny;
        }

        public String getSmall() {
            return small;
        }

        public void setSmall(String small) {
            this.small = small;
        }

        public String getMedium() {
            return medium;
        }

        public void setMedium(String medium) {
            this.medium = medium;
        }

        public String getLarge() {
            return large;
        }

        public void setLarge(String large) {
            this.large = large;
        }

        public String getOriginal() {
            return original;
        }

        public void setOriginal(String original) {
            this.original = original;
        }

        protected PosterImage(Parcel in) {
            tiny = in.readString();
            small = in.readString();
            medium = in.readString();
            large = in.readString();
            original = in.readString();
        }

        public static final Creator<PosterImage> CREATOR = new Creator<PosterImage>() {
            @Override
            public PosterImage createFromParcel(Parcel in) {
                return new PosterImage(in);
            }

            @Override
            public PosterImage[] newArray(int size) {
                return new PosterImage[size];
            }
        };

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(tiny);
            dest.writeString(small);
            dest.writeString(medium);
            dest.writeString(large);
            dest.writeString(original);
        }
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(type);
        dest.writeParcelable(attributes, flags);
    }
}
