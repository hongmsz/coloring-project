package com.example.customermgt;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

public class RegisterForm extends Activity {

	LinearLayout layout;
	EditText et_no;
	TextView tv_color;
	CheckBox chk_used;
	CheckBox chk_want;
	int str_no;
	int str_used;
	int str_want;
	AlertDialog CompDialog;
	AlertDialog.Builder alertDialog;
	final CharSequence[] items = {"F*ber Castel", "Staedtler", "Prisma"};
	String tmpS = "";
	
	
	SQLiteDatabase db;
	DBManager mDB;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_form);
        
        setTitle("색상 등록");
        layout = (LinearLayout)findViewById(R.id.LY2);
        et_no = (EditText)findViewById(R.id.no);
    	chk_used = (CheckBox)findViewById(R.id.used);
    	chk_want = (CheckBox)findViewById(R.id.want);
    	tv_color = (TextView)findViewById(R.id.TV2);    	
    	
    	et_no.setTextColor(Color.rgb(190, 190, 190));
    	et_no.setText("0");
    	et_no.setOnClickListener(on_ETinit);
    }
    
    private View.OnClickListener on_ETinit = new View.OnClickListener() {
		
		public void onClick(View v) {
			tmpS ="";
	        alertDialog = new AlertDialog.Builder(RegisterForm.this); 
			alertDialog.setTitle("제조사를 선택하세요.");  
			alertDialog.setSingleChoiceItems(items, -1, new DialogInterface.OnClickListener() {
				
				@Override
				public void onClick(DialogInterface dialog, int item) {
					
					switch(item){
					case 0:
						tmpS = " and comp=1";
						break;
					case 1:
						tmpS = " and comp=2";
						break;
					case 2:
						tmpS = " and comp=3";
						break;
					}
					CompDialog.dismiss();
				}
			}); 
			
			CompDialog = alertDialog.create();
			CompDialog.show();
			
			layout.setBackgroundColor(Color.TRANSPARENT);
	    	tv_color.setText("");
			et_no.setTextColor(Color.BLACK);
			et_no.setText("");
		}
	};
	
    public void register(View v) {
    	
        str_no = Integer.parseInt(et_no.getText().toString());
        
        str_used = 0;
        if (chk_used.isChecked()) {
        	str_used = 1;	
        }
        
        str_want = 0;
        if (chk_want.isChecked()) {
        	str_want = 1;	
        }

        Intent it = new Intent(this, Register.class);
    
        it.putExtra("it_no", str_no);
        it.putExtra("it_used", str_used);
        it.putExtra("it_want", str_want);
  
        startActivity(it);

        finish();
    }
 //*   
    public void search(View v) {
    	mDB = new DBManager(RegisterForm.this);        
        db = mDB.getWritableDatabase();
        Toast toast = Toast.makeText(this, "색상 번호를 입력하세요.", Toast.LENGTH_SHORT);        
        
        str_no = Integer.parseInt(et_no.getText().toString());
	
	    Cursor cursor = mDB.getReadableDatabase().rawQuery("select R, G, B, used, want, name from colors where no="+str_no+tmpS, null);
	    int tmp = cursor.getCount();
	    
	    if(tmp != 0){
	    	cursor.moveToFirst();
	        int fR = Integer.parseInt(cursor.getString(0));
	        int fG = Integer.parseInt(cursor.getString(1));
	        int fB = Integer.parseInt(cursor.getString(2));
	        int uS = Integer.parseInt(cursor.getString(3));
	        int wA = Integer.parseInt(cursor.getString(4));
	        String name = cursor.getString(5);
	        
	        if(uS == 1){
	        	chk_used.setChecked(true);
	        }else
	        	chk_used.setChecked(false);
	        
	        if(wA == 1){
	        	chk_want.setChecked(true);
	        }else
	        	chk_want.setChecked(false);
	        
	        layout.setBackgroundColor(Color.rgb(fR, fG, fB));
//	        tv_color.setTextColor(Color.rgb(fR, fG, fB));
	        tv_color.setTextSize(15);
	        tv_color.setText("  "+name);
        }else{
        	layout.setBackgroundColor(Color.TRANSPARENT);
        	tv_color.setText("");
        	et_no.setTextColor(Color.rgb(190, 190, 190));
        	et_no.setText("0");
        	toast.show();
        }

        
    }
  //*/  
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    /*
    // 15.3��
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        
        if (id == R.id.action_settings1) {
            Intent it    = new Intent(this, MainActivity.class);
            startActivity(it);
            finish();
            return true;
        } else 
            return super.onOptionsItemSelected(item);
    }    
    */
    
    // 15.4��
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        
        if (id == R.id.action_settings1) {
            Intent it    = new Intent(this, MainActivity.class);
            startActivity(it);
            finish();
            return true;
        } else if (id == R.id.action_settings3) {
            Intent it    = new Intent(this, CustomerList.class);
            startActivity(it);
            finish();
            return true;
        } else 
            return super.onOptionsItemSelected(item);
    }    
    
}
