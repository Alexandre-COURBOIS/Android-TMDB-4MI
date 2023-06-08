package mi.videoprime.service;

import android.content.Context;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.File;
import java.io.StringReader;
import java.lang.reflect.Type;

import javax.inject.Inject;

import dagger.hilt.android.qualifiers.ApplicationContext;
import mi.videoprime.dao.DaoFactory;
import mi.videoprime.model.User;
import mi.videoprime.model.UserLogin;
import mi.videoprime.service.interfaces.IFileManager;
import mi.videoprime.service.interfaces.IPasswordUtils;
import mi.videoprime.service.interfaces.IToastService;
import mi.videoprime.service.interfaces.IUserService;

public class UserService implements IUserService {
    Context _context;
    @Inject
    IToastService _toastService;
    @Inject
    IPasswordUtils _passwordUtils;
    @Inject
    IFileManager _fileManager;

    @Inject
    public UserService(@ApplicationContext Context context){
        _context = context;
    }


    public User createUser(User user) {
        user.setPassword(_passwordUtils.hashPassword(user.getPassword()));
        int userId = DaoFactory.getUserDao(_context).add(user);

        if (userId >= 1) {
            User us = DaoFactory.getUserDao(_context).get(userId);
            String userString = convertUserToString(us);
            File userFile = new File(_context.getFilesDir(), "user.json");
            _fileManager.writeFile(userFile, userString.getBytes());

            return us;
        } else {
            _toastService.showToastError("Cet email existe déjà", Toast.LENGTH_LONG);
            return null;
        }
    }

    public User isUserExist(String usernameOrEmail) {
        return DaoFactory.getUserDao(_context).getUserByEmailOrUsername(usernameOrEmail);
    }

    public Boolean isUserRegistered(UserLogin userLogin, User user) {

        if (_passwordUtils.checkPassword(userLogin.getPassword(), user.getPassword())) {
            return true;
        } else {
            return false;
        }
    }

    private User convertStringToUser(String userToJson) {
        Gson gson = new Gson();
        User user = new User();
        try (StringReader reader = new StringReader(userToJson)) {
            user = gson.fromJson(reader, User.class);
        }
        return user;
    }

    public User getUserFromJson(){
        File userFile = new File(_context.getFilesDir(), "user.json");
        try {
            String user_json = new String(_fileManager.readFile(userFile));
            return convertStringToUser(user_json);
        } catch (Exception e) {
            return null;
        }
    }

    private String convertUserToString(User user) {
        Gson gson = new Gson();
        Type type = new TypeToken<User>() {}.getType();
        return gson.toJson(user, type);
    }
}
