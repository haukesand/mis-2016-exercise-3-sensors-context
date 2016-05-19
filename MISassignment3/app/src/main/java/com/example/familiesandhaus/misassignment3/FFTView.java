package com.example.familiesandhaus.misassignment3;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.NotificationCompat;
import android.util.AttributeSet;
import android.util.Log;
import android.view.SurfaceView;
import android.view.View;
import java.util.Arrays;

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
    public static String Context = "resting";
    public static boolean contextUpdate = false;
    double sum, lastSum = 0;
    Paint paint;
    FFT fftObject ;
    MainActivity mA;
    //NotificationCompat.Builder mBuilder;




    public void fill(double x) {
        fftObject = new FFT(windowWidth);

        if (arrayLCounter < windowWidth) {
            xBuffer[arrayLCounter] = x;
            arrayLCounter += 1;
        }else if(arrayLCounter == windowWidth){
                double newxBuffer[] = new double[windowWidth];
                double newyBuffer[] = new double[windowWidth];
                System.arraycopy(xBuffer,0,newxBuffer,0,windowWidth);
                Arrays.fill(yBuffer,0);
            //calculate FFT and sum up
            fftObject.fft(newxBuffer,newyBuffer);
            for (int i = 0; i < windowWidth; i++){
            fftArray[i] = Math.sqrt(newxBuffer[i]*newxBuffer[i]+newyBuffer[i]*newyBuffer[i]);
            sum += fftArray[i];

            }
            lastSum = sum/windowWidth;
           //resetting working variables
            Arrays.fill(xBuffer,0);
            Arrays.fill(yBuffer,0);
            arrayLCounter = 0;
            sum = 0;
            //refreshing view
            invalidate();

            Context = checkContext(lastSum);
            contextUpdate = true;
        }

      }


public String checkContext(double FFTsum){
    String currContext = null;
    //From the FFT Accelerometer data only it is already possible to distinguish different activity context
    //we chose to use the whole sum of of all axes as this eliminates the influence of the devices orientation
    //Further the FFTsum for walking with a combination of fast position change (GPS) is driving in car/train on
    //non bumpy roads. a distinction between driving the bus and running can only be made with clever use of accelerometer data
    //that checks for regular sinusoidal leg movements. These context are for resting devices without hand interaction
    //it could be additionally checked if no touch events are happening and whether the screen is turned off

    if (FFTsum < 4) currContext = "resting";
    else if (FFTsum < 45) currContext = "walking";
    else if (FFTsum < 130) currContext = "running";
    else if (FFTsum > 131)currContext = "shaking";
    return currContext;
}
public String returnContext(){
    return Context;
}

public void changeWindow (int wWidth){

    windowWidth = (int)Math.pow(2,wWidth);
    Arrays.fill(xBuffer,0);
    Arrays.fill(yBuffer,0);
    arrayLCounter = 0;
    sum = 0;
}

   @Override
    protected void onFinishInflate(){
    super.onFinishInflate();
       setupDraw();
   }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (lastSum == 0)canvas.drawText("Waiting for FFT fill", xTextPos, yTextPos, paint);
         else
            canvas.drawText(fmt(lastSum), xTextPos, yTextPos, paint);

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
    }

    public static String fmt(double d)
    {
        if(d == (long) d)
            return String.format("%d",(long)d);
        else
            return String.format("%s",d);
    }}