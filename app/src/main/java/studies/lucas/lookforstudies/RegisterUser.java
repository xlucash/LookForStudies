package studies.lucas.lookforstudies;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


import java.util.Objects;

public class RegisterUser extends AppCompatActivity implements View.OnClickListener {

    private Button registerUser;
    private TextView loginUser;
    private EditText editTextName, editTextSurname, editTextEmail, editTextPassword, editTextPasswordRepeat;
    private DBHelper registerDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        Objects.requireNonNull(getSupportActionBar()).hide();
        setContentView(R.layout.activity_main);

        registerUser = (Button) findViewById(R.id.registerBtn);
        registerUser.setOnClickListener(this);

        loginUser = (TextView) findViewById(R.id.login);
        loginUser.setOnClickListener(this);

        editTextName = (EditText) findViewById(R.id.registerName);
        editTextSurname = (EditText) findViewById(R.id.registerSurname);
        editTextEmail = (EditText) findViewById(R.id.registerEmail);
        editTextPassword = (EditText) findViewById(R.id.registerPassword);
        editTextPasswordRepeat = (EditText) findViewById(R.id.registerPasswordRepeat);

        registerDB = new DBHelper(this);


    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View view) {
        switch(view.getId())
        {
            case R.id.login:
                startActivity(new Intent(this, LoginUser.class));
                break;
            case R.id.registerBtn:
                registerUser();
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + view.getId());
        }
    }


    private void registerUser() {
        final String email = editTextEmail.getText().toString().trim();
        final String name = editTextName.getText().toString().trim();
        final String surname = editTextSurname.getText().toString().trim();

        String password = editTextPassword.getText().toString().trim();
        String passwordRepeat = editTextPasswordRepeat.getText().toString().trim();

        if (name.isEmpty()) {
            editTextName.setError("Pole imię jest wymagane!");
            editTextName.requestFocus();
            return;
        }

        if (surname.isEmpty()) {
            editTextSurname.setError("Pole nazwisko jest wymagane!");
            editTextSurname.requestFocus();
            return;
        }

        if (email.isEmpty()) {
            editTextEmail.setError("Pole email jest wymagane!");
            editTextEmail.requestFocus();
            return;
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            editTextEmail.setError("Wprowadź prawidłowy adres email!");
            editTextEmail.requestFocus();
            return;
        }

        if (password.isEmpty()) {
            editTextPassword.setError("Pole hasło jest wymagane!");
            editTextPassword.requestFocus();
            return;
        }

        if (passwordRepeat.isEmpty()) {
            editTextPasswordRepeat.setError("Pole powtórz hasło jest wymagane!");
            editTextPasswordRepeat.requestFocus();
            return;
        }

        if (password.length() < 8) {
            editTextPassword.setError("Hasło nie może być krótsze niż 8 znaków");
            editTextPassword.requestFocus();
            return;
        }

        if (!passwordRepeat.equals(password)) {
            editTextPasswordRepeat.setError("Hasła muszą być takie same!");
            editTextPasswordRepeat.requestFocus();
            return;
        }

        User user = new User(name, surname, email, password);
        Boolean usercheckResult = registerDB.checkuser(email);
        if(usercheckResult) {
            Toast.makeText(RegisterUser.this, "Użytkownik o podanym mailu już istnieje!", Toast.LENGTH_LONG).show();
        }

        Boolean registerResult = registerDB.insertData(user);
        if(!registerResult){
            Toast.makeText(RegisterUser.this, "Nie udało się utworzyć konta!", Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(RegisterUser.this, "Konto utworzone pomyślnie!", Toast.LENGTH_LONG).show();
            Intent intent = new Intent(getApplicationContext(), LoginUser.class);
            startActivity(intent);
        }

    }
}