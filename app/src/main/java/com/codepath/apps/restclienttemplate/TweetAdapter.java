package com.codepath.apps.restclienttemplate;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.codepath.apps.restclienttemplate.models.Tweet;

import java.util.List;

import jp.wasabeef.glide.transformations.RoundedCornersTransformation;

/**
 * Created by mpan on 6/26/17.
 */

public class TweetAdapter extends RecyclerView.Adapter<TweetAdapter.ViewHolder> {

    private List<Tweet> mTweets;
    private TweetAdapterListener listener;
    Context context;

    public interface TweetAdapterListener {
        public void onItemSelected(View view, int position);
    }

    public TweetAdapter(List<Tweet> tweets, TweetAdapterListener listener) {
        mTweets = tweets;
        this.listener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        View tweetView = inflater.inflate(R.layout.item_tweet, parent, false);
        ViewHolder viewHolder = new ViewHolder(tweetView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Tweet tweet = mTweets.get(position);
        holder.tvName.setText(tweet.user.name);
        holder.tvUsername.setText("@" + tweet.user.username);
        holder.tvBody.setText(tweet.body);
        holder.tvTimestamp.setText(tweet.createdAt);

        holder.tvRetweetNum.setText((tweet.retweetNum == 0) ? "" : Integer.toString(tweet.retweetNum));
        holder.tvLikeNum.setText((tweet.likeNum == 0) ? "" : Integer.toString(tweet.likeNum));

        holder.ibRetweet.setImageResource((tweet.retweeted) ? R.drawable.ic_vector_retweet : R.drawable.ic_vector_retweet_stroke);
        holder.ibLike.setImageResource((tweet.liked) ? R.drawable.ic_vector_heart : R.drawable.ic_vector_heart_stroke);

        Glide.with(context)
                .load(tweet.user.profileImageUrl)
                .bitmapTransform(new RoundedCornersTransformation(context, 5, 0))
                .into(holder.ivProfileImage);

        if(!tweet.imageUrl.equals("")) {
            holder.ivMedia.setVisibility(View.VISIBLE);
            Glide.with(context)
                    .load(tweet.imageUrl)
                    .bitmapTransform(new RoundedCornersTransformation(context, 5, 0))
                    .into(holder.ivMedia);
        } else {
            holder.ivMedia.setVisibility(View.GONE);
        }
    }

    public void clear() {
        mTweets.clear();
        notifyDataSetChanged();
    }

    // Add a list of items -- change to type used
    public void addAll(List<Tweet> list) {
        mTweets.addAll(list);
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return mTweets.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public ImageView ivProfileImage;
        public TextView tvName;
        public TextView tvUsername;
        public TextView tvBody;
        public TextView tvTimestamp;
        public ImageView ivMedia;

        public ImageButton ibReply;
        public ImageButton ibRetweet;
        public ImageButton ibLike;

        public TextView tvReplyNum;
        public TextView tvRetweetNum;
        public TextView tvLikeNum;

        public ViewHolder(View itemView) {
            super(itemView);

            ivProfileImage = (ImageView) itemView.findViewById(R.id.ivProfileImage);
            tvName = (TextView) itemView.findViewById(R.id.tvName);
            tvUsername = (TextView) itemView.findViewById(R.id.tvUsername);
            tvBody = (TextView) itemView.findViewById(R.id.tvBody);
            tvTimestamp = (TextView) itemView.findViewById(R.id.tvTimestamp);
            ivMedia = (ImageView) itemView.findViewById(R.id.ivMedia);

            ibReply = (ImageButton) itemView.findViewById(R.id.ibReply);
            ibRetweet = (ImageButton) itemView.findViewById(R.id.ibRetweet);
            ibLike = (ImageButton) itemView.findViewById(R.id.ibLike);

            tvReplyNum = (TextView) itemView.findViewById(R.id.tvReplyNum);
            tvRetweetNum = (TextView) itemView.findViewById(R.id.tvRetweetNum);
            tvLikeNum = (TextView) itemView.findViewById(R.id.tvLikeNum);

            // TODO
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null) {
                        Log.e("DEBUG:ONCLICK", "itemView fired");
                        int position = getAdapterPosition();
                        listener.onItemSelected(v, position);
                    }
                }
            });

            ibReply.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null) {
                        Log.e("DEBUG:ONCLICK", "ibReply fired");
                        int position = getAdapterPosition();
                        listener.onItemSelected(v, position);
                    }
                }
            });

            ibRetweet.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null) {
                        Log.e("DEBUG:ONCLICK", "ibRetweet fired");
                        int position = getAdapterPosition();
                        listener.onItemSelected(v, position);
                    }
                }
            });

            ibLike.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.e("DEBUG:ONCLICK", "ibLike fired");
                    if (listener != null) {
                        int position = getAdapterPosition();
                        listener.onItemSelected(v, position);
                    }
                }
            });

            ivProfileImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.e("DEBUG:ONCLICK", "ivProfileImage fired");
                    if (listener != null) {
                        int position = getAdapterPosition();
                        listener.onItemSelected(v, position);
                    }
                }
            });
        }
//
        @Override
        public void onClick(View v) {
//            int position = getAdapterPosition();
//            int id = v.getId();
//            final Tweet mTweet = mTweets.get(position);
//            if (position != RecyclerView.NO_POSITION) {
//                if (id == ibReply.getId()) {
//                    Intent intent = new Intent(context, ComposeActivity.class);
//                    intent.putExtra("function", "reply");
//                    intent.putExtra("tweet", Parcels.wrap(mTweet));
//                    ((Activity) context).startActivityForResult(intent, REPLY_TWEET);
//                } else if (id == ibRetweet.getId()) {
//                    TwitterClient client = TwitterApp.getRestClient();
//                    client.retweetTweet(Long.toString(mTweet.uid), mTweet.retweeted, new JsonHttpResponseHandler() {
//                        @Override
//                        public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
//                            Log.d("DEBUG:LIKE", "success");
//                            mTweet.retweeted = !mTweet.retweeted;
//                            ibRetweet.setImageResource((mTweet.retweeted) ? R.drawable.ic_vector_retweet : R.drawable.ic_vector_retweet_stroke);
//                        }
//
//                        @Override
//                        public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
//                            super.onFailure(statusCode, headers, throwable, errorResponse);
//                            Log.e("DEBUG:LIKE", "could not like tweet", throwable);
//                        }
//                    });
//                } else if (id == ibLike.getId()) {
//                    TwitterClient client = TwitterApp.getRestClient();
//                    client.likeTweet(Long.toString(mTweet.uid), mTweet.liked, new JsonHttpResponseHandler() {
//                        @Override
//                        public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
//                            Log.d("DEBUG:LIKE", "success");
//                            mTweet.liked = !mTweet.liked;
//                            ibLike.setImageResource((mTweet.liked) ? R.drawable.ic_vector_heart : R.drawable.ic_vector_heart_stroke);
//                        }
//
//                        @Override
//                        public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
//                            super.onFailure(statusCode, headers, throwable, errorResponse);
//                            Log.e("DEBUG:LIKE", "could not like tweet", throwable);
//                        }
//                    });
//                } else {
//                    Tweet tweet = mTweets.get(position);
//                    Intent intent = new Intent(context, TweetDetailsActivity.class);
//                    intent.putExtra("tweet", Parcels.wrap(tweet));
//                    context.startActivity(intent);
//                }
//            }
        }
    }
}
