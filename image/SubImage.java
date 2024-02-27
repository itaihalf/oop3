package image;

import java.awt.*;

public class SubImage extends Image{

    public SubImage(Color[][] pixelArray, int width, int height) {
        super(pixelArray, width, height);
    }

    public SubImage(String filename) throws Exception {
        super(filename);
    }

    public static SubImage FromImage(Image image, int topLeftI, int topLeftJ, int resolution) {
        Color[][] pixelArray = new Color[resolution][resolution];
        for (int i = 0; i < resolution; i++) {
            for (int j = 0; j < resolution; j++) {
                pixelArray[i][j] = image.getPixel(topLeftI + i, topLeftJ + j);
            }
        }
        return new SubImage(pixelArray, resolution, resolution);
    }


    private static double brightnessFromRGB(Color color){
        return (0.2126 * color.getRed() + 0.7152 * color.getGreen() + 0.0722 * color.getBlue()) / 255;
    }

    public double getAverageBrightness(){
        double sum = 0;
        for (int i = 0; i < getHeight(); i++) {
            for (int j = 0; j < getWidth(); j++) {
                sum += brightnessFromRGB(getPixel(i, j));
            }
        }
        return sum / (getHeight() * getWidth());
    }

}
