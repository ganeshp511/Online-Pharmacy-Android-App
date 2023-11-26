package com.example.a1mgclone.utils;

import android.content.Context;

public class ApplicationContext {
    private static Context context;

    public static Context getContext() {
        return context;
    }

    public static void setContext(Context context) {
        ApplicationContext.context = context;
    }
}
