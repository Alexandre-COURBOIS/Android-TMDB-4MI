package mi.videoprime.service;

import javax.inject.Inject;

import at.favre.lib.crypto.bcrypt.BCrypt;
import mi.videoprime.service.interfaces.IPasswordUtils;

public class PasswordUtils implements IPasswordUtils {

    @Inject
    public PasswordUtils() {

    }

    public String hashPassword(String password) {
        // Génération du hachage du mot de passe
        String bcryptHashString = BCrypt.withDefaults().hashToString(12, password.toCharArray());

        return bcryptHashString;
    }

    public boolean checkPassword(String password, String hashedPassword) {
        // Vérifier si le mot de passe correspond au hash
        BCrypt.Result result = BCrypt.verifyer().verify(password.toCharArray(), hashedPassword);

        return result.verified;
    }
}