package com.example.customermgt;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class CustomerList extends Activity {
	
	SQLiteDatabase sqlitedb;
	DBManager dbmanager;
	AlertDialog.Builder alertDialog;
	String vOpt;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.customer_list);
        
        Cursor cursor = null;
        Intent it = getIntent();
        int comp_no   = it.getIntExtra("comp_op", 0);
        int view_opt = it.getIntExtra("view_opt", 0);
        String cName = it.getStringExtra("c_name");
        setTitle(cName);
        if(view_opt == 1)
        	vOpt = " and used=1";
        else
        	vOpt = "";
        
        LinearLayout layout = (LinearLayout)findViewById(R.id.customer);
        
	    try {    
	        dbmanager = new DBManager(this);
	        sqlitedb = dbmanager.getReadableDatabase();
	        switch(comp_no){
	        case 1:
	        	cursor = sqlitedb.rawQuery("select * from colors where comp=1"+vOpt, null);
	        	break;
	        case 2:
	        	cursor = sqlitedb.rawQuery("select * from colors where comp=2"+vOpt, null);
	        	break;
	        case 3:
	        	cursor = sqlitedb.rawQuery("select * from colors where comp=3"+vOpt, null);
	        	break;
	        case 0:
	        	cursor = sqlitedb.query("colors", null, "name is not null", null, null, null, null);
	        	break;
	        }
	        
	        int i = 0;
            while(cursor.moveToNext()) {
/*
	            String name   = cursor.getString(cursor.getColumnIndex("name"));
	        	String gender = cursor.getString(cursor.getColumnIndex("gender"));
	        	String sms    = cursor.getString(cursor.getColumnIndex("sms"));
	        	String email  = cursor.getString(cursor.getColumnIndex("email"));
//*/
            	int cnt = cursor.getInt(cursor.getColumnIndex("_id"));
            	int comp = cursor.getInt(cursor.getColumnIndex("comp"));
            	int R = cursor.getInt(cursor.getColumnIndex("R"));
            	int G = cursor.getInt(cursor.getColumnIndex("G"));
            	int B = cursor.getInt(cursor.getColumnIndex("B"));
            	int no = cursor.getInt(cursor.getColumnIndex("no"));
            	String name = cursor.getString(cursor.getColumnIndex("name"));
            	int used = cursor.getInt(cursor.getColumnIndex("used"));
            	int want = cursor.getInt(cursor.getColumnIndex("want"));

            	LinearLayout layout_list = new LinearLayout(this);
	        	layout_list.setOrientation(LinearLayout.VERTICAL);

	        	LinearLayout layout_list2 = new LinearLayout(this);
	        	layout_list2.setOrientation(LinearLayout.HORIZONTAL);
	 	        
	        	TextView tv_list3 = new TextView(this);
	        	TextView tv_list = new TextView(this);
	        	if(comp_no==0){
	        		switch(comp){
	        		case 1:
		        		tv_list3.setText("ⓕ ");
		        		break;
	        		case 2:
		        		tv_list3.setText("ⓢ ");
		        		break;
	        		case 3:
		        		tv_list3.setText("ⓟ ");
		        		break;
	        		}
	        		tv_list3.setTextSize(10);
	        		layout_list2.addView(tv_list3);
	        	}
	        	if(used==1 && want==0)
	        		tv_list.setText("■ ");
	        	else if(used==1 && want==1)
	        		tv_list.setText("▦ ");
	        	else if(used==0 && want==1)
	        		tv_list.setText("▥ ");
	        	else
	        		tv_list.setText("□ ");
	        	
	 	        tv_list.setTextSize(15);
	 	        tv_list.setTextColor(Color.rgb(R, G, B));
	 	        layout_list2.addView(tv_list);
	 	        
	        	TextView tv_list2 = new TextView(this);
	 	        tv_list2.setText(no + ": "+ name);
	 	        tv_list2.setTextColor(Color.BLACK);
//	 	        tv_list.setTextSize(10);
	 	        layout_list2.addView(tv_list2);

	 	        layout_list.addView(layout_list2);
/*	 	        
				alertDialog = new AlertDialog.Builder(CustomerList.this); 
				
				// Setting Dialog Title 
				alertDialog.setTitle("Save File...");  
				
				// Setting Dialog Message 
				alertDialog.setMessage("Type Messages"); 
				
				// Setting Icon to Dialog
//				alertDialog.setIcon(R.drawable.save);
				
				// Setting Positive "Yes" Button
				alertDialog.setPositiveButton("YES", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
						// User pressed YES button. Write Logic Here
						Toast.makeText(getApplicationContext(), "You clicked on YES", Toast.LENGTH_SHORT).show();
					}
				});
				// Setting Negative "NO" Button
				alertDialog.setNegativeButton("NO", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) { 
						// User pressed No button. Write Logic Here 
						Toast.makeText(getApplicationContext(), "You clicked on NO", Toast.LENGTH_SHORT).show();
					} 
				}); 
				// Setting Netural "Cancel" Button
				alertDialog.setNeutralButton("Cancel", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) { 
						// User pressed Cancel button. Write Logic Here 
						Toast.makeText(getApplicationContext(), "You clicked on Cancel", Toast.LENGTH_SHORT).show();
					} 
				}); 
    
	 	        layout_list2.setOnClickListener(new OnClickListener(){
	 	        	@Override
					public void onClick(View v) {
	 	        		alertDialog.show();
					}
	 	        });
//*/	 	        
	 	        
	 	        layout.addView(layout_list);
	        }

	        cursor.close();
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
            Intent it    = new Intent(this, MainActivity.class);
            startActivity(it);
            finish();
            return true;
        } else if (id == R.id.action_settings2) {
            Intent it    = new Intent(this, RegisterForm.class);
            startActivity(it);
            finish();
            return true;
        } 
        
        return super.onOptionsItemSelected(item);
    }        
}