package com.juvcarl.batch.mcs.movielistjava.view;

import android.arch.lifecycle.ViewModelProviders;
import android.content.res.Configuration;
import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.SearchView;

import com.juvcarl.batch.mcs.movielistjava.MovieListApplication;
import com.juvcarl.batch.mcs.movielistjava.R;
import com.juvcarl.batch.mcs.movielistjava.adapter.RecyclerViewItemAdapter;
import com.juvcarl.batch.mcs.movielistjava.databinding.ActivityMovieListBinding;
import com.juvcarl.batch.mcs.movielistjava.factory.ViewModelFactory;
import com.juvcarl.batch.mcs.movielistjava.repository.model.Datum;
import com.juvcarl.batch.mcs.movielistjava.viewmodel.MovieListViewModel;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

public class MovieListActivity extends AppCompatActivity {

    @Inject
    ViewModelFactory viewModelFactory;

    RecyclerView recyclerView;
    RecyclerViewItemAdapter adapter;
    MovieListViewModel movieListViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityMovieListBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_movie_list);
        setSupportActionBar(binding.toolbar);

        ((MovieListApplication)getApplication()).getAppComponent().doInjection(this);

        movieListViewModel = ViewModelProviders.of(this,viewModelFactory).get(MovieListViewModel.class);

        recyclerView = binding.recyclerView;
        int columns = 2;
        if(getResources().getConfiguration().orientation != Configuration.ORIENTATION_PORTRAIT)
        {
            columns = 3;
        }
        recyclerView.setLayoutManager(new GridLayoutManager(this,columns));
        List<Datum> list = new ArrayList<>();
        adapter = new RecyclerViewItemAdapter(list,this);
        recyclerView.setAdapter(adapter);

        binding.searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                if(adapter!=null){
                    adapter.getFilter().filter(query);
                }
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if(adapter!=null){
                    adapter.getFilter().filter(newText);
                }
                return false;
            }
        });

        movieListViewModel.getLiveDataListDatum().observe(this, this::setRecyclerView);
        movieListViewModel.getData();
    }

    private void setRecyclerView(List<Datum> data) {
        adapter.addData(data);
    }
}
