package com.juvcarl.batch.mcs.movielistjava;

import android.app.Application;
import android.content.Context;

import com.juvcarl.batch.mcs.movielistjava.di.AppComponent;
import com.juvcarl.batch.mcs.movielistjava.di.DaggerAppComponent;
import com.juvcarl.batch.mcs.movielistjava.repository.NetworkModule;
import com.juvcarl.batch.mcs.movielistjava.repository.RepositoryModule;

public class MovieListApplication extends Application {

    AppComponent appComponent;
    Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        context = this;
        appComponent = DaggerAppComponent.builder()
                .appModule(new AppModule(context))
                .networkModule(new NetworkModule())
                .repositoryModule(new RepositoryModule())
                .build();
    }

    public AppComponent getAppComponent() {
        return appComponent;
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
    }
}
