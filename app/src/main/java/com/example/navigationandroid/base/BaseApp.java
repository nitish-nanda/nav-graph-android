package com.example.navigationandroid.base;

import androidx.multidex.MultiDexApplication;

import com.example.navigationandroid.utils.helper.SharedPreferenceHelper;

public class BaseApp extends MultiDexApplication {

    private static BaseApp instance;

    @Override
    public void onCreate() {
        super.onCreate();

        instance = this;

        SharedPreferenceHelper.init(getApplicationContext());
    }

    public static BaseApp getInstance() {
        return instance;
    }
}
