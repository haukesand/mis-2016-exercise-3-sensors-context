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
import android.widget.TextView;

import java.util.List;


public class MainActivity extends AppCompatActivity implements SensorEventListener {

    private SensorManager mSensorManager;
    private Sensor mSensor;
    private Sensor aSensor;
    Context cv = this;
    float ax,ay,az,m;
    private AttributeSet aSet;
    TextView myTextView;
MyView av;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
       // Intent intent= new Intent(MainActivity.this,MyView.class);
       // startActivity(Intent);
         av = new MyView(this, aSet);
        mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        aSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        mSensorManager.registerListener((SensorEventListener) this, aSensor, SensorManager.SENSOR_DELAY_GAME);
        mSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);
        myTextView = (TextView)findViewById(R.id.sensortext);

    }
    @Override
    public void onSensorChanged(SensorEvent event) {
        if (event.sensor.getType()==Sensor.TYPE_ACCELEROMETER){
            ax=event.values[0];
            ay=event.values[1];
            az=event.values[2];

        }

        if (event.sensor.getType()==Sensor.TYPE_MAGNETIC_FIELD) m = event.values[0];

        myTextView.setText("Accelerometor x: "+ax+" y: "+ay+" z: "+az+" Magnetometer: "+m);
        av.update(ax,ay);

    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }


}

