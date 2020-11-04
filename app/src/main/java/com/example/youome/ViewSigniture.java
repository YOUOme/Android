package com.example.youome;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.Nullable;

public class ViewSigniture extends View {
    private int oldX,oldY = -1;
    private Canvas mcanvas;
    private Paint mpaint = new Paint();
    private Bitmap mbitmap;

    public ViewSigniture(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        mpaint.setColor(Color.BLACK);
        mpaint.setStrokeWidth(3);
        this.setLayerType(LAYER_TYPE_SOFTWARE,null);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mbitmap = Bitmap.createBitmap(w,h,Bitmap.Config.ARGB_8888);
        mcanvas = new Canvas();
        mcanvas.setBitmap(mbitmap);
        mcanvas.drawColor(Color.WHITE);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int x = (int)event.getX();
        int y = (int)event.getY();
        if(event.getAction() == MotionEvent.ACTION_DOWN){
            oldX = x; oldY= y;
        }
        else if(event.getAction() == MotionEvent.ACTION_MOVE){
            if(oldX !=-1)
               mcanvas.drawLine(oldX,oldY,x,y,mpaint);
            invalidate();
            oldX = x; oldY = y;
        }
        else if(event.getAction() == MotionEvent.ACTION_UP){
            if(oldX != -1)
                mcanvas.drawLine(oldX,oldY,x,y,mpaint);
            invalidate();
            oldX = -1; oldY = -1;
        }
        return true; // touch 인식
    }
}
