package cc.cloudist.cpllibrary.components;

import android.graphics.Color;

import java.util.ArrayList;
import java.util.List;

public class FlowerDataCalc {

    private double[] cosValues, sinValues;

    public FlowerDataCalc(int lineCount) {
        cosValues = new double[lineCount];
        sinValues = new double[lineCount];

        double angleUnit = 360d / lineCount;
        for (int i = 0; i < lineCount; i++) {
            double currentAngle = (angleUnit * i) * Math.PI / 180;
            cosValues[i] = Math.cos(currentAngle);
            sinValues[i] = Math.sin(currentAngle);
        }

    }

    public List<PetalCoordinate> getLineCoordinates(int rectSize, int outPadding, int inPadding, int lineCount) {

        List<PetalCoordinate> coordinates = new ArrayList<PetalCoordinate>(lineCount);

        int center = rectSize / 2;
        int outRadius = center - outPadding / 2;
        int inRadius = inPadding / 2;

        for (int i = 0; i < lineCount; i++) {
            double xOutOffset = outRadius * cosValues[i];
            double yOutOffset = outRadius * sinValues[i];

            int startX = (int) (center - xOutOffset);
            int startY = (int) (center + yOutOffset);

            double xInOffset = inRadius * cosValues[i];
            double yInOffset = inRadius * sinValues[i];

            int endX = (int) (center - xInOffset);
            int endY = (int) (center + yInOffset);

            PetalCoordinate coordinate = new PetalCoordinate(startX, startY, endX, endY);
            coordinates.add(coordinate);

        }

        return coordinates;
    }

    public List<Integer> getLineColors(int themeColor, int fadeColor, int petalCount, int petalAlpha) {
        List<Integer> colors = new ArrayList<>(petalCount);

        int themeRed = Color.red(themeColor);
        int themeGreen = Color.green(themeColor);
        int themeBlue = Color.blue(themeColor);

        int fadeRed = Color.red(fadeColor);
        int fadeGreen = Color.green(fadeColor);
        int fadeBlue = Color.blue(fadeColor);

        int redRange = fadeRed - themeRed;
        int greenRange = fadeGreen - themeGreen;
        int blueRange = fadeBlue - themeBlue;

        for (int i = 0; i < petalCount; i++) {
            int color = Color.argb(petalAlpha,
                    (int) (themeRed + (redRange / (double) petalCount) * i),
                    (int) (themeGreen + (greenRange / (double) petalCount) * i),
                    (int) (themeBlue + (blueRange / (double) petalCount) * i));
            colors.add(color);
        }

        return colors;
    }

}
