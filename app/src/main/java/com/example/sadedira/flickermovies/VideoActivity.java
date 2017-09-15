package com.example.sadedira.flickermovies;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.RatingBar;
import android.widget.TextView;
import android.view.View;

import com.example.sadedira.flickermovies.Adapters.FlickerMoviesAdapter;
import com.example.sadedira.flickermovies.Models.Movie;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerFragment;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import butterknife.BindView;
import butterknife.ButterKnife;
import cz.msebera.android.httpclient.Header;

import static android.R.attr.id;
import static android.icu.lang.UCharacter.GraphemeClusterBreak.L;

/**
 * Created by sadedira on 9/5/2017.
 */

public class VideoActivity  extends AppCompatActivity{
    String trailerId;
    private int position;

    @BindView(R.id.mTitle) TextView title;
    @BindView(R.id.mOverview) TextView overview;
    @BindView(R.id.mReleaseDate) TextView releaseDate;
    @BindView(R.id.ratingBar) RatingBar ratingbar;
    @BindView(R.id.other_views) public View otherViews;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.video);
        ButterKnife.bind(this);

       int position_get =  getIntent().getIntExtra("code", 0);
        if (position_get == 0){
            position = 0;
        }else {
            position = 1;
        }


        otherViews = findViewById(R.id.other_views);

        title.setText(getIntent().getStringExtra("title"));
        overview.setText(getIntent().getStringExtra("overview"));
        releaseDate.setText(getIntent().getStringExtra("date"));

        ratingbar.setRating(Float.parseFloat(getIntent().getStringExtra("rating")));

        String url = String.format("https://api.themoviedb.org/3/movie/%s/videos?api_key=a07e22bc18f5cb106bfe4cc1f83ad8ed", getIntent().getStringExtra("movieId"));


        AsyncHttpClient client = new AsyncHttpClient();
        client.get(url, new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                JSONArray trailerJsonResults = null;
                try {
                    trailerJsonResults = response.getJSONArray("results");
                    trailerId = trailerJsonResults.getJSONObject(0).getString("key");
                    Log.d("DEBUG-1", trailerId);
                }catch (JSONException e){
                    e.printStackTrace();
                }
                YouTubePlayerFragment youtubeFragment = (YouTubePlayerFragment)
                        getFragmentManager().findFragmentById(R.id.youtubeFragment);
                youtubeFragment.initialize("AIzaSyAfhlhdX8vPahDVFkki5jOxDIUenk5RfKw",
                        new YouTubePlayer.OnInitializedListener() {

                            @Override
                            public void onInitializationSuccess(YouTubePlayer.Provider provider,
                                                                YouTubePlayer youTubePlayer, boolean wasRestored) {

                                // do any work here to cue video, play video, etc.
                                // Start buffering
                                if (!wasRestored) {
                                    if (position == 1) {
                                        youTubePlayer.cueVideo(trailerId);
                                    }else {
                                        otherViews.setVisibility(View.GONE);
                                        youTubePlayer.setFullscreen(true);
                                        youTubePlayer.loadVideo(trailerId);

                                    }
                                }
                            }
                            @Override
                            public void onInitializationFailure(YouTubePlayer.Provider provider,
                                                                YouTubeInitializationResult youTubeInitializationResult) {

                            }
                        });

            }
            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                super.onFailure(statusCode, headers, responseString, throwable);
            }
        });



        // ...
    }

}
