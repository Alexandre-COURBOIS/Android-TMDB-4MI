package mi.videoprime.fragment;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.core.content.res.ResourcesCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import java.util.ArrayList;
import java.util.List;

import dagger.hilt.android.AndroidEntryPoint;
import mi.videoprime.R;
import mi.videoprime.databinding.FragmentSearchBinding;
import mi.videoprime.model.SearchResult;
import mi.videoprime.viewmodel.SearchViewModel;
import mi.videoprime.adapters.SearchResultsAdapter;

@AndroidEntryPoint
public class SearchFragment extends Fragment implements SearchResultsAdapter.OnItemClickListener {

    private SearchViewModel _viewModel;
    private SearchView _searchView;
    private RecyclerView _searchResults;
    private SearchResultsAdapter _adapter;

    public SearchFragment() {
        super(R.layout.fragment_home);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        FragmentSearchBinding binding = DataBindingUtil.inflate(inflater, R.layout.fragment_search, container, false);

        _viewModel = new ViewModelProvider(requireActivity()).get(SearchViewModel.class);

        if (getActivity() instanceof AppCompatActivity) {
            AppCompatActivity activity = (AppCompatActivity) getActivity();
            ActionBar actionBar = activity.getSupportActionBar();
            if (actionBar != null) {
                actionBar.setDisplayHomeAsUpEnabled(true);
                actionBar.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

                Typeface custom_font = ResourcesCompat.getFont(activity, R.font.caviardreams);

                TextView textView = new TextView(activity);
                textView.setText("Recherche");
                textView.setTextColor(ContextCompat.getColor(activity, R.color.default_blue));
                textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);
                textView.setTypeface(custom_font);

                ActionBar.LayoutParams layoutParams = new ActionBar.LayoutParams(
                        ActionBar.LayoutParams.WRAP_CONTENT,
                        ActionBar.LayoutParams.WRAP_CONTENT,
                        Gravity.CENTER_HORIZONTAL | Gravity.CENTER_VERTICAL
                );

                actionBar.setDisplayOptions(actionBar.getDisplayOptions() | ActionBar.DISPLAY_SHOW_CUSTOM);
                actionBar.setCustomView(textView, layoutParams);

                Toolbar toolbar = (Toolbar) actionBar.getCustomView().getParent();
                toolbar.setContentInsetsRelative(0, 0);
            }
        }

        binding.setViewModel(_viewModel);
        binding.setLifecycleOwner(this);

        _searchView = binding.searchView;

        _searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                _viewModel.search(newText);
                return false;
            }
        });

        _searchResults = binding.searchResults;
        _adapter = new SearchResultsAdapter(new ArrayList<>(), this);
        _searchResults.setAdapter(_adapter);
        _searchResults.setLayoutManager(new LinearLayoutManager(getContext()));

        _viewModel.searchResults.observe(getViewLifecycleOwner(), new Observer<List<SearchResult>>() {
            @Override
            public void onChanged(List<SearchResult> searchResults) {
                _adapter.updateResults(searchResults);
            }
        });

        return binding.getRoot();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
    }

    @Override
    public void onItemClicked(SearchResult actor) {
        if(actor.getMediaType().equals("person")) {
            Bundle bundle = new Bundle();
            bundle.putParcelable("actor", actor);
            NavController navController = Navigation.findNavController(getView());
            navController.navigate(R.id.actorDetailFragment, bundle);
        } else {
            Bundle bundle = new Bundle();
            bundle.putParcelable("movie", actor.toMovie());
            NavController navController = Navigation.findNavController(getView());
            navController.navigate(R.id.action_homeFragment_to_movieDetailFragment, bundle);
        }
    }
}
