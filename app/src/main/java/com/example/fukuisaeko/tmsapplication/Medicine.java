package com.example.fukuisaeko.tmsapplication;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by SaekoF on 2017-09-11.
 * This class is POJO class for medicine.
 * This data is binding for firebase realtime database.
 */
public class Medicine implements Parcelable {
    private String medicineName;
    private String medicineDescription;
    private boolean isFavorite;
    private int imgUrlId;
    private boolean ableCrash;
    private String crashWarnings;
    private boolean ableCombine;
    private String combineWarnings;
    private boolean forParenatal;
    private String parenatalWarnings;
    private boolean forLactation;
    private String lactationWarnigns;
    private String infoUrl;

    public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {
        public Medicine createFromParcel(Parcel in) {
            return new Medicine(in);
        }
        public Medicine[] newArray(int size) {
            return new Medicine[size];
        }
    };

    public Medicine() {
        //for firebase database
        //it requires empty constructor
    }

    public Medicine(Parcel in){
        this.medicineName = in.readString();
        this.medicineDescription = in.readString();
        this.imgUrlId = in.readInt();
        this.isFavorite = in.readByte() != 0;
        this.ableCrash = in.readByte() != 0;
        this.crashWarnings = in.readString();
        this.ableCombine = in.readByte() != 0;
        this.combineWarnings = in.readString();
        this.forParenatal = in.readByte() != 0;
        this.parenatalWarnings = in.readString();
        this.forLactation = in.readByte() != 0;
        this.lactationWarnigns = in.readString();
        this.infoUrl = in.readString();
    }

    public Medicine(String medicineName, String medicineDescription,
                    int imgUrlId, boolean ableCrash, String crashWarnings,
                    boolean ableCombine, String combineWarnings, boolean forParenatal,
                    String parenatalWarnings, boolean forLactation, String lactationWarnigns, String infoUrl) {
        this.medicineName = medicineName;
        this.medicineDescription = medicineDescription;
        this.imgUrlId = imgUrlId;
        this.ableCrash = ableCrash;
        this.crashWarnings = crashWarnings;
        this.ableCombine = ableCombine;
        this.combineWarnings = combineWarnings;
        this.forParenatal = forParenatal;
        this.parenatalWarnings = parenatalWarnings;
        this.forLactation = forLactation;
        this.lactationWarnigns = lactationWarnigns;
        this.infoUrl = infoUrl;
    }


    public String getMedicineName() {
        return medicineName;
    }

    public void setMedicineName(String medicineName) {
        this.medicineName = medicineName;
    }

    public boolean isFavorite() {
        return isFavorite;
    }

    public void setFavorite(boolean favorite) {
        isFavorite = favorite;
    }

    public int getImgUrlId() {
        return imgUrlId;
    }

    public void setImgUrlId(int imgUrlId) {
        this.imgUrlId = imgUrlId;
    }

    public boolean isAbleCrash() {
        return ableCrash;
    }

    public void setAbleCrash(boolean ableCrash) {
        this.ableCrash = ableCrash;
    }

    public String getCrashWarnings() {
        return crashWarnings;
    }

    public void setCrashWarnings(String crashWarnings) {
        this.crashWarnings = crashWarnings;
    }

    public String getCombineWarnings() {
        return combineWarnings;
    }

    public void setCombineWarnings(String combineWarnings) {
        this.combineWarnings = combineWarnings;
    }

    public boolean isAbleCombine() {
        return ableCombine;
    }

    public void setAbleCombine(boolean ableCombine) {
        this.ableCombine = ableCombine;
    }

    public boolean isForParenatal() {
        return forParenatal;
    }

    public void setForParenatal(boolean forParenatal) {
        this.forParenatal = forParenatal;
    }

    public String getParenatalWarnings() {
        return parenatalWarnings;
    }

    public void setParenatalWarnings(String parenatalWarnings) {
        this.parenatalWarnings = parenatalWarnings;
    }

    public boolean isForLactation() {
        return forLactation;
    }

    public void setForLactation(boolean forLactation) {
        this.forLactation = forLactation;
    }

    public String getLactationWarnigns() {
        return lactationWarnigns;
    }

    public void setLactationWarnigns(String lactationWarnigns) {
        this.lactationWarnigns = lactationWarnigns;
    }

    public String getMedicineDescription() {
        return medicineDescription;
    }

    public void setMedicineDescription(String medicineDescription) {
        this.medicineDescription = medicineDescription;
    }

    public String getInfoUrl() {
        return infoUrl;
    }

    public void setInfoUrl(String infoUrl) {
        this.infoUrl = infoUrl;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int i) {
        dest.writeString(this.medicineName);
        dest.writeString(this.medicineDescription);
        dest.writeInt(this.imgUrlId);
        dest.writeByte((byte) (isFavorite ? 1 : 0));
        dest.writeByte((byte) (ableCrash ? 1 : 0));
        dest.writeString(this.crashWarnings);
        dest.writeByte((byte) (ableCombine ? 1 : 0));
        dest.writeString(this.combineWarnings);
        dest.writeByte((byte) (forParenatal ? 1 : 0));
        dest.writeString(this.parenatalWarnings);
        dest.writeByte((byte) (forLactation ? 1 : 0));
        dest.writeString(this.lactationWarnigns);
        dest.writeString(this.infoUrl);
    }
}
