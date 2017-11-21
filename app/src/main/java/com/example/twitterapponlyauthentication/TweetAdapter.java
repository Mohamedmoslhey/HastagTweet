package com.example.twitterapponlyauthentication;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class TweetAdapter extends BaseAdapter {
	ArrayList<Tweets> mTweetList;
	Context mContext;

	public TweetAdapter(Context context, ArrayList<Tweets> tweetList) {
		mTweetList = tweetList;
		mContext = context;
	}


	@Override
	public int getCount() {
		return mTweetList.size();
	}

	@Override
	public Object getItem(int position) {
		return null;
	}

	@Override
	public long getItemId(int position) {
		return 0;
	}
// override method apply to get list of tweet in list view item layout slected
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if (convertView == null) {
			LayoutInflater inflater = LayoutInflater.from(mContext);
			convertView = inflater.inflate(R.layout.tweet_list_item, null);
		}

		Tweets tweet = mTweetList.get(position);
		TextView txtTweet = (TextView) convertView.findViewById(R.id.tweet);
		TextView txtTweetBy = (TextView) convertView.findViewById(R.id.tweetBy);

		txtTweet.setText(tweet.getTweet());
		txtTweetBy.setText(tweet.getTweetBy());

		return convertView;
	}
}
