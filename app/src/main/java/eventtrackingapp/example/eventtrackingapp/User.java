package eventtrackingapp.example.eventtrackingapp;

public class User {

    private String username;
    private String password;
    public static String USER_EXTRA="userExtra";


    public User(String usr, String pwd)   {
        username=usr;
        password=pwd;
    }

    public String getPassword() {
        return password;
    }

    public String getUsername() {
        return username;
    }


    public void setPassword(String password) {
        this.password = password;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
