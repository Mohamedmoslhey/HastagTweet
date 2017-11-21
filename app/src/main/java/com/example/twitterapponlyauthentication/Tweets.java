package com.example.twitterapponlyauthentication;

public class Tweets {
	String mTweetBy;
	String mTweet;

	public Tweets(String tweetBy, String tweet) {
		mTweetBy = tweetBy;
		mTweet = tweet;
	}
//get user ID
	public String getTweetBy() {
		return mTweetBy;
	}

	//get user Tweet

	public String getTweet() {
		return mTweet;
	}



}
