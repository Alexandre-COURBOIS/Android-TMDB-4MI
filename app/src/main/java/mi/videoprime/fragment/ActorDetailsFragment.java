package mi.videoprime.fragment;

import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.core.content.res.ResourcesCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;

import dagger.hilt.android.AndroidEntryPoint;
import mi.videoprime.R;
import mi.videoprime.adapters.ActorCastAdapter;
import mi.videoprime.model.ActorDetails;
import mi.videoprime.model.Movie;
import mi.videoprime.model.SearchResult;
import mi.videoprime.viewmodel.ActorViewModel;
import mi.videoprime.adapters.ActorCastAdapter;

import java.util.List;

@AndroidEntryPoint
public class ActorDetailsFragment extends Fragment {

    private ImageView actorImage;
    private TextView actorName;
    private TextView actorBio;
    private TextView actorKnownFor;
    private TextView actorBirthday;
    private TextView actorPlaceOfBirth;
    private TextView readMore;
    private RecyclerView recyclerView;
    private ActorCastAdapter actorCastAdapter;

    private ActorViewModel _viewModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_actor_detail, container, false);

        actorImage = rootView.findViewById(R.id.actor_image);
        actorName = rootView.findViewById(R.id.actor_name);
        actorBio = rootView.findViewById(R.id.actor_biography);
        actorKnownFor = rootView.findViewById(R.id.actor_known_for);
        actorBirthday = rootView.findViewById(R.id.actor_birthday);
        actorPlaceOfBirth = rootView.findViewById(R.id.actor_place_of_birth);
        readMore = rootView.findViewById(R.id.read_more);
        recyclerView = rootView.findViewById(R.id.recycler_view);

        actorCastAdapter = new ActorCastAdapter();
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
        recyclerView.setAdapter(actorCastAdapter);

        readMore.setOnClickListener(v -> {
            if(actorBio.getMaxLines() == 15) {
                actorBio.setMaxLines(Integer.MAX_VALUE);
                readMore.setText("Voir moins <");
            } else {
                actorBio.setMaxLines(15);
                readMore.setText("Voir plus >");
            }
        });

        _viewModel = new ViewModelProvider(requireActivity()).get(ActorViewModel.class);

        Bundle args = getArguments();
        if(args != null) {
            SearchResult actor = args.getParcelable("actor");
            if(actor != null) {
                _viewModel.getActorDetail(actor.getId());
                _viewModel.getActorMovieCredits(actor.getId());
            }
        }

        _viewModel.actorDetail.observe(getViewLifecycleOwner(), new Observer<ActorDetails>() {
            @Override
            public void onChanged(ActorDetails actorDetail) {
                if (getActivity() instanceof AppCompatActivity) {
                    AppCompatActivity activity = (AppCompatActivity) getActivity();
                    ActionBar actionBar = activity.getSupportActionBar();
                    if (actionBar != null) {
                        actionBar.setDisplayHomeAsUpEnabled(true);
                        actionBar.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

                        Typeface custom_font = ResourcesCompat.getFont(activity, R.font.caviardreams);

                        TextView textView = new TextView(activity);
                        textView.setText(actorDetail.getName());
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



                Glide.with(rootView)
                        .load("https://image.tmdb.org/t/p/w500" + actorDetail.getProfilePath())
                        .transform(new RoundedCorners(20))
                        .into(actorImage);
                actorName.setText(actorDetail.getName());
                actorBio.setText(actorDetail.getBiography());
                actorKnownFor.setText(actorDetail.getKnownForDepartment());
                actorBirthday.setText(actorDetail.getBirthday());
                actorPlaceOfBirth.setText(actorDetail.getPlaceOfBirth());
            }
        });

        _viewModel.actorMovies.observe(getViewLifecycleOwner(), new Observer<List<Movie>>() {
            @Override
            public void onChanged(List<Movie> movies) {
                actorCastAdapter.setData(movies);
            }
        });

        return rootView;
    }
}
