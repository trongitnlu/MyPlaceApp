package com.android.nvtrong.myplace.data.model;

import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;

import java.util.Arrays;


public class Place implements Parcelable {
    private int id;
    private int categoryID;
    private byte[] image;
    private String name;
    private String address;
    private String description;
    private double placeLat;
    private double placeLng;
    private String urlIcon;

    public Place(Builder builder) {
        this.id = builder.id;
        this.categoryID = builder.categoryID;
        this.image = builder.image;
        this.name = builder.name;
        this.address = builder.address;
        this.description = builder.description;
        this.placeLat = builder.placeLat;
        this.placeLng = builder.placeLng;
        this.urlIcon = builder.urlIcon;
    }

    protected Place(Parcel in) {
        id = in.readInt();
        categoryID = in.readInt();
        image = in.createByteArray();
        name = in.readString();
        address = in.readString();
        description = in.readString();
        placeLat = in.readDouble();
        placeLng = in.readDouble();
        urlIcon = in.readString();
    }

    public static final Creator<Place> CREATOR = new Creator<Place>() {
        @Override
        public Place createFromParcel(Parcel in) {
            return new Place(in);
        }

        @Override
        public Place[] newArray(int size) {
            return new Place[size];
        }
    };

    public static boolean validate(String name, String address, String description) {
        boolean result = (TextUtils.isEmpty(name) || TextUtils.isEmpty(address) || TextUtils.isEmpty(description) ? false : true);
        return result;
    }

    public Place setId(int id) {
        this.id = id;
        return this;
    }

    public Place setCategoryID(int categoryID) {
        this.categoryID = categoryID;
        return this;
    }

    public Place setImage(byte[] image) {
        this.image = image;
        return this;
    }

    public Place setName(String name) {
        this.name = name;
        return this;
    }

    public Place setAddress(String address) {
        this.address = address;
        return this;
    }

    public Place setDescription(String description) {
        this.description = description;
        return this;
    }

    public Place setPlaceLat(double placeLat) {
        this.placeLat = placeLat;
        return this;
    }

    public Place setPlaceLng(double placeLng) {
        this.placeLng = placeLng;
        return this;
    }

    public int getId() {
        return id;
    }

    public byte[] getImage() {
        return image;
    }

    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }

    public String getDescription() {
        return description;
    }

    public double getPlaceLat() {
        return placeLat;
    }

    public double getPlaceLng() {
        return placeLng;
    }

    public int getCategoryID() {
        return categoryID;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(id);
        parcel.writeInt(categoryID);
        parcel.writeByteArray(image);
        parcel.writeString(name);
        parcel.writeString(address);
        parcel.writeString(description);
        parcel.writeDouble(placeLat);
        parcel.writeDouble(placeLng);
        parcel.writeString(urlIcon);
    }

    public static class Builder {
        private int id;
        private int categoryID;
        private byte[] image;
        private String name;
        private String address;
        private String description;
        private double placeLat;
        private double placeLng;
        private String urlIcon;

        public Builder setId(int id) {
            this.id = id;
            return this;
        }

        public Builder setImage(byte[] image) {
            this.image = image;
            return this;
        }

        public Builder setName(String name) {
            this.name = name;
            return this;
        }

        public Builder setAddress(String address) {
            this.address = address;
            return this;
        }

        public Builder setDescription(String description) {
            this.description = description;
            return this;
        }

        public Builder setPlaceLat(double placeLat) {
            this.placeLat = placeLat;
            return this;
        }

        public Builder setPlaceLng(double placeLng) {
            this.placeLng = placeLng;
            return this;
        }

        public Builder setCategoryID(int categoryID) {
            this.categoryID = categoryID;
            return this;
        }

        public String getUrlIcon() {
            return urlIcon;
        }

        public Builder setUrlIcon(String urlIcon) {
            this.urlIcon = urlIcon;
            return this;
        }

        public Place build() {
            return new Place(this);
        }
    }

    public String getUrlIcon() {
        return urlIcon;
    }

    public Place setUrlIcon(String urlIcon) {
        this.urlIcon = urlIcon;
        return this;
    }

    @Override
    public String toString() {
        return "Place{" +
                "id=" + id +
                ", categoryID=" + categoryID +
                ", image=" + Arrays.toString(image) +
                ", name='" + name + '\'' +
                ", address='" + address + '\'' +
                ", description='" + description + '\'' +
                ", placeLat=" + placeLat +
                ", placeLng=" + placeLng +
                ", urlIcon='" + urlIcon + '\'' +
                '}';
    }
}
