package mi.videoprime.service;

import android.content.Context;
import android.net.Uri;

import com.android.volley.Request;
import com.android.volley.VolleyError;

import javax.inject.Inject;

import dagger.hilt.android.qualifiers.ApplicationContext;

import mi.videoprime.model.ActorDetails;
import mi.videoprime.model.MovieCreditsResponse;
import mi.videoprime.model.Movie;
import mi.videoprime.model.MovieCredits;
import mi.videoprime.model.MovieDetails;
import mi.videoprime.model.PopularMovies;
import mi.videoprime.model.SearchResponse;
import mi.videoprime.service.interfaces.ITMDBService;

public class TMDBService implements ITMDBService {

    private final String API_KEY = "3308ddc5843273a032628d9c66906ec2";
    private final String BASE_API_URL = "https://api.themoviedb.org/3";

    private final String CREATE_REQUEST_TOKEN_URL = "/authentication/token/new?api_key="+API_KEY;

    Context _context;

    @Inject
    public TMDBService(@ApplicationContext Context context) {
        _context = context;
    }

    @Override
    public void getPopularMovies(int page, OnMoviesReceivedListener listener) {
        String url = BASE_API_URL + "/movie/popular?api_key=" + API_KEY + "&page=" + page;

        new ApiRequest<PopularMovies>(_context, PopularMovies.class) {
            @Override
            protected void onSuccess(PopularMovies response) {
                listener.onSuccess(response.getResults());
            }

            @Override
            protected void onError(VolleyError error) {
                listener.onError(error);
            }
        }.makeRequest(Request.Method.GET, url);
    }



    @Override
    public void getMultiSearch(String query, OnSearchReceivedListener listener) {
        String url = BASE_API_URL + "/search/multi?query=" + Uri.encode(query) + "&api_key=" + API_KEY;

        new ApiRequest<SearchResponse>(_context, SearchResponse.class) {
            @Override
            protected void onSuccess(SearchResponse response) {
                listener.onSuccess(response.getResults());
            }
            @Override
            protected void onError(VolleyError error) {
                listener.onError(error);
            }
        }.makeRequest(Request.Method.GET, url);
    }
  
    @Override
    public void getMovieDetails(Movie movie, OnMovieDetailsReceivedListener listener) {
        String url = BASE_API_URL + "/movie/"+ movie.getId() +"?api_key=" + API_KEY;

        new ApiRequest<MovieDetails>(_context, MovieDetails.class) {
            @Override
            protected void onSuccess(MovieDetails response) {
                listener.onSuccess(response);
            }

            @Override
            protected void onError(VolleyError error) {
                listener.onError(error);
            }
        }.makeRequest(Request.Method.GET, url);
    }

    @Override
    public void getActorDetails(int actorId, OnActorDetailsReceivedListener listener) {
        String url = BASE_API_URL + "/person/" + actorId + "?api_key=" + API_KEY + "&language=fr-FR";

        new ApiRequest<ActorDetails>(_context, ActorDetails.class) {
            @Override
            protected void onSuccess(ActorDetails response) {
                listener.onSuccess(response);
            }
          @Override
            protected void onError(VolleyError error) {
                listener.onError(error);
            }
        }.makeRequest(Request.Method.GET, url);
    }

    @Override
    public void getMovieCredits(Movie movie, OnMovieCreditsReceivedListener listener) {
        String url = BASE_API_URL + "/movie/"+ movie.getId() +"/credits?api_key=" + API_KEY;

        new ApiRequest<MovieCredits>(_context, MovieCredits.class) {
            @Override
            protected void onSuccess(MovieCredits response) {
                listener.onSuccess(response.getCast());
            }
            @Override
            protected void onError(VolleyError error) {
                listener.onError(error);
            }
        }.makeRequest(Request.Method.GET, url);
    }

    @Override
    public void getActorMovieCredits(int actorId, OnActorMovieCreditsReceivedListener listener) {
        String url = BASE_API_URL + "/person/" + actorId + "/combined_credits?api_key=" + API_KEY + "&language=fr-FR";

        new ApiRequest<MovieCreditsResponse>(_context, MovieCreditsResponse.class) {
            @Override
            protected void onSuccess(MovieCreditsResponse response) {
                listener.onSuccess(response.getCast());
            }

            @Override
            protected void onError(VolleyError error) {
                listener.onError(error);
            }
        }.makeRequest(Request.Method.GET, url);
    }


}
