package com.juvcarl.batch.mcs.movielistjava.util;

import android.databinding.BindingAdapter;
import android.widget.ImageView;

import com.juvcarl.batch.mcs.movielistjava.R;
import com.squareup.picasso.Picasso;

public class ResourceDownloader {

    @BindingAdapter("imageUrl")
    public static void loadImage(ImageView view, String url){
        Picasso.get().load(url)
                .placeholder(R.drawable.ic_file_download_black_24dp)
                .error(R.drawable.ic_error_outline_black_24dp)
                .fit()
                .centerCrop()
                .into(view);
    }
}
