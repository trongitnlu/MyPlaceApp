package com.android.nvtrong.myplace.data.model;

/**
 * Created by nvtrong on 4/4/2018.
 */

public class Place {
    private int id;
    private int categoryID;
    private byte[] image;
    private String name;
    private String address;
    private String description;
    private double placeLat;
    private double placeLng;

    public Place(Builder builder) {
        this.id = builder.id;
        this.categoryID = builder.categoryID;
        this.image = builder.image;
        this.name = builder.name;
        this.address = builder.address;
        this.description = builder.description;
        this.placeLat = builder.placeLat;
        this.placeLng = builder.placeLng;
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

    public static class Builder {
        private int id;
        private int categoryID;
        private byte[] image;
        private String name;
        private String address;
        private String description;
        private double placeLat;
        private double placeLng;

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

        public Place build() {
            return new Place(this);
        }
    }
}
