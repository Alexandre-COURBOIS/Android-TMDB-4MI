package mi.videoprime.adapters;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

import javax.inject.Inject;

import mi.videoprime.R;
import mi.videoprime.model.Movie;
import mi.videoprime.repository.interfaces.ILoginRepository;
import mi.videoprime.service.interfaces.IToastService;
import mi.videoprime.ui.CustomButton;
import mi.videoprime.viewmodel.HomeViewModel;

public class MoviesAdapter extends RecyclerView.Adapter<MoviesAdapter.MovieViewHolder>{

    private List<Movie> movies;
    private HomeViewModel _homeViewModel;
    private OnMovieClickListener listener;
    private ILoginRepository _loginRepository;
    private IToastService _toastService;

    public MoviesAdapter(List<Movie> movies, HomeViewModel viewModel,OnMovieClickListener listener, ILoginRepository loginRepository, IToastService toastService) {
        this.movies = movies;
        _homeViewModel = viewModel;
        this.listener = listener;
        this._loginRepository = loginRepository;
        this._toastService = toastService;
    }

    @NonNull
    @Override
    public MovieViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_movie, parent, false);
        return new MovieViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MovieViewHolder holder, @SuppressLint("RecyclerView") int position) {
        Movie movie = movies.get(position);
        holder.titleTextView.setText(movie.getTitle());

        String recipe = getFirstEightWords(movie.getOverview());
        holder.overviewTextView.setText(recipe);

        String imageUrl = "https://image.tmdb.org/t/p/w500/"+movie.getBackdropPath();

        Glide.with(holder.itemView)
                .load(imageUrl)
                .error(R.drawable.noimagemovie)
                .into(holder.imageView);

        if (_homeViewModel.isFavorite(movie)) {
            holder.favoriteBtn.setImageResource(R.drawable.heartcoloredliked);
        } else {
            holder.favoriteBtn.setImageResource(R.drawable.heartcoloredunlike);
        }

        holder.favoriteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (_loginRepository.isLogged()) {
                    _homeViewModel.onFavoriteClicked(movie);
                    notifyItemChanged(position);
                } else {
                    _toastService.showToastError("Connectez-vous pour ajouter un film à vos favoris", Toast.LENGTH_LONG);
                }
            }
        });

        holder.customButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onMovieClicked(movie);
            }
        });
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onMovieClicked(movie);
            }
        });
    }

    @Override
    public int getItemCount() {
        return movies.size();
    }

    public void addMovies(List<Movie> newMovies) {
        int startPosition = movies.size();
        movies.addAll(newMovies);
        notifyItemRangeInserted(startPosition, newMovies.size());
    }

    private String getFirstEightWords(String str) {
        String[] words = str.split(" ");
        StringBuilder firstEightWords = new StringBuilder();

        for (int i = 0; i < 8 && i < words.length; i++) {
            firstEightWords.append(words[i]).append(" ");
        }

        return firstEightWords.toString().trim() + "...";
    }

    public interface OnMovieClickListener {
        void onMovieClicked(Movie movie);
    }
    public static class MovieViewHolder extends RecyclerView.ViewHolder {
        public TextView titleTextView;
        public TextView overviewTextView;
        public ImageView imageView;
        public ImageButton favoriteBtn;
        public CustomButton customButton;

        public MovieViewHolder(@NonNull View itemView) {
            super(itemView);
            titleTextView = itemView.findViewById(R.id.title_text_view);
            overviewTextView = itemView.findViewById(R.id.overview_text_view);
            imageView = itemView.findViewById(R.id.showMovieImage);
            favoriteBtn = itemView.findViewById(R.id.favoriteBtn);
            customButton = itemView.findViewById(R.id.more_button);
        }
    }
}
