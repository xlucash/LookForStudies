package studies.lucas.lookforstudies;

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

public class ExamResults extends AppCompatActivity implements View.OnClickListener{

    private ImageButton goBackBtn, confirmMathResultsBtn, confirmPolishResultsBtn, confirmEnglishResultsBtn, confirmAdvancedOneBtn, confirmAdvancedTwoBtn, confirmAdvancedThreeBtn;
    private EditText mathPercentage, polishPercentage, englishPercentage, advancedOnePercentage, advancedTwoPercentage, advancedThreePercentage;
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

        mathPercentage = (EditText) findViewById(R.id.editTextMathResult);
        mathPercentage.setFocusable(false);
        mathPercentage.setFocusableInTouchMode(false);

        confirmPolishResultsBtn = (ImageButton) findViewById(R.id.confirmPolishBtn);
        confirmPolishResultsBtn.setVisibility(View.INVISIBLE);

        polishPercentage = (EditText) findViewById(R.id.editTextPolishResult);
        polishPercentage.setFocusable(false);
        polishPercentage.setFocusableInTouchMode(false);

        confirmEnglishResultsBtn = (ImageButton) findViewById(R.id.confirmEnglishBtn);
        confirmEnglishResultsBtn.setVisibility(View.INVISIBLE);

        englishPercentage = (EditText) findViewById(R.id.editTextEnglishResult);
        englishPercentage.setFocusable(false);
        englishPercentage.setFocusableInTouchMode(false);


        mathPercentage.setOnClickListener(this);
        polishPercentage.setOnClickListener(this);
        englishPercentage.setOnClickListener(this);

        confirmAdvancedOneBtn = (ImageButton) findViewById(R.id.confirmAdvancedOneBtn);
        confirmAdvancedOneBtn.setVisibility(View.INVISIBLE);

        advancedOnePercentage = (EditText) findViewById(R.id.editTextAdvancedResultOne);
        advancedOnePercentage.setFocusable(false);
        advancedOnePercentage.setFocusableInTouchMode(false);

        advancedOneSubject = (AutoCompleteTextView) findViewById(R.id.autoCompleteAdvancedOne);
        advancedOneSubject.setAdapter(new ArrayAdapter<>(ExamResults.this, android.R.layout.simple_list_item_1, subjects));


        confirmAdvancedTwoBtn = (ImageButton) findViewById(R.id.confirmAdvancedTwoBtn);
        confirmAdvancedTwoBtn.setVisibility(View.INVISIBLE);

        advancedTwoPercentage = (EditText) findViewById(R.id.editTextAdvancedResultTwo);
        advancedTwoPercentage.setFocusable(false);
        advancedTwoPercentage.setFocusableInTouchMode(false);

        advancedTwoSubject = (AutoCompleteTextView) findViewById(R.id.autoCompleteAdvancedTwo);
        advancedTwoSubject.setAdapter(new ArrayAdapter<>(ExamResults.this, android.R.layout.simple_list_item_1, subjects));


        confirmAdvancedThreeBtn = (ImageButton) findViewById(R.id.confirmAdvancedThreeBtn);
        confirmAdvancedThreeBtn.setVisibility(View.INVISIBLE);


        advancedThreePercentage = (EditText) findViewById(R.id.editTextAdvancedResultThree);
        advancedThreePercentage.setFocusable(false);
        advancedThreePercentage.setFocusableInTouchMode(false);

        advancedThreeSubject = (AutoCompleteTextView) findViewById(R.id.autoCompleteAdvancedThree);
        advancedThreeSubject.setAdapter(new ArrayAdapter<>(ExamResults.this, android.R.layout.simple_list_item_1, subjects));


        advancedOnePercentage.setOnClickListener(this);
        advancedTwoPercentage.setOnClickListener(this);
        advancedThreePercentage.setOnClickListener(this);


        goBackBtn = (ImageButton) findViewById(R.id.goBackToProfile);
        goBackBtn.setOnClickListener(this);

        DBHelper.checkIfDataExistBasicResults(resultsDB, uid, mathPercentage, polishPercentage, englishPercentage);

        if(!resultsDB.checkIfAdvancedResultsInDB(uid)) {
            advancedOnePercentage.setText("");
            advancedTwoPercentage.setText("");
            advancedThreePercentage.setText("");
        } else {
            String searchSubject;
            String convertedSubject;

            searchSubject = resultsDB.getAdvancedSubject(uid, "0");
            convertedSubject = SubjectHelper.getSubjectReverse(searchSubject);
            advancedOneSubject.setText(convertedSubject);
            advancedOnePercentage.setText(resultsDB.getPercentage(searchSubject, uid)+"%");

            // check if there is more than 1 advanced subject assigned to the uid
            if (resultsDB.checkCountAdvancedResultsInDB(uid)>1) {
                searchSubject = resultsDB.getAdvancedSubject(uid, "1");
                convertedSubject = SubjectHelper.getSubjectReverse(searchSubject);
                advancedTwoSubject.setText(convertedSubject);
                advancedTwoPercentage.setText(resultsDB.getPercentage(searchSubject, uid) + "%");
            }

            // check if there is more than 2 advanced subjects assigned to the uid
            if(resultsDB.checkCountAdvancedResultsInDB(uid)>2) {
                searchSubject = resultsDB.getAdvancedSubject(uid, "2");
                convertedSubject = SubjectHelper.getSubjectReverse(searchSubject);
                advancedThreeSubject.setText(convertedSubject);
                advancedThreePercentage.setText(resultsDB.getPercentage(searchSubject, uid) + "%");
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
                confirmMathResultsBtn.setVisibility(View.VISIBLE);
                mathPercentage.setFocusable(true);
                mathPercentage.setFocusableInTouchMode(true);

                confirmMathResultsBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String result = mathPercentage.getText().toString().trim();
                        mathPercentage.setFocusable(false);
                        mathPercentage.setFocusableInTouchMode(false);
                        if (!String.valueOf(result.charAt(result.length() - 1)).equals("%")) {
                            mathPercentage.setText(result+"%");
                        } else {
                            mathPercentage.setText(result);
                        }
                        resultsDB.insertResultsData("MATH", result, uid);

                        confirmMathResultsBtn.setVisibility(View.INVISIBLE);
                    }
                });
                break;
            case R.id.editTextPolishResult:
                confirmPolishResultsBtn.setVisibility(View.VISIBLE);
                polishPercentage.setFocusable(true);
                polishPercentage.setFocusableInTouchMode(true);

                confirmPolishResultsBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String result = polishPercentage.getText().toString().trim();
                        polishPercentage.setFocusable(false);
                        polishPercentage.setFocusableInTouchMode(false);
                        if (!String.valueOf(result.charAt(result.length() - 1)).equals("%")) {
                            polishPercentage.setText(result+"%");
                        } else {
                            polishPercentage.setText(result);
                        }
                        resultsDB.insertResultsData("POLISH", result, uid);

                        confirmPolishResultsBtn.setVisibility(View.INVISIBLE);
                    }
                });
                break;
            case R.id.editTextEnglishResult:
                confirmEnglishResultsBtn.setVisibility(View.VISIBLE);
                englishPercentage.setFocusable(true);
                englishPercentage.setFocusableInTouchMode(true);

                confirmEnglishResultsBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String result = englishPercentage.getText().toString().trim();
                        englishPercentage.setFocusable(false);
                        englishPercentage.setFocusableInTouchMode(false);
                        if (!String.valueOf(result.charAt(result.length() - 1)).equals("%")) {
                            englishPercentage.setText(result+"%");
                        } else {
                            englishPercentage.setText(result);
                        }
                        resultsDB.insertResultsData("ENGLISH", result, uid);

                        confirmEnglishResultsBtn.setVisibility(View.INVISIBLE);
                    }
                });
                break;
            case R.id.editTextAdvancedResultOne:
                confirmAdvancedOneBtn.setVisibility(View.VISIBLE);
                advancedOnePercentage.setFocusable(true);
                advancedOnePercentage.setFocusableInTouchMode(true);

                confirmAdvancedOneBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String userSubject = advancedOneSubject.getText().toString().trim();
                        String subject = SubjectHelper.getSubject(userSubject);
                        String result = advancedOnePercentage.getText().toString().trim();
                        advancedOnePercentage.setFocusable(false);
                        advancedOnePercentage.setFocusableInTouchMode(false);
                        if (!String.valueOf(result.charAt(result.length() - 1)).equals("%")) {
                            advancedOnePercentage.setText(result+"%");
                        } else {
                            advancedOnePercentage.setText(result);
                        }
                        resultsDB.insertResultsData(subject, result, uid);

                        confirmAdvancedOneBtn.setVisibility(View.INVISIBLE);
                    }
                });
                break;
            case R.id.editTextAdvancedResultTwo:
                confirmAdvancedTwoBtn.setVisibility(View.VISIBLE);
                advancedTwoPercentage.setFocusable(true);
                advancedTwoPercentage.setFocusableInTouchMode(true);

                confirmAdvancedTwoBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String userSubject = advancedTwoSubject.getText().toString().trim();
                        String subject = SubjectHelper.getSubject(userSubject);
                        String result = advancedTwoPercentage.getText().toString().trim();
                        advancedTwoPercentage.setFocusable(false);
                        advancedTwoPercentage.setFocusableInTouchMode(false);
                        if (!String.valueOf(result.charAt(result.length() - 1)).equals("%")) {
                            advancedTwoPercentage.setText(result+"%");
                        } else {
                            advancedTwoPercentage.setText(result);
                        }
                        resultsDB.insertResultsData(subject, result, uid);

                        confirmAdvancedTwoBtn.setVisibility(View.INVISIBLE);
                    }
                });
                break;
            case R.id.editTextAdvancedResultThree:
                confirmAdvancedThreeBtn.setVisibility(View.VISIBLE);
                advancedThreePercentage.setFocusable(true);
                advancedThreePercentage.setFocusableInTouchMode(true);

                confirmAdvancedThreeBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String userSubject = advancedThreeSubject.getText().toString().trim();
                        String subject = SubjectHelper.getSubject(userSubject);
                        String result = advancedThreePercentage.getText().toString().trim();
                        advancedThreePercentage.setFocusable(false);
                        advancedThreePercentage.setFocusableInTouchMode(false);
                        if (!String.valueOf(result.charAt(result.length() - 1)).equals("%")) {
                            advancedThreePercentage.setText(result+"%");
                        } else {
                            advancedThreePercentage.setText(result);
                        }
                        resultsDB.insertResultsData(subject, result, uid);

                        confirmAdvancedThreeBtn.setVisibility(View.INVISIBLE);
                    }
                });
                break;
        }
    }

}