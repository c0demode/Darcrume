package com.walderman.darcrume;

class Chem {
    private int chemID;
    private String chemBrand;
    private String chemName;

    public Chem(int chemID, String chemBrand, String chemName) {
        this.chemID = chemID;
        this.chemBrand = chemBrand;
        this.chemName = chemName;
    }

    public int getChemID() {
        return chemID;
    }

    public void setChemID(int chemID) {
        this.chemID = chemID;
    }

    public String getChemBrand() {
        return chemBrand;
    }

    public void setChemBrand(String chemBrand) {
        this.chemBrand = chemBrand;
    }

    public String getChemName() {
        return chemName;
    }

    public void setChemName(String chemName) {
        this.chemName = chemName;
    }
}
