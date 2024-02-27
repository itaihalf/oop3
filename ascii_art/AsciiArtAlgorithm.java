package ascii_art;

import ascii_output.AsciiOutput;
import ascii_output.ConsoleAsciiOutput;
import ascii_output.HtmlAsciiOutput;
import image.DimensionException;
import image.Image;
import image.ImageProcessor;
import image.SubImageList;
import image_char_matching.CharConverter;
import image_char_matching.SubImgCharMatcher;

import java.awt.*;
import java.io.IOException;

public class AsciiArtAlgorithm {


    private Image image;
    private int resolution;
    private char [] asciiChars;
    private final SubImgCharMatcher subImgCharMatcher;



    public AsciiArtAlgorithm(Image image, int resolution, char [] asciiChars)  {

        this.image = ImageProcessor.fixSize(image);
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

    public static void main(String[] args) throws IOException, DimensionException {
        Image cat = new Image("cat.jpeg");
        char [] asciiChars = {'1','2','3','4'};

        AsciiArtAlgorithm asciiArtAlgorithm = new AsciiArtAlgorithm(cat,128,asciiChars);
        char [] [] res = asciiArtAlgorithm.run();

        HtmlAsciiOutput htmlAsciiOutput = new HtmlAsciiOutput("cat.html","Courier New");
        htmlAsciiOutput.out(res);
    }

}
