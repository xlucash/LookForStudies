package studies.lucas.lookforstudies;

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

public class LoginUser extends AppCompatActivity implements View.OnClickListener{

    private TextView register;
    private Button loginUserBtn;
    private EditText editTextEmail, editTextPassword;
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

        editTextEmail = (EditText) findViewById(R.id.loginEmail);
        editTextPassword = (EditText) findViewById(R.id.loginPassword);

        loginDB = new DBHelper(this);


    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View view) {
        switch(view.getId()) {
            case R.id.register:
                startActivity(new Intent(this, MainActivity.class));
                break;
            case R.id.loginBtn:
                loginUser();
                break;
        }
    }


    private void loginUser() {
        String userEmail = editTextEmail.getText().toString().trim();
        String userPassword = editTextPassword.getText().toString().trim();

        if (userEmail.isEmpty()) {
            editTextEmail.setError("Pole email jest wymagane!");
            editTextEmail.requestFocus();
            return;
        }

        if (userPassword.isEmpty()) {
            editTextPassword.setError("Pole powtórz hasło jest wymagane!");
            editTextPassword.requestFocus();
            return;
        }

        Boolean userValid = loginDB.checkEmailPassword(userEmail,userPassword);
        if(userValid) {
            Intent intent = new Intent(getApplicationContext(), UserProfile.class);
            intent.putExtra("currentUserMail", userEmail);
            startActivity(intent);
        } else {
            Toast.makeText(LoginUser.this, "Nieprawidłowe dane!", Toast.LENGTH_LONG).show();
        }

    }
}