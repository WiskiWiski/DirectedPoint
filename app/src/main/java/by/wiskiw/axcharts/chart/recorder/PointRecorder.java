package by.wiskiw.axcharts.chart.recorder;

import android.util.Log;

import by.wiskiw.axcharts.chart.DirectedPoint;
import by.wiskiw.axcharts.chart.DirectedPoint.Direction;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by WiskiW on 16.02.2017.
 */

public class PointRecorder {

    private static final String TAG = "PointRecorder";

    private boolean recording;

    private List<DirectedPoint> valueList;

    public PointRecorder() {
        recording = false;
        valueList = new ArrayList<>();
    }

    private void showStartRecMsg() {
        Log.d(TAG, "showStartRecMsg: Recording...");
    }

    private void showFinishRecMsg() {
        Log.d(TAG, "showStartRecMsg: Recording finished");
    }

    public void addValue(DirectedPoint directedPoint) {
        if (recording) {
            valueList.add(directedPoint);
        }
    }

    public String valueListToExcelString() {
        if (valueList.size() > 0) {
            StringBuilder sb = new StringBuilder();
            for (DirectedPoint directedPoint : valueList) {
                sb.append(String.valueOf(directedPoint.getValue()).replace(".", ",")).append("; ");
            }
            return sb.substring(0, sb.lastIndexOf(";"));
        } else {
            return "No own record found!";
        }
    }

    public String valueListToListString() {
        if (valueList.size() > 0) {
            StringBuilder sb = new StringBuilder();
            for (DirectedPoint directedPoint : this.valueList) {
                sb.append(directedPoint.getValue()).append("f, ");
            }
            return sb.substring(0, sb.lastIndexOf(","));
        } else {
            return "No own record found!";
        }
    }

    public List<DirectedPoint> getValueList() {
        return valueList;
    }

    public void startRecording() {
        showStartRecMsg();
        valueList.clear();
        recording = true;
    }

    public void stopRecording() {
        showFinishRecMsg();
        recording = false;
    }

}
