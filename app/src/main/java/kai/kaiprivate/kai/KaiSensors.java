package kai.kaiprivate.kai;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;

import kai.kaiprivate.R;

public class KaiSensors extends AppCompatActivity implements SensorEventListener{

    private SensorManager sensorManager;

    private Sensor accelerometer;   //TYPE_ACCELEROMETER
    private Sensor gyroscope;       //TYPE_GYROSCOPE
    private Sensor magnetometer;    //TYPE_MAGNETIC_FIELD
    private Sensor barometer;       //TYPE_PRESSURE

    @Override
    protected void onPause() {
        super.onPause();

        sensorManager.unregisterListener(this);
    }

    @Override
    protected void onResume() {
        super.onResume();

        sensorManager.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_NORMAL);
        sensorManager.registerListener(this, gyroscope, SensorManager.SENSOR_DELAY_NORMAL);
        sensorManager.registerListener(this, magnetometer, SensorManager.SENSOR_DELAY_NORMAL);
        sensorManager.registerListener(this, barometer, SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kai_sensors2);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);

        if (sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER) != null) {
            // success! we have an accelerometer
            accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
            sensorManager.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_NORMAL);
        } else {
            // fai! we dont have an accelerometer!
        }

        if (sensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE) != null) {
            // success! we have an accelerometer
            gyroscope = sensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE);
            sensorManager.registerListener(this, gyroscope, SensorManager.SENSOR_DELAY_NORMAL);
        } else {
            // fai! we dont have an accelerometer!
        }

        if (sensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD) != null) {
            // success! we have an accelerometer
            magnetometer = sensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);
            sensorManager.registerListener(this, magnetometer, SensorManager.SENSOR_DELAY_NORMAL);
        } else {
            // fai! we dont have an accelerometer!
        }

        if (sensorManager.getDefaultSensor(Sensor.TYPE_PRESSURE) != null) {
            // success! we have an accelerometer
            barometer = sensorManager.getDefaultSensor(Sensor.TYPE_PRESSURE);
            sensorManager.registerListener(this, barometer, SensorManager.SENSOR_DELAY_NORMAL);
        } else {
            // fai! we dont have an accelerometer!
        }

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        Sensor sensor = sensorEvent.sensor;
        if (sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
            Log.v("kai", "Accelerometer: " + String.valueOf(sensorEvent.values[0]) + ", " + String.valueOf(sensorEvent.values[1]) + ", " + String.valueOf(sensorEvent.values[2]));
        }else if (sensor.getType() == Sensor.TYPE_GYROSCOPE) {
            Log.v("kai", "Gyroscope: " + String.valueOf(sensorEvent.values[0]) + ", " + String.valueOf(sensorEvent.values[1]) + ", " + String.valueOf(sensorEvent.values[2]));
        }else if (sensor.getType() == Sensor.TYPE_MAGNETIC_FIELD) {
            Log.v("kai", "Magnetometer: " + String.valueOf(sensorEvent.values[0]) + ", " + String.valueOf(sensorEvent.values[1]) + ", " + String.valueOf(sensorEvent.values[2]));
        }else if (sensor.getType() == Sensor.TYPE_PRESSURE) {
            Log.v("kai", "Barometer: " + String.valueOf(sensorEvent.values[0]));
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }
}
