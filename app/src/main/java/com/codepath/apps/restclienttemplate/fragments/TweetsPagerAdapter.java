package com.codepath.apps.restclienttemplate.fragments;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.codepath.apps.restclienttemplate.models.Tweet;

/**
 * Created by mpan on 7/3/17.
 */

public class TweetsPagerAdapter extends FragmentPagerAdapter {

    private String tabTitles[] = new String[] {"Home", "Mentions"};
    private Context context;
    private HomeTimelineFragment timelineFragment;
    private MentionsTimelineFragment mentionsFragment;

    // return total num fragments
    public TweetsPagerAdapter(FragmentManager fm, Context context) {
        super(fm);
        this.context = context;
        timelineFragment = new HomeTimelineFragment();
        mentionsFragment = new MentionsTimelineFragment();
    }

    @Override
    public int getCount() {
        return 2;
    }

    // return frag to use depending on pos

    @Override
    public Fragment getItem(int position) {
        if (position == 0) {
//            return new HomeTimelineFragment();
//            timelineFragment = getTimelineInstance();
            return timelineFragment;
        } else if (position == 1) {
//            return new MentionsTimelineFragment();
//            mentionsFragment = getMentionsInstance();
            return mentionsFragment;
        } else {
            return null;
        }
    }

    public void addTweetToHome(Tweet tweet) {
        timelineFragment.addTweet(tweet);
    }

//    private HomeTimelineFragment getTimelineInstance() {
//        if (timelineFragment == null) {
//            timelineFragment = new HomeTimelineFragment();
//        }
//        return timelineFragment;
//    }
//
//    private MentionsTimelineFragment getMentionsInstance() {
//        if (mentionsFragment == null) {
//            mentionsFragment = new MentionsTimelineFragment();
//        }
//        return mentionsFragment;
//    }

    // return title
    @Override
    public CharSequence getPageTitle(int position) {
        return null;
    }
}
