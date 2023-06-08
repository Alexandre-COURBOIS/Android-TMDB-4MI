package mi.videoprime.viewmodel;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.android.volley.VolleyError;

import java.util.List;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;
import mi.videoprime.model.ActorDetails;
import mi.videoprime.model.Movie;
import mi.videoprime.service.TMDBService;
import mi.videoprime.service.interfaces.ITMDBService;

@HiltViewModel
public class ActorViewModel extends AndroidViewModel {

    private MutableLiveData<ActorDetails> _actorDetail = new MutableLiveData<>();
    public LiveData<ActorDetails> actorDetail = _actorDetail;
    public LiveData<List<Movie>> actorMovies = new MutableLiveData<>();

    @Inject
    ITMDBService tmdbService;

    @Inject
    public ActorViewModel(Application application) {
        super(application);
    }

    public void getActorDetail(int id) {
        tmdbService.getActorDetails(id, new TMDBService.OnActorDetailsReceivedListener() {
            @Override
            public void onSuccess(ActorDetails actor) {
                _actorDetail.setValue(actor);
            }

            @Override
            public void onError(VolleyError error) {
                System.out.println(error.toString());
            }
        });
    }

    public void getActorMovieCredits(int actorId) {
        tmdbService.getActorMovieCredits(actorId, new TMDBService.OnActorMovieCreditsReceivedListener() {
            @Override
            public void onSuccess(List<Movie> movies) {
                ((MutableLiveData<List<Movie>>) actorMovies).setValue(movies);
            }

            @Override
            public void onError(VolleyError error) {
                System.out.println(error.toString());
            }
        });
    }
}
