package com.example.anidex.Models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Anime implements Parcelable {
    private String id;
    private String type;
    private Attributes attributes;


//my stuff
    // Add fields for review and comments
    private String userReview;
    private String userComment;

    // Getters and setters for the new fields
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

    //my stuff end


    public Anime() {
    }

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

        @SerializedName("subtype") // New attribute subType
        private String subType;

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

        protected Attributes(Parcel in) {
            canonicalTitle = in.readString();
            posterImage = in.readParcelable(PosterImage.class.getClassLoader());
            subType = in.readString(); // Read subType from Parcel
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
            dest.writeString(subType); // Write subType to Parcel
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
        dest.writeString(this.id);
        dest.writeString(this.type);
        dest.writeParcelable(this.attributes, flags);
        dest.writeString(this.userReview);
        dest.writeString(this.userComment);
    }

    public void readFromParcel(Parcel source) {
        this.id = source.readString();
        this.type = source.readString();
        this.attributes = source.readParcelable(Attributes.class.getClassLoader());
        this.userReview = source.readString();
        this.userComment = source.readString();
    }

    protected Anime(Parcel in) {
        this.id = in.readString();
        this.type = in.readString();
        this.attributes = in.readParcelable(Attributes.class.getClassLoader());
        this.userReview = in.readString();
        this.userComment = in.readString();
    }

    public static final Creator<Anime> CREATOR = new Creator<Anime>() {
        @Override
        public Anime createFromParcel(Parcel source) {
            return new Anime(source);
        }

        @Override
        public Anime[] newArray(int size) {
            return new Anime[size];
        }
    };
}
