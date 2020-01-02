package com.interactive.demointro.config;

import android.app.Application;
import android.content.Context;

public class App extends Application {
    private static Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();
    }

    public static App getApp(Context context) {
        if (context instanceof App) {
            return (App) context;
        }
        return (App) App.getContext();
    }

    public static Context getContext() {
        return context;
    }
}