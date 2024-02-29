package image;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * A package-private class representing an image.
 * This class provides methods to read an image from a file, create an image from a Color array,
 * and save the image to a file.
 *
 * @author Dan Nirel
 */
public class Image {

    private static final String JPEG_SUFFIX = "jpeg";
    private static final String JPEG_SUFFIX_WITH_DOT = ".jpeg";

    /**
     * The 2D array representing the pixels of the image, where each element is a Color object.
     */
    protected final Color[][] pixelArray;

    /**
     * The width of the image.
     */
    protected final int width;

    /**
     * The height of the image.
     */
    protected final int height;

    /**
     * Constructs an Image object by reading an image from the specified file.
     *
     * @param filename The path to the image file.
     * @throws IOException If an error occurs while reading the image file.
     */
    public Image(String filename) throws IOException {
        BufferedImage im = ImageIO.read(new File(filename));
        width = im.getWidth();
        height = im.getHeight();

        pixelArray = new Color[height][width];
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                pixelArray[i][j] = new Color(im.getRGB(j, i));
            }
        }
    }

    /**
     * Constructs an Image object using the specified pixel array, width, and height.
     *
     * @param pixelArray The 2D array representing the pixels of the image.
     * @param width      The width of the image.
     * @param height     The height of the image.
     */
    public Image(Color[][] pixelArray, int width, int height) {
        this.pixelArray = pixelArray;
        this.width = width;
        this.height = height;
    }

    /**
     * Gets the width of the image.
     *
     * @return The width of the image.
     */
    public int getWidth() {
        return width;
    }

    /**
     * Gets the height of the image.
     *
     * @return The height of the image.
     */
    public int getHeight() {
        return height;
    }

    /**
     * Gets the Color object representing the pixel at the specified coordinates.
     *
     * @param x The x-coordinate of the pixel.
     * @param y The y-coordinate of the pixel.
     * @return The Color object representing the pixel.
     */
    public Color getPixel(int x, int y) {
        return pixelArray[x][y];
    }

    /**
     * Saves the image to the specified file.
     *
     * @param fileName The name of the output file (without the file extension).
     */
    public void saveImage(String fileName) {
        // Initialize BufferedImage, assuming Color[][] is already properly populated.
        BufferedImage bufferedImage = new BufferedImage(pixelArray[0].length, pixelArray.length,
                BufferedImage.TYPE_INT_RGB);
        // Set each pixel of the BufferedImage to the color from the Color[][].
        for (int x = 0; x < pixelArray.length; x++) {
            for (int y = 0; y < pixelArray[x].length; y++) {
                bufferedImage.setRGB(y, x, pixelArray[x][y].getRGB());
            }
        }
        File outputfile = new File(fileName + JPEG_SUFFIX_WITH_DOT);
        try {
            ImageIO.write(bufferedImage, JPEG_SUFFIX, outputfile);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
