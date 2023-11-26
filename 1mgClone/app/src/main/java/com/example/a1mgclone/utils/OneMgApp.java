package com.example.a1mgclone.utils;

import android.app.Application;

public class OneMgApp extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        // use the global context
        ApplicationContext.setContext(this);
    }
}
