package mi.videoprime.model;

public class UserLogin {

    private String identifier;
    private String password;

    public String getIdentifier() {
        return identifier;
    }

    public void setIdentifier(String identifier) {
        if (identifier != null) {
            this.identifier = identifier.toLowerCase();
        } else {
            this.identifier = identifier;
        }
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
