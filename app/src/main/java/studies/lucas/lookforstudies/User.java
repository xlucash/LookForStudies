package studies.lucas.lookforstudies;


public class User {

    public String name, surname, email, password;

    public User(){
    }

    public User(String name, String surname, String email, String password) {
        this.name=name;
        this.surname=surname;
        this.email=email;
        this.password=password;
    }

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() { return password; }
}
