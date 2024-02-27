package ascii_art;

import image.DimensionException;
import image.Image;
import image.SubImageList;
public class AsciiArtAlgorithm {



    private final User user;
    public AsciiArtAlgorithm(User user)  {
        this.user = user;
    }

    public char [] [] run() throws DimensionException {


        int resolution = user.getResolution();
        Image image = user.getImage();
        char [] [] res = new char[resolution][resolution];
        SubImageList subImageList = new SubImageList(image, resolution); // todo: is necessary to use list (instead of stack)?

        for (int i = 0; i < resolution; i++) {
            for (int j = 0; j < resolution; j++) {
                double averageSubImage = subImageList.get(i * resolution + j).getAverageBrightness();
                res[i][j] = user.getSubImgCharMatcher().getCharByImageBrightness(averageSubImage);
            }
        }
        return res;

    }


}
