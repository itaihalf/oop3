package ascii_art;

import image.DimensionException;
import image.Image;
import image.SubImageList;

public class AsciiArtAlgorithm {


	private User user;
	private User previousUser;

	private double[][] previousAverages;

	public AsciiArtAlgorithm(User user) {
		this.user = user;
	}

	public char[][] run() throws DimensionException {

		int resolution = user.getResolution();
		Image image = user.getImage();
		char[][] res = new char[resolution][resolution];

		if (user.equals(previousUser)) {
			System.out.println("same user, using previous averages");
			for (int i = 0; i < resolution; i++) {
				for (int j = 0; j < resolution; j++) {
					res[i][j] = user.getSubImgCharMatcher().getCharByImageBrightness(previousAverages[i][j]);
				}
			}
		} else {
			System.out.println("new user, calculating new averages");
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
