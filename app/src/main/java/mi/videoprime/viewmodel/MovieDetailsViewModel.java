package mi.videoprime.viewmodel;

import android.app.Application;
import android.util.Log;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.android.volley.VolleyError;

import java.util.List;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;
import mi.videoprime.model.Actor;
import mi.videoprime.model.Movie;
import mi.videoprime.model.MovieDetails;
import mi.videoprime.service.TMDBService;
import mi.videoprime.service.interfaces.ITMDBService;

@HiltViewModel
public class MovieDetailsViewModel extends AndroidViewModel {

    public MutableLiveData<MovieDetails> movieDetails = new MutableLiveData<MovieDetails>();
    public MutableLiveData<MovieDetails> getMovieDetails() {
        return movieDetails;
    }
    public MutableLiveData<List<Actor>> actorList = new MutableLiveData<>();
    public MutableLiveData<List<Actor>> getActorList() {
        return actorList;
    }
    private final MutableLiveData<Boolean> isTextViewExpanded = new MutableLiveData<Boolean>(false);
    public LiveData<Boolean> getIsTextViewExpanded() {
        return isTextViewExpanded;
    }
    public void toggleTextViewExpansion() {
        Boolean currentState = isTextViewExpanded.getValue();
        isTextViewExpanded.setValue(currentState == null ? true : !currentState);
    }
    @Inject
    TMDBService _tmdbService;

    @Inject
    public MovieDetailsViewModel(Application application) {
        super(application);
    }

    public void loadDetailsMovie(Movie movie) {
        _tmdbService.getMovieDetails(movie, new ITMDBService.OnMovieDetailsReceivedListener() {
            @Override
            public void onSuccess(MovieDetails movieDetail) {
                movieDetail.setGenreString(movieDetail.getGenres());
                movieDetail.setRunTimeToHour(movieDetail.getRuntime());
                movieDetail.setRatingOnTen(movieDetail.getVoteAverage());
                movieDetails.setValue(movieDetail);
            }
            @Override
            public void onError(VolleyError error) {
                Log.e("MoviesDetailsFragment", "Error receiving details on LoadDetailsMovie from ViewModel: " + error.getMessage());
            }
        });
    }

    public void loadCreditsMovie(Movie movie) {
        _tmdbService.getMovieCredits(movie, new ITMDBService.OnMovieCreditsReceivedListener() {
            @Override
            public void onSuccess(List<Actor> actors) {
                actorList.setValue(actors);
            }

            @Override
            public void onError(VolleyError error) {
                Log.e("MoviesDetailsFragment", "Error receiving details on loadCreditsMovie from ViewModel: " + error.getMessage());
            }
        });
    }
}
