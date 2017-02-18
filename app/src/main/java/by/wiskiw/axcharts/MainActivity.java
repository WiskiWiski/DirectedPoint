package by.wiskiw.axcharts;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;

import by.wiskiw.axcharts.chart.DirectedPoint;
import by.wiskiw.axcharts.chart.DirectedPoint.Direction;

import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import by.wiskiw.axcharts.chart.processor.PointProcessorListener;
import by.wiskiw.axcharts.chart.recorder.PointRecorder;
import by.wiskiw.axcharts.chart.processor.PointProcessor;

public class MainActivity extends AppCompatActivity
        implements SensorEventListener, PointProcessorListener {


    private static final String TAG = "MainActivity";
    private TextView textView;
    private TextView directionTextView;
    private Button btn1;
    private Button btn2;
    private Button btn3;

    private PointRecorder pointRecorder;
    private PointProcessor pointProcessor;


    private SensorManager sensorManager;
    private Sensor accelerometer;

    private static int n = 0;
    private final static int LOG_EVERY = 3;
    private final static int GRAPH_MAX_LINES = 65;
    private final static int GRAPH_MAX_COLONS = 125;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        textView = (TextView) findViewById(R.id.textView);
        directionTextView = (TextView) findViewById(R.id.direction_tv);
        btn1 = (Button) findViewById(R.id.btn_1);
        btn2 = (Button) findViewById(R.id.btn_2);
        btn3 = (Button) findViewById(R.id.btn_3);

        pointProcessor = new PointProcessor(this);
        pointRecorder = new PointRecorder();


        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pointRecorder.startRecording();
            }
        });
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pointRecorder.stopRecording();
                //Log.d(TAG, "excel chart: " + pointRecorder.valueListToExcelString());
                //Log.d(TAG, "list chart: " + pointRecorder.valueListToListString());
                Log.d(TAG, "------------ Chart Recorder ------------");
                for (DirectedPoint directedPoint : pointRecorder.getValueList()) {
                    Log.d(TAG, "pointRecorder: " + directedPoint.toString());
                }
                Log.d(TAG, "----------------------------------------");

            }
        });


        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        sensorManager.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_GAME);
    }

    private void logMsg(String msg) {
        String tvText = (String) directionTextView.getText();
        StringBuilder line = new StringBuilder(msg);
        directionTextView.setText(line.append("\n").append(tvText));
    }

    private void drawValue(float val) {
        if (n < LOG_EVERY) {
            n++;
            return;
        }
        final char point = '*';
        String tvText = (String) textView.getText();
        val = val > 10 ? 10 : val;
        val = val < -10 ? -10 : val;

        int factor = GRAPH_MAX_COLONS / 2;
        factor += (val * factor / 10);

        StringBuilder line = new StringBuilder();
        for (int i = 0; i < factor; i++) {
            line.append(point);
        }

        int lines = textView.getLineCount();
        if (lines > GRAPH_MAX_LINES) {
            tvText = tvText.substring(0, tvText.lastIndexOf("\n"));
            textView.setText(line.append("\n").append(tvText));
        } else {
            textView.setText(line.append("\n").append(tvText));
        }
    }

    protected void onPause() {
        super.onPause();
        sensorManager.unregisterListener(this);
    }


    protected void onResume() {
        super.onResume();
        sensorManager.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        float val = sensorEvent.values[2];
        drawValue(val);
        pointProcessor.addValue(val);
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }

    @Override
    public void onChangeDirection(Direction direction) {
        //Log.d(TAG, "direction changed: " + direction.toString());
        logMsg(direction.toString());
    }

    @Override
    public void onDirectedPointReceived(DirectedPoint directedPoint) {
        Log.d(TAG, "point received: " + directedPoint.toString());
        pointRecorder.addValue(directedPoint);
    }
}
