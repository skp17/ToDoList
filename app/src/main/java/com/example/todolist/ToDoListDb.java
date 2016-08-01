package com.example.todolist;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Peters on 2016-07-31.
 */
public class ToDoListDb extends SQLiteOpenHelper {

    // If you change the database schema, you must increment the database version.
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "ToDoList.db";

    private static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + ToDoListContract.FeedEntry.TABLE_NAME + " (" +
                    ToDoListContract.FeedEntry._ID + "INTEGER PRIMARY KEY," +
                    ToDoListContract.FeedEntry.ITEM + " TEXT," +
                    ToDoListContract.FeedEntry.IS_CHECKED + " INTEGER" + " NOT NULL CHECK" +
                    " (" + ToDoListContract.FeedEntry.IS_CHECKED + "INTEGER (0,1)" + " ))"; // TODO

    private static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + ToDoListContract.FeedEntry.TABLE_NAME;

    public ToDoListDb(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(SQL_CREATE_ENTRIES);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        // This database is only a cache for online data, so its upgrade policy is
        // to simply to discard the data and start over
        sqLiteDatabase.execSQL(SQL_DELETE_ENTRIES);
        onCreate(sqLiteDatabase);
    }

    @Override
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }

    public boolean insertData(String item, boolean isChecked) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(ToDoListContract.FeedEntry.ITEM, item);
        values.put(ToDoListContract.FeedEntry.IS_CHECKED, (isChecked? 1 : 0));

        long result = db.insert(ToDoListContract.FeedEntry.TABLE_NAME, null, values);

        return (result != -1);
    }

    public Cursor getAllData() {
        SQLiteDatabase db = getReadableDatabase();
        Cursor res = db.rawQuery("select * from" + ToDoListContract.FeedEntry.TABLE_NAME, null);
        return res;
    }

    public boolean updateData(String item, boolean isChecked) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(ToDoListContract.FeedEntry.ITEM, item);
        values.put(ToDoListContract.FeedEntry.IS_CHECKED, (isChecked? 1 : 0));

        db.update(ToDoListContract.FeedEntry.TABLE_NAME, values, "ITEM = ?",
                new String[] {item});
        return true;
    }

    public Integer deleteData(String item) {
        SQLiteDatabase db = getWritableDatabase();
        return db.delete(ToDoListContract.FeedEntry.TABLE_NAME, "ITEM = ?",
                new String[] {item});
    }

    public boolean deleteAllData() {
        SQLiteDatabase db = getReadableDatabase();
        db.execSQL("delete from " + ToDoListContract.FeedEntry.TABLE_NAME);
        return true;
    }
}
