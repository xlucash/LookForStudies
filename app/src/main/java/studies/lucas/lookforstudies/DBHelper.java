package studies.lucas.lookforstudies;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {
    public DBHelper(Context context) {
        super(context, "LookForStudies.db",null,1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("create Table users(uid INTEGER PRIMARY KEY AUTOINCREMENT,name TEXT, surname TEXT, email TEXT, password TEXT)");
        sqLiteDatabase.execSQL("create Table examresults(id INTEGER PRIMARY KEY AUTOINCREMENT, subject TEXT,result TEXT,uid INTEGER)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("drop Table if exists users");
        sqLiteDatabase.execSQL("drop Table if exists examresults");
    }


    public Boolean insertData(User user) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put("name",user.getName());
        contentValues.put("surname",user.getSurname());
        contentValues.put("email",user.getEmail());
        contentValues.put("password",user.getPassword());

        long result = sqLiteDatabase.insert("users",null,contentValues);


        if(result == -1) {
            return false;
        } else {
            return true;
        }
    }

    public void insertResultsData(String subject, String examResult, String uid) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put("subject", subject);
        contentValues.put("result", examResult);
        contentValues.put("uid", Integer.valueOf(uid));

        @SuppressLint("Recycle") Cursor cursor = sqLiteDatabase.rawQuery("select result from examresults where subject = ? AND uid = ?", new String[]{subject, uid});
        if (cursor.getCount() > 0) {
            sqLiteDatabase.update("examresults",contentValues,"uid = ? and subject = ?",new String[] {uid, subject});
        } else {
            sqLiteDatabase.insert("examresults",null, contentValues);
        }

    }


    public Boolean checkuser (String email) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        @SuppressLint("Recycle") Cursor cursor = sqLiteDatabase.rawQuery("select * from users where email = ?", new String[] {email});

        if(cursor.getCount()>0)
        {
            return true;
        } else{
            return false;
        }

    }

    public Boolean checkEmailPassword (String email, String password) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        @SuppressLint("Recycle") Cursor cursor = sqLiteDatabase.rawQuery("select * from users where email = ? and password = ?", new String[] {email, password});

        if(cursor.getCount()>0){
            return true;
        } else {
            return false;
        }
    }

    public int getUid (String email) {
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        return (int) DatabaseUtils.longForQuery(sqLiteDatabase, "select uid from users where email = ?", new String[] {email});
    }

    public String getName (String uid) {
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        return DatabaseUtils.stringForQuery(sqLiteDatabase, "select name from users where uid = ?", new String[] {uid});
    }

    public String getSurname (String uid) {
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        return DatabaseUtils.stringForQuery(sqLiteDatabase, "select surname from users where uid = ?", new String[] {uid});
    }

    public Boolean checkResultsInDB (String subject, String uid) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        @SuppressLint("Recycle") Cursor cursor = sqLiteDatabase.rawQuery("select * from examresults where subject = ? and uid = ?", new String[] {subject, uid});
        if(cursor.getCount()>0){
            return true;
        } else {
            return false;
        }
    }

    public String getPercentage (String subject, String uid) {
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        return DatabaseUtils.stringForQuery(sqLiteDatabase, "select result from examresults where subject = ? AND uid = ?", new String[] {subject, uid});
    }

}
