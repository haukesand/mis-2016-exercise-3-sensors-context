package com.example.familiesandhaus.misassignment3;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.os.Handler;
import android.util.AttributeSet;
import android.util.Log;
import android.view.SurfaceView;
import android.view.View;

import java.util.Arrays;


/**
 * Created by familiesandhaus on 19.05.16.
 */
public class FFTView extends View {


    public FFTView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }
    Bitmap b = Bitmap.createBitmap(100, 100, Bitmap.Config.ARGB_8888);
    Canvas canvas = new Canvas(b);
    private double xBuffer[] = new double[1024];
    private double yBuffer[] = new double[1024];
    private double fftArray[] = new double[1024];
    private int windowWidth = 1024;
    public int arrayLCounter = 0;
    private int xTextPos, yTextPos;
    double sum = 0;
    Paint paint;
    FFT fftObject ;


    public void fill(double x) {
        fftObject = new FFT(windowWidth);

        if (arrayLCounter < windowWidth) {
            xBuffer[arrayLCounter] = x;
            arrayLCounter += 1;
        }else{
            Arrays.fill(yBuffer,0);
            fftObject.fft(xBuffer,yBuffer);
            arrayLCounter = 0;
            for (int i = 0; i < windowWidth; i++){
            fftArray[i] = Math.sqrt(xBuffer[i]*xBuffer[i]+yBuffer[i]*yBuffer[i]);
            sum += fftArray[i];
            }
            setupDraw();
            canvas.drawText(String.valueOf(sum), xTextPos, yTextPos, paint);
        }

      }
public void changeWindow (int wWidth){

    windowWidth = (int)Math.pow(2,wWidth);

}

  //  @Override
   // protected void onFinishInflate(){
   // super.onFinishInflate();}

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
    }
    private void setupDraw() {
        paint = new Paint();
        paint.setColor(Color.WHITE);
        paint.setStyle(Paint.Style.FILL);
        canvas.drawPaint(paint);

        paint.setColor(Color.BLACK);
        paint.setTextSize(20);
        xTextPos = (canvas.getWidth() / 2);
        yTextPos = (int) ((canvas.getHeight() / 2) - ((paint.descent() + paint.ascent()) / 2)) ;

    }}