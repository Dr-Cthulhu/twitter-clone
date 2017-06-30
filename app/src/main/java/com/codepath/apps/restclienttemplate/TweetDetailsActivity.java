package com.codepath.apps.restclienttemplate;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.codepath.apps.restclienttemplate.models.Tweet;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONObject;
import org.parceler.Parcels;

import cz.msebera.android.httpclient.Header;
import jp.wasabeef.glide.transformations.RoundedCornersTransformation;

public class TweetDetailsActivity extends AppCompatActivity {

    ImageButton ibRetweet;
    ImageButton ibLike;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tweet_details);

        ImageView ivProfileImage = (ImageView) findViewById(R.id.ivProfileImage);
        TextView tvName = (TextView) findViewById(R.id.tvName);
        TextView tvUsername = (TextView) findViewById(R.id.tvUsername);
        TextView tvBody = (TextView) findViewById(R.id.tvBody);
        TextView tvTime = (TextView) findViewById(R.id.tvTime);
        ImageView ivMedia = (ImageView) findViewById(R.id.ivMedia);

        ImageButton ibReply = (ImageButton) findViewById(R.id.ibReply);
        ibRetweet = (ImageButton) findViewById(R.id.ibRetweet);
        ibLike = (ImageButton) findViewById(R.id.ibLike);

        Intent intent = getIntent();
        final Tweet tweet = (Tweet) Parcels.unwrap(intent.getParcelableExtra("tweet"));

        tvName.setText(tweet.user.name);
        tvUsername.setText("@" + tweet.user.username);
        tvBody.setText(tweet.body);
        tvTime.setText(tweet.createdAt);

        Glide.with(this)
                .load(tweet.user.profileImageUrl)
                .bitmapTransform(new RoundedCornersTransformation(this, 5, 0))
                .into(ivProfileImage);

        if (!tweet.imageUrl.equals("")) {
            ivMedia.setVisibility(View.VISIBLE);
            Glide.with(this)
                    .load(tweet.imageUrl)
                    .bitmapTransform(new RoundedCornersTransformation(this, 5, 0))
                    .into(ivMedia);
        } else {
            ivMedia.setVisibility(View.GONE);
        }

        ibRetweet.setImageResource((tweet.retweeted) ? R.drawable.ic_vector_retweet : R.drawable.ic_vector_retweet_stroke);
        ibLike.setImageResource((tweet.liked) ? R.drawable.ic_vector_heart : R.drawable.ic_vector_heart_stroke);

        ibReply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(TweetDetailsActivity.this, ComposeActivity.class);
                i.putExtra("function", "reply");
                i.putExtra("tweet", Parcels.wrap(tweet));
                startActivity(i);
            }
        });

        ibRetweet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TwitterClient client = TwitterApp.getRestClient();
                client.retweetTweet(Long.toString(tweet.uid), tweet.retweeted, new JsonHttpResponseHandler() {
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                        Log.d("DEBUG:LIKE", "success");
                        tweet.retweeted = !tweet.retweeted;
                        ibRetweet.setImageResource((tweet.retweeted) ? R.drawable.ic_vector_retweet : R.drawable.ic_vector_retweet_stroke);
                    }

                    @Override
                    public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                        super.onFailure(statusCode, headers, throwable, errorResponse);
                        Log.e("DEBUG:LIKE", "could not like tweet", throwable);
                    }
                });
            }
        });

        ibLike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TwitterClient client = TwitterApp.getRestClient();
                client.likeTweet(Long.toString(tweet.uid), tweet.liked, new JsonHttpResponseHandler() {
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                        Log.d("DEBUG:LIKE", "success");
                        tweet.liked = !tweet.liked;
                        ibLike.setImageResource((tweet.liked) ? R.drawable.ic_vector_heart : R.drawable.ic_vector_heart_stroke);
                    }

                    @Override
                    public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                        super.onFailure(statusCode, headers, throwable, errorResponse);
                        Log.e("DEBUG:LIKE", "could not like tweet", throwable);
                    }
                });
            }
        });
    }
}
