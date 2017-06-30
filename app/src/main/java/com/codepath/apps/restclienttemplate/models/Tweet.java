package com.codepath.apps.restclienttemplate.models;

import android.text.format.DateUtils;

import org.json.JSONException;
import org.json.JSONObject;
import org.parceler.Parcel;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Locale;

/**
 * Created by mpan on 6/26/17.
 */

@Parcel
public class Tweet {

    public String body;
    public long uid;
    public User user;
    public String createdAt;
    public String imageUrl;
    public int replyNum;
    public int retweetNum;
    public int likeNum;

    public Tweet() {}

    public static Tweet fromJSON(JSONObject object) throws JSONException {
        Tweet tweet = new Tweet();

        tweet.body = object.getString("text");
        tweet.uid = object.getLong("id");
        tweet.user = User.fromJSON(object.getJSONObject("user"));
        tweet.createdAt = getRelativeTimeAgo(object.getString("created_at"));
        tweet.imageUrl = "";

//        tweet.replyNum = object.getInt();
        tweet.likeNum = (object.has("favorite_count")) ? object.getInt("favorite_count") : 0;
        tweet.retweetNum = object.getInt("retweet_count");

        JSONObject tempObject = object.getJSONObject("entities");
        if (tempObject.has("media")) {
            JSONObject tempO = tempObject.getJSONArray("media").getJSONObject(0);
//            Log.d("DEBUG:IMAGEURL", tempO.toString());
            tweet.imageUrl = tempO.getString("media_url");
//            Log.d("DEBUG:IMAGEURL", tweet.imageUrl);
        }
        return tweet;
    }

    @Override
    public String toString() {
        return String.format("Username: %s\nBody: %s\nUID: %s\nCreated at: %s", user.getUsername(), body, uid, createdAt);
    }

    public static String getRelativeTimeAgo(String rawJsonDate) {
        String twitterFormat = "EEE MMM dd HH:mm:ss ZZZZZ yyyy";
        SimpleDateFormat sf = new SimpleDateFormat(twitterFormat, Locale.ENGLISH);
        sf.setLenient(true);

        String relativeDate = "";
        try {
            long dateMillis = sf.parse(rawJsonDate).getTime();
            relativeDate = DateUtils.getRelativeTimeSpanString(dateMillis,
                    System.currentTimeMillis(), DateUtils.SECOND_IN_MILLIS).toString();
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return relativeDate;
    }
}
