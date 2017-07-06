package com.codepath.apps.restclienttemplate.models;

import org.json.JSONException;
import org.json.JSONObject;
import org.parceler.Parcel;

/**
 * Created by mpan on 6/26/17.
 */

@Parcel
public class User {

    public String name;
    public long uid;
    public String username;
    public String profileImageUrl;

    public String tagline;
    public int followersCount;
    public int followingCount;

    public User () {}

    public String getUsername() {
        return username;
    }

    public static User fromJSON(JSONObject object) throws JSONException {
        User user = new User();

        user.name = object.getString("name");
        user.uid = object.getLong("id");
        user.username = object.getString("screen_name");
        user.tagline = object.getString("description");
        user.followersCount = object.getInt("followers_count");
        user.followingCount = object.getInt("friends_count");

        user.profileImageUrl = object.getString("profile_image_url");

        return user;
    }


}
