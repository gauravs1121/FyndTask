package com.fynddemo.twitterfeed.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.fynddemo.twitterfeed.ui.MainActivity;
import com.google.gson.Gson;
import com.twitter.sdk.android.core.TwitterSession;

public class PrefUtils {
    private SharedPreferences sharedPref;
    private SharedPreferences.Editor editor;

    private static final String TWEET_AUTH_KEY = "auth_key";
    private static final String TWEET_AUTH_SECRET_KEY = "auth_secret_key";
    private static final String TWEET_USER_NAME = "user_name";
    private static final String SHARED = "Twitter_Preferences";

    public PrefUtils(Context context) {
        sharedPref = context.getSharedPreferences(SHARED, Context.MODE_PRIVATE);
        editor = sharedPref.edit();
    }

//    public void storeAccessToken(AccessToken accessToken, String username) {
//        editor.putString(TWEET_AUTH_KEY, accessToken.getToken());
//        editor.putString(TWEET_AUTH_SECRET_KEY, accessToken.getTokenSecret());
//        editor.putString(TWEET_USER_NAME, username);
//        editor.commit();
//    }

    public void resetAccessToken() {
        editor.putString(TWEET_AUTH_KEY, null);
        editor.putString(TWEET_AUTH_SECRET_KEY, null);
        editor.putString(TWEET_USER_NAME, null);

        editor.commit();
    }

    public String getSeesion() {
        return sharedPref.getString("session", "");
    }

    public void putSession(Context context, TwitterSession twitterSession) {
        editor.putString("session", new Gson().toJson(twitterSession));
        editor.commit();

    }

    public void saveTweets(String tweets) {
        editor.putString("tweets", tweets);
        editor.commit();
    }

    public String getTweet(){
        return sharedPref.getString("tweets", null);
    }

//    public AccessToken getAccessToken() {
//        String token = sharedPref.getString(TWEET_AUTH_KEY, null);
//        String tokenSecret = sharedPref.getString(TWEET_AUTH_SECRET_KEY, null);
//
//        if (token != null && tokenSecret != null)
//            return new AccessToken(token, tokenSecret);
//        else
//            return null;
//    }
}
