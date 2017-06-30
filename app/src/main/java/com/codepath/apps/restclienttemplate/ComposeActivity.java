package com.codepath.apps.restclienttemplate;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.codepath.apps.restclienttemplate.models.Tweet;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONException;
import org.json.JSONObject;
import org.parceler.Parcels;

import cz.msebera.android.httpclient.Header;

public class ComposeActivity extends AppCompatActivity {

    TwitterClient client;
    private Intent mIntent;
    private EditText etNewTweet;
    private TextView tvCharCount;
    private final TextWatcher mTextEditorWatcher = new TextWatcher() {
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            tvCharCount.setText("140 characters remaining");
        }

        public void onTextChanged(CharSequence s, int start, int before, int count) {
            tvCharCount.setText(String.valueOf(140 - s.length()) + " characters remaining");
        }

        public void afterTextChanged(Editable s) {
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_compose);
        mIntent = getIntent();
        client = TwitterApp.getRestClient();
        tvCharCount = (TextView) findViewById(R.id.tvCharCount);
        etNewTweet = (EditText) findViewById(R.id.etNewTweet);
        etNewTweet.addTextChangedListener(mTextEditorWatcher);
        if (mIntent.getStringExtra("function").equals("reply")) {
            etNewTweet.setText("@" + ((Tweet) Parcels.unwrap(mIntent.getParcelableExtra("tweet"))).user.username + " ");
        }
        etNewTweet.setSelection(etNewTweet.getText().length());
    }

    public void onSubmit(View v) {
        EditText simpleEditText = (EditText) findViewById(R.id.etNewTweet);
        Log.d("DEBUG:REPLY", mIntent.getStringExtra("function"));
        String replyId = null;
        if (mIntent.getStringExtra("function").equals("reply")) {
            replyId = Long.toString(((Tweet) Parcels.unwrap(mIntent.getParcelableExtra("tweet"))).uid);
            Log.d("DEBUG:REPLY", replyId);
        }
        String newTweet = simpleEditText.getText().toString();

        client.sendTweet(newTweet, replyId, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);

                Tweet tweet = null;

                Intent i = new Intent();

                try {
                    tweet = Tweet.fromJSON(response);
                    i.putExtra("message", Parcels.wrap(tweet));
                    setResult(RESULT_OK, i);
                } catch (JSONException e) {
                    e.printStackTrace();
                    setResult(RESULT_CANCELED, i);
                }
                Tweet tester = Parcels.unwrap(i.getParcelableExtra("message"));

                finish();
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                throwable.toString();
                try {
                    String eMessage = errorResponse.getJSONArray("errors").getJSONObject(0).getString("message");
                    Toast.makeText(getApplicationContext(), eMessage, Toast.LENGTH_LONG).show();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
//        client.sendTweet(newTweet, new JsonHttpResponseHandler() {
//            @Override
//            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
//                super.onSuccess(statusCode, headers, response);
//                Log.d("onsubmit", "succeeded");
//            }
//
//            @Override
//            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
//                Log.d("onsubmit", "failed");
//            }
//        });
    }
}
