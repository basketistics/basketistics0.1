package de.berlin.hwr.basketistics.UI;

import android.content.Context;
import android.util.Log;

import com.bumptech.glide.GlideBuilder;
import com.bumptech.glide.annotation.GlideModule;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.cache.MemoryCache;
import com.bumptech.glide.module.AppGlideModule;
import com.bumptech.glide.request.RequestOptions;

@GlideModule
public class BasketisticsGlideModule extends AppGlideModule {

    @Override
    public void applyOptions(Context context, GlideBuilder builder) {

        RequestOptions requestOptions = new RequestOptions()
                // .skipMemoryCache(true)
                // .onlyRetrieveFromCache(false)
                .diskCacheStrategy(DiskCacheStrategy.NONE);

        builder.setDefaultRequestOptions(requestOptions);
        builder.setLogLevel(Log.VERBOSE);
    }

}
