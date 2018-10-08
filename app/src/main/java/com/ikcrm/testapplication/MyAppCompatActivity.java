package com.ikcrm.testapplication;

import android.app.Application;

import com.squareup.leakcanary.LeakCanary;

/**
 * Created by furuoxuan on 2018-09-29.
 */
public class MyAppCompatActivity extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        LeakCanary.install(this);
    }
}
