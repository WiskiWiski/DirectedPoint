package by.wiskiw.axcharts.chart.processor;

import by.wiskiw.axcharts.chart.DirectedPoint;

/**
 * Created by WiskiW on 18.02.2017.
 */

public interface PointProcessorListener {

    void onChangeDirection(DirectedPoint.Direction direction);

    void onDirectedPointReceived(DirectedPoint directedPoint);

}
