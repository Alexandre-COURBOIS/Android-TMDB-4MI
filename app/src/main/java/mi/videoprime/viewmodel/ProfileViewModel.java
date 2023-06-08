package mi.videoprime.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.security.PublicKey;
import java.util.List;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;
import mi.videoprime.model.Favorite;
import mi.videoprime.model.User;
import mi.videoprime.repository.interfaces.ILoginRepository;
import mi.videoprime.service.FavoriteService;
import mi.videoprime.service.UserService;

@HiltViewModel
public class ProfileViewModel extends AndroidViewModel {

    @Inject
    ILoginRepository _loginRepository;
    @Inject
    UserService _userService;
    @Inject
    FavoriteService _favoriteService;

    public MutableLiveData<User> _user = new MutableLiveData<User>();
    public MutableLiveData<User> getUser() {
        return _user;
    }
    public MutableLiveData<Boolean> navigateToAuthActivity = new MutableLiveData<>();

    public void onNavigateToAuthComplete() {
        navigateToAuthActivity.setValue(false);
    }

    public LiveData<Boolean> getNavigateToAuth() {
        return navigateToAuthActivity;
    }

    @Inject
    public ProfileViewModel(@NonNull Application application) {
        super(application);
    }

    public boolean isLogged() {
        return _loginRepository.isLogged();
    }

    public void goToAuthActivity()
    {
        navigateToAuthActivity.setValue(true);
    }
    public void deleteFavorite(int id){
        _favoriteService.deleteById(id);
    }

    public void loadUser() {
        User user = _userService.getUserFromJson();
        _user.setValue(user);
    }

}
