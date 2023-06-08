package mi.videoprime.viewmodel;

import android.app.Application;
import android.util.Log;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.android.volley.VolleyError;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;
import mi.videoprime.adapters.MoviesAdapter;
import mi.videoprime.model.Favorite;
import mi.videoprime.model.Movie;
import mi.videoprime.service.TMDBService;
import mi.videoprime.service.interfaces.IFavoriteService;
import mi.videoprime.service.interfaces.IMovieService;
import mi.videoprime.service.interfaces.IOfflineDataService;
import mi.videoprime.service.interfaces.ITMDBService;

@HiltViewModel
public class HomeViewModel extends AndroidViewModel {
    private int count = 1;
    private Boolean isLoading = false;
    public int getCount() {
        return count;
    }
    public void incrementCount() {
        count++;
    }
    public Boolean getIsLoading() {
        return isLoading;
    }
    public void setIsLoading(Boolean status) {
        this.isLoading = status;
    }
    private MutableLiveData<List<Favorite>> favoritesLiveData = new MutableLiveData<>();
    public LiveData<List<Favorite>> getFavoritesLiveData() {
        return favoritesLiveData;
    }
    private List<Movie> allMovies = new ArrayList<>();
    public List<Movie> getAllMovies() {
        return allMovies;
    }

    @Inject
    IFavoriteService _favoriteService;
    @Inject
    TMDBService _tmdbService;
    @Inject
    IMovieService _movieService;
    @Inject
    IOfflineDataService _offlineDataService;
    @Inject
    public HomeViewModel(Application application) {
        super(application);
    }

    public void loadMovies(int pageNumber, MoviesAdapter moviesAdapter) {
        if (!isLoading) {
            isLoading = true;
            _tmdbService.getPopularMovies(pageNumber, new ITMDBService.OnMoviesReceivedListener() {

                @Override
                public void onSuccess(List<Movie> movies) {
                    allMovies.addAll(movies);
                    moviesAdapter.addMovies(movies);
                    for (Movie movie : movies) {
                        movie.setMovieId(movie.getId());
                        _movieService.createMovie(movie);
                    }
                    isLoading = false;
                }

                @Override
                public void onError(VolleyError error) {
                    _offlineDataService.loadOfflineMovies(moviesAdapter, _movieService.getAll());
                    Log.e("MoviesFragment", "Error receiving movies now playing: " + error.getMessage());
                    isLoading = false;
                }
            });
        }
    }

    public void onFavoriteClicked(Movie movie) {
        Favorite favorite = new Favorite();
        favorite.setMovieId(movie.getId());
        favorite.setMovieTitle(movie.getTitle());
        _favoriteService.createFavorite(favorite);
        favoritesLiveData.setValue(_favoriteService.getAll());
    }

    public boolean isFavorite(Movie movie) {
        return _favoriteService.isFavorite(movie);
    }
}
