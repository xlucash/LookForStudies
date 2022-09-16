package studies.lucas.lookforstudies;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {
    public DBHelper(Context context) {
        super(context, "Login.db",null,1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("create Table users(uid INTEGER PRIMARY KEY AUTOINCREMENT, name TEXT, surname TEXT, email TEXT, password TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("drop Table if exists user");
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


    public Boolean checkuser (String email) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery("select * from users where email = ?", new String[] {email});

        if(cursor.getCount()>0)
        {
            return true;
        } else{
            return false;
        }

    }

    public Boolean checkEmailPassword (String email, String password) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery("select * from users where email = ? and password = ?", new String[] {email, password});

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

}
