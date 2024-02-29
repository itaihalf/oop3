package image;

import java.awt.*;

/**
 * A class representing a sub-image derived from an existing image.
 * This class provides methods for calculating average brightness and initializing sub-images.
 */
public class SubImage extends Image {

    /**
     * Constructs a sub-image with the specified resolution, derived from the given image starting from the specified coordinates.
     *
     * @param image      The original image.
     * @param topLeftI   The starting row index of the sub-image in the original image.
     * @param topLeftJ   The starting column index of the sub-image in the original image.
     * @param resolution The resolution (width and height) of the sub-image.
     */
    public SubImage(Image image, int topLeftI, int topLeftJ, int resolution) {
        super(new Color[resolution][resolution], resolution, resolution);
        for (int i = 0; i < resolution; i++) {
            for (int j = 0; j < resolution; j++) {
                pixelArray[i][j] = image.getPixel(topLeftI + i, topLeftJ + j);
            }
        }
    }

    /**
     * Calculates the brightness from the RGB values of a color.
     *
     * @param color The color for which to calculate brightness.
     * @return The brightness value.
     */
    private static double brightnessFromRGB(Color color) {
        return (0.2126 * color.getRed() + 0.7152 * color.getGreen() + 0.0722 * color.getBlue()) / 255;
    }

    /**
     * Calculates the average brightness of the sub-image.
     *
     * @return The average brightness value.
     */
    public double getAverageBrightness() {
        double sum = 0;
        for (int i = 0; i < getHeight(); i++) {
            for (int j = 0; j < getWidth(); j++) {
                sum += brightnessFromRGB(getPixel(i, j));
            }
        }
        return sum / (getHeight() * getWidth());
    }
}
