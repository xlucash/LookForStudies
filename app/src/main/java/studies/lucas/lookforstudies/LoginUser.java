package studies.lucas.lookforstudies;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import java.util.Objects;

public class LoginUser extends AppCompatActivity implements View.OnClickListener{

    private TextView register;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        Objects.requireNonNull(getSupportActionBar()).hide();
        setContentView(R.layout.activity_login_user);

        register = (TextView) findViewById(R.id.register);
        register.setOnClickListener(this);


        Button btn = (Button) findViewById(R.id.loginBtn);

        btn.setOnClickListener(view -> startActivity(new Intent(LoginUser.this, UserProfile.class)));
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View view) {
        switch(view.getId()) {
            case R.id.register:
                startActivity(new Intent(this, MainActivity.class));
                break;
        }
    }
}