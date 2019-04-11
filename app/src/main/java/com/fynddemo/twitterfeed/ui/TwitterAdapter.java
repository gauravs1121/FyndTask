package com.fynddemo.twitterfeed.ui;

import android.content.Context;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


import com.facebook.drawee.view.SimpleDraweeView;
import com.fynddemo.twitterfeed.R;
import com.google.gson.Gson;
import com.twitter.sdk.android.core.models.Tweet;

import java.util.List;

public class TwitterAdapter extends RecyclerView.Adapter<TwitterAdapter.TwitterHolder> {
    Context context;
    List<Tweet> dataList;

    public TwitterAdapter(Context context, List<Tweet> dataList) {
    this.context= context;
    this.dataList = dataList;
    }

    @NonNull
    @Override
    public TwitterHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int position) {
        View view = LayoutInflater.from(context).inflate(R.layout.row_twitter, viewGroup, false);
        return new TwitterHolder(view);    }

    @Override
    public void onBindViewHolder(@NonNull TwitterHolder twitterHolder, int position) {

//
        Uri uri = Uri.parse(dataList.get(position).user.profileImageUrlHttps);
        twitterHolder.userAvi.setImageURI(uri);
        twitterHolder.tvTweet.setText(dataList.get(position).text);
        twitterHolder.tvName.setText(dataList.get(position).user.name);
        twitterHolder.tvUserName.setText(String.format("@%s", dataList.get(position).user.screenName));
    }

    @Override
    public int getItemCount() {
        return dataList == null ? 0 : dataList.size();
    }

    public class TwitterHolder extends RecyclerView.ViewHolder {
        TextView tvName, tvUserName, tvTweet;
        SimpleDraweeView userAvi;
        public TwitterHolder(@NonNull View itemView) {
            super(itemView);
            userAvi = itemView.findViewById(R.id.user_avi);
            tvName = itemView.findViewById(R.id.twt_name);
            tvUserName = itemView.findViewById(R.id.twt_username);
            tvTweet = itemView.findViewById(R.id.twt_tweet);
        }
    }
}
