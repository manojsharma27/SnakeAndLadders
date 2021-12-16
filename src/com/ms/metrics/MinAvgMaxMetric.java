package com.ms.metrics;

public class MinAvgMaxMetric {
    private int min;
    private int max;
    private double avg;

    public MinAvgMaxMetric(int min, double avg, int max) {
        this.min = min;
        this.avg = avg;
        this.max = max;
    }

    public int getMin() {
        return min;
    }

    public int getMax() {
        return max;
    }

    public double getAvg() {
        return avg;
    }

    @Override
    public String toString() {
        return "{" +
                "min=" + min +
                ", max=" + max +
                ", avg=" + avg +
                '}';
    }
}
