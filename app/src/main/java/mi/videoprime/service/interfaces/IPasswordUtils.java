package mi.videoprime.service.interfaces;

public interface IPasswordUtils {

    String hashPassword(String password);

    boolean checkPassword(String password, String hashedPassword);
}
