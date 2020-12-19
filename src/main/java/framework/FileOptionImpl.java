package framework;


import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static java.util.regex.Pattern.CASE_INSENSITIVE;

/**
 * === Representation of a FileOptionImpl
 *
 * === What is a FileOptionImpl?
 * A FileOptionImpl is just a valid markdown comment of the form `[key]: <> (value)`, though
 * with the added constraint that the comment is placed at the top of the file; that is,
 * as soon as we read a line that is not a valid markdown comment, everything including and below
 * that line is not considered the top of the file anymore and thus not a FileOptionImpl.
 *
 * === Responsibilities of FileOptionImpl class
 * (1) Store the enum of all valid FileOptionImpl keys.
 * (2) Given a line from a .md file, create a FileOptionImpl instance from it.
 *      (if the line doesn't contain a valid FileOptionImpl, then throw exception.
 */
public class FileOptionImpl implements FileOption {
	/**
	 * The set of valid {@code FileOptionImpl} keys.
	 */
	public enum KEY {
		ID("[a-f0-9]{8}-[a-f0-9]{4}-[a-f0-9]{4}-[a-f0-9]{4}-[a-f0-9]{12}"),
		// Whether the file contains dynamic links
		D_LINKS("(true|false)", CASE_INSENSITIVE);


		public final Pattern validValuesPattern;

		/**
		 * @param validValuesRegex regex string representing the set of valid values for the key.
		 */
		KEY(String validValuesRegex, int flag) {
			this.validValuesPattern = Pattern.compile(validValuesRegex, flag);
		}
		KEY(String validValuesRegex) {
			this.validValuesPattern = Pattern.compile(validValuesRegex);
		}

	}

	private final KEY key;
	private final String val;

	/**
	 * @param FileOptionComment a .md comment of the form `[key] <>(value)` that classifies
	 *                          as a valid FileOptionComment (see {@link FileOptionImpl}#what-is-a-FileOptionImpl?)
	 */
	public FileOptionImpl(String FileOptionComment) {
		this.key = getFileOptionKeyOf(FileOptionComment);
		this.val = getValOf(FileOptionComment);
	}


	/* === PRIVATE METHODS */

	private static String getValOf(String comment) {
		Matcher matcher = Pattern.compile("^.*?].*?\\((.*?)\\)").matcher(comment);
		matcher.find();
		return matcher.group(1);
	}

	/**
	 * @throws IllegalArgumentException If they key identified of the FileOptionImpl isn't valid.
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

	public KEY getKey() {
		return key;
	}

	public String getVal() {
		return val;
	}
}
