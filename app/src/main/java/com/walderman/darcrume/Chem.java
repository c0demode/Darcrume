package com.walderman.darcrume;

class Chem {
    private int chemId;
    private String brand;
    private String name;
    private String bw_Color;
    private String chemRole;

    public Chem() {

    }

    public Chem(int chemId, String brand, String name, String bw_Color, String chemRole) {
        this.chemId = chemId;
        this.brand = brand;
        this.name = name;
        this.bw_Color = bw_Color;
        this.chemRole = chemRole;
    }

    public int getChemId() {
        return chemId;
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

    public String getBw_Color() {
        return bw_Color;
    }

    public void setBw_Color(String bw_Color) {
        this.bw_Color = bw_Color;
    }

    public String getChemRole() {
        return chemRole;
    }

    public void setChemRole(String chemRole) {
        this.chemRole = chemRole;
    }
}
