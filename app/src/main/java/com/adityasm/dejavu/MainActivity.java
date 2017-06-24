package com.adityasm.dejavu;

import android.app.Activity;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.hardware.TriggerEvent;
import android.hardware.TriggerEventListener;
import android.os.Handler;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.util.Arrays;

public class MainActivity extends AppCompatActivity
{

	final int GYRO_READ_DELAY_MS = 500;

	private SensorManager mSensorManager;
	private Sensor mGyro;
	private TriggerEventListener mGyroTriggerEventListener;

	private TextView textView, textView2, textView3, textView4;
	double lz = -1;


	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		//Start up and load UI
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main_activity);

		//Get sensor
		mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
		mGyro = mSensorManager.getDefaultSensor(Sensor.TYPE_ROTATION_VECTOR);



		this.textView = (TextView) findViewById(R.id.textView);
		this.textView2 = (TextView) findViewById(R.id.textView2);
		this.textView3 = (TextView) findViewById(R.id.textView3);
		this.textView4 = (TextView) findViewById(R.id.textView4);
		this.textView4.setText("Not turning");

		mSensorManager.registerListener(new SensorEventListener()
		{
			@Override
			public void onSensorChanged(SensorEvent event)
			{
				double x = event.values[0];
				double y = event.values[1];
				double z = event.values[2];

				DecimalFormat df = new DecimalFormat();
				df.setMaximumFractionDigits(2);
				textView.setText(df.format(x));
				textView2.setText(df.format(y));
				textView3.setText(df.format(z));

				if(lz == -1) lz = z;

				else
				{
					if(Math.abs(lz - z) > .005)
					{
						textView4.setText("Turning");
						((ConstraintLayout) findViewById(R.id.layout)).setBackgroundColor(getApplicationContext().getResources().getColor(android.R.color.holo_red_light));
					}
					else
					{
						((ConstraintLayout) findViewById(R.id.layout)).setBackgroundColor(getApplicationContext().getResources().getColor(android.R.color.white));
						textView4.setText("Not Turning");
					}

					lz = z;
				}

			}
			@Override
			public void onAccuracyChanged(Sensor sensor, int accuracy)
			{ }
		}, mGyro, 10000);


	}

}
