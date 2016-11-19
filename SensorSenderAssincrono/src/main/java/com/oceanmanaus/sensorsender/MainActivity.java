package com.oceanmanaus.sensorsender;

// -- https://github.com/eclipse/paho.mqtt.android

import android.Manifest;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;

import org.eclipse.paho.android.service.MqttAndroidClient;
import org.eclipse.paho.client.mqttv3.IMqttActionListener;
import org.eclipse.paho.client.mqttv3.IMqttToken;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.MqttPersistenceException;

import java.io.UnsupportedEncodingException;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();
    private static final int QOS = 0;
    private SensorManager mSensorManager;
    private Sensor mAcel, mGir, mTemper, mHum, mLumn, mPressure;

    TextView aX, aY, aZ, gX, gY, gZ, temp, umid, lum, press;

    SensorEventListener acelSL, girSL, tempSL, humSL,lumSL, presSL;

    private String clientID;
    private MqttAndroidClient client;
    private boolean isConneted;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        aX = (TextView) findViewById(R.id.acel_x);
        aY = (TextView) findViewById(R.id.acel_y);
        aZ = (TextView) findViewById(R.id.acel_z);

        gX = (TextView) findViewById(R.id.gir_x);
        gY = (TextView) findViewById(R.id.gir_y);
        gZ = (TextView) findViewById(R.id.gir_z);

        temp = (TextView) findViewById(R.id.temp);
        umid = (TextView) findViewById(R.id.umid);
        lum = (TextView) findViewById(R.id.lum);
        press = (TextView) findViewById(R.id.press);

        clientID = MqttClient.generateClientId();
        //client = new MqttAndroidClient(this.getApplicationContext(), "tcp://iot.eclipse.org:1883", clientID);
        client = new MqttAndroidClient(this.getApplicationContext(), "tcp://iot.oceanmanaus.com:1883", clientID);

        IMqttToken token = null;
        try {
            token = client.connect();
            token.setActionCallback(new IMqttActionListener() {
                @Override
                public void onSuccess(IMqttToken asyncActionToken) {
                    Log.d(TAG, "onSuccess");

                    String payload = "Sender " + clientID + " está online!";
                    byte[] encodedPayload;
                    try {
                        encodedPayload = payload.getBytes("UTF-8");

                        MqttMessage message = new MqttMessage(encodedPayload);
                        client.publish("/ocean/sensores/debug", message);

                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    } catch (MqttPersistenceException e) {
                        e.printStackTrace();
                    } catch (MqttException e) {
                        e.printStackTrace();
                    }
                    enviarDados();
                    isConneted= true;
                }

                @Override
                public void onFailure(IMqttToken asyncActionToken, Throwable exception) {

                }
            });
        } catch (MqttException e) {
            e.printStackTrace();
        }

        if (mAcel != null) {
            mSensorManager.registerListener(acelSL = new SensorEventListener() {
                @Override
                public void onSensorChanged(SensorEvent event) {
                    aX.setText("" + event.values[0]);
                    aY.setText("" + event.values[1]);
                    aZ.setText("" + event.values[2]);

                    MqttMessage messageX = new MqttMessage();
                    MqttMessage messageY = new MqttMessage();
                    MqttMessage messageZ = new MqttMessage();

                    messageX.setQos(QOS);
                    messageX.setPayload(("" + event.values[0]).getBytes());
                    messageY.setQos(QOS);
                    messageY.setPayload(("" + event.values[1]).getBytes());
                    messageZ.setQos(QOS);
                    messageZ.setPayload(("" + event.values[2]).getBytes());

                    try {
                        client.publish("/ocean/sensores/" + clientID + "/acelerometro/x", messageX);
                        client.publish("/ocean/sensores/" + clientID + "/acelerometro/y", messageY);
                        client.publish("/ocean/sensores/" + clientID + "/acelerometro/z", messageZ);
                    } catch (MqttException e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onAccuracyChanged(Sensor sensor, int accuracy) {

                }
            }, mAcel, SensorManager.SENSOR_DELAY_NORMAL);
        }

        if (mTemper != null) {
            mSensorManager.registerListener(tempSL = new SensorEventListener() {
                @Override
                public void onSensorChanged(SensorEvent event) {
                    temp.setText("" + event.values[0]);
                }

                @Override
                public void onAccuracyChanged(Sensor sensor, int accuracy) {

                }
            }, mTemper, SensorManager.SENSOR_DELAY_NORMAL);
        }

        if (mGir != null) {
            mSensorManager.registerListener(girSL = new SensorEventListener() {
                @Override
                public void onSensorChanged(SensorEvent event) {
                    gX.setText("" + event.values[0]);
                    gY.setText("" + event.values[1]);
                    gZ.setText("" + event.values[2]);

                    MqttMessage messageX = new MqttMessage();
                    MqttMessage messageY = new MqttMessage();
                    MqttMessage messageZ = new MqttMessage();

                    messageX.setQos(QOS);
                    messageX.setPayload(("" + event.values[0]).getBytes());
                    messageY.setQos(QOS);
                    messageY.setPayload(("" + event.values[1]).getBytes());
                    messageZ.setQos(QOS);
                    messageZ.setPayload(("" + event.values[2]).getBytes());


                    try {
                        client.publish("/ocean/sensores/" + clientID + "/giroscopio/x", messageX);
                        client.publish("/ocean/sensores/" + clientID + "/giroscopio/y", messageY);
                        client.publish("/ocean/sensores/" + clientID + "/giroscopio/z", messageZ);
                    } catch (MqttException e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onAccuracyChanged(Sensor sensor, int accuracy) {

                }
            }, mGir, SensorManager.SENSOR_DELAY_NORMAL);
        }

        if (mHum != null) {
            mSensorManager.registerListener(humSL = new SensorEventListener() {
                @Override
                public void onSensorChanged(SensorEvent event) {
                    umid.setText("" + event.values[0]);


                    MqttMessage message = new MqttMessage();

                    message.setQos(QOS);
                    message.setPayload(("" + event.values[0]).getBytes());


                    try {
                        client.publish("/ocean/sensores/" + clientID + "/umidade", message);
                    } catch (MqttException e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onAccuracyChanged(Sensor sensor, int accuracy) {

                }
            }, mHum, SensorManager.SENSOR_DELAY_NORMAL);
        }

        if (mPressure != null) {
            mSensorManager.registerListener(presSL = new SensorEventListener() {
                @Override
                public void onSensorChanged(SensorEvent event) {
                    press.setText("" + event.values[0]);

                    MqttMessage message = new MqttMessage();

                    message.setQos(QOS);
                    message.setPayload(("" + event.values[0]).getBytes());


                    try {
                        client.publish("/ocean/sensores/" + clientID + "/pressao", message);
                    } catch (MqttException e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onAccuracyChanged(Sensor sensor, int accuracy) {

                }
            }, mPressure, SensorManager.SENSOR_DELAY_NORMAL);
        }

        mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);

        if (mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER) != null){
            mAcel = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        }else {
            Log.i("Sensor", "Não tem TYPE_ACCELEROMETER");
        }

        if (mSensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE) != null){
            mGir = mSensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE);
        }else {
            Log.i("Sensor", "Não tem TYPE_GYROSCOPE");
        }

        if (mSensorManager.getDefaultSensor(Sensor.TYPE_AMBIENT_TEMPERATURE) != null){
            mTemper = mSensorManager.getDefaultSensor(Sensor.TYPE_AMBIENT_TEMPERATURE);
        }else {
            Log.i("Sensor", "Não tem TYPE_AMBIENT_TEMPERATURE");
        }

        if (mSensorManager.getDefaultSensor(Sensor.TYPE_RELATIVE_HUMIDITY) != null){
            mHum = mSensorManager.getDefaultSensor(Sensor.TYPE_RELATIVE_HUMIDITY);
        }else {
            Log.i("Sensor", "Não tem TYPE_RELATIVE_HUMIDITY");
        }

        if (mSensorManager.getDefaultSensor(Sensor.TYPE_LIGHT) != null){
            mLumn = mSensorManager.getDefaultSensor(Sensor.TYPE_LIGHT);
        }else {
            Log.i("Sensor", "Não tem TYPE_LIGHT");
        }

        if (mSensorManager.getDefaultSensor(Sensor.TYPE_PRESSURE) != null){
            mPressure = mSensorManager.getDefaultSensor(Sensor.TYPE_PRESSURE);
        }else {
            Log.i("Sensor", "Não tem TYPE_PRESSURE");
        }
    }

    protected void enviarDados(){
        if(mAcel != null){
            mSensorManager.registerListener(lumSL = new SensorEventListener() {
                @Override
                public void onSensorChanged(SensorEvent event) {
                    lum.setText("" + event.values[0]);

                    MqttMessage message = new MqttMessage();
                    message.setQos(QOS);
                    message.setPayload(("" + event.values[0]).getBytes());

                    try {
                        client.publish("/ocean/sensores/" + clientID + "/luminosidade", message);
                    } catch (MqttException e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onAccuracyChanged(Sensor sensor, int accuracy) {

                }
            }, mLumn, mSensorManager.SENSOR_DELAY_NORMAL);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        enviarDados();
    }
}

