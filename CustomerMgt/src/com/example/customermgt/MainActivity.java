package com.example.customermgt;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.Display;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.WindowManager;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.Toast;

public class MainActivity extends Activity {

	SQLiteDatabase db;
	DBManager mDB;
	Cursor cursor;
	Colors colorF, colorS, colorP;
	int comp_no, opt1;
	String cName;
	LinearLayout Item_Container;
	extern_View test;
	Intent it;
	boolean checkDB = false, checkDB1 = false, checkDB2 = false, checkDB3 = false;
	int cnt1, cnt2, cnt3;
	float x1, x2, y1, y2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        it = new Intent(this, CustomerList.class);
        
        test = new extern_View(this);
    
        setTitle("Coloring");
        
        LinearLayout layout = (LinearLayout)findViewById(R.id.container);
        
        Toast toast = Toast.makeText(this, "데이터 등록 완료.", Toast.LENGTH_SHORT);
        Toast toast2 = Toast.makeText(this, "데이터를 등록합니다.", Toast.LENGTH_SHORT);
//*        
        mDB = new DBManager(MainActivity.this);
        
        db = mDB.getWritableDatabase();
        
        ContentValues values[] = new ContentValues[120];
        for(int k=0;k<120;k++){
        	values[k] = new ContentValues();
        }
        
        ContentValues values2[] = new ContentValues[150];
        for(int k=0;k<150;k++){
        	values2[k] = new ContentValues();
        }
        
        ContentValues values3[] = new ContentValues[60];
        for(int k=0;k<60;k++){
        	values3[k] = new ContentValues();
        }
        
        cursor = mDB.getReadableDatabase().rawQuery("select name from colors", null);
        
        while(cursor.moveToNext()){
        	if((cursor.getString(0)).equals("white")){
        		checkDB = true;
        		break;
        	}
        }
        if(checkDB == false){
        	toast2.show();
        	colorF = new Colors(120);
        	colorS = new Colors(60);
        	colorP = new Colors(150);
        	
        	values = colorF.init_Color_FC();
        	values3 = colorS.init_Color_ST();
        	values2 = colorP.init_Color_PR();
        	
	        for(int k=0;k<120;k++){
	        	db.insert("colors",null,values[k]);
        	}
	           
	        for(int k=0;k<60;k++){
	        	db.insert("colors",null,values3[k]);
        	}
	        
	        for(int k=0;k<150;k++){
	        	db.insert("colors",null,values2[k]);
        	}
	        
	        toast.show();
        }
        cursor.close();
//*/
        int[] cntNum = new int[1];
        cntNum[0] = 0;
        checkDB1=Check_DB("comp=1 and used=1", checkDB1, cntNum);

        test.cnt1 = cntNum[0];
        if(checkDB1 == false)
        	test.Fimg01 = BitmapFactory.decodeResource(getBaseContext().getResources(),R.drawable.f2);
        else
        	test.Fimg01 = BitmapFactory.decodeResource(getBaseContext().getResources(),R.drawable.f1);
        

        checkDB2=Check_DB("comp=2 and used=1", checkDB2, cntNum);
        
        test.cnt2 = cntNum[0];
        if(checkDB2 == false)
	    	test.Fimg02 = BitmapFactory.decodeResource(getBaseContext().getResources(),R.drawable.s2);
	    else 
	    	test.Fimg02 = BitmapFactory.decodeResource(getBaseContext().getResources(),R.drawable.s1);
	    
        checkDB3=Check_DB("comp=3 and used=1", checkDB3, cntNum);
        
        test.cnt3 = cntNum[0];
	    if(checkDB3 == false)
	        test.Fimg03 = BitmapFactory.decodeResource(getBaseContext().getResources(),R.drawable.p2);
	    else
	    	test.Fimg03 = BitmapFactory.decodeResource(getBaseContext().getResources(),R.drawable.p1);
        
        createContainer();
        layout.addView(Item_Container);
        
    }
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
    	getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings2) {
            Intent it    = new Intent(this, RegisterForm.class);
            startActivity(it);
//            finish();
            return true;
        } else if (id == R.id.action_settings3) {
        	comp_no = 0;
            Intent it    = new Intent(this, CustomerList.class);
            it.putExtra("comp_op", comp_no);
            it.putExtra("c_name", "전체 색상표");
            startActivity(it);
//            finish();
            return true;
        } else 
            return super.onOptionsItemSelected(item);
    }   
    
	public boolean Check_DB(String tmp, boolean checkDB, int[] cnt){
		Cursor ch = mDB.getReadableDatabase().rawQuery("select count(name) from colors where "+tmp, null);
		ch.moveToFirst();
		cnt[0] = ch.getInt(0);
		if(cnt[0]>0)
    		checkDB = true;
		ch.close();
		return checkDB;
    }
    
    public void createContainer(){
    	Item_Container = new LinearLayout(this);
    	
    	Item_Container.addView(test);
    	
    	test.setOnTouchListener(new OnTouchListener(){
        	public boolean onTouch(View arg0, MotionEvent event) {               
        		if (event.getAction() == MotionEvent.ACTION_DOWN) {
        			x1=event.getX();
        			y1=event.getY();
                }
                if (event.getAction() == MotionEvent.ACTION_UP) {
        			x2=event.getX();
        			y2=event.getY();
        			
        			if(checkDB1 == true && x2>0 && x2 < 330 && y2>150 && y2 < 450){
        				cName = "Faber Castel";
        				comp_no = 1;
        				opt1 = 1;
        	            it.putExtra("comp_op", comp_no);
        	            it.putExtra("view_opt", opt1);
        	            it.putExtra("c_name", cName);
        	            startActivity(it);
        			}
        			if(checkDB2 == true && x2>335 && x2 < 665 && y2>150 && y2 < 450){
        				cName = "Staedtler";
        				comp_no = 2;
        				opt1 = 1;
        	            it.putExtra("comp_op", comp_no);
        	            it.putExtra("view_opt", opt1);
        	            it.putExtra("c_name", cName);
        	            startActivity(it);
        			}
        			if(checkDB3 == true && x2>670 && x2 < 1000 && y2>150 && y2 < 450){
        				cName = "Prisma";
        				comp_no = 3;
        				opt1 = 1;
        	            it.putExtra("comp_op", comp_no);
        	            it.putExtra("view_opt", opt1);
        	            it.putExtra("c_name", cName);
        	            startActivity(it);
        			}
                }
 				return true;
        }});
    }
}