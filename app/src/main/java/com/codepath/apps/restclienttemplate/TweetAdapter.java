package com.codepath.apps.restclienttemplate;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.codepath.apps.restclienttemplate.models.Tweet;

import org.parceler.Parcels;

import java.util.List;

import jp.wasabeef.glide.transformations.RoundedCornersTransformation;

/**
 * Created by mpan on 6/26/17.
 */

public class TweetAdapter extends RecyclerView.Adapter<TweetAdapter.ViewHolder> {

    private List<Tweet> mTweets;
    Context context;

    public TweetAdapter(List<Tweet> tweets) {
        mTweets = tweets;
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

        holder.ibReply.setOnClickListener(new ReplyButtonListener(tweet, context));
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

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int position = getAdapterPosition();
            if (position != RecyclerView.NO_POSITION) {
                Tweet tweet = mTweets.get(position);
                Intent intent = new Intent(context, TweetDetailsActivity.class);
                intent.putExtra("tweet", Parcels.wrap(tweet));
                context.startActivity(intent);
            }
        }
    }
}
