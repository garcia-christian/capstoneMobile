package com.example.heremiStartup;

import java.io.Serializable;

//By: Chrstian Philip Garcia
public class modelTimeRes implements Serializable {

    private  int time_id;
    private int rem_id;
    private String time;
    private String timeupdate;


    public modelTimeRes(int time_id, int rem_id, String time, String timeupdate) {
        this.time_id = time_id;
        this.rem_id = rem_id;
        this.time = time;
        this.timeupdate = timeupdate;
    }

    public modelTimeRes() {
    }



    public int getTime_id() {
        return time_id;
    }

    public void setTime_id(int time_id) {
        this.time_id = time_id;
    }

    public int getRem_id() {
        return rem_id;
    }

    public void setRem_id(int rem_id) {
        this.rem_id = rem_id;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getTimeupdate() {
        return timeupdate;
    }

    public void setTimeupdate(String timeupdate) {
        this.timeupdate = timeupdate;
    }
}
