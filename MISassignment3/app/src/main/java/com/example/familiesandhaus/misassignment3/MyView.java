package com.example.familiesandhaus.misassignment3;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.RelativeLayout;

public class MyView extends View {

    private Paint PaintX, PaintY, PaintZ, PaintST;

    private Bitmap canvasBitmap;
    float speedX, speedY, speedZ;

    float radius = 10;
    float oldPosX, oldPosY;
    float posX,posY, posZ = radius;
    float centerX, centerY;
    float right, bottom;
    float left, top = 0;
    float gX, gY, gZ = 0;
    double magnitude, speedT;
    long lastUpdateTime = 0;
    final float METER_TO_PIXEL = 50.0f;
    RelativeLayout rl;


    public MyView(Context context, AttributeSet attrs) {
        super(context, attrs);
        setupDraw();
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        //DisplayMetrics metrics = this.getResources().getDisplayMetrics();

        //float centerX = this.getResources().getDisplayMetrics().widthPixels /2;
       // float centerY = this.getResources().getDisplayMetrics().heightPixels /2;

        if (centerX <200) {
            centerX = getWidth()/2;
         centerY = getHeight()/2;
        right = getWidth();
        bottom = getHeight();
       }

        //x-axis // this uses abstract speed and show to few values
        //canvas.drawLine(centerX, centerY, centerX + posX, centerY, PaintX);
        //y-axis
        //canvas.drawLine(centerX, centerY, centerX, centerY - posY, PaintY);

        //z-axis
       // canvas.drawLine(centerX, centerY, posX, posZ, PaintZ);


        //canvas.drawLine(posX, top, posX, bottom, PaintX); // this uses not realtime data but abstract speed
       // canvas.drawLine(left, posY, right, posY, PaintY);
        canvas.drawLine(centerX+(gX*-1)*20.0f, top, centerX+(gX*-1)*20.0f, bottom, PaintX);
        canvas.drawLine(left,centerY + gY*20.0f , right,centerY + gY*20.0f , PaintY);
        canvas.drawCircle(centerX,centerY,(gZ*-1)*3.0f+26, PaintZ);
        canvas.drawCircle(centerX,centerY, (float) (speedT*10.0f), PaintST);
        //canvas.drawLine(centerX, centerY, posZ, posZ, PaintZ);


    }


    private void setupDraw() {

        PaintX = new Paint();
        PaintX.setColor(Color.RED);
        PaintY = new Paint();
        PaintY.setColor(Color.GREEN);
        PaintZ = new Paint();
        PaintZ.setColor(Color.YELLOW);
        PaintST = new Paint();
        PaintST.setColor(Color.LTGRAY);

        canvasBitmap = Bitmap.createBitmap(640, 1200, Bitmap.Config.ARGB_8888);
        oldPosX = posX;
        oldPosY = posY;

        posX = getWidth() /2;
        posY = getHeight() /2;

    }

    public void update(float gravityX, float gravityY, float gravityZ) {

        if(lastUpdateTime == 0) {
            lastUpdateTime = System.currentTimeMillis();
            return;
        }
        long now = System.currentTimeMillis();
        long ellapse = now - lastUpdateTime;
        lastUpdateTime = now;

        gX = gravityX;
        gY = gravityY;
        gZ = gravityZ;

        magnitude = Math.sqrt(gX*gX+gY*gY+gZ*gZ);
        speedT = magnitude - 9.8f;
        speedX -=((gravityX * ellapse)/1000.0f) * METER_TO_PIXEL;
        speedY +=((gravityY * ellapse)/1000.0f) * METER_TO_PIXEL;
        speedZ +=((gravityZ * ellapse)/1000.0f) * METER_TO_PIXEL;

        posX += ((speedX * ellapse) / 1000.0f);
        posY += ((speedY * ellapse) / 1000.0f);
        posZ += ((speedZ * ellapse) / 1000.0f);

        /*Checks screen limits*/
        if (posX < radius) {
            posX = radius;
            speedX = 0;
        }
        else if (posX > (getWidth() - radius)) {
            posX = getWidth() - radius;
            speedX = 0;
        }
        if (posY < radius) {
            posY = radius;
            speedY = 0;
        }
        else if (posY > (getHeight() - radius)) {
            posY = getHeight() - radius;
            speedY = 0;
        }





        this.invalidate();
    }



}


