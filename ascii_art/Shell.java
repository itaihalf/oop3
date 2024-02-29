package ascii_art;

import ascii_output.ConsoleAsciiOutput;
import ascii_output.HtmlAsciiOutput;
import image.DimensionException;

import java.io.IOException;

/**
 * The Shell class represents the command-line interface for the ASCII Art application.
 * It provides user interactions, such as changing resolution, selecting output methods, adding or removing characters,
 * and generating ASCII art from an image.
 */
public class Shell {

	private static final String COMMAND_PROMPT = ">>>";
	private static final String CMD_CHARS = "chars";
	private static final String CMD_RES = "res";
	private static final String CMD_IMAGE = "image";
	private static final String CMD_OUTPUT = "output";
	private static final String CMD_ASCII_ART = "asciiArt";
	private static final String CMD_ADD = "add";
	private static final String CMD_REMOVE = "remove";
	private static final String CMD_EXIT = "exit";
	private static final String MSG_RESOLUTION_CHANGE = "Resolution set to %d.";
	private static final String MSG_NOT_CHANGE_RESOLUTION_FORMAT = "Did not change resolution due" +
			" to incorrect format.";
	private static final String MSG_NOT_EXECUTE_IMAGE = "Did not execute due to a problem with" +
			" the image file.";
	private static final String MSG_NOT_CHANGE_OUTPUT_METHOD = "Did not change output method due" +
			" to incorrect format.";
	private static final String MSG_NOT_ADD = "Did not add due to incorrect format.";
	private static final String MSG_INCORRECT_COMMAND = "Did not execute due to incorrect command.";
	private static final String OUTPUT_HTML_FILE = "out.html";
	private static final String OUTPUT_HTML_FONT = "Courier New";
	private static final String OUTPUT_HTML = "html";
	private static final String OUTPUT_CONSOLE = "console";
	private static final int RESOLUTION_MULTIPLIER_UP = 2;
	private static final int RESOLUTION_MULTIPLIER_DOWN = 2;
	private static final String RESOLUTION_DOWN = "down";
	private static final String RESOLUTION_UP = "up";
	private static final String ADD_ALL = "all";
	private static final String REMOVE_ALL = "all";
	private static final String MSG_NOT_ADD_FORMAT = "Did not add due to incorrect format.";
	private static final int LENGTH_ONE = 1;
	private static final char CHAR_SPACE_SEP = ' ';
	private static final char CHAR_DASH_SEP = '-';
	private static final char CHAR_TILDE_SEP = '~';
	private static final int MINIMUM_LEN_FRO_COMMAND = 2;

	private static final int DEFAULT_RESOLUTION = 128;
	private static final char[] DEFAULT_ASCII_CHARS = {'1', '2', '3', '4', '5', '6', '7', '8', '9', '0'};
	private static final String STR_DASH_SEP = "-";
	private static final char FIRST_CHAR_ASCII = ' ';
	private static final char LAST_CHAR_ASCII = '~';
	private static final String STR_SPACE_SEP = " ";
	private static final int FIRST_COMMAND_ARG = 0;
	private static final int SECOND_COMMAND_ARG = 1;
	private static final char CHAR_INDEX_START = '0';
	private static final int LENGTH_TWO = 2;
	private static final int CHAR_INDEX_END = 1;
	private static final String EMPTY_RESPONSE = "";

	private final AsciiArtAlgorithm algorithm;
	private final User user;

	/**
	 * Constructs a Shell object and initializes it with the default user and ASCII art algorithm.
	 *
	 * @throws IOException If an error occurs while initializing the default user.
	 */
	public Shell() throws IOException {
		user = new User();
		algorithm = new AsciiArtAlgorithm(user);
	}

	/**
	 * Runs the interactive shell for user interactions.
	 */
	public void run() {
		String response = EMPTY_RESPONSE;
		while (!response.equals(CMD_EXIT)) {
			System.out.println(COMMAND_PROMPT);
			response = KeyboardInput.readLine();

			String[] args = response.split(STR_SPACE_SEP);

			switch (args[FIRST_COMMAND_ARG]) {
				case CMD_CHARS:
					chars();
					break;
				case CMD_RES:
					if (args.length < MINIMUM_LEN_FRO_COMMAND) {
						System.out.println(MSG_NOT_CHANGE_RESOLUTION_FORMAT);
						break;
					}
					res(args[SECOND_COMMAND_ARG]);
					break;
				case CMD_IMAGE:
					if (args.length < MINIMUM_LEN_FRO_COMMAND) {
						System.out.println(MSG_NOT_EXECUTE_IMAGE);
						break;
					}
					image(args[SECOND_COMMAND_ARG]);
					break;
				case CMD_OUTPUT:
					if (args.length < MINIMUM_LEN_FRO_COMMAND) {
						System.out.println(MSG_NOT_CHANGE_OUTPUT_METHOD);
						break;
					}
					output(args[SECOND_COMMAND_ARG]);
					break;
				case CMD_ASCII_ART:
					asciiArt();
					break;
				case CMD_ADD:
					if (args.length < MINIMUM_LEN_FRO_COMMAND) {
						System.out.println(MSG_NOT_ADD);
						break;
					}
					add(args[SECOND_COMMAND_ARG]);
					break;
				case CMD_REMOVE:
					if (args.length < MINIMUM_LEN_FRO_COMMAND) {
						System.out.println(MSG_NOT_ADD);
						break;
					}
					remove(args[SECOND_COMMAND_ARG]);
					break;
				case CMD_EXIT:
					break;
				default:
					System.out.println(MSG_INCORRECT_COMMAND);
			}
		}
	}

	/**
	 * Changes the output method for displaying ASCII art.
	 *
	 * @param arg The argument specifying the output method.
	 */
	private void output(String arg) {
		switch (arg) {
			case OUTPUT_HTML:
				user.setOutput(new HtmlAsciiOutput(OUTPUT_HTML_FILE, OUTPUT_HTML_FONT));
				break;
			case OUTPUT_CONSOLE:
				user.setOutput(new ConsoleAsciiOutput());
				break;
			default:
				System.out.println(MSG_NOT_CHANGE_OUTPUT_METHOD);
		}
	}

	/**
	 * Prints the list of characters in the ASCII character database.
	 */
	private void chars() {
		for (Character iter : user.getCharsFromDB()) {
			System.out.print(((char) iter) + " ");
		}
		System.out.println();
	}

	/**
	 * Changes the resolution for ASCII conversion.
	 *
	 * @param arg The argument specifying the resolution change.
	 */
	private void res(String arg) {
		try {
			switch (arg) {
				case RESOLUTION_DOWN:
					user.setResolution(user.getResolution() / RESOLUTION_MULTIPLIER_DOWN);
					break;
				case RESOLUTION_UP:
					user.setResolution(user.getResolution() * RESOLUTION_MULTIPLIER_UP);
					break;
				default:
					System.out.println(MSG_NOT_CHANGE_RESOLUTION_FORMAT);
					return;
			}
		} catch (DimensionException e) {
			System.out.println(MSG_NOT_CHANGE_RESOLUTION_FORMAT);
			return;
		}
		System.out.println(String.format(MSG_RESOLUTION_CHANGE, user.getResolution()));
	}

	/**
	 * Changes the user's image using the provided path.
	 *
	 * @param arg The argument specifying the image path.
	 */
	private void image(String arg) {
		try {
			user.setImage(arg);
		} catch (IOException exception) {
			System.out.println(MSG_NOT_EXECUTE_IMAGE);
		}
	}

	/**
	 * Runs the ASCII art algorithm and displays the result.
	 */
	private void asciiArt() {
		try {
			char[][] res = algorithm.run();
			user.getOutput().out(res);
		} catch (DimensionException e) {
			throw new RuntimeException(e);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * Adds characters to the ASCII character database.
	 *
	 * @param arg The argument specifying characters to add.
	 */
	private void add(String arg) {
		if (arg.length() == LENGTH_ONE) {
			user.addCharToDB(arg.charAt(0));
		} else if (arg.equals(ADD_ALL)) {
			user.addCharsToDB(charsFromRange(CHAR_SPACE_SEP, CHAR_TILDE_SEP));
		} else if (arg.split(String.valueOf(CHAR_DASH_SEP)).length == LENGTH_ONE) {
			char start = arg.split(String.valueOf(CHAR_DASH_SEP))[0].charAt(0);
			char end = arg.split(String.valueOf(CHAR_DASH_SEP))[1].charAt(0);
			user.addCharsToDB(charsFromRange(start, end));
		} else {
			System.out.println(MSG_NOT_ADD_FORMAT);
		}
	}

	/**
	 * Removes characters from the ASCII character database.
	 *
	 * @param arg The argument specifying characters to remove.
	 */
	private void remove(String arg) {
		if (arg.length() == LENGTH_ONE) {
			user.removeCharFromDB(arg.charAt(CHAR_INDEX_START));
		} else if (arg.equals(REMOVE_ALL)) {
			user.removeCharsFromDB(charsFromRange(FIRST_CHAR_ASCII, LAST_CHAR_ASCII));
		} else if (arg.split(STR_DASH_SEP).length == LENGTH_TWO) {
			char start = arg.split(STR_DASH_SEP)[CHAR_INDEX_START].charAt(CHAR_INDEX_START);
			char end = arg.split(STR_DASH_SEP)[CHAR_INDEX_END].charAt(CHAR_INDEX_START);
			user.removeCharsFromDB(charsFromRange(start, end));
		} else {
			System.out.println(MSG_NOT_ADD_FORMAT);
		}
	}

	/**
	 * Generates an array of characters within a specified range.
	 *
	 * @param start The starting character.
	 * @param end   The ending character.
	 * @return An array of characters within the specified range.
	 */
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

	/**
	 * The main method to start the ASCII Art application.
	 *
	 * @param args Command-line arguments.
	 * @throws IOException If an error occurs while initializing the Shell.
	 */
	public static void main(String[] args) throws IOException {
		new Shell().run();
	}
}
