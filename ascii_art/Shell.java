package ascii_art;

import ascii_output.ConsoleAsciiOutput;
import ascii_output.HtmlAsciiOutput;
import image.DimensionException;

import java.io.IOException;

public class Shell {

	private final AsciiArtAlgorithm algorithm;
	private final User user;

	public Shell() throws IOException {
		user = new User();
		algorithm = new AsciiArtAlgorithm(user);
	}

	public void run() {
		String response = "";
		while (!response.equals("exit")) {
			System.out.println(">>>");
			response = KeyboardInput.readLine();

			String[] args = response.split(" ");

			switch (args[0]) {
				case "chars":
					chars();
					break;
				case "res":
					if (args.length < 2) {
						System.out.println("Did not change resolution due to incorrect format");
						break;
					}
					res(args[1]);
					break;
				case "image":
					if (args.length < 2) {
						System.out.println("Did not execute due to problem with image file.");
						break;
					}
					image(args[1]);
					break;
				case "output":
					if (args.length < 2) {
						System.out.println("Did not change output method due to incorrect format.");
						break;
					}
					output(args[1]);
					break;
				case "asciiArt":
					asciiArt();
					break;
				case "add":
					if (args.length < 2) {
						System.out.println("Did not add due to incorrect format.");
						break;
					}
					add(args[1]);
					break;
				case "remove":
					if (args.length < 2) {
						System.out.println("Did not add due to incorrect format.");
						break;
					}
					remove(args[1]);
					break;
				case "exit":
					break;
				default:
					System.out.println("Did not execute due to incorrect command.");
			}

		}
	}


	private void asciiArt() {

		try {
			char[][] res = algorithm.run();
			user.getOutput().out(res);
		} catch (DimensionException e) {
			throw new RuntimeException(e);
		}
	}


	private static char[] charsFromRange(char start, char end) {
		if (start > end) {
			char temp = start;
			start = end;
			end = temp;
		}
		char[] res = new char[end - start + 1];
		for (int i = 0; i < res.length; i++) {
			res[i] = (char) (start + i);
		}
		return res;
	}


	private void add(String arg) {
		if (arg.length() == 1) {
			user.addCharToDB(arg.charAt(0));
		} else if (arg.equals("all")) {
			user.addCharsToDB(charsFromRange(' ', '~'));
		} else if (arg.split("-").length == 2) {
			char start = arg.split("-")[0].charAt(0);
			char end = arg.split("-")[1].charAt(0);
			user.addCharsToDB(charsFromRange(start, end));
		} else {
			System.out.println("Did not add due to incorrect format.");
		}
	}

	private void remove(String arg) {
		if (arg.length() == 1) {
			user.removeCharFromDB(arg.charAt(0));
		} else if (arg.equals("all")) {
			user.removeCharsFromDB(charsFromRange(' ', '~'));
		} else if (arg.split("-").length == 2) {
			char start = arg.split("-")[0].charAt(0);
			char end = arg.split("-")[1].charAt(0);
			user.removeCharsFromDB(charsFromRange(start, end));
		} else {
			System.out.println("Did not add due to incorrect format.");
		}
	}

	private void res(String arg) {
		try {
			switch (arg) {
				case "down":
					user.setResolution(user.getResolution() / 2);
					break;
				case "up":
					user.setResolution(user.getResolution() * 2);
					break;
				default:
					System.out.println("Did not change resolution due to incorrect format.");
					return;
			}
		} catch (DimensionException e) {
			System.out.println("Did not change resolution due to incorrect format.");
			return;
		}
		System.out.println(String.format("Resolution set to %d.", user.getResolution()));
	}

	private void chars() {
		for (Character iter : user.getCharsFromDB()) {
			System.out.print(((char) iter) + " ");
		}
		System.out.println();
	}


	private void output(String arg) {
		switch (arg) {
			case "html":
				user.setOutput(new HtmlAsciiOutput("out.html", "Courier New"));
				break;
			case "console":
				user.setOutput(new ConsoleAsciiOutput());
				break;
			default:
				System.out.println("Did not change output method due to incorrect format.");
		}
	}

	private void image(String arg) {
		try {
			user.setImage(arg);
		} catch (IOException exception) {
			System.out.println("Did not execute due to problem with image file.");
		}
	}

	public static void main(String[] args) throws IOException {
		new Shell().run();
	}
}

