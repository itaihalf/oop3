package image;

import java.awt.*;

/**
 * A utility class for processing images.
 * This class provides methods for fixing the size of an image and finding the nearest power of two.
 */
public class ImageProcessor {

    private static final int BITS_IN_INTEGER = 32;

    /**
     * Fixes the size of an image by adding borders and filling them with white color.
     * The resulting image will have dimensions that are the nearest power of two.
     *
     * @param img The original image.
     * @return A new image with a fixed size.
     */
    public static Image fixSize(Image img) {
        int heightNearestPower = nearestPowerOfTwo(img.getHeight());
        int widthNearestPower = nearestPowerOfTwo(img.getWidth());
        int numToAddHeight = (heightNearestPower - img.getHeight()) / 2;
        int numToAddWidth = (widthNearestPower - img.getWidth()) / 2;

        Color[][] fixed = new Color[heightNearestPower][widthNearestPower];
        boolean whiteCondition;

        for (int tempHeight = 0; tempHeight < heightNearestPower; tempHeight++) {
            for (int tempWidth = 0; tempWidth < widthNearestPower; tempWidth++) {
                whiteCondition = (tempHeight < numToAddHeight) ||
                        (tempHeight >= heightNearestPower - numToAddHeight) ||
                        (tempWidth < numToAddWidth) ||
                        (tempWidth >= widthNearestPower - numToAddWidth);

                if (whiteCondition) {
                    fixed[tempHeight][tempWidth] = Color.white;
                } else {
                    fixed[tempHeight][tempWidth] =
                            img.getPixel(tempHeight - numToAddHeight, tempWidth - numToAddWidth);
                }
            }
        }

        return new Image(fixed, widthNearestPower, heightNearestPower);
    }

    /**
     * Finds the nearest power of two for a given number.
     *
     * @param num The input number.
     * @return The nearest power of two.
     */
    private static int nearestPowerOfTwo(int num) {
        if (num != 0) {
            num = BITS_IN_INTEGER - Integer.numberOfLeadingZeros(num - 1);
        }
        return (int) Math.pow(2, num);
    }
}
