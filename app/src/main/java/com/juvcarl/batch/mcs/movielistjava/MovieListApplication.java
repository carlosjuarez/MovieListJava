package com.juvcarl.batch.mcs.movielistjava;

import android.app.Application;
import android.content.Context;

import com.juvcarl.batch.mcs.movielistjava.di.AppComponent;
import com.juvcarl.batch.mcs.movielistjava.di.DaggerAppComponent;
import com.juvcarl.batch.mcs.movielistjava.di.UtilsModule;

public class MovieListApplication extends Application {

    AppComponent appComponent;
    Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        context = this;
        appComponent = DaggerAppComponent.builder().utilsModule(new UtilsModule()).build();
    }

    public AppComponent getAppComponent() {
        return appComponent;
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
    }
}
