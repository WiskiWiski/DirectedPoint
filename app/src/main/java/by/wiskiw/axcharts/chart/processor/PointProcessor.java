package by.wiskiw.axcharts.chart.processor;


import java.util.ArrayDeque;
import java.util.Queue;

import by.wiskiw.axcharts.chart.DirectedPoint;
import by.wiskiw.axcharts.chart.DirectedPoint.Direction;
import by.wiskiw.axcharts.chart.Properties;

/**
 * Created by WiskiW on 17.02.2017.
 */

public class PointProcessor {

    private static final String TAG = "PointProcessor";
    private PointProcessorListener listener;
    private Queue<Float> valueQueue;


    private float previewPoint;
    private float lastChangeDirectionPoint;

    private Direction direction;
    private int truePointNumb = 0; // time
    private int newDirNumb = 0;


    public PointProcessor(PointProcessorListener listener) {
        this.listener = listener;
        valueQueue = new ArrayDeque<>();
        direction = Direction.FORWARD;
    }

    public void addValue(float value) {
        valueQueue.add(value);
        if (valueQueue.size() > 1) {
            checkQueue();
        }
    }

    private void checkQueue() {
        previewPoint = valueQueue.element();
        valueQueue.remove();
        float currentPoint = valueQueue.element();


        float diff = currentPoint - previewPoint;
        if (Math.abs(diff) > Properties.PERMISSOBLE_VERTICAL_VARIATION) {
            if (diff > 0) {
                checkDirection(Direction.UP);
            } else {
                checkDirection(Direction.DOWN);
            }
        } else {
            checkDirection(Direction.FORWARD);
        }

    }


    private void checkDirection(Direction _direction) {
        if (_direction == direction) {
            truePointNumb += newDirNumb;
            truePointNumb++;
            newDirNumb = 0;
        } else {
            if (newDirNumb < Properties.SET_DIRECTION_POINT_NUMB) {
                if (newDirNumb == 0) {
                    lastChangeDirectionPoint = previewPoint;
                }
                newDirNumb++;
            } else {
                changeDirection(_direction);
                truePointNumb = newDirNumb;
                newDirNumb = 0;
            }
        }

    }

    private void changeDirection(Direction _direction) {
        float value = lastChangeDirectionPoint;
        int time = truePointNumb + 1;
        listener.onDirectedPointReceived(new DirectedPoint(direction, value, time));
        direction = _direction;
        listener.onChangeDirection(direction);

    }


}
