package com.example.customermgt;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

public class Register extends Activity {
	
	SQLiteDatabase sqlitedb;
	DBManager dbmanager;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register);
        
        setTitle("색상등록결과");
        
        Intent it = getIntent();
        int str_no   = it.getIntExtra("it_no", 0);
        String str_comp = it.getStringExtra("it_comp");
        int str_used    = it.getIntExtra("it_used", 0);
        int str_want  = it.getIntExtra("it_want", 0);
        
	    try {    
	        dbmanager = new DBManager(this);
	        sqlitedb = dbmanager.getWritableDatabase();
	        ContentValues values = new ContentValues();
	        values.put("no",   str_no);
	        values.put("used",    str_used);
	        values.put("want",  str_want);
	        
	        Cursor cursor = sqlitedb.rawQuery("select count(*) from colors where no="+str_no+str_comp, null);
	        cursor.moveToFirst();
			int cnt = cursor.getInt(0);
			cursor.close();
			
	        if(cnt > 0){
	        
		        long newRowId = sqlitedb.update("colors", values, "no="+str_no+str_comp, null);
		        //long newRowId = sqlitedb.insert("colors", null, values);
		        
		        if (newRowId != -1) {
			        TextView tv_no   = (TextView)findViewById(R.id.no);
			        TextView tv_used = (TextView)findViewById(R.id.used);
			        TextView tv_want = (TextView)findViewById(R.id.want);
			        
			        tv_no.append(": " + str_no);
			        tv_used.append(": " + str_used);
			        tv_want.append(": " + str_want);
		        } else {
		    		Toast.makeText(this, "색상등록에러!", Toast.LENGTH_LONG).show();
		        }
	        }else
	        	Toast.makeText(this, "존재하지 않는 색상입니다.", Toast.LENGTH_LONG).show();
	        
	        sqlitedb.close();
	        dbmanager.close();
	        
	    } catch(SQLiteException e) {
	    	Toast.makeText(this,  e.getMessage(), Toast.LENGTH_LONG).show();
	    }
        
    }
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings1) {
//            Intent it    = new Intent(this, MainActivity.class);
//            startActivity(it);
            finish();
            return true;
        } else if (id == R.id.action_settings2) {
            Intent it    = new Intent(this, RegisterForm.class);
            startActivity(it);
            finish();
            return true; 
        } else if (id == R.id.action_settings3) {
            Intent it    = new Intent(this, CustomerList.class);
            
            it.putExtra("comp_op", 0);
            it.putExtra("c_name", "전체 색상표");
            
            startActivity(it);
            finish();
            return true; 
        } else 
            return super.onOptionsItemSelected(item);
    }    
}