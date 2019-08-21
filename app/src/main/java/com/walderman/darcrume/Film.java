package com.walderman.darcrume;


public class Film {
    private int film_id;
    private int imageResource; //This will display either 2 black/white circles or 3 RGB circles to denote film type. database will need to be updated.
    private String brand;
    private String name;
    private String type;
    private int exp;
    private int iso;

    public Film(int film_id, int imageResource, String brand, String name, String type, int exp, int iso) {
        this.film_id = film_id;
        this.imageResource = imageResource;
        this.brand = brand;
        this.name = name;
        this.type = type;
        this.exp = exp;
        this.iso = iso;
    }

    public int getFilm_id() {
        return film_id;
    }

    public void setFilm_id(int film_id) {
        this.film_id = film_id;
    }

    public int getImageResource() {
        return imageResource;
    }

    public void setImageResource(int imageResource) {
        this.imageResource = imageResource;
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
}
