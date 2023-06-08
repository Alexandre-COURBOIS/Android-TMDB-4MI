package mi.videoprime.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import mi.videoprime.R;
import mi.videoprime.model.Movie;

public class ActorCastAdapter extends RecyclerView.Adapter<ActorCastAdapter.ActorCastViewHolder> {

    private List<Movie> moviesList = new ArrayList<>();
    private Context context;

    public void setData(List<Movie> movies) {
        this.moviesList = movies;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ActorCastViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.actor_cast, parent, false);
        context = parent.getContext();
        return new ActorCastViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ActorCastViewHolder holder, int position) {
        Movie movie = moviesList.get(position);
        holder.bind(movie);
    }

    @Override
    public int getItemCount() {
        return moviesList.size();
    }

    class ActorCastViewHolder extends RecyclerView.ViewHolder {

        ImageView actorCastImage;
        TextView actorCastTitle;

        public ActorCastViewHolder(@NonNull View itemView) {
            super(itemView);

            actorCastImage = itemView.findViewById(R.id.actor_cast_image);
            actorCastTitle = itemView.findViewById(R.id.actor_cast_title);
        }

        public void bind(Movie movie) {
            Glide.with(context)
                    .load("https://image.tmdb.org/t/p/w500" + movie.getPosterPath())
                    .into(actorCastImage);

            actorCastTitle.setText(movie.getTitle());
        }
    }
}


