package com.oceanmanaus.sensorsreader;


import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.eclipse.paho.android.service.MqttAndroidClient;
import org.eclipse.paho.client.mqttv3.IMqttActionListener;
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.IMqttToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.MqttPersistenceException;
import org.w3c.dom.Text;

import java.io.UnsupportedEncodingException;


public class MainActivity extends AppCompatActivity {

    public static final String TAG = "OCEAN_MQTT_SUBSCRIBER";
    String clientID;

    MqttAndroidClient client;
    String topic_pai = "/ocean/sensores/+/+";
    public static final int QOS = 0;

    TextView topicoHum, topicoGas, topicoTemp, topicoLum, payloadHum, payloadGas, payloadTemp, payloadLum;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        topicoHum = (TextView) findViewById(R.id.topico_hum);
        topicoGas = (TextView) findViewById(R.id.topico_gas);
        topicoTemp = (TextView) findViewById(R.id.topico_temp);
        topicoLum = (TextView) findViewById(R.id.topico_light);

        payloadHum = (TextView) findViewById(R.id.payload_hum);
        payloadGas = (TextView) findViewById(R.id.payload_gas);
        payloadTemp = (TextView) findViewById(R.id.payload_temp);
        payloadLum = (TextView) findViewById(R.id.payload_light);

        clientID = MqttClient.generateClientId();
        //client = new MqttAndroidClient(this.getApplicationContext(), "tcp://iot.eclipse.org:1883", clientID);
        client = new MqttAndroidClient(this.getApplicationContext(), "tcp://iot.oceanmanaus.com:1883", clientID);

        try {
            IMqttToken token = client.connect();
            token.setActionCallback(new IMqttActionListener() {
                @Override
                public void onSuccess(IMqttToken asyncActionToken) {
                    // We are connected
                    Log.d(TAG, "onSuccess");

                    String payload = "Leitor " + clientID + " está online!";
                    byte[] encodedPayload;

                    try {
                        encodedPayload = payload.getBytes("UTF-8");

                        MqttMessage message = new MqttMessage(encodedPayload);
                        client.publish("/ocean/sensores/debug", message);

                        IMqttToken subToken = client.subscribe(topic_pai, QOS);
                        subToken.setActionCallback(new IMqttActionListener() {
                            @Override
                            public void onSuccess(IMqttToken asyncActionToken) {
                                Log.d(TAG, topic_pai + " sobrescrito");
                            }

                            @Override
                            public void onFailure(IMqttToken asyncActionToken,
                                                  Throwable exception) {
                                // The subion could not be performed, maybe the user was not
                                // authorized to subscribe on the specified topic e.g. using wildcards

                            }
                        });



                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    } catch (MqttPersistenceException e) {
                        e.printStackTrace();
                    } catch (MqttException e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onFailure(IMqttToken asyncActionToken, Throwable exception) {
                    // Something went wrong e.g. connection timeout or firewall problems
                    Log.d(TAG, "onFailure");

                }
            });

            client.setCallback(new MqttCallback() {
                @Override
                public void connectionLost(Throwable cause) {

                }

                @Override
                public void messageArrived(String topic, MqttMessage message) throws Exception {
                    Log.i(TAG, topic + " --> " + message.toString());

                    if (topic.contains("umidade")) {
                        topicoHum.setText(topic);
                        payloadHum.setText(message.toString() + "%");
                    } else if (topic.contains("gas")) {
                        topicoGas.setText(topic);
                        payloadGas.setText(message.toString() + "%");
                    } else if (topic.contains("luminosidade")) {
                        topicoLum.setText(topic);
                        payloadLum.setText(message.toString() + " lux");
                    } else if (topic.contains("temperatura")) {
                        topicoTemp.setText(topic);
                        payloadTemp.setText(message.toString() + "°C");
                    }

                }

                @Override
                public void deliveryComplete(IMqttDeliveryToken token) {

                }
            });

        } catch (MqttException e) {
            e.printStackTrace();
        }
        
    }
}
