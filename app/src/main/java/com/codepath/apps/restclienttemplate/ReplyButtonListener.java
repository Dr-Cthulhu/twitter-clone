package com.codepath.apps.restclienttemplate;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.View;

import com.codepath.apps.restclienttemplate.models.Tweet;

import org.parceler.Parcels;

import static com.codepath.apps.restclienttemplate.TimelineActivity.REPLY_TWEET;

/**
 * Created by mpan on 6/30/17.
 */

public class ReplyButtonListener implements View.OnClickListener {

    Tweet mTweet;
    Context context;

    public ReplyButtonListener(Tweet tweet, Context c) {
        mTweet = tweet;
        context = c;
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent(context, ComposeActivity.class);
        intent.putExtra("function", "reply");
        intent.putExtra("tweet", Parcels.wrap(mTweet));
        ((Activity) context).startActivityForResult(intent, REPLY_TWEET);
    }
}
