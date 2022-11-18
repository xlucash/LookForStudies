package studies.lucas.lookforstudies.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Objects;

import studies.lucas.lookforstudies.R;
import studies.lucas.lookforstudies.database.DBHelper;

public class LoginUser extends AppCompatActivity implements View.OnClickListener{

    private TextView register;
    private Button loginUserBtn;
    private EditText emailEditText, passwordEditText;
    private DBHelper loginDB;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        Objects.requireNonNull(getSupportActionBar()).hide();
        setContentView(R.layout.activity_login_user);

        register = (TextView) findViewById(R.id.register);
        register.setOnClickListener(this);


        loginUserBtn = (Button) findViewById(R.id.loginBtn);
        loginUserBtn.setOnClickListener(this);

        emailEditText = (EditText) findViewById(R.id.loginEmail);
        passwordEditText = (EditText) findViewById(R.id.loginPassword);

        loginDB = new DBHelper(this);


    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View view) {
        switch(view.getId()) {
            case R.id.register:
                startActivity(new Intent(this, RegisterUser.class));
                break;
            case R.id.loginBtn:
                loginUser();
                break;
        }
    }


    private void loginUser() {
        String userEmail = emailEditText.getText().toString().trim();
        String userPassword = passwordEditText.getText().toString().trim();

        if (userEmail.isEmpty()) {
            emailEditText.setError("Pole email jest wymagane!");
            emailEditText.requestFocus();
            return;
        }

        if (userPassword.isEmpty()) {
            passwordEditText.setError("Pole powtórz hasło jest wymagane!");
            passwordEditText.requestFocus();
            return;
        }

        Boolean userValidation = loginDB.checkEmailPassword(userEmail,userPassword);
        if(!userValidation) {
            Toast.makeText(LoginUser.this, "Nieprawidłowe dane!", Toast.LENGTH_LONG).show();
        } else {
            Intent intent = new Intent(LoginUser.this, UserHome.class);
            intent.putExtra("currentUserMail", userEmail);
            startActivity(intent);
        }

    }
}