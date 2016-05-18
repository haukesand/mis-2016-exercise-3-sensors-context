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

    private Paint PaintX;
    private Paint PaintY;
<<<<<<< HEAD
    private Paint PaintZ;
=======
>>>>>>> a7f88fd5aece1ddf1cf2c577707cf4784e60db62
    private Bitmap canvasBitmap;
    float speedX;
    float speedY;
    float speedZ;
    float radius = 10;
    float oldPosX, oldPosY;
    float posX = radius;
    float posY = radius;
    float posZ = radius;
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

        float centerX = this.getResources().getDisplayMetrics().widthPixels /2;
        float centerY = this.getResources().getDisplayMetrics().heightPixels /2;

        //x-axis
        canvas.drawLine(centerX, centerY, posX, centerY, PaintX);
        //y-axis
        canvas.drawLine(centerX, centerY, centerX, posY, PaintY);
<<<<<<< HEAD
        //z-axis
        canvas.drawLine(centerX, centerY, posX, posZ, PaintZ);
=======
        
>>>>>>> a7f88fd5aece1ddf1cf2c577707cf4784e60db62
    }


    private void setupDraw() {

        PaintX = new Paint();
        PaintX.setColor(Color.RED);
        PaintY = new Paint();
        PaintY.setColor(Color.GREEN);
<<<<<<< HEAD
        PaintZ = new Paint();
        PaintZ.setColor(Color.YELLOW);
=======
>>>>>>> a7f88fd5aece1ddf1cf2c577707cf4784e60db62
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

        speedX -=((gravityX * ellapse)/1000.0f) * METER_TO_PIXEL;
        speedY +=((gravityY * ellapse)/1000.0f) * METER_TO_PIXEL;
        speedZ +=((gravityY * ellapse)/1000.0f) * METER_TO_PIXEL;

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


