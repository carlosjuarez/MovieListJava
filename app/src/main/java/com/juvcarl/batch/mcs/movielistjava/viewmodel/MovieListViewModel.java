package com.juvcarl.batch.mcs.movielistjava.viewmodel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import com.google.gson.JsonElement;
import com.juvcarl.batch.mcs.movielistjava.repository.Repository;
import com.juvcarl.batch.mcs.movielistjava.repository.model.Data;
import com.juvcarl.batch.mcs.movielistjava.repository.model.Datum;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

public class MovieListViewModel extends ViewModel {

    private Repository repository;
    private final CompositeDisposable disposable = new CompositeDisposable();
    private final MutableLiveData<List<Datum>> liveDataListDatum = new MutableLiveData<>();

    public MovieListViewModel(Repository repository){
        this.repository = repository;
    }

    public MutableLiveData<List<Datum>> getLiveDataListDatum() {
        return liveDataListDatum;
    }

    public void getData(){
        disposable.add(repository.executeCall()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        this::setLiveDataValues,
                        this::showErrors
                ));
    }

    private void setLiveDataValues(Data data) {
        if(data!=null && data.getData()!=null){
            liveDataListDatum.postValue(data.getData());
        }
    }

    private void showErrors(Throwable throwable) {
        throwable.printStackTrace();
    }

    @Override
    protected void onCleared() {
        disposable.clear();
    }
}
