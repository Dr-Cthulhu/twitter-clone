package com.codepath.apps.restclienttemplate;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.codepath.apps.restclienttemplate.fragments.HomeTimelineFragment;
import com.codepath.apps.restclienttemplate.fragments.TweetsListFragment;
import com.codepath.apps.restclienttemplate.fragments.TweetsPagerAdapter;
import com.codepath.apps.restclienttemplate.models.Tweet;

import org.parceler.Parcels;

public class TimelineActivity extends AppCompatActivity implements TweetsListFragment.TweetSelectedListener{

    public static final int COMPOSE_TWEET = 1;
    public static final int REPLY_TWEET = 2;

//    private SwipeRefreshLayout swipeContainer;
    private ViewPager vPgr;
    private TweetsPagerAdapter pagerAdapter;
    private int[] imageResId = {
            R.drawable.ic_vector_home,
            R.drawable.ic_vector_location };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timeline);

//        swipeContainer = (SwipeRefreshLayout) findViewById(R.id.swipeContainer);
//
//        swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
//            @Override
//            public void onRefresh() {
//                // Your code to refresh the list here.
//                // Make sure you call swipeContainer.setRefreshing(false)
//                // once the network request has completed successfully.
//                populateTimeline();
//            }
//        });
//        // Configure the refreshing colors
//        swipeContainer.setColorSchemeResources(android.R.color.holo_blue_bright,
//                android.R.color.holo_green_light,
//                android.R.color.holo_orange_light,
//                android.R.color.holo_red_light);

//        ViewPager vpPager = (ViewPager) findViewById(R.id.viewpager);
//        vpPager.setAdapter(new TweetsPagerAdapter(getSupportFragmentManager(), this));
//        TabLayout tabLayout = (TabLayout) findViewById(R.id.sliding_tabs);
//        tabLayout.setupWithViewPager(vpPager);

        vPgr = (ViewPager) findViewById(R.id.viewpager);
        pagerAdapter = new TweetsPagerAdapter(getSupportFragmentManager(), this);
        vPgr.setAdapter(pagerAdapter);
        TabLayout tabLayout = (TabLayout) findViewById(R.id.sliding_tabs);
        tabLayout.setupWithViewPager(vPgr);

        for (int i = 0; i < imageResId.length; i++) {
            tabLayout.getTabAt(i).setIcon(imageResId[i]);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.timeline, menu);
        return true;
    }

    public void onComposeAction(MenuItem mi) {
        Intent i = new Intent(TimelineActivity.this, ComposeActivity.class);
        i.putExtra("function", "compose");
        startActivityForResult(i, COMPOSE_TWEET);
    }

    public void onProfileView(MenuItem item) {
        Intent i = new Intent(this, ProfileActivity.class);
        //i.putExtra("screen_name", item.
        startActivity(i);
    }

    @Override
    public void onTweetSelected(Tweet tweet) {
        Toast.makeText(this, tweet.body, Toast.LENGTH_SHORT).show();

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {

            case COMPOSE_TWEET:
            case REPLY_TWEET:
                if (resultCode == RESULT_OK) {
                    super.onActivityResult(requestCode, resultCode, data);
                    Tweet tweet = (Tweet) Parcels.unwrap(data.getParcelableExtra("message"));
                    Log.d("DEBUG:TIMELINE", tweet.toString());
//                    tweets.add(0, tweet);
//                    tweetAdapter.notifyItemInserted(0);
//                    rvTweets.scrollToPosition(0);
                    ((HomeTimelineFragment) pagerAdapter.getItem(0)).addTweet(tweet);
                }
                else if (resultCode == RESULT_CANCELED) {
                    Toast.makeText(this, "Unable to send tweet", Toast.LENGTH_LONG).show();
                }
        }
    }
}
