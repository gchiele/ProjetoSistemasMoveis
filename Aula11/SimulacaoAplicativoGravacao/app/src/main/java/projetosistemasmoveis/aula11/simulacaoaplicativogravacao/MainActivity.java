package projetosistemasmoveis.aula11.simulacaoaplicativogravacao;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.graphics.Color;
import android.graphics.PorterDuff;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private TextView teste;
    private ImageView imageView;

    private SensorManager sensorManager;
    private Sensor sensorLuminosidade;
    private Sensor sensorAproximacao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        teste = (TextView) findViewById( R.id.teste );

        imageView = (ImageView) findViewById( R.id.imageView );

        sensorManager = (SensorManager)getSystemService(SENSOR_SERVICE);
        if (sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT) != null) {
            sensorLuminosidade = sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT);
        }

        if (sensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY) != null) {
            sensorAproximacao = sensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY);
        }

        sensorManager.registerListener(new SensorLuminosidade(), sensorLuminosidade, SensorManager.SENSOR_DELAY_NORMAL);
        sensorManager.registerListener(new SensorAproximacao(), sensorAproximacao, SensorManager.SENSOR_DELAY_NORMAL);
    }

    class SensorLuminosidade implements SensorEventListener {
        public void onAccuracyChanged(Sensor sensor, int accuracy) {
        }

        public void onSensorChanged(SensorEvent event) {
            float Valor = event.values[0];;

            if(Valor < 10){
                imageView.setColorFilter(ContextCompat.getColor(MainActivity.this, R.color.white), PorterDuff.Mode.SRC_IN);
                imageView.setBackgroundColor(Color.BLACK);
            }else{
                imageView.setColorFilter(ContextCompat.getColor(MainActivity.this, R.color.black), PorterDuff.Mode.SRC_IN);
                imageView.setBackgroundColor(Color.WHITE);
            }
        }
    }

    class SensorAproximacao implements SensorEventListener {
        public void onAccuracyChanged(Sensor sensor, int accuracy) {
        }

        public void onSensorChanged(SensorEvent event) {
            float Valor = event.values[0];;

            if(Valor < 5){
                imageView.setImageResource(R.drawable.ic_mic_on);
            }else{
                imageView.setImageResource(R.drawable.ic_mic_off);
            }
        }
    }
}