package com.codepath.apps.restclienttemplate;

import android.content.Intent;
import android.preference.PreferenceActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.codepath.apps.restclienttemplate.models.Tweet;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONObject;
import org.parceler.Parcels;

public class ComposeActivity extends AppCompatActivity {

    TwitterClient client;
    EditText simpleEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_compose);

        simpleEditText = (EditText) findViewById(R.id.composeText);
        String strValue = simpleEditText.getText().toString();
        client = TwitterApp.getRestClient(this);
    }


    public void onSubmit(View view) {
        client.sendTweet(simpleEditText.getText().toString(), new JsonHttpResponseHandler(){

        @Override
        public void onSubmit(int statusCode, PreferenceActivity.Header[] headers, JSONObject response){
            try {

                Tweet tweet = Tweet.fromJSON(response);
                Intent data = new Intent();
                // Pass relevant data back as a result
                data.putExtra("tweet", Parcels.wrap(tweet));
                // Activity finished ok, return the data
                setResult(RESULT_OK, data); // set result code and bundle data for response
                finish(); // closes the activity, pass data to pare
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    });

    }
}
