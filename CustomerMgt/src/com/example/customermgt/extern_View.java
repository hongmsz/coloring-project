package com.example.customermgt;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

public class extern_View extends View{

	int width, height;
	Bitmap Fimg01, Fimg02, Fimg03;
	int cnt1, cnt2, cnt3;
	Paint pnt = new Paint();
	
	int x_start=0, x_interval = 335, y_start=150, y_interval = 335;
	
	int Tx_start=x_start + 190, Ty_start=y_start + 195;
	
	public extern_View(Context context)
	{
		super(context);
	}
	
	public extern_View(Context context, AttributeSet attrs) {
		super(context, attrs);
	}
	
	public void onDraw(Canvas canvas){
		drawvalue_1(canvas);
	}

	public void drawvalue_1(Canvas canvas){
		pnt.setColor(Color.WHITE);
		pnt.setAntiAlias(true);
		pnt.setTextSize(30);
		
		canvas.drawBitmap(Fimg01, x_start, y_start, pnt);
		canvas.drawText(String.valueOf(cnt1), Tx_start, Ty_start, pnt);
		canvas.drawBitmap(Fimg02, x_start + x_interval, y_start, pnt);
		canvas.drawText(String.valueOf(cnt2), Tx_start + x_interval, Ty_start, pnt);
		canvas.drawBitmap(Fimg03, x_start + (x_interval*2), y_start, pnt);
		canvas.drawText(String.valueOf(cnt3), Tx_start + (x_interval*2), Ty_start, pnt);
	}
}
