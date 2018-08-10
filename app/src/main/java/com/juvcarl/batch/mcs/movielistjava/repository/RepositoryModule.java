package com.juvcarl.batch.mcs.movielistjava.repository;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class RepositoryModule {

    @Provides
    @Singleton
    Repository getRepository(ApiCallInterface apiCallInterface) {
        return new Repository(apiCallInterface);
    }
}
