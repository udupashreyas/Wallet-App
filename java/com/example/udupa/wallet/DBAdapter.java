package com.example.udupa.wallet;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.sql.SQLException;
import java.util.Date;
import java.text.*;

/**
 * Created by udupa on 20-01-2015.
 */
public class DBAdapter {
    public static final String KEY_ROW_ID = "id";
    public static final String KEY_DATE = "d";
    public static final String KEY_AMOUNT = "amount";
    public static final String KEY_REASON = "reason";
    public static final String KEY_NAME = "name";

    private static final String TAG = "DBAdapter";

    private static final String DATABASE_NAME = "WalletDB";
    private static final String DATABASE_TABLE = "Expenses";
    private static final String DATABASE_TABLE2 = "Deals";
    private static final int DATABASE_VERSION = 1;

    private static final String DATABASE_CREATE = "create table Expenses (id integer primary key autoincrement, "
            + "d varchar(30), amount integer not null, reason varchar(30));";

    //here
    private static final String DB_CREATE = "create table Deals (id integer primary key autoincrement, "
            + "name varchar(30), amount integer);";
    //till

    private final Context context;
    private DatabaseHelper DBHelper;
    private SQLiteDatabase db;

    public DBAdapter(Context ctx) {
        this.context = ctx;
        DBHelper = new DatabaseHelper(context);
    }

    private static class DatabaseHelper extends SQLiteOpenHelper {

        DatabaseHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        public void onCreate(SQLiteDatabase db) {
            db.execSQL(DATABASE_CREATE);
            db.execSQL(DB_CREATE);
        }

        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL("drop table if exists Expenses");
            db.execSQL("drop table if exists Deals");
            onCreate(db);
        }
    }

    public DBAdapter open() {
        db = DBHelper.getWritableDatabase();
        return this;
    }

    public void close() {
        DBHelper.close();
    }

    public long insertInfo(int amount, String reason) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String date = sdf.format(new Date());
        ContentValues initialValues = new ContentValues();
        initialValues.put(KEY_DATE, date);
        initialValues.put(KEY_AMOUNT,amount);
        initialValues.put(KEY_REASON,reason);
        return db.insert(DATABASE_TABLE,null,initialValues);
    }

    public Cursor getAllInfo() {
        return db.query(DATABASE_TABLE, new String[] {KEY_ROW_ID, KEY_DATE, KEY_AMOUNT, KEY_REASON},null, null, null, null, null);
    }

    public long lendMoney(int amount, String person) {
        int new_id = getID(person);
        if(new_id==-1) {
            ContentValues values = new ContentValues();
            values.put(KEY_AMOUNT,amount);
            values.put(KEY_NAME,person);
            return db.insert(DATABASE_TABLE2,null,values);
        }
        else {
            ContentValues values = new ContentValues();
            Cursor mCursor = db.rawQuery("SELECT amount FROM " + DATABASE_TABLE2 + " WHERE id=" + new_id, null);
            int existing = 0;
            if(mCursor != null) {
                mCursor.moveToFirst();
                existing = mCursor.getInt(0);
            }
            values.put(KEY_AMOUNT,existing+amount);
            values.put(KEY_NAME,person);
            return db.update(DATABASE_TABLE2,values,"id="+new_id,null);
        }
    }

    public long borrowMoney(int amount, String person) {
        int new_id = getID(person);
        if(new_id==-1) {
            ContentValues values = new ContentValues();
            values.put(KEY_AMOUNT,amount*-1);
            values.put(KEY_NAME,person);
            return db.insert(DATABASE_TABLE2,null,values);
        }
        else {
            ContentValues values = new ContentValues();
            Cursor mCursor = db.rawQuery("SELECT amount FROM " + DATABASE_TABLE2 + " WHERE id=" + new_id, null);
            int existing = 0;
            if(mCursor != null) {
                mCursor.moveToFirst();
                existing = mCursor.getInt(0);
            }
            values.put(KEY_AMOUNT,existing-amount);
            values.put(KEY_NAME,person);
            return db.update(DATABASE_TABLE2,values,"id="+new_id,null);
        }
    }

    public int getID(String person)
    {
        int res;
        Cursor mCursor = db.rawQuery("SELECT id FROM " + DATABASE_TABLE2 + " WHERE name='" + person + "'" , null);
        if (mCursor.moveToFirst())
        {
            mCursor.moveToFirst();
            res=mCursor.getInt(0);
            return res;
        }
        else
            return -1;
    }
}
