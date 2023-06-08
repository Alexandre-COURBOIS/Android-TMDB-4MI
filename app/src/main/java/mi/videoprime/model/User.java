package mi.videoprime.model;

import android.util.Patterns;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.regex.Pattern;

import mi.videoprime.BR;
import mi.videoprime.mapping.UserMapping;

public class User extends BaseObservable implements Serializable, UserMapping {
    private static final String PASSWORD_PATTERN = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}$";
    private int id;
    @SerializedName("username")
    private String username;
    @SerializedName("email")
    private String email;
    private transient String password;

    public User(int id, String username, String email, String password) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.password = password;
    }

    public User() {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Bindable
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        if (username != null) {
            this.username = username.toLowerCase();
        } else {
            this.username = username;
        }
        notifyPropertyChanged(BR.username);
    }

    @Bindable
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        if (email != null) {
            this.email = email.toLowerCase();
        } else {
            this.email = email;
        }
        notifyPropertyChanged(BR.email);
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isUsernameValid() {
        if (getUsername() != null) {
            return getUsername().trim().length() >= 1;
        } else {
            return false;
        }
    }

    public boolean isEmailValid() {
        if (getEmail() != null) {
            return Patterns.EMAIL_ADDRESS.matcher(getEmail()).matches();
        } else {
            return false;
        }
    }

    public boolean isPasswordAvailable() {
        if (getPassword() != null) {
            Pattern pattern = Pattern.compile(PASSWORD_PATTERN);
            return pattern.matcher(getPassword()).matches();
        } else {
            return false;
        }
    }




}
