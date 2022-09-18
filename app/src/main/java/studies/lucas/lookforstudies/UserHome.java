package studies.lucas.lookforstudies;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.Objects;

public class UserHome extends AppCompatActivity implements View.OnClickListener {

    private TextView welcomeText, logOutBtn;
    private ImageButton goToProfileBtn;
    private DBHelper usersDB;
    private String uid;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        Objects.requireNonNull(getSupportActionBar()).hide();
        setContentView(R.layout.activity_user_profile);

        goToProfileBtn = (ImageButton) findViewById(R.id.goToProfileBtn);
        goToProfileBtn.setOnClickListener(this);
        logOutBtn = (TextView) findViewById(R.id.logOutTextView);
        logOutBtn.setOnClickListener(this);

        String currentUser = getIntent().getStringExtra("currentUserMail");
        usersDB = new DBHelper(this);
        uid = String.valueOf((usersDB.getUid(currentUser)));

        String loggedName, loggedSurname;
        loggedName = usersDB.getName(uid);
        loggedSurname = usersDB.getSurname(uid);

        welcomeText = (TextView) findViewById(R.id.welcomeTextView);
        welcomeText.setText("Witaj "+loggedName+" "+loggedSurname+"!");

    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View view) {
        switch(view.getId()) {
            case R.id.logOutTextView:
                Intent logout = new Intent(UserHome.this, LoginUser.class);
                startActivity(logout);
            case R.id.goToProfileBtn:
                Intent profile = new Intent(UserHome.this, UserProfile.class);
                profile.putExtra("currentUserUid", uid);
                startActivity(profile);
        }
    }
}