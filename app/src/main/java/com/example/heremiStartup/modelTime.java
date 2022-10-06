package com.example.heremiStartup;

import java.io.Serializable;

//By: Chrstian Philip Garcia
public class modelTime implements Serializable {


    private int rem_id;
    private String time;



    public modelTime(int time_id, int rem_id, String time, String timeupdate) {

        this.rem_id = rem_id;
        this.time = time;

    }

    public modelTime() {
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


}
