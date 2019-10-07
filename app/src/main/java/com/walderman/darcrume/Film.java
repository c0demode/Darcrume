package com.walderman.darcrume;


import android.os.Parcel;
import android.os.Parcelable;

public class Film implements Parcelable {
    private int film_id;
    private String brand;
    private String name;
    private String type;
    private int exp;
    private int iso;

    public Film(int film_id, String brand, String name, String type, int exp, int iso) {
        this.film_id = film_id;
        this.brand = brand;
        this.name = name;
        this.type = type;
        this.exp = exp;
        this.iso = iso;
    }

    public Film() {
        this.film_id = -1;
        this.brand = "Error";
        this.name = "Error";
        this.type = "Error";
        this.exp = -1;
        this.iso = -1;
    }

    protected Film(Parcel in) {
        film_id = in.readInt();
        brand = in.readString();
        name = in.readString();
        type = in.readString();
        exp = in.readInt();
        iso = in.readInt();
    }

    public static final Creator<Film> CREATOR = new Creator<Film>() {
        @Override
        public Film createFromParcel(Parcel in) {
            return new Film(in);
        }

        @Override
        public Film[] newArray(int size) {
            return new Film[size];
        }
    };

    public int getFilm_id() {
        return film_id;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getExp() {
        return exp;
    }

    public void setExp(int exp) {
        this.exp = exp;
    }

    public int getIso() {
        return iso;
    }

    public void setIso(int iso) {
        this.iso = iso;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(film_id);
        parcel.writeString(brand);
        parcel.writeString(name);
        parcel.writeString(type);
        parcel.writeInt(exp);
        parcel.writeInt(iso);
    }
}
