package studies.lucas.lookforstudies.database;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.EditText;

import studies.lucas.lookforstudies.constructor.User;

public class DBHelper extends SQLiteOpenHelper {
    public static final String CREATE_TABLE_USERS = "create Table users(uid INTEGER PRIMARY KEY AUTOINCREMENT,name TEXT, surname TEXT, email TEXT, password TEXT)";
    public static final String CREATE_TABLE_EXAMRESULTS = "create Table examresults(id INTEGER PRIMARY KEY AUTOINCREMENT, subject TEXT,result TEXT,uid INTEGER)";
    public static final String DROP_TABLE_IF_EXISTS_USERS = "drop Table if exists users";
    public static final String DROP_TABLE_IF_EXISTS_EXAMRESULTS = "drop Table if exists examresults";
    public static final String SELECT_RESULT_FROM_EXAMRESULTS_WHERE_SUBJECT_AND_UID = "select result from examresults where subject = ? AND uid = ?";
    public static final String SELECT_FROM_USERS_WHERE_EMAIL = "select * from users where email = ?";
    public static final String SELECT_FROM_USERS_WHERE_EMAIL_AND_PASSWORD = "select * from users where email = ? and password = ?";
    public static final String SELECT_UID_FROM_USERS_WHERE_EMAIL = "select uid from users where email = ?";
    public static final String SELECT_NAME_FROM_USERS_WHERE_UID = "select name from users where uid = ?";
    public static final String SELECT_SURNAME_FROM_USERS_WHERE_UID = "select surname from users where uid = ?";
    public static final String SELECT_FROM_EXAMRESULTS_WHERE_SUBJECT_AND_UID = "select * from examresults where subject = ? and uid = ?";
    public static final String SELECT_FROM_EXAMRESULTS_WHERE_SUBJECT_LIKE_ADVANCED_AND_UID = "select * from examresults where subject like 'ADVANCED%' and uid = ?";
    public static final String SELECT_SUBJECT_FROM_EXAMRESULTS_WHERE_SUBJECT_ADVANCED = "select subject from examresults where subject like 'ADVANCED%' AND uid = ? LIMIT 1 OFFSET ?";

    public DBHelper(Context context) {
        super(context, "LookForStudies.db",null,1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(CREATE_TABLE_USERS);
        sqLiteDatabase.execSQL(CREATE_TABLE_EXAMRESULTS);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL(DROP_TABLE_IF_EXISTS_USERS);
        sqLiteDatabase.execSQL(DROP_TABLE_IF_EXISTS_EXAMRESULTS);
    }


    public Boolean insertData(User user) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put("name",user.getName());
        contentValues.put("surname",user.getSurname());
        contentValues.put("email",user.getEmail());
        contentValues.put("password",user.getPassword());

        long result = sqLiteDatabase.insert("users",null,contentValues);


        return result != -1;
    }

    public void insertResultsData(String subject, String examResult, String uid) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put("subject", subject);
        contentValues.put("result", examResult);
        contentValues.put("uid", Integer.valueOf(uid));

        @SuppressLint("Recycle") Cursor cursor = sqLiteDatabase.rawQuery(SELECT_RESULT_FROM_EXAMRESULTS_WHERE_SUBJECT_AND_UID, new String[]{subject, uid});
        if (cursor.getCount() > 0) {
            sqLiteDatabase.update("examresults",contentValues,"uid = ? and subject = ?",new String[] {uid, subject});
        } else {
            sqLiteDatabase.insert("examresults",null, contentValues);
        }

    }


    public Boolean checkUser(String email) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        @SuppressLint("Recycle") Cursor cursor = sqLiteDatabase.rawQuery(SELECT_FROM_USERS_WHERE_EMAIL, new String[] {email});

        return cursor.getCount() > 0;

    }

    public Boolean checkEmailPassword (String email, String password) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        @SuppressLint("Recycle") Cursor cursor = sqLiteDatabase.rawQuery(SELECT_FROM_USERS_WHERE_EMAIL_AND_PASSWORD, new String[] {email, password});

        return cursor.getCount() > 0;
    }

    public int getUid (String email) {
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        return (int) DatabaseUtils.longForQuery(sqLiteDatabase, SELECT_UID_FROM_USERS_WHERE_EMAIL, new String[] {email});
    }

    public String getName (String uid) {
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        return DatabaseUtils.stringForQuery(sqLiteDatabase, SELECT_NAME_FROM_USERS_WHERE_UID, new String[] {uid});
    }

    public String getSurname (String uid) {
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        return DatabaseUtils.stringForQuery(sqLiteDatabase, SELECT_SURNAME_FROM_USERS_WHERE_UID, new String[] {uid});
    }

    public Boolean checkResultsInDB (String subject, String uid) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        @SuppressLint("Recycle") Cursor cursor = sqLiteDatabase.rawQuery(SELECT_FROM_EXAMRESULTS_WHERE_SUBJECT_AND_UID, new String[] {subject, uid});
        return cursor.getCount() > 0;
    }

    public Boolean checkIfAdvancedResultsInDB (String uid) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        @SuppressLint("Recycle") Cursor cursor = sqLiteDatabase.rawQuery(SELECT_FROM_EXAMRESULTS_WHERE_SUBJECT_LIKE_ADVANCED_AND_UID, new String[] {uid});
        return cursor.getCount() > 0;
    }

    public int checkCountAdvancedResultsInDB (String uid) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        @SuppressLint("Recycle") Cursor cursor = sqLiteDatabase.rawQuery(SELECT_FROM_EXAMRESULTS_WHERE_SUBJECT_LIKE_ADVANCED_AND_UID, new String[] {uid});
        return cursor.getCount();
    }

    public String getPercentage (String subject, String uid) {
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        return DatabaseUtils.stringForQuery(sqLiteDatabase, SELECT_RESULT_FROM_EXAMRESULTS_WHERE_SUBJECT_AND_UID, new String[] {subject, uid});
    }

    public String getAdvancedSubject (String uid, String offset) {
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        return DatabaseUtils.stringForQuery(sqLiteDatabase, SELECT_SUBJECT_FROM_EXAMRESULTS_WHERE_SUBJECT_ADVANCED, new String[] {uid,offset});
    }

    @SuppressLint("SetTextI18n")
    public static void checkIfDataExistBasicResults(DBHelper resultsDB, String uid, EditText mathPercentage, EditText polishPercentage, EditText englishPercentage) {
        if(!resultsDB.checkResultsInDB("MATH",uid)) {
            mathPercentage.setText("");
        } else {
            mathPercentage.setText(resultsDB.getPercentage("MATH", uid)+"%");
        }

            if(!resultsDB.checkResultsInDB("POLISH",uid)) {
            polishPercentage.setText("");
        } else {
            polishPercentage.setText(resultsDB.getPercentage("POLISH", uid)+"%");
        }

            if(!resultsDB.checkResultsInDB("ENGLISH",uid)) {
            englishPercentage.setText("");
        } else {
            englishPercentage.setText(resultsDB.getPercentage("ENGLISH", uid)+"%");
        }
    }

}
