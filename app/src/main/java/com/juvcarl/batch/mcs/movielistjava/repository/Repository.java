package com.juvcarl.batch.mcs.movielistjava.repository;

import com.juvcarl.batch.mcs.movielistjava.repository.model.Data;

import io.reactivex.Observable;

public class Repository {

    private ApiCallInterface apiCallInterface;

    public Repository(ApiCallInterface apiCallInterface){
        this.apiCallInterface = apiCallInterface;
    }

    public Observable<Data> executeCall(){
        return apiCallInterface.getData();
    }

}
