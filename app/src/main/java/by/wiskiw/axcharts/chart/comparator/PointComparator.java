package by.wiskiw.axcharts.chart.comparator;

import java.util.ArrayDeque;
import java.util.List;
import java.util.Queue;

import by.wiskiw.axcharts.chart.DirectedPoint;

/**
 * Created by WiskiW on 18.02.2017.
 */

public class PointComparator {


    private String templateName;
    private ComparatorListener listener;
    private List<DirectedPoint> template;
    private Queue<DirectedPoint> pointQueue;


    public PointComparator(String templateName, ComparatorListener listener) {
        this.templateName = templateName;
        this.listener = listener;
        pointQueue = new ArrayDeque<>();
    }

    public void setTemplate(List<DirectedPoint> template) {
        this.template = template;
    }

    public void addDirectedPoint(DirectedPoint directedPoint){
        pointQueue.add(directedPoint);
        if (pointQueue.size()>1){
            compare();
        }
    }

    private void compare() {
        DirectedPoint previewPoint = pointQueue.element();
        pointQueue.remove();
        DirectedPoint currentPoint = pointQueue.element();




    }

}
