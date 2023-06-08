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
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.core.content.res.ResourcesCompat;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.mikhaellopez.circularprogressbar.CircularProgressBar;

import java.util.List;

import mi.videoprime.R;
import mi.videoprime.adapters.ActorAdapter;
import mi.videoprime.databinding.FragmentMovieDetailsBinding;
import mi.videoprime.model.Actor;
import mi.videoprime.model.Movie;
import mi.videoprime.viewmodel.MovieDetailsViewModel;

public class MovieDetailsFragment extends Fragment {

    private RecyclerView _actorRecyclerView;
    private ActorAdapter _actorAdapter;
    MovieDetailsViewModel _viewModel;

    public MovieDetailsFragment() {
        super(R.layout.fragment_movie_details);
    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        FragmentMovieDetailsBinding binding = DataBindingUtil.inflate(inflater, R.layout.fragment_movie_details, container, false);
        _viewModel = new ViewModelProvider(requireActivity()).get(MovieDetailsViewModel.class);

        _actorRecyclerView = binding.actorsRecyclerView;
        _actorRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));

        binding.setViewModel(_viewModel);
        binding.setLifecycleOwner(this);

        Movie movie = getArguments().getParcelable("movie");

        _viewModel.loadDetailsMovie(movie);
        _viewModel.loadCreditsMovie(movie);

        if (getActivity() instanceof AppCompatActivity) {
            AppCompatActivity activity = (AppCompatActivity) getActivity();
            ActionBar actionBar = activity.getSupportActionBar();
            if (actionBar != null) {
                actionBar.setDisplayHomeAsUpEnabled(true);
                actionBar.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

                Typeface custom_font = ResourcesCompat.getFont(activity, R.font.caviardreams);

                TextView textView = new TextView(activity);
                textView.setText(movie.getTitle());
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

        final FrameLayout textContainer = binding.textContainer;
        final TextView textView = binding.getRoot().findViewById(R.id.movieResume);

        CircularProgressBar circularProgressBar = binding.getRoot().findViewById(R.id.circularProgressBar);

        float averageOnPercent = (float)movie.getVoteAverage() *10;
        circularProgressBar.setProgressWithAnimation(averageOnPercent, 1500L);
        circularProgressBar.setProgressBarColor(Color.BLACK);
        circularProgressBar.setProgressBarWidth(2f);
        circularProgressBar.setBackgroundProgressBarWidth(4f);

        _viewModel.getIsTextViewExpanded().observe(getViewLifecycleOwner(), new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean isExpanded) {
                if (isExpanded) {
                    textView.setMaxLines(Integer.MAX_VALUE);
                    textContainer.setForeground(null);
                } else {
                    textView.setMaxLines(3);
                    textContainer.setForeground(ContextCompat.getDrawable(getContext(), R.drawable.gradient));
                }
            }
        });

        _viewModel.getActorList().observe(getViewLifecycleOwner(), new Observer<List<Actor>>() {
            @Override
            public void onChanged(List<Actor> actors) {
                _actorAdapter = new ActorAdapter(actors);
                _actorRecyclerView.setAdapter(_actorAdapter);
            }
        });

        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                _viewModel.toggleTextViewExpansion();
            }
        });

        ImageView movieDetailImage = binding.showMovieImage;

        String imageUrl = "https://image.tmdb.org/t/p/w500/"+movie.getBackdropPath();
        Glide.with(binding.getRoot())
                .load(imageUrl)
                .error(R.drawable.noimagemovie)
                .transform(new RoundedCorners(20))
                .into(movieDetailImage);

        return binding.getRoot();
    }
}
