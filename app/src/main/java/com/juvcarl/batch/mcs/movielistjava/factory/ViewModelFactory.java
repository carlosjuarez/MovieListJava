package com.juvcarl.batch.mcs.movielistjava.factory;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.support.annotation.NonNull;

import com.juvcarl.batch.mcs.movielistjava.repository.Repository;
import com.juvcarl.batch.mcs.movielistjava.viewmodel.MovieListViewModel;

import javax.inject.Inject;

public class ViewModelFactory implements ViewModelProvider.Factory {

    private Repository repository;

    @Inject
    public ViewModelFactory(Repository repository) {
        this.repository = repository;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if(modelClass.isAssignableFrom(MovieListViewModel.class)){
            return (T) new MovieListViewModel(repository);
        }
        throw new IllegalArgumentException("Unknown class name");
    }
}
