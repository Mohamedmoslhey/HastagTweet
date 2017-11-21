package com.example.twitterapponlyauthentication;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import twitter4j.Query;
import twitter4j.QueryResult;
import twitter4j.Twitter;
import twitter4j.TwitterFactory;
import twitter4j.auth.OAuth2Token;
import twitter4j.conf.ConfigurationBuilder;

public class MainActivity extends Activity {
    private final String CONSUMER_KEY = "vUHq5vsxBHdWBbbYVM5iqac1T";//add Consumer key from Tweet Dev
    private final String CONSUMER_SECRET = "ZvixeL0cuatQ7bmGCVRjRHdJ1O3mJoG2RiYVmTSZTYMKnBgMoy"; //add consumer secret from Tweet Dev
    EditText edtSearch;
    Button btnSearch;
    ListView list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        edtSearch = (EditText) findViewById(R.id.editSearch);
        btnSearch = (Button) findViewById(R.id.btnSearch);
        list = (ListView) findViewById(R.id.list);
        //apply touch to button to get what user type and then search
        btnSearch.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                new SearchOnTwitter().execute(edtSearch.getText().toString());
            }
        });
    }

    class SearchOnTwitter extends AsyncTask<String, Void, Integer> {
        final int SUCCESS = 0;
        final int FAILURE = SUCCESS + 1;
        ArrayList<Tweets> tweets;
        ProgressDialog dialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialog = ProgressDialog.show(MainActivity.this, "", getString(R.string.searching));
        }

        //do some fetch and internet connection in background
        @Override
        protected Integer doInBackground(String... params) {
            try {
                ConfigurationBuilder builder = new ConfigurationBuilder();
                builder.setUseSSL(true);
                builder.setApplicationOnlyAuthEnabled(true);
                //gnerate Consumer_Secret+Consumer key to access REST API
                builder.setOAuthConsumerKey(CONSUMER_KEY);
                builder.setOAuthConsumerSecret(CONSUMER_SECRET);

                OAuth2Token token = new TwitterFactory(builder.build()).getInstance().getOAuth2Token();

                builder = new ConfigurationBuilder();
                builder.setUseSSL(true);
                builder.setApplicationOnlyAuthEnabled(true);

                builder.setOAuthConsumerKey(CONSUMER_SECRET);
                builder.setOAuthConsumerSecret(CONSUMER_SECRET);
                builder.setOAuth2TokenType(token.getTokenType());
                builder.setOAuth2AccessToken(token.getAccessToken());

                Twitter twitter = new TwitterFactory(builder.build()).getInstance();

                Query query = new Query(params[0]);
                // YOu can set the count of maximum records here
                query.setCount(50);
                QueryResult result;
                result = twitter.search(query);
                List<twitter4j.Status> tweets = result.getTweets();
                StringBuilder str = new StringBuilder();
                if (tweets != null) {
                    this.tweets = new ArrayList<Tweets>();
                    for (twitter4j.Status tweet : tweets) {
                        str.append("@" + tweet.getUser().getScreenName() + " - " + tweet.getText() + "\n");
                        System.out.println(str);
                        this.tweets.add(new Tweets("@" + tweet.getUser().getScreenName(), tweet.getText()));
                    }
                    return SUCCESS;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            return FAILURE;
        }

        @Override
        protected void onPostExecute(Integer result) {
            super.onPostExecute(result);
            dialog.dismiss();
            if (result == SUCCESS) {
                list.setAdapter(new TweetAdapter(MainActivity.this, tweets));
            } else {
                Toast.makeText(MainActivity.this, getString(R.string.error), Toast.LENGTH_LONG).show();
            }
        }
    }
}
