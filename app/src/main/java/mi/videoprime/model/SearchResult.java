package mi.videoprime.model;

import android.os.Parcel;
import android.os.Parcelable;

public class SearchResult implements Parcelable {
    private String title;
    private String name;
    private String profile_path;
    private String poster_path;
    private String media_type;
    private double vote_average;
    private String backdrop_path;

    private int id;
    public SearchResult() { }

    protected SearchResult(Parcel in) {
        title = in.readString();
        name = in.readString();
        profile_path = in.readString();
        poster_path = in.readString();
        media_type = in.readString();
        vote_average = in.readDouble();
        id = in.readInt();
    }

    public static final Creator<SearchResult> CREATOR = new Creator<SearchResult>() {
        @Override
        public SearchResult createFromParcel(Parcel in) {
            return new SearchResult(in);
        }

        @Override
        public SearchResult[] newArray(int size) {
            return new SearchResult[size];
        }
    };

    public String getName() {
        if(media_type.equals("person") || media_type.equals("tv")) {
            return name;
        } else if(media_type.equals("movie")) {
            return title;
        }
        return null;
    }

    public String getImagePath() {
        if(media_type.equals("person")) {
            return profile_path;
        } else if(media_type.equals("movie") || media_type.equals("tv")) {
            return poster_path;
        }
        return null;
    }

    public String getMediaType() {
        return media_type;
    }

    public double getVoteAverage() {
        return vote_average;
    }
    public String getBackdrop_path() {
        return backdrop_path;
    }

    public String getFormattedMediaType() {
        switch (media_type) {
            case "person":
                return "Acteur";
            case "tv":
                return "Série";
            case "movie":
                return "Film";
            default:
                return "Inconnu";
        }
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(title);
        parcel.writeString(name);
        parcel.writeString(profile_path);
        parcel.writeString(poster_path);
        parcel.writeString(media_type);
        parcel.writeDouble(vote_average);
        parcel.writeInt(id);
    }

    public int getId() {
        return id;
    }

    public Movie toMovie() {
        String releaseDate = "";
        String overview = "";

        return new Movie(
                this.getId(),
                this.getName(),
                overview,
                this.getImagePath(),
                this.getBackdrop_path(),
                this.getVoteAverage(),
                releaseDate,
                0
        );
    }
}
