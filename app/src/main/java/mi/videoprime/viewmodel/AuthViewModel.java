package mi.videoprime.viewmodel;

import android.app.Application;
import android.widget.EditText;
import android.widget.Toast;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.Objects;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;
import mi.videoprime.model.User;
import mi.videoprime.model.UserLogin;
import mi.videoprime.repository.interfaces.ILoginRepository;
import mi.videoprime.service.interfaces.ITMDBService;
import mi.videoprime.service.interfaces.IToastService;
import mi.videoprime.service.interfaces.IUserService;

@HiltViewModel
public class AuthViewModel extends AndroidViewModel {

    @Inject
    IUserService _userService;
    @Inject
    ITMDBService _tmdbService;
    @Inject
    IToastService _toastService;
    @Inject
    ILoginRepository _loginRepository;

    //Observation pour déclencher le changement de page si on souhaites acceder au login
    private final MutableLiveData<Boolean> navigateToLogin = new MutableLiveData<>();

    public LiveData<Boolean> getNavigateToLogin() {
        return navigateToLogin;
    }

    public void onButtonClick() {
        navigateToLogin.setValue(true);
    }

    public void doneNavigating() {
        navigateToLogin.setValue(false);
    }

    //Observation pour déclencher la vérification du formulaire d'inscription lorsqu'on clique sur le bouton register
    private MutableLiveData<Boolean> registerFormValid = new MutableLiveData<>();

    public LiveData<Boolean> isRegisterFromValid() {
        return registerFormValid;
    }

    //Navigation vers l'activity home lorsque connecté

    public MutableLiveData<Boolean> navigateToHome = new MutableLiveData<>();

    public void onNavigateToHomeCOmplete() {
        navigateToHome.setValue(false);
    }

    public LiveData<Boolean> getNavigateToHome() {
        return navigateToHome;
    }


    //Définition de l'user sur le formulaire
    public MutableLiveData<User> userData = new MutableLiveData<>();

    public MutableLiveData<User> getUserData() {
        return userData;
    }

    public void setUserData(User user) {
        userData.setValue(user);
    }

    //Définition de l'userLogin sur le formulaire de connexion
    public MutableLiveData<UserLogin> userLogin = new MutableLiveData<>();

    public MutableLiveData<UserLogin> getUserLogin() {
        return userLogin;
    }

    public void setUserLogin(UserLogin userLog) {
        userLogin.setValue(userLog);
    }

    public boolean isRegistered() {
        return _loginRepository.isRegistered();
    }

    public void setIsRegistered(boolean isRegistered) {
        _loginRepository.setIsRegistered(isRegistered);
    }

    public boolean isLogged() {
        return _loginRepository.isLogged();
    }

    public void setIsLogged(boolean isLogged) {
        _loginRepository.setIsLogged(isLogged);
    }

    @Inject
    public AuthViewModel(Application application) {
        super(application);
    }


    //Soumission du formulaire d'inscription
    public void register() {
        _userService.createUser(getUserData().getValue());
        setIsRegistered(true);
        navigateToHome.setValue(true);
        _toastService.showDefaultToast("Bienvenue " + Objects.requireNonNull(getUserData().getValue()).getUsername(), Toast.LENGTH_LONG);
    }

    public void login() {
        if (Objects.requireNonNull(getUserLogin().getValue()).getIdentifier() != null && getUserLogin().getValue().getPassword() != null) {

            User user = _userService.isUserExist(Objects.requireNonNull(getUserLogin().getValue()).getIdentifier());

            if (user != null) {
                Boolean pwdMatch = _userService.isUserRegistered(getUserLogin().getValue(), user);
                if (!pwdMatch) {
                    _toastService.showToastError("Mot de passe ou nom d'utilisateur incorrect", Toast.LENGTH_LONG);
                } else {
                    _toastService.showDefaultToast("Bienvenue " + user.getUsername(), Toast.LENGTH_LONG);
                    setIsLogged(true);
                    navigateToHome.setValue(true);
                }
            } else {
                _toastService.showToastError("Mot de passe ou nom d'utilisateur incorrect", Toast.LENGTH_LONG);
            }
        } else {
            _toastService.showToastError("Veuillez saisir vos identifiants", Toast.LENGTH_LONG);
        }
    }

    //Vérification des saisies du formulaire d'inscription
    private void validateRegisterForm() {
        boolean isUsernameValid = Objects.requireNonNull(getUserData().getValue()).isUsernameValid();
        boolean isEmailValid = Objects.requireNonNull(getUserData().getValue()).isEmailValid();
        boolean isPasswordValid = Objects.requireNonNull(getUserData().getValue()).isPasswordAvailable();
        registerFormValid.setValue(isUsernameValid && isEmailValid && isPasswordValid);
    }

    //Vérification des inputs Username && Email && Password
    public void isUsernameValid(EditText value) {
        if (!Objects.requireNonNull(getUserData().getValue()).isUsernameValid()) {
            value.setError("Renseigner un nom d'utilisateur");
        }
        validateRegisterForm();
    }

    public void isEmailValid(EditText value) {
        if (!Objects.requireNonNull(getUserData().getValue()).isEmailValid()) {
            value.setError("Email incorrect");
        }
        validateRegisterForm();
    }

    public void isPasswordValid(EditText password) {
        if (Objects.requireNonNull(getUserData().getValue()).isPasswordAvailable()) {
            password.setError(null);
        } else {
            password.setError("8 caractères, une majuscule, un chiffre, un caractère spécial");
        }

        validateRegisterForm();
    }
}
