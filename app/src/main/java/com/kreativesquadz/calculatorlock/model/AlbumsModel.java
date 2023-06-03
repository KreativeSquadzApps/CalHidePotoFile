package com.kreativesquadz.calculatorlock.model;


public class AlbumsModel {
    private int itemCount;
    private int itemIcon;
    private String itemName;

    public AlbumsModel(String str, int i, int i2) {
        this.itemName = str;
        this.itemCount = i;
        this.itemIcon = i2;
    }

    public String getItemName() {
        return this.itemName;
    }

    public void setItemName(String str) {
        this.itemName = str;
    }

    public int getItemCount() {
        return this.itemCount;
    }

    public void setItemCount(int i) {
        this.itemCount = i;
    }

    public int getItemIcon() {
        return this.itemIcon;
    }

    public void setItemIcon(int i) {
        this.itemIcon = i;
    }
}
