package ascii_art;
import image.Image;
import image.SubImageList;

import java.io.IOException;

/**
 * The AsciiArtAlgorithm class represents the algorithm for converting an image into ASCII art.
 * It calculates averages of sub-images and maps them to ASCII characters based on brightness.
 */
public class AsciiArtAlgorithm {

	private User user;
	private User previousUser;
	private double[][] previousAverages;

	/**
	 * Constructs an AsciiArtAlgorithm object with the specified user.
	 *
	 * @param user The user for whom the ASCII art algorithm is applied.
	 */
	public AsciiArtAlgorithm(User user) {
		this.user = user;
	}

	/**
	 * Runs the ASCII art algorithm to convert the user's image into a matrix of ASCII characters.
	 *
	 * @return A 2D char array representing the ASCII art.
	 *
	 */
	public char[][] run() {
		int resolution = user.getResolution();
		Image image = user.getImage();
		char[][] res = new char[resolution][resolution];

		if (user.equals(previousUser)) {
			for (int i = 0; i < resolution; i++) {
				for (int j = 0; j < resolution; j++) {
					res[i][j] = user.getSubImgCharMatcher().getCharByImageBrightness
							(previousAverages[i][j]);
				}
			}
		} else {
			SubImageList subImageList = new SubImageList(image, resolution);
			previousAverages = new double[resolution][resolution];
			for (int i = 0; i < resolution; i++) {
				for (int j = 0; j < resolution; j++) {
					double averageSubImage = subImageList.get(i * resolution + j).getAverageBrightness();
					res[i][j] = user.getSubImgCharMatcher().getCharByImageBrightness(averageSubImage);
					previousAverages[i][j] = averageSubImage;
				}
			}
			previousUser = new User(user);
		}
		return res;
	}



}

