package com.example.swapPricer;

public class IRresults {

    private Double[] dataPoints;
    private double value;
    private double y10rate;
    
    public IRresults(Double[] dataPoints, double value, double y10rate){
        this.dataPoints = dataPoints;
        this.value = value;
        this.y10rate = y10rate;
    }

    public Double[] getDatapoints() {
        return this.dataPoints;
    }

    public double getValue() {
        return this.value;
    }

    public double getY10rate() {
        return this.y10rate;
    }


}
