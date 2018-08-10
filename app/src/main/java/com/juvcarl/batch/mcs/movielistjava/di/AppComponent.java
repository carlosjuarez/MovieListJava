package com.juvcarl.batch.mcs.movielistjava.di;

import com.juvcarl.batch.mcs.movielistjava.AppModule;
import com.juvcarl.batch.mcs.movielistjava.repository.NetworkModule;
import com.juvcarl.batch.mcs.movielistjava.repository.RepositoryModule;
import com.juvcarl.batch.mcs.movielistjava.view.MovieListActivity;

import javax.inject.Singleton;

import dagger.Component;

@Component(modules = {AppModule.class, NetworkModule.class, RepositoryModule.class})
@Singleton
public interface AppComponent {

    void doInjection(MovieListActivity movieListActivity);


}
