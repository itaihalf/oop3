package ascii_art;

import ascii_output.AsciiOutput;
import ascii_output.ConsoleAsciiOutput;
import image.Image;
import image.ImageProcessor;
import image_char_matching.SubImgCharMatcher;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

/**
 * The User class represents a user in the ASCII Art application. It manages the user's image,
 * resolution, ASCII character database, and output preferences.
 */
public class User {

    private Image image;
    private int resolution;
    private final SubImgCharMatcher subImgCharMatcher;
    private AsciiOutput output;

    // Default values
    public static final String DEFAULT_IMAGE_PATH = "cat.jpeg";
    public static final int DEFAULT_RESOLUTION = 128;
    private static final int MIN_RES =1;
    public static final char[] DEFAULT_ASCII_CHARS = {'1', '2', '3', '4', '5', '6', '7', '8', '9', '0'};

    /**
     * Constructs a User object with the specified image, resolution, ASCII characters, and output.
     *
     * @param image      The user's image.
     * @param resolution The resolution for ASCII conversion.
     * @param asciiChars The ASCII characters used for mapping brightness.
     * @param output     The output strategy for displaying ASCII art.
     */
    public User(Image image, int resolution, char[] asciiChars, AsciiOutput output) {
        this.image = ImageProcessor.fixSize(image);
        this.resolution = resolution;
        this.subImgCharMatcher = new SubImgCharMatcher(asciiChars);
        this.output = output;
    }

    /**
     * Constructs a User object by copying another User object.
     *
     * @param user The User object to be copied.
     */
    public User(User user) {
        this.image = user.image;
        this.resolution = user.resolution;
        this.subImgCharMatcher = user.subImgCharMatcher;
        this.output = user.output;
    }

    /**
     * Constructs a User object with default values.
     *
     * @throws IOException If an error occurs while reading the default image.
     */
    public User() throws IOException {
        this(new Image(DEFAULT_IMAGE_PATH), DEFAULT_RESOLUTION, DEFAULT_ASCII_CHARS, new ConsoleAsciiOutput());
    }

    /**
     * Overrides the default equals method to compare User objects based on image and resolution.
     *
     * @param obj The object to be compared.
     * @return true if the objects are equal, false otherwise.
     */
    @Override
    public boolean equals(Object obj) {
        if (obj instanceof User user) {
            return user.image.equals(image) && user.resolution == resolution;
        }
        return false;
    }

    /**
     * Sets the output strategy for displaying ASCII art.
     *
     * @param output The output strategy.
     */
    public void setOutput(AsciiOutput output) {
        this.output = output;
    }

    /**
     * Adds a character to the ASCII character database.
     *
     * @param c The character to be added.
     */
    public void addCharToDB(char c) {
        subImgCharMatcher.addChar(c);
    }

    /**
     * Removes a character from the ASCII character database.
     *
     * @param c The character to be removed.
     */
    public void removeCharFromDB(char c) {
        subImgCharMatcher.removeChar(c);
    }

    /**
     * Adds an array of characters to the ASCII character database.
     *
     * @param c The array of characters to be added.
     */
    public void addCharsToDB(char[] c) {
        for (char c1 : c) {
            addCharToDB(c1);
        }
    }

    /**
     * Removes an array of characters from the ASCII character database.
     *
     * @param c The array of characters to be removed.
     */
    public void removeCharsFromDB(char[] c) {
        for (char c1 : c) {
            removeCharFromDB(c1);
        }
    }

    /**
     * Retrieves the list of characters from the ASCII character database.
     *
     * @return An ArrayList containing the characters sorted in ascending order.
     */
    public ArrayList<Character> getCharsFromDB() {
        ArrayList<Character> chars = subImgCharMatcher.getChars();
        Collections.sort(chars);
        return chars;
    }

    /**
     * Gets the user's image.
     *
     * @return The user's image.
     */
    public Image getImage() {
        return image;
    }

    /**
     * Sets the user's image using the provided path.
     *
     * @param path The path to the new image.
     * @throws IOException If an error occurs while reading the new image.
     */
    public void setImage(String path) throws IOException {
        this.image = ImageProcessor.fixSize(new Image(path));
    }


    /**
     * Gets the resolution for ASCII conversion.
     *
     * @return The resolution.
     */
    public int getResolution() {
        return resolution;
    }

    /**
     * Sets the resolution for ASCII conversion.
     *
     * @param resolution The new resolution.
     * @throws InvalidResolutionException If the new resolution is out of bounds.
     */
    public void setResolution(int resolution) throws InvalidResolutionException {
        if (resolution < Math.max(MIN_RES, image.getWidth() /
                image.getHeight()) || resolution > image.getWidth()) {
            throw new InvalidResolutionException("");
        }
        this.resolution = resolution;
    }

    /**
     * Gets the ASCII character matcher associated with the user.
     *
     * @return The ASCII character matcher.
     */
    public SubImgCharMatcher getSubImgCharMatcher() {
        return subImgCharMatcher;
    }

    /**
     * Gets the output strategy for displaying ASCII art.
     *
     * @return The output strategy.
     */
    public AsciiOutput getOutput() {
        return output;
    }
}
