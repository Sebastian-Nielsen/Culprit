package framework;


import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static java.util.regex.Pattern.CASE_INSENSITIVE;


public class FileOptionImpl implements FileOption {


	private final KEY key;
	private final String val;

	/**
	 * Precondition: isFileOption() returns true for {@code FileOptionComment}
	 * @param FileOptionComment a .md comment of the form `[key] <>(value)` that classifies
	 *                          as a valid FileOptionComment (see {@link FileOption}#what-is-a-FileOption?)
	 */
	public FileOptionImpl(String FileOptionComment) {
		this.key = getFileOptionKeyOf(FileOptionComment);
		this.val = getValOf(FileOptionComment);
	}

	public static boolean isFileOption(String line) {
		if (line == null)
			return false;

		boolean isValidMdComment = line.matches(" {0,3}\\[.*?]:\s*<>\s+\\(.*?\\)\s*");
		if (!isValidMdComment)
			return false;

		boolean hasValidKey = KEY.contains(getKeyOf(line));
		if (!hasValidKey)
			return false;

		return true;
	}



	/* === PRIVATE METHODS */

	private static String getValOf(String comment) {
		Matcher matcher = Pattern.compile("^.*?].*?\\((.*?)\\)").matcher(comment);
		matcher.find();
		return matcher.group(1);
	}

	/**
	 * @throws IllegalArgumentException If they key identified of the FileOption isn't valid.
	 */
	private static KEY getFileOptionKeyOf(String line) {
		return KEY.valueOf(getKeyOf(line).toUpperCase());
	}

	private static String getKeyOf(String comment) {
		Matcher matcher = Pattern.compile("^\s*\\[(.*?)]").matcher(comment);
		matcher.find();
		return matcher.group(1);
	}


	/* GETTERS & SETTERS */

	public String getVal() {
		return val;
	}
}
