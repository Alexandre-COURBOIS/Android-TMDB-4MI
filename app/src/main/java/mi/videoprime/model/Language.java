package mi.videoprime.model;

import com.google.gson.annotations.SerializedName;

public class Language {
    @SerializedName("english_name")
    private String englishName;
    @SerializedName("iso_639_1")
    private String iso639_1;
    @SerializedName("name")
    private String name;

    public String getEnglishName() {
        return englishName;
    }

    public void setEnglishName(String englishName) {
        this.englishName = englishName;
    }

    public String getIso639_1() {
        return iso639_1;
    }

    public void setIso639_1(String iso639_1) {
        this.iso639_1 = iso639_1;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
