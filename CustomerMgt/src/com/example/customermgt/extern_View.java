package com.example.customermgt;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

public class extern_View extends View{

	int width, height;
	Bitmap Fimg01, Fimg02, Fimg03;
	Paint pnt = new Paint();
	
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
		canvas.drawBitmap(Fimg01, 0, 150, pnt);
		canvas.drawBitmap(Fimg02, 335, 150, pnt);
		canvas.drawBitmap(Fimg03, 670, 150, pnt);
	}
}
