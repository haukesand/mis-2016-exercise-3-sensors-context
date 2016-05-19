package com.example.familiesandhaus.misassignment3;

import android.content.Context;

import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.SeekBar;
import android.widget.TextView;

import java.util.List;
import com.example.familiesandhaus.misassignment3.MyView;



public class MainActivity extends AppCompatActivity implements SensorEventListener {

    Context cv = this;
    float ax, ay, az, mx, my, mz;
    TextView myTextView;
    private SensorManager mSensorManager;
    private Sensor mSensor;
    private Sensor aSensor;
    private AttributeSet aSet;
    private MyView mView;
    private int progressB;

    private SeekBar yourSeekBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // Intent intent= new Intent(MainActivity.this,MyView.class);
        // startActivity(Intent);
        mView = (MyView) findViewById(R.id.view);
        mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        mSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);
        aSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);


        myTextView = (TextView) findViewById(R.id.sensortext);

        yourSeekBar = (SeekBar) findViewById(R.id.seekBar);

        yourSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                                                   @Override
                                                   public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                                                       progressB = i;
                                                       //set textView's text
                                                   }

                                                   @Override
                                                   public void onStartTrackingTouch(SeekBar seekBar) {

                                                   }

                                                   @Override
                                                   public void onStopTrackingTouch(SeekBar seekBar) {
                                                       updateSR(progressB);
                                                       Log.d(MainActivity.class.getSimpleName(), "changed Sampling Rate");
                                                   }
                                               }
        );
    }

    protected void onResume() {
        super.onResume();
        mSensorManager.registerListener((SensorEventListener) this, aSensor, SensorManager.SENSOR_DELAY_GAME);
        mSensorManager.registerListener((SensorEventListener) this, mSensor, SensorManager.SENSOR_DELAY_GAME);

    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
            ax = event.values[0];
            ay = event.values[1];
            az = event.values[2];
        }

        if (event.sensor.getType() == Sensor.TYPE_MAGNETIC_FIELD) {
            mx = event.values[0];
            my = event.values[1];
            mz = event.values[2];
        }

        myTextView.setText("Accelerometer x: " + (short) ax + " y: " + (short) ay + " z: " + (short) az + " Magnetometer x: " + (short) mx + " y: " + (short) my + " z: " + (short) mz);
        mView.update(ax, ay, az);

    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }

    public void updateSR(int sampleRate) {

        mSensorManager.unregisterListener(this);

        if (sampleRate < 10) {
            mSensorManager.unregisterListener(this);
        }
        if (sampleRate > 90) {
            mSensorManager.registerListener(this, aSensor, mSensorManager.SENSOR_DELAY_FASTEST);
            mSensorManager.registerListener((SensorEventListener) this, mSensor, SensorManager.SENSOR_DELAY_FASTEST);
        } else if (sampleRate < 30) {
            mSensorManager.registerListener(this, aSensor, mSensorManager.SENSOR_DELAY_NORMAL);
            mSensorManager.registerListener((SensorEventListener) this, mSensor, SensorManager.SENSOR_DELAY_NORMAL);
        } else if (sampleRate < 50) {
            mSensorManager.registerListener((SensorEventListener) this, mSensor, SensorManager.SENSOR_DELAY_UI);
            mSensorManager.registerListener(this, aSensor, mSensorManager.SENSOR_DELAY_UI);
        } else if (sampleRate < 80) {
            mSensorManager.registerListener(this, aSensor, mSensorManager.SENSOR_DELAY_GAME);
            mSensorManager.registerListener((SensorEventListener) this, mSensor, SensorManager.SENSOR_DELAY_GAME);
        }

    }

    protected void onPause() {
        super.onPause();
        mSensorManager.unregisterListener(this);
    }


}





