package mi.videoprime.repository;

import android.content.Context;
import android.content.SharedPreferences;

import javax.inject.Inject;

import dagger.hilt.android.qualifiers.ApplicationContext;
import mi.videoprime.repository.interfaces.ILoginRepository;

public class LoginRepository implements ILoginRepository {

    private final SharedPreferences _sharedPreferences;

    @Inject
    public LoginRepository(@ApplicationContext Context context)
    {
        _sharedPreferences = context.getSharedPreferences("PREFERENCES", Context.MODE_PRIVATE);
    }

    public boolean isRegistered()
    {
        return _sharedPreferences.getBoolean("IS_REGISTERED", false);
    }

    public boolean isLogged() {
        return _sharedPreferences.getBoolean("IS_LOGGED", false);
    }

    public void setIsRegistered(Boolean isRegistered)
    {
        SharedPreferences.Editor editor = _sharedPreferences.edit();
        editor.putBoolean("IS_REGISTERED", isRegistered);
        editor.apply();
    }

    public void setIsLogged(Boolean isLogged)
    {
        SharedPreferences.Editor editor = _sharedPreferences.edit();
        editor.putBoolean("IS_LOGGED", isLogged);
        editor.apply();
    }

}
