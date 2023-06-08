package mi.videoprime.model;

import com.google.gson.annotations.SerializedName;

public class Country {
    @SerializedName("iso_3166_1")
    private String iso3166_1;
    @SerializedName("name")
    private String name;

    public String getIso3166_1() {
        return iso3166_1;
    }

    public void setIso3166_1(String iso3166_1) {
        this.iso3166_1 = iso3166_1;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}