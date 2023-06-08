package mi.videoprime.adapters;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

import javax.inject.Inject;

import mi.videoprime.R;
import mi.videoprime.model.Favorite;
import mi.videoprime.model.Movie;
import mi.videoprime.service.MovieService;
import mi.videoprime.viewmodel.ProfileViewModel;

public class FavoriteAdapter extends RecyclerView.Adapter<FavoriteAdapter.FavoriteViewHolder> {

    private List<Favorite> _favorites;

    private ProfileViewModel _profileViewModel;

    private MovieService _movieService;

    public FavoriteAdapter(List<Favorite> favorites, ProfileViewModel profileViewModel,MovieService movieService) {
        this._favorites = favorites;
        this._movieService = movieService;
        _profileViewModel = profileViewModel;
    }

    @NonNull
    @Override
    public FavoriteAdapter.FavoriteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.favorite, parent, false);
        return new FavoriteAdapter.FavoriteViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FavoriteAdapter.FavoriteViewHolder holder,
                                 @SuppressLint("RecyclerView") int position) {

        Favorite favorite = _favorites.get(position);
        Movie movie = _movieService.getById(favorite.getMovieId());
        holder.favorite_title_text_view.setText(favorite.getMovieTitle());
        String imageUrl = "https://image.tmdb.org/t/p/w500/"+movie.getBackdropPath();
        Glide.with(holder.itemView)
                .load(imageUrl)
                .error(R.drawable.noimagemovie)
                .into(holder.favorite_image_view);

    }

    @Override
    public int getItemCount() {
        return _favorites.size();
    }

    public static class FavoriteViewHolder extends RecyclerView.ViewHolder {
        public TextView favorite_title_text_view;
        public ImageView favorite_image_view;

        public FavoriteViewHolder(@NonNull View itemView) {
            super(itemView);
            favorite_title_text_view = itemView.findViewById(R.id.favorite_title_text_view);
            favorite_image_view = itemView.findViewById(R.id.favorite_image_view);
        }
    }
}
