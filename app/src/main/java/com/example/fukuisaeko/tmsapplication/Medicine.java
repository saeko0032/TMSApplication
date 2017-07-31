package com.example.fukuisaeko.tmsapplication;

import java.io.Serializable;

/**
 * Created by fukuisaeko on 2017-07-30.
 */

public class Medicine implements Serializable {
    private String medicineName;
    private boolean isFavorite;
    private int[] imgUrl;
    private boolean ableCrash;
    private String[] crashWarnings;
    private boolean ableCombine;
    private String[] combineWarnings;
    private boolean forPrenatal;
    private String[] parentalWarnings;
    private boolean forLactation;
    private String[] lactationWarnigns;

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

    public int[] getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(int[] imgUrl) {
        this.imgUrl = imgUrl;
    }

    public boolean isAbleCrash() {
        return ableCrash;
    }

    public void setAbleCrash(boolean ableCrash) {
        this.ableCrash = ableCrash;
    }

    public String[] getCrashWarnings() {
        return crashWarnings;
    }

    public void setCrashWarnings(String[] crashWarnings) {
        this.crashWarnings = crashWarnings;
    }

    public String[] getCombineWarnings() {
        return combineWarnings;
    }

    public void setCombineWarnings(String[] combineWarnings) {
        this.combineWarnings = combineWarnings;
    }

    public boolean isAbleCombine() {
        return ableCombine;
    }

    public void setAbleCombine(boolean ableCombine) {
        this.ableCombine = ableCombine;
    }

    public boolean isForPrenatal() {
        return forPrenatal;
    }

    public void setForPrenatal(boolean forPrenatal) {
        this.forPrenatal = forPrenatal;
    }

    public String[] getParentalWarnings() {
        return parentalWarnings;
    }

    public void setParentalWarnings(String[] parentalWarnings) {
        this.parentalWarnings = parentalWarnings;
    }

    public boolean isForLactation() {
        return forLactation;
    }

    public void setForLactation(boolean forLactation) {
        this.forLactation = forLactation;
    }

    public String[] getLactationWarnigns() {
        return lactationWarnigns;
    }

    public void setLactationWarnigns(String[] lactationWarnigns) {
        this.lactationWarnigns = lactationWarnigns;
    }

}
