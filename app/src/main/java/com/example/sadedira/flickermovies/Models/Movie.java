package com.example.sadedira.flickermovies.Models;

import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sadedira on 9/2/2017.
 */

public class Movie {

    String posterPath;
    String originalTitle;
    String overview;
    String backDrop;
    double vote;
    String movieId;
    String releaseDate;

    public String getPosterPath() {
        return String.format("https://image.tmdb.org/t/p/w342%s", posterPath);
    }

    public String getOriginalTitle() {
        return originalTitle;
    }

    public String getOverview() {
        return overview;
    }

    public String  getVote() {
       double voteReduce = (vote/10.0)*5.0;
        return Double.toString(voteReduce);
    }

    public String getMovieId() {
        return movieId;
    }

    public int type(){
        if (vote > 7.0){
            return 0;
        }else
            return 1;
    }

    public String getBackDrop() {
        return String.format("https://image.tmdb.org/t/p/w342%s", backDrop);
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public Movie(JSONObject jsonObject) throws JSONException {
        this.originalTitle = jsonObject.getString("original_title");
        this.posterPath = jsonObject.getString("poster_path");

        this.overview = jsonObject.getString("overview");
        this.backDrop = jsonObject.getString("backdrop_path");
        this.vote = jsonObject.getDouble("vote_average");
        this.movieId = jsonObject.getString("id");
        this.releaseDate = jsonObject.getString("release_date");
    }

    public static List<Movie> fromJsonArray(JSONArray array){
        List<Movie> results = new ArrayList<>();

        for (int i = 0; i < array.length(); i++) {
            try {
                results.add(new Movie(array.getJSONObject(i)));
            }catch (JSONException e){
                e.printStackTrace();
            }

        }
        return results;
    }
}
