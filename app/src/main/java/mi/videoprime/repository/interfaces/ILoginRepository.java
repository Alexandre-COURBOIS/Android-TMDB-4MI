package mi.videoprime.repository.interfaces;

public interface ILoginRepository {

    boolean isRegistered();

    boolean isLogged();

    void setIsRegistered(Boolean isRegistered);

    void setIsLogged(Boolean isLogged);
}
