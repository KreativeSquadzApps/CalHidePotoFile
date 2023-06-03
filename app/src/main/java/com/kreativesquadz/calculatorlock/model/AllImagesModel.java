package com.kreativesquadz.calculatorlock.model;

import android.os.Parcel;
import android.os.Parcelable;


public class AllImagesModel implements Parcelable {
    public static final Creator<AllImagesModel> CREATOR = new C06171();
    private int imageId;
    private long imageLastModified;
    private String imagePath;
    private boolean isEditable;
    private boolean isSelected;
    private String newPath;

    @Override
    public int describeContents() {
        return 0;
    }

    public AllImagesModel() {
        this.isEditable = false;
        this.isSelected = false;
    }


    static class C06171 implements Creator<AllImagesModel> {
        C06171() {
        }


        @Override
        public AllImagesModel createFromParcel(Parcel parcel) {
            return new AllImagesModel(parcel);
        }


        @Override
        public AllImagesModel[] newArray(int i) {
            return new AllImagesModel[i];
        }
    }

    public boolean isEditable() {
        return this.isEditable;
    }

    public void setEditable(boolean z) {
        this.isEditable = z;
    }

    public AllImagesModel(String str, long j) {
        this.isEditable = false;
        this.isSelected = false;
        this.imagePath = str;
        this.imageLastModified = j;
    }

    public AllImagesModel(String str, String str2) {
        this.isEditable = false;
        this.isSelected = false;
        this.imagePath = str;
        this.newPath = str2;
    }

    public AllImagesModel(int i, String str, String str2) {
        this.isEditable = false;
        this.isSelected = false;
        this.imageId = i;
        this.imagePath = str;
        this.newPath = str2;
    }

    public int getImageId() {
        return this.imageId;
    }

    public void setImageId(int i) {
        this.imageId = i;
    }

    public String getNewPath() {
        return this.newPath;
    }

    public void setNewPath(String str) {
        this.newPath = str;
    }

    public static Creator<AllImagesModel> getCREATOR() {
        return CREATOR;
    }

    public long getImageLastModified() {
        return this.imageLastModified;
    }

    public void setImageLastModified(long j) {
        this.imageLastModified = j;
    }

    public String getImagePath() {
        return this.imagePath;
    }

    public void setImagePath(String str) {
        this.imagePath = str;
    }

    public boolean isSelected() {
        return this.isSelected;
    }

    public void setSelected(boolean z) {
        this.isSelected = z;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(this.imageId);
        parcel.writeString(this.imagePath);
        parcel.writeString(this.newPath);
        parcel.writeByte(this.isSelected ? (byte) 1 : 0);
        parcel.writeByte(this.isEditable ? (byte) 1 : 0);
        parcel.writeLong(this.imageLastModified);
    }

    protected AllImagesModel(Parcel parcel) {
        boolean z = false;
        this.isEditable = false;
        this.isSelected = false;
        this.imageId = parcel.readInt();
        this.imagePath = parcel.readString();
        this.newPath = parcel.readString();
        this.isSelected = parcel.readByte() != 0;
        this.isEditable = parcel.readByte() != 0 ? true : z;
        this.imageLastModified = parcel.readLong();
    }
}
