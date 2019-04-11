package com.fynddemo.twitterfeed;

import android.app.Application;
import android.support.multidex.MultiDex;
import android.util.Log;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.twitter.sdk.android.core.DefaultLogger;
import com.twitter.sdk.android.core.Twitter;
import com.twitter.sdk.android.core.TwitterAuthConfig;
import com.twitter.sdk.android.core.TwitterConfig;

public class FeedApplication extends Application {
    TwitterConfig config;
    @Override
    public void onCreate() {
        super.onCreate();
        MultiDex.install(this);

        config =  new TwitterConfig.Builder(this)
                .logger(new DefaultLogger(Log.DEBUG))
                .twitterAuthConfig(new TwitterAuthConfig(getResources().getString(R.string.API_KEY),
                        getResources().getString(R.string.SECRET_KEY)))
                .debug(true)
                .build();
        Twitter.initialize(config);
        Fresco.initialize(this);
        MultiDex.install(this);

    }
}
