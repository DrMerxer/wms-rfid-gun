package com.example.wms_rfid_gun;

import android.app.Application;

public class Data extends Application {
    private int usrid;

    public int getUsrid(){
        return this.usrid;
    }

    public void setUsrid(int id){
        this.usrid = id;
    }

    @Override
    public void onCreate(){
        usrid = 0;
        super.onCreate();
    }
}
