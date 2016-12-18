package com.stepstone.stepper.sample;

import android.app.Application;

import com.facebook.stetho.Stetho;

/**
 * Created by leonardo on 18/12/16.
 */

public class SampleApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Stetho.initializeWithDefaults(this);
    }
}
