package mi.videoprime.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

import mi.videoprime.R;
import mi.videoprime.model.SearchResult;

public class SearchResultsAdapter extends RecyclerView.Adapter<SearchResultsAdapter.SearchResultViewHolder> {

    private List<SearchResult> searchResults;
    private final OnItemClickListener listener;

    public interface OnItemClickListener {
        void onItemClicked(SearchResult item);
    }

    public SearchResultsAdapter(List<SearchResult> searchResults, OnItemClickListener listener) {
        this.searchResults = searchResults;
        this.listener = listener;
    }

    @NonNull
    @Override
    public SearchResultViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.search_item, parent, false);
        return new SearchResultViewHolder(itemView, listener);
    }

    @Override
    public void onBindViewHolder(@NonNull SearchResultViewHolder holder, int position) {
        SearchResult searchResult = searchResults.get(position);
        holder.bind(searchResult, listener);
    }

    @Override
    public int getItemCount() {
        return searchResults.size();
    }

    public void updateResults(List<SearchResult> searchResults) {
        this.searchResults = searchResults;
        notifyDataSetChanged();
    }

    static class SearchResultViewHolder extends RecyclerView.ViewHolder {
        TextView name;
        TextView type;
        RatingBar voteAverage;
        ImageView image;

        SearchResultViewHolder(View itemView, OnItemClickListener listener) {
            super(itemView);
            name = itemView.findViewById(R.id.name);
            type = itemView.findViewById(R.id.type);
            voteAverage = itemView.findViewById(R.id.voteAverage);
            image = itemView.findViewById(R.id.image);

            itemView.setOnClickListener(v -> listener.onItemClicked((SearchResult) itemView.getTag()));
        }

        void bind(SearchResult searchResult, OnItemClickListener listener) {
            itemView.setTag(searchResult);

            name.setText(searchResult.getName());
            type.setText(searchResult.getFormattedMediaType());
            if(searchResult.getMediaType().equals("movie") || searchResult.getMediaType().equals("tv")) {
                voteAverage.setVisibility(View.VISIBLE);
                float adjustedVoteAverage = (float) (searchResult.getVoteAverage() / 2.0);
                voteAverage.setRating(adjustedVoteAverage);
            } else {
                voteAverage.setVisibility(View.GONE);
            }

            String imageUrl = "https://image.tmdb.org/t/p/w500" + searchResult.getImagePath();

            Glide.with(itemView)
                    .load(imageUrl)
                    .into(image);
        }
    }
}
