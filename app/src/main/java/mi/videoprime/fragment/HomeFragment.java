package mi.videoprime.fragment;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;
import mi.videoprime.R;
import mi.videoprime.adapters.MoviesAdapter;
import mi.videoprime.databinding.FragmentHomeBinding;
import mi.videoprime.model.Movie;
import mi.videoprime.repository.interfaces.ILoginRepository;
import mi.videoprime.service.interfaces.IToastService;
import mi.videoprime.viewmodel.HomeViewModel;

@AndroidEntryPoint
public class HomeFragment extends Fragment implements MoviesAdapter.OnMovieClickListener {

    private static final String RECYCLER_VIEW_POSITION = "recycler_view_position";
    private RecyclerView recyclerView;
    private Parcelable recyclerViewState;
    HomeViewModel _viewModel;
    @Inject
    ILoginRepository _loginRepository;
    @Inject
    IToastService _toastService;

    public HomeFragment() {
        super(R.layout.fragment_home);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        FragmentHomeBinding binding = DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false);

        _viewModel = new ViewModelProvider(requireActivity()).get(HomeViewModel.class);

        recyclerView = binding.recyclerView;

        MoviesAdapter moviesAdapter = new MoviesAdapter(new ArrayList<>(), _viewModel, this, _loginRepository, _toastService);
        recyclerView.setAdapter(moviesAdapter);

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        List<Movie> movies = _viewModel.getAllMovies();

        if (movies.isEmpty()) {
            _viewModel.loadMovies(_viewModel.getCount(), moviesAdapter);
        } else {
            moviesAdapter.addMovies(movies);
        }

        if (getActivity() instanceof AppCompatActivity) {
            ActionBar actionBar = ((AppCompatActivity) getActivity()).getSupportActionBar();
            if (actionBar != null) {
                actionBar.setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.warm_white)));

                ActionBar.LayoutParams layoutParams = new ActionBar.LayoutParams(
                        ActionBar.LayoutParams.MATCH_PARENT,
                        ActionBar.LayoutParams.MATCH_PARENT);

                inflater = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                View view = inflater.inflate(R.layout.actionbar_view, null);

                actionBar.setTitle(R.string.title_activity_home);
                actionBar.setCustomView(view, layoutParams);
                actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
                actionBar.show();
            }
        }

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                LinearLayoutManager layoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
                if (layoutManager != null) {
                    int totalItemCount = layoutManager.getItemCount();
                    int lastVisibleItem = layoutManager.findLastVisibleItemPosition();
                    if (!_viewModel.getIsLoading() && totalItemCount -1 == lastVisibleItem) {
                        _viewModel.incrementCount();
                        _viewModel.loadMovies(_viewModel.getCount(), moviesAdapter);
                    }
                }
            }
        });

        binding.setViewModel(_viewModel);
        binding.setLifecycleOwner(this);

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
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (savedInstanceState != null && recyclerView.getLayoutManager() != null) {
            int position = savedInstanceState.getInt(RECYCLER_VIEW_POSITION);
            recyclerView.getLayoutManager().scrollToPosition(position);
        }
    }
    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        LinearLayoutManager layoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
        if (layoutManager != null) {
            int position = layoutManager.findFirstVisibleItemPosition();
            outState.putInt(RECYCLER_VIEW_POSITION, position);
        }
    }
    @Override
    public void onMovieClicked(Movie movie) {
        Bundle bundle = new Bundle();
        bundle.putParcelable("movie", movie);
        NavController navController = Navigation.findNavController(getView());
        navController.navigate(R.id.action_homeFragment_to_movieDetailFragment, bundle);
    }
}