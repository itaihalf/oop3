package image;

import java.util.ArrayList;

public class SubImageList extends ArrayList<SubImage> {


    public SubImageList(Image image, int resolution) throws DimensionExeption {

        // todo: check if image is a integral multiple of 2
        if (image.getWidth() % resolution != 0 || image.getHeight() % resolution != 0) {
            throw new DimensionExeption();
        }
        for (int i = 0; i < image.getHeight(); i += resolution) { //todo is resolution or image/resolution
            for (int j = 0; j < image.getWidth(); j += resolution) {
                add(SubImage.FromImage(image, i, j, resolution));
            }
        }
    }
}
