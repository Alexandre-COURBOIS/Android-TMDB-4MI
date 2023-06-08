package mi.videoprime.service;

import static android.content.Context.MODE_PRIVATE;

import android.content.Context;
import android.content.SharedPreferences;
import android.widget.Switch;

import androidx.appcompat.app.AppCompatDelegate;

import mi.videoprime.R;

public class DarkModeService {
    private static final String PREF_MODE = "pref_mode";
    private static final String MODE_KEY = "mode_key";
    private final Context context;

    private Switch modeToggleSwitch;

    public DarkModeService(Context context,Switch modeToggleSwitch) {
        this.context = context;
        this.modeToggleSwitch = modeToggleSwitch;
    }

    public boolean getModePreference() {
        SharedPreferences preferences = this.context.getSharedPreferences(PREF_MODE, MODE_PRIVATE);
        return preferences.getBoolean(MODE_KEY, false);
    }

    public void applyMode(boolean isDarkModeEnabled) {

        SharedPreferences.Editor editor = this.context.getSharedPreferences(PREF_MODE, MODE_PRIVATE).edit();
        editor.putBoolean(MODE_KEY, isDarkModeEnabled);
        editor.apply();

        if (isDarkModeEnabled) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
            this.modeToggleSwitch.setCompoundDrawablesWithIntrinsicBounds(R.drawable.dark_mode_icon, 0, 0, 0);
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
            this.modeToggleSwitch.setCompoundDrawablesWithIntrinsicBounds(R.drawable.light_mode_icon, 0, 0, 0);
        }
    }
}
