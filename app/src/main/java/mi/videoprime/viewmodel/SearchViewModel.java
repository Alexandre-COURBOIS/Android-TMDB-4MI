package mi.videoprime.viewmodel;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.android.volley.VolleyError;

import java.util.List;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;
import mi.videoprime.model.Movie;
import mi.videoprime.model.SearchResult;
import mi.videoprime.service.TMDBService;
import mi.videoprime.service.interfaces.ITMDBService;

@HiltViewModel
public class SearchViewModel extends AndroidViewModel {


    private MutableLiveData<List<SearchResult>> _searchResults = new MutableLiveData<>();
    public LiveData<List<SearchResult>> searchResults = _searchResults;

    @Inject
    public SearchViewModel(Application application) {
        super(application);
    }
    @Inject
    ITMDBService tmdbService;

    public void search(String query) {
        tmdbService.getMultiSearch(query, new TMDBService.OnSearchReceivedListener() {
            @Override
            public void onSuccess(List<SearchResult> results) {
                _searchResults.setValue(results);
            }

            @Override
            public void onError(VolleyError error) {
                System.out.println(error.toString());
            }
        });
    }
}
