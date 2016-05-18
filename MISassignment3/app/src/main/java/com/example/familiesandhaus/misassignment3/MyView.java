package com.example.familiesandhaus.misassignment3;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.View;

public class MyView extends View {

    private Path drawPath;
    private Paint drawPaint;
    private Bitmap canvasBitmap;
    float speedX;
    float speedY;
    float radius = 10;
    float posX = radius;
    float posY = radius;
    long lastUpdateTime = 0;
    final float METER_TO_PIXEL = 50.0f;


    public MyView(Context context, AttributeSet attrs) {
        super(context, attrs);
        setupDraw();
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawPath(drawPath, drawPaint);
        canvas.drawCircle(posX, posY, radius, drawPaint);
    }


    private void setupDraw() {

        drawPaint = new Paint();
        drawPaint.setColor(Color.BLACK);
        canvasBitmap = Bitmap.createBitmap(640, 1200, Bitmap.Config.ARGB_8888);
        posX = getWidth() /2;
        posY = getHeight() /2;
    }

    public void update(float gravityX, float gravityY) {

        if(lastUpdateTime == 0) {
            lastUpdateTime = System.currentTimeMillis();
            return;
        }
        long now = System.currentTimeMillis();
        long ellapse = now - lastUpdateTime;
        lastUpdateTime = now;

        speedX -=((gravityX * ellapse)/1000.0f) * METER_TO_PIXEL;
        speedY +=((gravityY * ellapse)/1000.0f) * METER_TO_PIXEL;

        posX += ((speedX * ellapse) / 1000.0f);
        posY += ((speedY * ellapse) / 1000.0f);

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


