package image;

import java.awt.*;

public class SubImage extends Image{

    public  SubImage (Image image, int topLeftI, int topLeftJ, int resolution) {
        super(new Color[resolution][resolution], resolution, resolution);
        for (int i = 0; i < resolution; i++) {
            for (int j = 0; j < resolution; j++) {
                pixelArray[i][j] = image.getPixel(topLeftI + i, topLeftJ + j);
            }
        }
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
