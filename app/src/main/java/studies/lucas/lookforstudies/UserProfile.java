package studies.lucas.lookforstudies;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.Objects;

public class UserProfile extends AppCompatActivity implements View.OnClickListener {

    private ImageButton hobbyBtn, resultsBtn, univeristyBtn, localizationBtn, goBackBtn;
    private TextView currentUser;
    private String uid, name, surname;
    private DBHelper usersDB;

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
        goBackBtn.setOnClickListener(this);


    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.goBackBtn:
                Intent goHome = new Intent(getApplicationContext(), UserHome.class);
                startActivity(goHome);
        }

    }
}