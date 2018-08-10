package com.juvcarl.batch.mcs.movielistjava.interfaces;

import com.google.gson.JsonElement;
import com.juvcarl.batch.mcs.movielistjava.repository.model.Data;
import com.juvcarl.batch.mcs.movielistjava.util.Constants;

import io.reactivex.Observable;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;

public interface ApiCallInterface {

    @GET(Constants.MOVIES)
    Observable<Data> getData();
}
