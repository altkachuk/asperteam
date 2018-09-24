package atproj.cyplay.com.asperteamapi.util;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by andre on 18-Jul-18.
 */

public class LinearRegression {

    /**
     * Calculate Linear Regression for 2 values
     * Linear Regression Function: y = a + b*x
     * Slope (b) of Regression Line: b = r*Sy/Sx; r-correlation coef: r = sum((x-xAvg)*(y-yAvg))/sqrt(sum((x-xAvg)*(x-XAvg))*sum((y-yAvg)*(y-yAvg)))
     * Y-Intercept (a) of Regression Line: a = yAvg - b * xAvg
     * @param xSrc list of x values
     * @param ySrc list of y values
     * @param x0 first x value for calculating y
     * @param x1 second x value for calculating y
     * @return list of y0 and y1 values corresponding to x0 and x1
     */

    static public List<Double> calc(List<Double> xSrc, List<Double> ySrc, float x0, float x1) {
        if (xSrc.size() < 2)
            return Arrays.asList(0d, 0d);


        double xAvg = 0;
        double yAvg = 0;

        for (int i = 0; i < xSrc.size(); i++) {
            xAvg += xSrc.get(i);
            yAvg += ySrc.get(i);
        }

        xAvg /= xSrc.size();
        yAvg /= ySrc.size();

        // calculate r
        double xyAvg = 0;
        double xSqrAvgSum = 0;
        double ySqrAvgSum = 0;

        for (int i = 0; i < xSrc.size(); i++) {
            xyAvg += (xSrc.get(i) - xAvg) * (ySrc.get(i) - yAvg);
            xSqrAvgSum += (xSrc.get(i) - xAvg) * (xSrc.get(i) - xAvg);
            ySqrAvgSum += (ySrc.get(i) - yAvg) * (ySrc.get(i) - yAvg);
        }

        double r = xyAvg / (Math.sqrt(xSqrAvgSum * ySqrAvgSum));

        // calculate b
        double Sx = Math.sqrt(xSqrAvgSum / (xSrc.size()-1));
        double Sy = Math.sqrt(ySqrAvgSum / (xSrc.size()-1));

        double b = r * Sy / Sx;

        // calculate a
        double a = yAvg - (b * xAvg);

        // calculate y0 and y1
        double y0 = a + (b * x0);
        double y1 = a + (b * x1);

        return Arrays.asList(y0, y1);
    }
}
