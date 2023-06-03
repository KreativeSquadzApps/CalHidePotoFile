package com.kreativesquadz.calculatorlock.image.adapter;

import java.util.ArrayList;


public class ModelImage {
    ArrayList<String> al_imgPath;
    private boolean isSelected = false;
    String str_folder;

    public String getStr_folder() {
        return this.str_folder;
    }

    public void setStr_folder(String str) {
        this.str_folder = str;
    }

    public ArrayList<String> getAl_Imagepath() {
        return this.al_imgPath;
    }

    public void setAl_Imagepath(ArrayList<String> arrayList) {
        this.al_imgPath = arrayList;
    }

    public boolean isSelected() {
        return this.isSelected;
    }

    public void setSelected(boolean z) {
        this.isSelected = z;
    }
}
