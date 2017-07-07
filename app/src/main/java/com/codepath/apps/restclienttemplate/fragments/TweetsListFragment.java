package com.codepath.apps.restclienttemplate.fragments;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import com.codepath.apps.restclienttemplate.ComposeActivity;
import com.codepath.apps.restclienttemplate.ProfileActivity;
import com.codepath.apps.restclienttemplate.R;
import com.codepath.apps.restclienttemplate.TimelineActivity;
import com.codepath.apps.restclienttemplate.TweetAdapter;
import com.codepath.apps.restclienttemplate.TweetDetailsActivity;
import com.codepath.apps.restclienttemplate.TwitterClient;
import com.codepath.apps.restclienttemplate.models.Tweet;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.parceler.Parcels;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

import static com.codepath.apps.restclienttemplate.TwitterApp.getRestClient;

/**
 * Created by mpan on 7/3/17.
 */

public class TweetsListFragment extends Fragment implements TweetAdapter.TweetAdapterListener {

    public interface TweetSelectedListener {
        public void onTweetSelected(Tweet tweet);
    }
    TweetAdapter tweetAdapter;
    ArrayList<Tweet> tweets;
    RecyclerView rvTweets;

    SwipeRefreshLayout swipeLayout;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragments_tweets_list, container, false);

        rvTweets = (RecyclerView) v.findViewById(R.id.rvTweet);
        tweets = new ArrayList<>();
        tweetAdapter = new TweetAdapter(tweets, this);

        rvTweets.setLayoutManager(new LinearLayoutManager(getContext()));
        rvTweets.setAdapter(tweetAdapter);

        swipeLayout = (SwipeRefreshLayout) v.findViewById(R.id.swipe_container);
        swipeLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                populateTimeline();

            }
        });
        swipeLayout.setColorSchemeResources(R.color.twitter_blue,
                R.color.twitter_blue_50,
                R.color.twitter_blue_30,
                R.color.twitter_blue_50);

        return v;
    }

    public void populateTimeline() {}

//    public void fetchTimelineAsync(int page) {
//        getRestClient().getHomeTimeline(new JsonHttpResponseHandler() {
//            @Override
//            public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
//                // Remember to CLEAR OUT old items before appending in the new ones
//                tweetAdapter.clear();
//                // ...the data has come back, add new items to your adapter...
//                addItems(response);
//                // Now we call setRefreshing(false) to signal refresh has finished
//                swipeLayout.setRefreshing(false);
//            }
//
//            @Override
//            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
//                Log.d("DEBUG", "Fetch timeline error: " + throwable.toString());
//            }
//        });
//    }


    public void addItems(JSONArray response) {
        for (int i = 0; i < response.length(); i++) {
            Tweet tweet = null;
            try {
                tweet = Tweet.fromJSON(response.getJSONObject(i));
                tweets.add(tweet);
                tweetAdapter.notifyItemInserted(tweets.size() - 1);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

//    public void addTweet(Tweet tweet) {
//        tweets.add(0, tweet);
//        tweetAdapter.notifyItemInserted(0);
//        rvTweets.scrollToPosition(0);
//    }

    @Override
    public void onItemSelected(View view, int position) {
        final Tweet tweet = tweets.get(position);
        int id = view.getId();
        final View v = view;
        Context context = getContext();

        if (position != RecyclerView.NO_POSITION) {
            switch (id) {

                case R.id.ibReply:
//                    Log.e("DEBUG:ONCLICK", "entered reply case");
                    Intent intent = new Intent(context, ComposeActivity.class);
                    intent.putExtra("function", "reply");
                    intent.putExtra("tweet", Parcels.wrap(tweet));
                    ((Activity) context).startActivityForResult(intent, TimelineActivity.REPLY_TWEET);
                    break;

                case R.id.ibRetweet:
//                    Log.e("DEBUG:ONCLICK", "entered retweet case");
                    TwitterClient client = getRestClient();
                    client.retweetTweet(Long.toString(tweet.uid), tweet.retweeted, new JsonHttpResponseHandler() {
                        @Override
                        public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                            Log.d("DEBUG:LIKE", "success");
                            tweet.setRetweet();
                            ImageButton ibRetweet = (ImageButton) v;
                            ibRetweet.setImageResource((tweet.retweeted) ? R.drawable.ic_vector_retweet : R.drawable.ic_retweet_selector);
                        }

                        @Override
                        public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                            super.onFailure(statusCode, headers, throwable, errorResponse);
                            Log.e("DEBUG:LIKE", "could not like tweet", throwable);
                        }
                    });
                    break;

                case R.id.ibLike:
//                    Log.e("DEBUG:ONCLICK", "entered like case");
                    client = getRestClient();
                    client.likeTweet(Long.toString(tweet.uid), tweet.liked, new JsonHttpResponseHandler() {
                        @Override
                        public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                            Log.d("DEBUG:LIKE", "success");
                            tweet.setLike();
                            ImageButton ibLike = (ImageButton) v;
                            ibLike.setImageResource((tweet.liked) ? R.drawable.ic_vector_heart : R.drawable.ic_heart_selector);
                        }

                        @Override
                        public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                            super.onFailure(statusCode, headers, throwable, errorResponse);
                            Log.e("DEBUG:LIKE", "could not like tweet", throwable);
                        }
                    });
                    break;

                case R.id.ivProfileImage:
//                    Log.e("DEBUG:ONCLICK", "entered profile image case");
                    intent = new Intent(context, ProfileActivity.class);
                    intent.putExtra("user", Parcels.wrap(tweet.user));
                    intent.putExtra("screen_name", tweet.user.username);
                    context.startActivity(intent);
                    break;

                default:
//                    Log.e("DEBUG:ONCLICK", "entered default case");
                    intent = new Intent(context, TweetDetailsActivity.class);
                    intent.putExtra("tweet", Parcels.wrap(tweet));
                    context.startActivity(intent);
                    break;
            }
        }
    }
}
