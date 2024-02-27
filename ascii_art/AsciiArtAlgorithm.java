package ascii_art;

import image.DimensionException;
import image.Image;
import image.SubImageList;
import image_char_matching.CharConverter;
import image_char_matching.SubImgCharMatcher;

public class AsciiArtAlgorithm {


    private Image image;
    private int resolution;
    private char [] asciiChars;
    private final SubImgCharMatcher subImgCharMatcher;



    public AsciiArtAlgorithm(Image image, int resolution, char [] asciiChars) throws DimensionException {
        if (image.getWidth() % resolution != 0 || image.getHeight() % resolution != 0) {
            throw new DimensionException();
        }
        this.image = image;
        this.resolution = resolution;
        this.asciiChars = asciiChars;
        this.subImgCharMatcher = new SubImgCharMatcher(asciiChars);
    }

    public char [] [] run() throws DimensionException {

        // convert to subimagelist

        char [] [] res = new char[resolution][resolution];
        SubImageList subImageList = new SubImageList(image, resolution); // todo: is necessary to use list (instead of stack)?

        for (int i = 0; i < resolution; i++) {
            for (int j = 0; j < resolution; j++) {
                double averageSubImage = subImageList.get(i * resolution + j).getAverageBrightness();
                res[i][j] = subImgCharMatcher.getCharByImageBrightness(averageSubImage);
            }
        }
        return res;

    }

}
