package com.example.yanis.realmtestapp;

import android.app.Application;

import io.realm.Realm;

/**
 * Created by Yanis 11/21/16
 */

public class MyApplication extends Application {

    private static MyApplication instance;

    public static MyApplication getInstance() {
        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        Realm.init(this);

        instance = this;
    }


}
