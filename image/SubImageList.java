package image;

import java.util.ArrayList;

/**
 * A list of sub-images derived from a given image.
 * This class represents a collection of sub-images generated from an original image based on a specified resolution.
 */
public class SubImageList extends ArrayList<SubImage> {

    /**
     * Constructs a list of sub-images from the given image with the specified resolution.
     *
     * @param image      The original image.
     * @param resolution The resolution (width and height) of each sub-image.
     *
     */
    public SubImageList(Image image, int resolution){
        super();
        for (int i = 0; i < image.getHeight(); i += image.getWidth() / resolution) {
            for (int j = 0; j < image.getWidth(); j += image.getWidth() / resolution) {
                add(new SubImage(image, i, j, image.getWidth() / resolution));
            }
        }
    }
}
