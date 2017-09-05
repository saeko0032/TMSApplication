package com.example.fukuisaeko.tmsapplication;

import java.io.Serializable;
import java.net.URL;

/**
 * Created by fukuisaeko on 2017-07-30.
 */

public class Medicine implements Serializable {
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

    public Medicine() {
        //for firebase database
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
}
