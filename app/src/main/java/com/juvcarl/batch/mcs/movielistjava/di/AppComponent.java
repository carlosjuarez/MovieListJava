package com.juvcarl.batch.mcs.movielistjava.di;

import com.juvcarl.batch.mcs.movielistjava.view.MovieListActivity;

import javax.inject.Singleton;

import dagger.Component;

@Component(modules = {UtilsModule.class})
@Singleton
public interface AppComponent {

    void doInjection(MovieListActivity movieListActivity);


}
