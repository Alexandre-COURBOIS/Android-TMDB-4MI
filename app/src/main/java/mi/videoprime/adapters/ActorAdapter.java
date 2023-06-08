package mi.videoprime.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;

import java.util.ArrayList;
import java.util.List;

import mi.videoprime.R;
import mi.videoprime.model.Actor;

public class ActorAdapter extends RecyclerView.Adapter<ActorAdapter.ViewHolder> {
    private List<Actor> actorsList;

    public ActorAdapter(List<Actor> actorsList) {
        this.actorsList = actorsList;
        List<Actor> realActorList = new ArrayList<Actor>();
        for (Actor actor : actorsList) {
            if (actor.getKnown_for_department().toLowerCase().equals("acting")) {
                realActorList.add(actor);
            }
        }
        this.actorsList = realActorList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.actor_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Actor actor = actorsList.get(position);
        holder.actorName.setText(actor.getName());

        String imageUrl = "https://image.tmdb.org/t/p/w500/"+actor.getProfile_path();

        Glide.with(holder.itemView)
                .load(imageUrl)
                .error(R.drawable.noimagemovie)
                .transform(new RoundedCorners(20))
                .into(holder.actorImage);
    }

    @Override
    public int getItemCount() {
        return actorsList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView actorImage;
        TextView actorName;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            actorImage = itemView.findViewById(R.id.actorImage);
            actorName = itemView.findViewById(R.id.actorName);
        }
    }
}
