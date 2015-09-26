package com.hackgt2015project.application;

import android.app.Application;

/**
 * Created by ZkHaider on 9/25/15.
 */
public class DropApplication extends Application {

    private static DropApplication sDropApplication;

    public static DropApplication getInstance() {
        return sDropApplication;
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }
}
