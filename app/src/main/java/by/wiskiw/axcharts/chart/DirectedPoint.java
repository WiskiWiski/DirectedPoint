package by.wiskiw.axcharts.chart;

import java.util.Formatter;

/**
 * Created by WiskiW on 17.02.2017.
 */

public class DirectedPoint {

    public enum Direction {
        UP, DOWN, FORWARD
    }

    private Direction direction;
    private float value;
    private int time;

    public DirectedPoint(Direction direction, float value, int time) {
        this.direction = direction;
        this.value = value;
        this.time = time;
    }

    public float getValue() {
        return value;
    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }

    public Direction getDirection() {
        return direction;
    }

    @Override
    public String toString() {
        Formatter formatter = new Formatter();
        formatter.format("\tdirection: %-8s", direction);
        formatter.format("\tvalue: %-14s", value);
        formatter.format("\ttime: %-4s", time);
        return formatter.toString();
    }
}

