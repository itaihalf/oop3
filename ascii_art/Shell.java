package ascii_art;

import ascii_output.ConsoleAsciiOutput;
import ascii_output.HtmlAsciiOutput;
import image.InvalidResolutionException;

import java.io.IOException;

/**
 * The Shell class represents the command-line interface for the ASCII Art application.
 * It provides user interactions, such as changing resolution, selecting output methods, adding or removing characters,
 * and generating ASCII art from an image.
 */
public class Shell {

	private static final String COMMAND_PROMPT = ">>> ";
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
	private static final String MSG_NOT_EXECUTE_IMAGE = "Did not execute due to problem with" +
			" image file."; //todo wrong
	private static final String MSG_NOT_CHANGE_OUTPUT_METHOD = "Did not change output method due" +
			" to incorrect format.";
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
	private static final int ARG_LEN_FOR_COMMANDS_TYPE_2 = 2;

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
	private static final String STRING_SPACE_SEP = " ";
	private static final String CHARSET_EMPTY_MSG = "Did not execute. Charset is empty.";
	private static final String MSG_NOT_CHANGE_RESOLUTION_BOUNDARIES = 
			"Did not change resolution due to exceeding boundaries.";
	private static final String MSG_NOT_REMOVE = "Did not remove due to incorrect format.";

	private final AsciiArtAlgorithm algorithm;
	private final User user;

	/**
	 * Constructs a Shell object and initializes it with the default user and ASCII art algorithm.
	 *
	 */
	public Shell() {
		try {
			user = new User();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
		algorithm = new AsciiArtAlgorithm(user);
	}

	/**
	 * Runs the interactive shell for user interactions.
	 */
	public void run() {
		String response = EMPTY_RESPONSE;
		while (!response.equals(CMD_EXIT)) {
			System.out.print(COMMAND_PROMPT);
			response = KeyboardInput.readLine();

			String[] args = response.split(STR_SPACE_SEP);
			try{
				switch (args[FIRST_COMMAND_ARG]) {
					case CMD_CHARS:
						chars();
						break;
					case CMD_RES:
						res(args);
						break;
					case CMD_IMAGE:
						image(args);
						break;
					case CMD_OUTPUT:
						output(args);
						break;
					case CMD_ASCII_ART:
						asciiArt();
						break;
					case CMD_ADD:
						add(args);
						break;
					case CMD_REMOVE:
						remove(args);
						break;
					case CMD_EXIT:
						break;
					default:
						System.out.println(MSG_INCORRECT_COMMAND);
				}
			}catch (ParamsException e){
				System.out.println(e.getMessage());
			}

		}
	}

	/**
	 * Changes the output method for displaying ASCII art.
	 *
	 * @param args The argument specifying the output method.
	 */
	private void output(String[] args) throws InvalidArgFormat {

		if (args.length != ARG_LEN_FOR_COMMANDS_TYPE_2){
			throw new InvalidArgFormat(MSG_NOT_CHANGE_OUTPUT_METHOD);
		}
		switch (args[SECOND_COMMAND_ARG]) {
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
			System.out.print(((char) iter) + STRING_SPACE_SEP);
		}
		System.out.println();
	}

	/**
	 * Changes the resolution for ASCII conversion.
	 *
	 * @param args The arguments specifying the resolution change.
	 */
	private void res(String[] args) throws InvalidArgFormat {
		if (args.length != ARG_LEN_FOR_COMMANDS_TYPE_2){
			throw new InvalidArgFormat(MSG_NOT_CHANGE_RESOLUTION_FORMAT);
		}
		try {
			switch (args[SECOND_COMMAND_ARG]) {
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
		} catch (InvalidResolutionException e) {
			throw new InvalidArgFormat(MSG_NOT_CHANGE_RESOLUTION_BOUNDARIES);
		}
		System.out.println(String.format(MSG_RESOLUTION_CHANGE, user.getResolution()));
	}

	/**
	 * Changes the user's image using the provided path.
	 *
	 * @param args The arguments specifying the image path.
	 */
	private void image(String[] args) throws InvalidArgFormat {
		if (args.length != ARG_LEN_FOR_COMMANDS_TYPE_2){
			throw new InvalidArgFormat(MSG_NOT_EXECUTE_IMAGE);
		}
		try {
			user.setImage(args[SECOND_COMMAND_ARG]);
		} catch (IOException exception) {
			System.out.println(MSG_NOT_EXECUTE_IMAGE);
		}
	}

	/**
	 * Runs the ASCII art algorithm and displays the result.
	 */
	private void asciiArt() throws EmptyCharsException {
		if (user.getCharsFromDB().isEmpty()){
			throw new EmptyCharsException(CHARSET_EMPTY_MSG);
		}
			char[][] res = algorithm.run();
			user.getOutput().out(res);
	}

	/**
	 * Adds characters to the ASCII character database.
	 *
	 * @param args The argument specifying characters to add.
	 */
	private void add(String [] args) throws InvalidArgFormat {

		if (args.length != ARG_LEN_FOR_COMMANDS_TYPE_2){
			throw new InvalidArgFormat(MSG_NOT_ADD_FORMAT);

		}
		String arg = args[SECOND_COMMAND_ARG];
		if (arg.length() == LENGTH_ONE) {
			user.addCharToDB(arg.charAt(FIRST_COMMAND_ARG));
		} else if (arg.equals(ADD_ALL)) {
			user.addCharsToDB(charsFromRange(CHAR_SPACE_SEP, CHAR_TILDE_SEP));
		} else if (arg.split(String.valueOf(CHAR_DASH_SEP)).length == LENGTH_TWO) {
			char start = arg.split(String.valueOf(CHAR_DASH_SEP))[FIRST_COMMAND_ARG]
					.charAt(FIRST_COMMAND_ARG);

			char end = arg.split(String.valueOf(CHAR_DASH_SEP))[SECOND_COMMAND_ARG]
					.charAt(FIRST_COMMAND_ARG);
			user.addCharsToDB(charsFromRange(start, end));
		} else {
			throw new InvalidArgFormat(MSG_NOT_ADD_FORMAT);
		}
	}

	/**
	 * Removes characters from the ASCII character database.
	 *
	 * @param args The argument specifying characters to remove.
	 */
	private void remove(String[] args) throws InvalidArgFormat {

		if (args.length != ARG_LEN_FOR_COMMANDS_TYPE_2){
			throw new InvalidArgFormat(MSG_NOT_REMOVE);
		}
		String arg = args[SECOND_COMMAND_ARG];
		if (arg.length() == LENGTH_ONE) {
			user.removeCharFromDB(arg.charAt(FIRST_COMMAND_ARG));
		} else if (arg.equals(REMOVE_ALL)) {
			user.removeCharsFromDB(charsFromRange(FIRST_CHAR_ASCII, LAST_CHAR_ASCII));
		} else if (arg.split(STR_DASH_SEP).length == LENGTH_TWO) {
			char start = arg.split(STR_DASH_SEP)[CHAR_INDEX_START].charAt(CHAR_INDEX_START);
			char end = arg.split(STR_DASH_SEP)[CHAR_INDEX_END].charAt(CHAR_INDEX_START);
			user.removeCharsFromDB(charsFromRange(start, end));
		} else {
			throw new InvalidArgFormat(MSG_NOT_REMOVE);
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
		char[] res = new char[end - start + SECOND_COMMAND_ARG];
		for (int i = 0; i < res.length; i++) {
			res[i] = (char) (start + i);
		}
		return res;
	}

	/**
	 * The main method to start the ASCII Art application.
	 *
	 * @param args Command-line arguments.
	 *
	 */
	public static void main(String[] args) {
		new Shell().run();
	}
}
