package com.example.sadedira.flickermovies;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.sadedira.flickermovies.Adapters.FlickerMoviesAdapter;
import com.example.sadedira.flickermovies.Models.Movie;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerFragment;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import cz.msebera.android.httpclient.Header;

import static com.example.sadedira.flickermovies.Models.Movie.fromJsonArray;


public class MovieActivity extends AppCompatActivity {


    public static List<Movie> movies;
    public static FlickerMoviesAdapter fMoviesAdapter;
    public  static ListView lvItems;
    public static String url = "https://api.themoviedb.org/3/movie/now_playing?api_key=a07e22bc18f5cb106bfe4cc1f83ad8ed";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie);

        lvItems = (ListView) findViewById(R.id.lvmovies);
        movies = new ArrayList<>();
        fMoviesAdapter = new FlickerMoviesAdapter(this, movies);
        lvItems.setAdapter(fMoviesAdapter);


        lvItems.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                Movie currentMovie = fMoviesAdapter.getItem(position);

                    // Create new intent to go to {@link VideoActivity}
                    Intent intent = new Intent(MovieActivity.this, VideoActivity.class);

                    intent.putExtra("title", currentMovie.getOriginalTitle());
                    intent.putExtra("overview", currentMovie.getOverview());
                    intent.putExtra("movieId", currentMovie.getMovieId());
                    intent.putExtra("rating", currentMovie.getVote());
                    intent.putExtra("date", currentMovie.getReleaseDate());
                    intent.putExtra("code", currentMovie.type());

                    startActivity(intent);

            }
        });

            getLatestMoviesA();

    }

    public static void getLatestMoviesB(){

        AsyncHttpClient client = new AsyncHttpClient();
        client.get(url, new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                JSONArray flickerJsonResults = null;
                try {
                    flickerJsonResults = response.getJSONArray("results");
                    movies.addAll(Movie.fromJsonArray(flickerJsonResults));
                    fMoviesAdapter.notifyDataSetChanged();
                    Log.d("DEBUD", movies.toString());
                }catch (JSONException e){
                    e.printStackTrace();
                }

            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                super.onFailure(statusCode, headers, responseString, throwable);
            }
        });

    }

    public  void getLatestMoviesA(){
        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder().url(url).build();

        // Get a handler that can be used to post to the main thread
        client.newCall(request).enqueue(new Callback() {

            @Override
            public void onFailure(Request request, IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(final Response response) throws IOException {
                JSONArray flickerJsonResults = null;
                try {
                    String responseData = response.body().string();
                    JSONObject json = new JSONObject(responseData);
                    flickerJsonResults = json.getJSONArray("results");
                    movies.addAll(Movie.fromJsonArray(flickerJsonResults));

                    Log.d("DEBUD", movies.toString());
                }catch (JSONException e){
                    e.printStackTrace();
                }

                // Run view-related code back on the main thread
                MovieActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        fMoviesAdapter.notifyDataSetChanged();
                    }
                });

            }

        });

    }

}
