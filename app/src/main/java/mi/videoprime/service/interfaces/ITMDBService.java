package mi.videoprime.service.interfaces;

import com.android.volley.VolleyError;
import java.util.List;

import mi.videoprime.model.Actor;
import mi.videoprime.model.Movie;
import mi.videoprime.model.MovieDetails;
import mi.videoprime.model.SearchResult;
import mi.videoprime.model.ActorDetails;

public interface ITMDBService {
    void getPopularMovies(int page, OnMoviesReceivedListener listener);
    void getMovieDetails(Movie movie, OnMovieDetailsReceivedListener listener);
    void getMovieCredits(Movie movie, OnMovieCreditsReceivedListener listener);
    void getMultiSearch(String query, OnSearchReceivedListener listener);
    void getActorDetails(int actorId, OnActorDetailsReceivedListener listener);
    void getActorMovieCredits(int actorId, OnActorMovieCreditsReceivedListener listener);

    interface OnMoviesReceivedListener {
        void onSuccess(List<Movie> popularMovies);
        void onError(VolleyError error);
    }

    interface OnMovieDetailsReceivedListener {
        void onSuccess(MovieDetails movieDetails);
        void onError(VolleyError error);
    }

    interface OnMovieCreditsReceivedListener {
        void onSuccess(List<Actor> actors);
        void onError(VolleyError error);
    }
  
    interface OnActorMovieCreditsReceivedListener {
        void onSuccess(List<Movie> movies);
        void onError(VolleyError error);
    }
  
    interface OnActorDetailsReceivedListener {
        void onSuccess(ActorDetails actorDetails);
        void onError(VolleyError error);
    }
  
    interface OnSearchReceivedListener {
        void onSuccess(List<SearchResult> results);
        void onError(VolleyError error);
    }
}