package studies.lucas.lookforstudies;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Objects;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button registerUser;
    private TextView loginUser;
    private EditText editTextName, editTextSurname, editTextEmail, editTextPassword, editTextPasswordRepeat;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        Objects.requireNonNull(getSupportActionBar()).hide();
        setContentView(R.layout.activity_main);

        mAuth = FirebaseAuth.getInstance();

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("Users");

        myRef.setValue("Hello, World!");


        registerUser = (Button) findViewById(R.id.registerBtn);
        registerUser.setOnClickListener(this);

        loginUser = (TextView) findViewById(R.id.login);
        loginUser.setOnClickListener(this);

        editTextName = (EditText) findViewById(R.id.registerName);
        editTextSurname = (EditText) findViewById(R.id.registerSurname);
        editTextEmail = (EditText) findViewById(R.id.registerEmail);
        editTextPassword = (EditText) findViewById(R.id.registerPassword);
        editTextPasswordRepeat = (EditText) findViewById(R.id.registerPasswordRepeat);


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


        User user = new User(name, surname, email);

        System.out.println("Register user");
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        System.out.println("Task started");
                        if(task.isSuccessful()){
                            System.out.println("Task successsful");
                            FirebaseDatabase.getInstance().getReference("Users")
                                    .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                    .setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            System.out.println("ACCOUNT CREATED");
                                            if(task.isSuccessful()){
                                                Toast.makeText(MainActivity.this, "Konto zostało utworzone pomyślnie!", Toast.LENGTH_LONG).show();
                                                System.out.println("TASK SUCCEED");

                                                // redirect to login

                                            } else {
                                                Toast.makeText(MainActivity.this, "Nie udało się utworzyć konta!", Toast.LENGTH_LONG).show();
                                                System.out.println("TASK FAILED");
                                            }
                                        }
                                    });
                        } else {
                            Toast.makeText(MainActivity.this, "Wystąpił problem z utworzeniem konta!", Toast.LENGTH_LONG).show();
                        }
                    }
                });
    }
}