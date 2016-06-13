package com.vvsai.rxjava;

import android.app.Application;

import com.squareup.leakcanary.LeakCanary;

/**
 * Created by lychee on 2016/4/7.
 */
public class MyAppcation extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        LeakCanary.install(this);
//        YlwlManager ylwlmanager = YlwlManager.getInstance(this);
    }
}
