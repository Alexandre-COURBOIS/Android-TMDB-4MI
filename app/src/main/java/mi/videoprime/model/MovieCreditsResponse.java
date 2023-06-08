package mi.videoprime.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class MovieCreditsResponse {
    @SerializedName("cast")
    private List<Movie> cast;

    public List<Movie> getCast() {
        return cast;
    }
}

