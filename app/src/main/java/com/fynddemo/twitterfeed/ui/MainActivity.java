package com.fynddemo.twitterfeed.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.fynddemo.twitterfeed.R;
import com.fynddemo.twitterfeed.utils.PrefUtils;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.twitter.sdk.android.core.Callback;
import com.twitter.sdk.android.core.Result;
import com.twitter.sdk.android.core.TwitterAuthToken;
import com.twitter.sdk.android.core.TwitterCore;
import com.twitter.sdk.android.core.TwitterException;
import com.twitter.sdk.android.core.TwitterSession;
import com.twitter.sdk.android.core.identity.TwitterLoginButton;
import com.twitter.sdk.android.core.models.Tweet;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    List<Tweet> dataList = new ArrayList<>();
    TwitterLoginButton twitterLoginButton;
    PrefUtils prefUtils;
    Gson gson = new Gson();

    RecyclerView rvTweet;
    TwitterAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        twitterLoginButton = findViewById(R.id.twitterLoginButton);
        rvTweet = findViewById(R.id.rv_tweets);
        prefUtils = new PrefUtils(this);
        TwitterSession session = new Gson().fromJson(prefUtils.getSeesion(), TwitterSession.class);
//        if (session != null)
//            twitterLoginButton.setVisibility(View.VISIBLE);
//        else {
//            twitterLoginButton.setVisibility(View.GONE);
//            login(session);
//        }
        dataList = (ArrayList<Tweet>) gson.fromJson(prefUtils.getTweet(),
                new TypeToken<ArrayList<Tweet>>() {
                }.getType());
        if (dataList != null && dataList.size() > 0)
            twitterLoginButton.setVisibility(View.GONE);

        rvTweet.setLayoutManager(new LinearLayoutManager(this));
        adapter = new TwitterAdapter(this, dataList);
        rvTweet.setAdapter(adapter);
        login(null);
        twitterLoginButton.setCallback(new Callback<TwitterSession>() {
            @Override
            public void success(Result<TwitterSession> result) {

                TwitterSession session = TwitterCore.getInstance().getSessionManager().getActiveSession();
                TwitterAuthToken authToken = session.getAuthToken();
//                session.getUserId();
                Log.e("DB", session.getUserId() + " ");
                prefUtils.putSession(MainActivity.this, result.data);
                login(result.data);

            }

            @Override
            public void failure(TwitterException exception) {
                Log.e("DB", "failure");

            }
        });

    }

    private void login(TwitterSession result) {
//        TwitterSession session = result;

        TwitterCore.getInstance().getApiClient().getStatusesService().homeTimeline(800,
                null, null, null, null, false,
                false).enqueue(new Callback<List<Tweet>>() {
            @Override
            public void success(Result<List<Tweet>> result) {
                prefUtils.saveTweets(gson.toJson(result.data));
                if (dataList != null)
                    dataList.clear();
                dataList = result.data;
                adapter = new TwitterAdapter(MainActivity.this, dataList);
                rvTweet.setAdapter(adapter);
                if (dataList != null && dataList.size() > 0)
                    twitterLoginButton.setVisibility(View.GONE);
            }

            @Override
            public void failure(TwitterException exception) {

            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //Adding the login result back to the button
        twitterLoginButton.onActivityResult(requestCode, resultCode, data);

    }

}
