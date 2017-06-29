package com.codepath.apps.restclienttemplate;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.codepath.apps.restclienttemplate.models.Tweet;

import org.parceler.Parcels;

import jp.wasabeef.glide.transformations.RoundedCornersTransformation;

public class TweetDetailsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tweet_details);

        ImageView ivProfileImage = (ImageView) findViewById(R.id.ivProfileImage);
        TextView tvName = (TextView) findViewById(R.id.tvName);
        TextView tvUsername = (TextView) findViewById(R.id.tvUsername);
        TextView tvBody = (TextView) findViewById(R.id.tvBody);
        TextView tvTime = (TextView) findViewById(R.id.tvTime);

        ImageButton ibReply = (ImageButton) findViewById(R.id.ibReply);
        ImageButton ibLike = (ImageButton) findViewById(R.id.ibLike);

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

//        Glide.with(this)
//                .load(tweet.imageUrl)
//                .bitmapTransform(new RoundedCornersTransformation(this, 5, 0))
//                .into()

        ibReply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(TweetDetailsActivity.this, ComposeActivity.class);
                i.putExtra("function", "reply");
                i.putExtra("tweet", Parcels.wrap(tweet));
                startActivity(i);
            }
        });
    }

//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//
//        switch (requestCode) {
//
//            case REPLY_TWEET:
//                if (resultCode == RESULT_OK) {
//                    super.onActivityResult(requestCode, resultCode, data);
//                    Tweet tweet = (Tweet) Parcels.unwrap(data.getParcelableExtra("message"));
//                    Log.d("DEBUG:TIMELINE", tweet.toString());
//                    tweets.add(0, tweet);
//                    tweetAdapter.notifyItemInserted(0);
//                    rvTweets.scrollToPosition(0);
//                }
//                else if (resultCode == RESULT_CANCELED) {
//                    Toast.makeText(this, "Unable to send tweet", Toast.LENGTH_LONG).show();
//                }
//        }
//    }
}
