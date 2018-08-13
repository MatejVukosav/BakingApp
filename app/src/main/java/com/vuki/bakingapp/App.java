package com.vuki.bakingapp;

import android.app.Application;

/**
 * Created by mvukosav
 */
public class App extends Application {

    public static Application instance;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
    }
}
