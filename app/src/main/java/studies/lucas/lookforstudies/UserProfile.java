package studies.lucas.lookforstudies;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.Window;
import android.widget.TextView;

import java.util.Objects;

public class UserProfile extends AppCompatActivity {

    private TextView welcomeText;
    private DBHelper usersDB;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        Objects.requireNonNull(getSupportActionBar()).hide();
        setContentView(R.layout.activity_user_profile);

        String currentUser = getIntent().getStringExtra("currentUserMail");
        usersDB = new DBHelper(this);
        String uid = String.valueOf((usersDB.getUid(currentUser)));

        String loggedName, loggedSurname;
        loggedName = usersDB.getName(uid);
        loggedSurname = usersDB.getSurname(uid);

        welcomeText = (TextView) findViewById(R.id.welcomeTextView);
        welcomeText.setText("Witaj "+loggedName+" "+loggedSurname+"!");

    }
}