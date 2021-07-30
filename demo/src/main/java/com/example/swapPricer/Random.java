package com.example.swapPricer;

public class Random {
    private static double xysquare;
    private static double result;

    public static double operator() {
        double x;
        double y;
        do {
            x = (2.0*Math.random() / (double) 32766);
            y = (2.0*Math.random() / (double) 32766);
            xysquare = x*x + y*y;
        } while (xysquare >= 1.0);
        
        result = x*Math.sqrt(-2 * Math.log(xysquare) / xysquare);
	    return result;
    }
}

