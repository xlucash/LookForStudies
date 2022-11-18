package studies.lucas.lookforstudies.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.Objects;

import studies.lucas.lookforstudies.R;
import studies.lucas.lookforstudies.database.DBHelper;

public class UserProfile extends AppCompatActivity {

    private ImageButton hobbyBtn, resultsBtn, univeristyBtn, localizationBtn, goBackBtn;
    private TextView currentUser;
    private String uid, name, surname;
    private DBHelper usersDB;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        Objects.requireNonNull(getSupportActionBar()).hide();
        setContentView(R.layout.activity_user_profile2);

        usersDB = new DBHelper(this);
        uid = getIntent().getStringExtra("currentUserUid");
        name = usersDB.getName(uid);
        surname = usersDB.getSurname(uid);

        currentUser = (TextView) findViewById(R.id.currentUserProfile);
        currentUser.setText(name +" "+surname);

        goBackBtn = (ImageButton) findViewById(R.id.goBackBtn);
        goBackBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent goHome = new Intent(UserProfile.this, UserHome.class);
                startActivity(goHome);
            }
        });

        resultsBtn = (ImageButton) findViewById(R.id.resultsBtn);
        resultsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent goToResults = new Intent(UserProfile.this, ExamResults.class);
                goToResults.putExtra("currentUserUid", uid);
                startActivity(goToResults);
            }
        });


    }


}