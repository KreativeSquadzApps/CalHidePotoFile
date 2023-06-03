package com.kreativesquadz.calculatorlock.model;

import android.os.Parcel;
import android.os.Parcelable;


public class AllVideosModel implements Parcelable {
    public static final Creator<AllVideosModel> CREATOR = new C06181();
    private boolean isEditable;
    private boolean isSelected;
    private long lastModified;
    private String newPath;
    private String oldPath;
    private int videoId;

    @Override
    public int describeContents() {
        return 0;
    }

    public AllVideosModel() {
        this.isEditable = false;
        this.isSelected = false;
    }


    static class C06181 implements Creator<AllVideosModel> {
        C06181() {
        }


        @Override
        public AllVideosModel createFromParcel(Parcel parcel) {
            return new AllVideosModel(parcel);
        }


        @Override
        public AllVideosModel[] newArray(int i) {
            return new AllVideosModel[i];
        }
    }

    public AllVideosModel(String str, long j) {
        this.isEditable = false;
        this.isSelected = false;
        this.oldPath = str;
        this.lastModified = j;
    }

    public AllVideosModel(String str, String str2) {
        this.isEditable = false;
        this.isSelected = false;
        this.oldPath = str;
        this.newPath = str2;
    }

    public AllVideosModel(int i, String str, String str2) {
        this.isEditable = false;
        this.isSelected = false;
        this.videoId = i;
        this.oldPath = str;
        this.newPath = str2;
    }

    public boolean isEditable() {
        return this.isEditable;
    }

    public void setEditable(boolean z) {
        this.isEditable = z;
    }

    public int getVideoId() {
        return this.videoId;
    }

    public void setVideoId(int i) {
        this.videoId = i;
    }

    public String getNewPath() {
        return this.newPath;
    }

    public void setNewPath(String str) {
        this.newPath = str;
    }

    public static Creator<AllVideosModel> getCREATOR() {
        return CREATOR;
    }

    public long getLastModified() {
        return this.lastModified;
    }

    public void setLastModified(long j) {
        this.lastModified = j;
    }

    public String getOldPath() {
        return this.oldPath;
    }

    public void setOldPath(String str) {
        this.oldPath = str;
    }

    public boolean isSelected() {
        return this.isSelected;
    }

    public void setSelected(boolean z) {
        this.isSelected = z;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.oldPath);
        parcel.writeString(this.newPath);
        parcel.writeByte(this.isSelected ? (byte) 1 : 0);
        parcel.writeLong(this.lastModified);
    }

    protected AllVideosModel(Parcel parcel) {
        boolean z = false;
        this.isEditable = false;
        this.isSelected = false;
        this.oldPath = parcel.readString();
        this.newPath = parcel.readString();
        this.isSelected = parcel.readByte() != 0 ? true : z;
        this.lastModified = parcel.readLong();
    }
}
