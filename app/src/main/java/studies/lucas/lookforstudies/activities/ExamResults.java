package studies.lucas.lookforstudies.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.ImageButton;

import java.util.Objects;

import studies.lucas.lookforstudies.R;
import studies.lucas.lookforstudies.database.DBHelper;
import studies.lucas.lookforstudies.database.SubjectHelper;

public class ExamResults extends AppCompatActivity implements View.OnClickListener{

    private ImageButton goBackBtn, confirmMathResultsBtn, confirmPolishResultsBtn, confirmEnglishResultsBtn, confirmAdvancedOneBtn, confirmAdvancedTwoBtn, confirmAdvancedThreeBtn;
    private EditText mathPercentageEditText, polishPercentageEditText, englishPercentageEditText, advancedOnePercentageEditText, advancedTwoPercentageEditText, advancedThreePercentageEditText;
    private AutoCompleteTextView advancedOneSubject, advancedTwoSubject, advancedThreeSubject;
    private String uid;
    private DBHelper resultsDB;
    private String[] subjects;
    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        Objects.requireNonNull(getSupportActionBar()).hide();
        setContentView(R.layout.activity_exam_results);

        uid = getIntent().getStringExtra("currentUserUid");
        resultsDB = new DBHelper(this);
        subjects = new String[] {"Matematyka","Jezyk polski","Jezyk angielski","Jezyk niemiecki","Informatyka","Fizyka","Biologia","Chemia","Geografia","Historia"};


        confirmMathResultsBtn = (ImageButton) findViewById(R.id.confirmMathBtn);
        confirmMathResultsBtn.setVisibility(View.INVISIBLE);

        mathPercentageEditText = (EditText) findViewById(R.id.editTextMathResult);
        mathPercentageEditText.setFocusable(false);
        mathPercentageEditText.setFocusableInTouchMode(false);

        confirmPolishResultsBtn = (ImageButton) findViewById(R.id.confirmPolishBtn);
        confirmPolishResultsBtn.setVisibility(View.INVISIBLE);

        polishPercentageEditText = (EditText) findViewById(R.id.editTextPolishResult);
        polishPercentageEditText.setFocusable(false);
        polishPercentageEditText.setFocusableInTouchMode(false);

        confirmEnglishResultsBtn = (ImageButton) findViewById(R.id.confirmEnglishBtn);
        confirmEnglishResultsBtn.setVisibility(View.INVISIBLE);

        englishPercentageEditText = (EditText) findViewById(R.id.editTextEnglishResult);
        englishPercentageEditText.setFocusable(false);
        englishPercentageEditText.setFocusableInTouchMode(false);


        mathPercentageEditText.setOnClickListener(this);
        polishPercentageEditText.setOnClickListener(this);
        englishPercentageEditText.setOnClickListener(this);

        confirmAdvancedOneBtn = (ImageButton) findViewById(R.id.confirmAdvancedOneBtn);
        confirmAdvancedOneBtn.setVisibility(View.INVISIBLE);

        advancedOnePercentageEditText = (EditText) findViewById(R.id.editTextAdvancedResultOne);
        advancedOnePercentageEditText.setFocusable(false);
        advancedOnePercentageEditText.setFocusableInTouchMode(false);

        advancedOneSubject = (AutoCompleteTextView) findViewById(R.id.autoCompleteAdvancedOne);
        advancedOneSubject.setAdapter(new ArrayAdapter<>(ExamResults.this, android.R.layout.simple_list_item_1, subjects));


        confirmAdvancedTwoBtn = (ImageButton) findViewById(R.id.confirmAdvancedTwoBtn);
        confirmAdvancedTwoBtn.setVisibility(View.INVISIBLE);

        advancedTwoPercentageEditText = (EditText) findViewById(R.id.editTextAdvancedResultTwo);
        advancedTwoPercentageEditText.setFocusable(false);
        advancedTwoPercentageEditText.setFocusableInTouchMode(false);

        advancedTwoSubject = (AutoCompleteTextView) findViewById(R.id.autoCompleteAdvancedTwo);
        advancedTwoSubject.setAdapter(new ArrayAdapter<>(ExamResults.this, android.R.layout.simple_list_item_1, subjects));


        confirmAdvancedThreeBtn = (ImageButton) findViewById(R.id.confirmAdvancedThreeBtn);
        confirmAdvancedThreeBtn.setVisibility(View.INVISIBLE);


        advancedThreePercentageEditText = (EditText) findViewById(R.id.editTextAdvancedResultThree);
        advancedThreePercentageEditText.setFocusable(false);
        advancedThreePercentageEditText.setFocusableInTouchMode(false);

        advancedThreeSubject = (AutoCompleteTextView) findViewById(R.id.autoCompleteAdvancedThree);
        advancedThreeSubject.setAdapter(new ArrayAdapter<>(ExamResults.this, android.R.layout.simple_list_item_1, subjects));


        advancedOnePercentageEditText.setOnClickListener(this);
        advancedTwoPercentageEditText.setOnClickListener(this);
        advancedThreePercentageEditText.setOnClickListener(this);


        goBackBtn = (ImageButton) findViewById(R.id.goBackToProfile);
        goBackBtn.setOnClickListener(this);

        DBHelper.checkIfDataExistBasicResults(resultsDB, uid, mathPercentageEditText, polishPercentageEditText, englishPercentageEditText);

        if(!resultsDB.checkIfAdvancedResultsInDB(uid)) {
            advancedOnePercentageEditText.setText("");
            advancedTwoPercentageEditText.setText("");
            advancedThreePercentageEditText.setText("");
        } else {
            String searchSubject;
            String convertedSubject;

            searchSubject = resultsDB.getAdvancedSubject(uid, "0");
            convertedSubject = SubjectHelper.getSubjectReverse(searchSubject);
            advancedOneSubject.setText(convertedSubject);
            advancedOnePercentageEditText.setText(resultsDB.getPercentage(searchSubject, uid)+"%");

            // check if there is more than 1 advanced subject assigned to the uid
            if (resultsDB.checkCountAdvancedResultsInDB(uid)>1) {
                searchSubject = resultsDB.getAdvancedSubject(uid, "1");
                convertedSubject = SubjectHelper.getSubjectReverse(searchSubject);
                advancedTwoSubject.setText(convertedSubject);
                advancedTwoPercentageEditText.setText(resultsDB.getPercentage(searchSubject, uid) + "%");
            }

            // check if there is more than 2 advanced subjects assigned to the uid
            if(resultsDB.checkCountAdvancedResultsInDB(uid)>2) {
                searchSubject = resultsDB.getAdvancedSubject(uid, "2");
                convertedSubject = SubjectHelper.getSubjectReverse(searchSubject);
                advancedThreeSubject.setText(convertedSubject);
                advancedThreePercentageEditText.setText(resultsDB.getPercentage(searchSubject, uid) + "%");
            }
        }
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View view) {
        switch(view.getId()) {
            case R.id.goBackToProfile:
                Intent intent = new Intent(ExamResults.this, UserProfile.class);
                startActivity(intent);
                break;
            case R.id.editTextMathResult:
                settingValueOfBasicResult(confirmMathResultsBtn, mathPercentageEditText, "MATH");
                break;
            case R.id.editTextPolishResult:
                settingValueOfBasicResult(confirmPolishResultsBtn, polishPercentageEditText, "POLISH");
                break;
            case R.id.editTextEnglishResult:
                settingValueOfBasicResult(confirmEnglishResultsBtn, englishPercentageEditText, "ENGLISH");
                break;
            case R.id.editTextAdvancedResultOne:
                settingValueOfAdvancedResultField(confirmAdvancedOneBtn, advancedOnePercentageEditText, advancedOneSubject);
                break;
            case R.id.editTextAdvancedResultTwo:
                settingValueOfAdvancedResultField(confirmAdvancedTwoBtn, advancedTwoPercentageEditText, advancedTwoSubject);
                break;
            case R.id.editTextAdvancedResultThree:
                settingValueOfAdvancedResultField(confirmAdvancedThreeBtn, advancedThreePercentageEditText, advancedThreeSubject);
                break;
        }
    }

    private void settingValueOfBasicResult(ImageButton confirmResultsBtn, EditText percentageEditText, String subject) {
        confirmResultsBtn.setVisibility(View.VISIBLE);
        percentageEditText.setFocusable(true);
        percentageEditText.setFocusableInTouchMode(true);

        confirmResultsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String result = percentageEditText.getText().toString().trim();
                percentageEditText.setFocusable(false);
                percentageEditText.setFocusableInTouchMode(false);
                if (!String.valueOf(result.charAt(result.length() - 1)).equals("%")) {
                    percentageEditText.setText(result+"%");
                } else {
                    percentageEditText.setText(result);
                }
                resultsDB.insertResultsData(subject, result, uid);

                confirmResultsBtn.setVisibility(View.INVISIBLE);
            }
        });
    }

    private void settingValueOfAdvancedResultField(ImageButton confirmAdvancedBtn, EditText advancedPercentageEditText, AutoCompleteTextView advancedSubject) {
        confirmAdvancedBtn.setVisibility(View.VISIBLE);
        advancedPercentageEditText.setFocusable(true);
        advancedPercentageEditText.setFocusableInTouchMode(true);

        confirmAdvancedBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String userSubject = advancedSubject.getText().toString().trim();
                String subject = SubjectHelper.getSubject(userSubject);
                String result = advancedPercentageEditText.getText().toString().trim();
                advancedPercentageEditText.setFocusable(false);
                advancedPercentageEditText.setFocusableInTouchMode(false);
                if (!String.valueOf(result.charAt(result.length() - 1)).equals("%")) {
                    advancedPercentageEditText.setText(result+"%");
                } else {
                    advancedPercentageEditText.setText(result);
                }
                resultsDB.insertResultsData(subject, result, uid);

                confirmAdvancedBtn.setVisibility(View.INVISIBLE);
            }
        });
    }

}