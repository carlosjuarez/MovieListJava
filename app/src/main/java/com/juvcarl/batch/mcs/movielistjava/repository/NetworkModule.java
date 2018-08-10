package com.juvcarl.batch.mcs.movielistjava.repository;

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import com.juvcarl.batch.mcs.movielistjava.util.Constants;

import java.io.File;
import java.util.concurrent.TimeUnit;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.Cache;
import okhttp3.CacheControl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

@Module
public class NetworkModule {

    @Provides
    @Singleton
    Gson provideGson(){
        GsonBuilder builder = new GsonBuilder();
        return builder.setLenient().create();
    }

    @Provides
    @Singleton
    Retrofit provideRetrofit(Gson gson, OkHttpClient okHttpClient){

        return new Retrofit.Builder()
                .baseUrl(Constants.BASE_URL)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create(gson)).build();
    }

    @Provides
    @Singleton
    ApiCallInterface getApiCallInterface(Retrofit retrofit){
        return retrofit.create(ApiCallInterface.class);
    }

    @Provides
    @Singleton
    OkHttpClient getRequestHeader(Context context) {
        Cache cache = new Cache(new File(context.getCacheDir(), "http-cache"), 10 * 1024 * 1024);
        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        httpClient.addInterceptor(chain -> {
            Request original = chain.request();
            Request request = original.newBuilder().build();
            return chain.proceed(request);
        })
                .addNetworkInterceptor(chain -> {
                    Response response = chain.proceed(chain.request());
                    CacheControl cacheControl = new CacheControl.Builder()
                            .maxAge(10, TimeUnit.MINUTES)
                            .build();

                    return response.newBuilder()
                            .header("Cache-Control", cacheControl.toString())
                            .build();
                })
                .cache(cache)
                .connectTimeout(100, TimeUnit.SECONDS)
                .writeTimeout(100,TimeUnit.SECONDS)
                .readTimeout(100,TimeUnit.SECONDS);

        return httpClient.build();
    }

}
