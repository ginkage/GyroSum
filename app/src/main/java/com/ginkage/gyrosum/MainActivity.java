package com.ginkage.gyrosum;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends Activity implements SensorEventListener {

    private TextView textX;
    private TextView textY;
    private TextView textZ;
    private int mode = 0;
    private float sum[] = new float[3];
    private SensorManager mSensorManager;

    void reset() {
        sum[0] = sum[1] = sum[2] = 0;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);
        textX = (TextView) findViewById(R.id.textViewX);
        textY = (TextView) findViewById(R.id.textViewY);
        textZ = (TextView) findViewById(R.id.textViewZ);

        findViewById(R.id.buttonX).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mode = 0;
                reset();
            }
        });

        findViewById(R.id.buttonY).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mode = 1;
                reset();
            }
        });

        findViewById(R.id.buttonZ).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mode = 2;
                reset();
            }
        });

        reset();

        mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        Sensor sensorGyroscope = mSensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE);
        mSensorManager.registerListener(this, sensorGyroscope, SensorManager.SENSOR_DELAY_GAME);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mSensorManager.unregisterListener(this);
    }

    @Override
    @SuppressLint("DefaultLocale")
    public void onSensorChanged(SensorEvent sensorEvent) {
        switch (sensorEvent.sensor.getType()) {
            case Sensor.TYPE_GYROSCOPE:
                sum[mode] += sensorEvent.values[mode];
                textX.setText(String.format("%.06f", sum[0]));
                textY.setText(String.format("%.06f", sum[1]));
                textZ.setText(String.format("%.06f", sum[2]));
                break;
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {
    }
}
