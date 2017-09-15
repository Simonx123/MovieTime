package com.example.sadedira.flickermovies.Network;

/**
 * Created by sadedira on 9/13/2017.
 */

import android.util.Log;

import com.example.sadedira.flickermovies.Adapters.FlickerMoviesAdapter;
import com.example.sadedira.flickermovies.Models.Movie;
import com.example.sadedira.flickermovies.MovieActivity;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.squareup.okhttp.Call;
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


public class NetworkConnectivity {

    public static List<Movie> movies = new ArrayList<>();
    public static FlickerMoviesAdapter fMoviesAdapter;
    public static String url = "https://api.themoviedb.org/3/movie/now_playing?api_key=a07e22bc18f5cb106bfe4cc1f83ad8ed";


    public static void getLastestMoviesA(){
        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url(url)
                .build();
        // Get a handler that can be used to post to the main thread
        client.newCall(request).enqueue(new Callback() {

            @Override
            public void onFailure(Request request, IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(final Response response) throws IOException {

            }

        });

    }




}
