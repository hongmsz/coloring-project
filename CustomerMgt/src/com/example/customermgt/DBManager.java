package com.example.customermgt;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBManager extends SQLiteOpenHelper {

	public DBManager(Context context) {
		super(context, "myDB", null, 2);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL("create table colors (_id integer primary key autoincrement, comp INTEGER, R INTEGER, G INTEGER, B INTEGER, no INTEGER, name TEXT, used INTEGER, want INTEGER, sC1 INTEGER, sC2 INTEGER, sC3 INTEGER);");
	}
		
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		db.execSQL("drop table if exists colors");
        onCreate(db);
	}
}
