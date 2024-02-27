package image;

import java.util.ArrayList;

public class SubImageList extends ArrayList<SubImage> {


    public SubImageList(Image image, int resolution) throws DimensionException {
        super();
        // todo: check if image is a integral multiple of 2
        if (image.getWidth() % resolution != 0 || image.getHeight() % resolution != 0) {
            throw new DimensionException();
        }
        for (int i = 0; i < image.getHeight(); i += image.getWidth()/resolution) { //todo is resolution or image/resolution
            for (int j = 0; j < image.getWidth(); j += image.getWidth()/ resolution) {
                add(new SubImage(image, i, j, image.getWidth()/resolution));
            }
        }
    }
}
