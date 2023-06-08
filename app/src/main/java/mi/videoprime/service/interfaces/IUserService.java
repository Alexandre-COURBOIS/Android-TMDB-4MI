package mi.videoprime.service.interfaces;

import mi.videoprime.model.User;
import mi.videoprime.model.UserLogin;

public interface IUserService {
    User createUser(User user);
    User isUserExist(String usernameOrEmail);
    Boolean isUserRegistered(UserLogin userLogin, User user);
}
