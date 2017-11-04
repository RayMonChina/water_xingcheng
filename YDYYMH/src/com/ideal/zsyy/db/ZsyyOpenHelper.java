package com.ideal.zsyy.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class ZsyyOpenHelper extends SQLiteOpenHelper {

	private static final int DATABASE_VERSION = 2;
	private static final String DATABASE_NAME = "zsyy_hn";

	public ZsyyOpenHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {		
		
		db.execSQL("CREATE TABLE IF NOT EXISTS it_doctor_info (id varchar(100) NOT NULL, hosp_Id varchar(100), doctor_Name varchar(100), "
				+ " doctor_Id varchar(100), job_title varchar(100), is_Specialist varchar(20), introduce text, expertise varchar(300), "
				+ " photo varchar(100), reg_Cost varchar(100), doctor_Deptid varchar(100), doctor_Office varchar(100), depName varchar(100) )");
		
		
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		
		db.execSQL("DROP TABLE IF EXISTS it_doctor_info");  
        onCreate(db);  

	}
}
