package mi.videoprime.dao;

import java.util.List;

import mi.videoprime.model.User;

public interface UserDao {
    public List<User> getAll();
    public User get(int id);
    public User getUserByEmailOrUsername(String value);
    public void update(User user);
    public int add(User user);
}
