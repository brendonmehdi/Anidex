package com.example.anidex.Models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Manga implements Parcelable {
    private String id;
    private String type;
    private Attributes attributes;

    public Manga() {
    }

    protected Manga(Parcel in) {
        id = in.readString();
        type = in.readString();
        attributes = in.readParcelable(Attributes.class.getClassLoader());
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

        protected Attributes(Parcel in) {
            canonicalTitle = in.readString();
            posterImage = in.readParcelable(PosterImage.class.getClassLoader());
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
