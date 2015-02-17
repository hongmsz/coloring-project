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
//	Cursor ch;
	Colors colorF, colorS, colorP;
	int comp_no, opt1;
	String cName;
	LinearLayout Item_Container;
	LinearLayout Opt_Container;
	extern_View test;
	Intent it;
	boolean checkDB = false, checkDB1 = false, checkDB2 = false, checkDB3 = false;
	float x1, x2, y1, y2;
	
//	Display dp = ((WindowManager)this.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();
	
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
        
        checkDB1=Check_DB("comp=1 and used=1", checkDB1);

        if(checkDB1 == false)
        	test.Fimg01 = BitmapFactory.decodeResource(getBaseContext().getResources(),R.drawable.f2);
        else
        	test.Fimg01 = BitmapFactory.decodeResource(getBaseContext().getResources(),R.drawable.f1);
        

        checkDB2=Check_DB("comp=2 and used=1", checkDB2);
        
        if(checkDB2 == false)
	    	test.Fimg02 = BitmapFactory.decodeResource(getBaseContext().getResources(),R.drawable.s2);
	    else 
	    	test.Fimg02 = BitmapFactory.decodeResource(getBaseContext().getResources(),R.drawable.s1);
	    
        checkDB3=Check_DB("comp=3 and used=1", checkDB3);
        
	    if(checkDB3 == false)
	        test.Fimg03 = BitmapFactory.decodeResource(getBaseContext().getResources(),R.drawable.p2);
	    else
	    	test.Fimg03 = BitmapFactory.decodeResource(getBaseContext().getResources(),R.drawable.p1);
        
        ContentValues values[] = new ContentValues[120];
        for(int k=0;k<120;k++){
        	values[k] = new ContentValues();
        }
        
        ContentValues values2[] = new ContentValues[150];
        for(int k=0;k<150;k++){
        	values2[k] = new ContentValues();
        }
        
        createContainer();
        layout.addView(Item_Container);
        
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
        	colorP = new Colors(150);
        	
        	values = colorF.init_Color_FC();
        	values2 = colorP.init_Color_PR();
        	
	        for(int k=0;k<120;k++){
	        	db.insert("colors",null,values[k]);
        	}
	                	
	        for(int k=0;k<150;k++){
	        	db.insert("colors",null,values2[k]);
        	}
	        
	        toast.show();
        }
//*/     
        
        createOptView();
        layout.addView(Opt_Container);
    }
    /*
    public void Color_F(View v) {
    	comp_no = 1;
    	
    	Intent it = new Intent(this, CustomerList.class);
    
        it.putExtra("comp_op", comp_no);
  
        startActivity(it);

        finish();
    }
	
	public void Color_S(View v) {
		comp_no = 2;
    	Intent it = new Intent(this, CustomerList.class);
    
    	it.putExtra("comp_op", comp_no);
  
        startActivity(it);

        finish();
    }
	
	public void Color_P(View v) {
		comp_no = 3;
    	Intent it = new Intent(this, CustomerList.class);
    
    	it.putExtra("comp_op", comp_no);
  
        startActivity(it);

        finish();
    }
//*/
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
            finish();
            return true;
        } else if (id == R.id.action_settings3) {
        	comp_no = 0;
            Intent it    = new Intent(this, CustomerList.class);
            it.putExtra("comp_op", comp_no);
            it.putExtra("c_name", "전체 색상표");
            startActivity(it);
            finish();
            return true;
        } else 
            return super.onOptionsItemSelected(item);
    }   
    
	public boolean Check_DB(String tmp, boolean checkDB){
		Cursor ch = mDB.getReadableDatabase().rawQuery("select count(name) from colors where "+tmp, null);
		while(ch.moveToNext()){
	    	if(Integer.parseInt(ch.getString(0))>0){
	    		checkDB = true;
	    	}
	    }
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
        			
//					test.invalidate();
        			if(checkDB1 == true && x2>0 && x2 < 330 && y2>150 && y2 < 450){
        				cName = "Faber Castel";
        				comp_no = 1;
        				opt1 = 1;
        	            it.putExtra("comp_op", comp_no);
        	            it.putExtra("view_opt", opt1);
        	            it.putExtra("c_name", cName);
        	            startActivity(it);

//        	            finish();
        			}
        			if(checkDB2 == true && x2>335 && x2 < 665 && y2>150 && y2 < 450){
        				cName = "Staedtler";
        				comp_no = 2;
        				opt1 = 1;
        	            it.putExtra("comp_op", comp_no);
        	            it.putExtra("view_opt", opt1);
        	            it.putExtra("c_name", cName);
        	            startActivity(it);

//        	            finish();
        			}
        			if(checkDB3 == true && x2>670 && x2 < 1000 && y2>150 && y2 < 450){
        				cName = "Prisma";
        				comp_no = 3;
        				opt1 = 1;
        	            it.putExtra("comp_op", comp_no);
        	            it.putExtra("view_opt", opt1);
        	            it.putExtra("c_name", cName);
        	            startActivity(it);

//        	            finish();
        			}
                }
 				return true;
        }});
    }
    
    public void createOptView(){
    	Opt_Container = new LinearLayout(this);    	
    	Opt_Container.setOrientation(LinearLayout.HORIZONTAL);
    	    	  
        CheckBox vOptBox1 = new CheckBox(this);
        vOptBox1.setText("보유");
        CheckBox vOptBox2 = new CheckBox(this);
        vOptBox2.setText("필요");
        
        Opt_Container.addView(vOptBox1);
        Opt_Container.addView(vOptBox2);
    }
}