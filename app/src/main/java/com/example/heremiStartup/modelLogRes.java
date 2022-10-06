package com.example.heremiStartup;

public class modelLogRes {
    int logID;
    int remID;
    int MedID;
    String DateCreated;
    String DateTaken;
    String Time;
    String Day;
    int timeID;
    String med_name;
    int med_dose;

    public modelLogRes(int logID, int remID, int medID, String dateCreated, String dateTaken, String time, String day, int timeID, String med_name, int med_dose) {
        this.logID = logID;
        this.remID = remID;
        MedID = medID;
        DateCreated = dateCreated;
        DateTaken = dateTaken;
        Time = time;
        Day = day;
        this.timeID = timeID;
        this.med_name = med_name;
        this.med_dose = med_dose;
    }

    public modelLogRes() {
    }

    public int getLogID() {
        return logID;
    }

    public void setLogID(int logID) {
        this.logID = logID;
    }

    public int getRemID() {
        return remID;
    }

    public void setRemID(int remID) {
        this.remID = remID;
    }

    public int getMedID() {
        return MedID;
    }

    public void setMedID(int medID) {
        MedID = medID;
    }

    public String getDateCreated() {
        return DateCreated;
    }

    public void setDateCreated(String dateCreated) {
        DateCreated = dateCreated;
    }

    public String getDateTaken() {
        return DateTaken;
    }

    public void setDateTaken(String dateTaken) {
        DateTaken = dateTaken;
    }

    public String getTime() {
        return Time;
    }

    public void setTime(String time) {
        Time = time;
    }

    public String getDay() {
        return Day;
    }

    public void setDay(String day) {
        Day = day;
    }

    public int getTimeID() {
        return timeID;
    }

    public void setTimeID(int timeID) {
        this.timeID = timeID;
    }

    public String getMed_name() {
        return med_name;
    }

    public void setMed_name(String med_name) {
        this.med_name = med_name;
    }

    public int getMed_dose() {
        return med_dose;
    }

    public void setMed_dose(int med_dose) {
        this.med_dose = med_dose;
    }
}
