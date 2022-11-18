package studies.lucas.lookforstudies.activities;

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

import studies.lucas.lookforstudies.R;
import studies.lucas.lookforstudies.database.DBHelper;
import studies.lucas.lookforstudies.constructor.User;

public class RegisterUser extends AppCompatActivity implements View.OnClickListener {

    private Button registerUserBtn;
    private TextView loginUserTextView;
    private EditText nameEditText, surnameEditText, emailEditText, passwordEditText, passwordRepeatEditText;
    private DBHelper registerDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        Objects.requireNonNull(getSupportActionBar()).hide();
        setContentView(R.layout.activity_main);

        registerUserBtn = (Button) findViewById(R.id.registerBtn);
        registerUserBtn.setOnClickListener(this);

        loginUserTextView = (TextView) findViewById(R.id.login);
        loginUserTextView.setOnClickListener(this);

        nameEditText = (EditText) findViewById(R.id.registerName);
        surnameEditText = (EditText) findViewById(R.id.registerSurname);
        emailEditText = (EditText) findViewById(R.id.registerEmail);
        passwordEditText = (EditText) findViewById(R.id.registerPassword);
        passwordRepeatEditText = (EditText) findViewById(R.id.registerPasswordRepeat);

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
        final String email = emailEditText.getText().toString().trim();
        final String name = nameEditText.getText().toString().trim();
        final String surname = surnameEditText.getText().toString().trim();

        String password = passwordEditText.getText().toString().trim();
        String passwordRepeat = passwordRepeatEditText.getText().toString().trim();

        if (name.isEmpty()) {
            nameEditText.setError("Pole imię jest wymagane!");
            nameEditText.requestFocus();
            return;
        }

        if (surname.isEmpty()) {
            surnameEditText.setError("Pole nazwisko jest wymagane!");
            surnameEditText.requestFocus();
            return;
        }

        if (email.isEmpty()) {
            emailEditText.setError("Pole email jest wymagane!");
            emailEditText.requestFocus();
            return;
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            emailEditText.setError("Wprowadź prawidłowy adres email!");
            emailEditText.requestFocus();
            return;
        }

        if (password.isEmpty()) {
            passwordEditText.setError("Pole hasło jest wymagane!");
            passwordEditText.requestFocus();
            return;
        }

        if (passwordRepeat.isEmpty()) {
            passwordRepeatEditText.setError("Pole powtórz hasło jest wymagane!");
            passwordRepeatEditText.requestFocus();
            return;
        }

        if (password.length() < 8) {
            passwordEditText.setError("Hasło nie może być krótsze niż 8 znaków");
            passwordEditText.requestFocus();
            return;
        }

        if (!passwordRepeat.equals(password)) {
            passwordRepeatEditText.setError("Hasła muszą być takie same!");
            passwordRepeatEditText.requestFocus();
            return;
        }

        User user = new User(name, surname, email, password);
        Boolean usercheckResult = registerDB.checkUser(email);
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