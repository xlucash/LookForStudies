package studies.lucas.lookforstudies;

import com.google.firebase.database.IgnoreExtraProperties;

public class User {

    public String name, surname, email;

    public User(){
    }

    public User(String name, String surname, String email) {
        this.name=name;
        this.surname=surname;
        this.email=email;
    }
}
