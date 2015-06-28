package cc.cloudist.acplibrary.components;

import android.graphics.Color;

import java.util.ArrayList;
import java.util.List;

public class FlowerDataCalc {

    private double[] mCosValues;
    private double[] mSinValues;

    public FlowerDataCalc(int segmentCount) {
        mCosValues = new double[segmentCount];
        mSinValues = new double[segmentCount];

        double angleUnit = Math.PI * 2.0 / segmentCount;
        for (int i = 0; i < segmentCount; i++) {
            double currentAngle = angleUnit * i;
            mCosValues[i] = Math.cos(currentAngle);
            mSinValues[i] = Math.sin(currentAngle);
        }
    }

    public List<PetalCoordinate> getSegmentsCoordinates(int rectSize, int outPadding, int inPadding, int segmentCount, int finalWidth) {

        List<PetalCoordinate> coordinates = new ArrayList<>(segmentCount);

        double centerY = rectSize / 2.0;
        double centerX = finalWidth / 2.0;
        double outRadius = (rectSize - outPadding) / 2.0;
        double inRadius = inPadding / 2.0;

        for (int i = 0; i < segmentCount; i++) {
            double xOutOffset = outRadius * mCosValues[i];
            double yOutOffset = outRadius * mSinValues[i];

            int startX = (int) (centerX - xOutOffset);
            int startY = (int) (centerY + yOutOffset);

            double xInOffset = inRadius * mCosValues[i];
            double yInOffset = inRadius * mSinValues[i];

            int endX = (int) (centerX - xInOffset);
            int endY = (int) (centerY + yInOffset);

            PetalCoordinate coordinate = new PetalCoordinate(startX, startY, endX, endY);
            coordinates.add(coordinate);
        }

        return coordinates;
    }

    public int[] getSegmentsColors(int themeColor, int fadeColor, int petalCount, int petalAlpha) {
        int[] colors = new int[petalCount];

        int themeRed = Color.red(themeColor);
        int themeGreen = Color.green(themeColor);
        int themeBlue = Color.blue(themeColor);

        int fadeRed = Color.red(fadeColor);
        int fadeGreen = Color.green(fadeColor);
        int fadeBlue = Color.blue(fadeColor);

        double redDelta = (double) (fadeRed - themeRed) / (petalCount - 1);
        double greenDelta = (double) (fadeGreen - themeGreen) / (petalCount - 1);
        double blueDelta = (double) (fadeBlue - themeBlue) / (petalCount - 1);

        for (int i = 0; i < petalCount; i++) {
            int color = Color.argb(petalAlpha,
                    (int) (themeRed + redDelta * i),
                    (int) (themeGreen + greenDelta * i),
                    (int) (themeBlue + blueDelta * i));
            colors[i] = color;
        }

        return colors;
    }

}
