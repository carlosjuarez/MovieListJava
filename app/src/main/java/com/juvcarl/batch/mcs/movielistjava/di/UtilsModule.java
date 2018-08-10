package com.juvcarl.batch.mcs.movielistjava.di;

import android.arch.lifecycle.ViewModelProvider;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import com.juvcarl.batch.mcs.movielistjava.factory.ViewModelFactory;
import com.juvcarl.batch.mcs.movielistjava.interfaces.ApiCallInterface;
import com.juvcarl.batch.mcs.movielistjava.repository.Repository;
import com.juvcarl.batch.mcs.movielistjava.util.Constants;

import java.util.concurrent.TimeUnit;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

@Module
public class UtilsModule {

    @Provides
    @Singleton
    Gson provideGson(){
        GsonBuilder builder = new GsonBuilder();
        return builder.setLenient().create();
    }

    @Provides
    @Singleton
    Retrofit provideRetrofit(Gson gson, OkHttpClient okHttpClient){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constants.BASE_URL)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create(gson)).build();

        return retrofit;
    }

    @Provides
    @Singleton
    ApiCallInterface getApiCallInterface(Retrofit retrofit){
        return retrofit.create(ApiCallInterface.class);
    }

    @Provides
    @Singleton
    Repository getRepository(ApiCallInterface apiCallInterface){
        return new Repository(apiCallInterface);
    }

    @Provides
    @Singleton
    OkHttpClient getRequestHeader(){
        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        httpClient.addInterceptor(chain -> {
            Request original = chain.request();
            Request request = original.newBuilder().build();
            return chain.proceed(request);
        }).connectTimeout(100, TimeUnit.SECONDS)
                .writeTimeout(100,TimeUnit.SECONDS)
                .readTimeout(100,TimeUnit.SECONDS);

        OkHttpClient client = httpClient.build();
        return client;
    }

    @Provides
    @Singleton
    ViewModelProvider.Factory getViewModelFactory(Repository repository){
        return new ViewModelFactory(repository);
    }


}
