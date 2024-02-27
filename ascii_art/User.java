package ascii_art;

import image.Image;
import image.ImageProcessor;
import image_char_matching.SubImgCharMatcher;

import java.io.IOException;

public class User {

    private Image image;
    private int resolution;
    private final SubImgCharMatcher subImgCharMatcher;

    public static final String DEFAULT_IMAGE_PATH = "cat.jpeg";
    public static final int DEFAULT_RESOLUTION = 128;
    public static final char [] DEFAULT_ASCII_CHARS = {'1','2','3','4','5','6','7','8','9','0'};

    public User(Image image, int resolution, char[] asciiChars) {
        this.image = ImageProcessor.fixSize(image);
        this.resolution = resolution;
        this.subImgCharMatcher = new SubImgCharMatcher(asciiChars);
    }

    public User() throws IOException {
        this(new Image(DEFAULT_IMAGE_PATH), DEFAULT_RESOLUTION, DEFAULT_ASCII_CHARS);
    }


    public Image getImage() {
        return image;
    }

    public void setImage(Image image) {
        this.image = image;
    }

    public int getResolution() {
        return resolution;
    }

    public void setResolution(int resolution) {
        this.resolution = resolution;
    }

    public SubImgCharMatcher getSubImgCharMatcher() {
        return subImgCharMatcher;
    }


}
