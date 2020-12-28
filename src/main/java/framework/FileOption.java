package framework;


import java.io.File;
import java.util.EnumSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static framework.FileOption.KEY.ID;
import static java.util.regex.Pattern.CASE_INSENSITIVE;
import static framework.ValidatorImpl.REGEXES;

/**
 * === Representation of a FileOption
 *
 * === What is a FileOption?
 * A FileOption is just a valid markdown comment of the form `[key]: <> (value)` though
 * with the added constraint that the comment is placed at the "top of the file"; that is,
 * as soon as we read a line that is not a valid markdown comment, everything including and below
 * that line is not considered the top of the file anymore and thus not a FileOption.
 *
 * === Responsibilities of FileOption class
 * (1) Store the enum of all valid FileOption-keys.
 * (2) Given a line from a .md file, create a FileOption instance from it.
 *      (if the line doesn't contain a valid FileOption, then throw an exception.
 */
public class FileOption {
	/**
	 * The set of valid {@code FileOption} keys.
	 */
	public enum KEY {
		 ID(REGEXES.UUID, null),
		// Whether the file contains dynamic links
		D_LINKS("(true|false)", "false");

		private final Pattern validValuesPattern;
		public final String defaultVal;

		/**
		 * @param validValuesRegex regex string representing the set of valid values for the key.
		 * @param defaultVal the default value of the KEY.
		 *                   'null' signifies the value has to be given.
		 */
		KEY(String validValuesRegex, String defaultVal) {
			this.defaultVal = defaultVal;
			this.validValuesPattern = Pattern.compile(validValuesRegex, CASE_INSENSITIVE);
		}

		public static boolean contains(String key) {
			for (KEY k : KEY.values())
				if (k.name().equals(key))
					return true;
			return false;
		}

		public boolean isValidValue(String value) {
			Matcher matcher = validValuesPattern.matcher(value);
			return matcher.find();

		}
	}
	public static final EnumSet<KEY> REQUIRED_KEYS = EnumSet.of(ID);



	public static KEY getKeyOf(String comment) {
		Matcher matcher = Pattern.compile("^\s*\\[(.*?)]").matcher(comment);
		matcher.find();
		return KEY.valueOf( matcher.group(1) );
	}

	public static String getValOf(String comment) {
		Matcher matcher = Pattern.compile("^.*?].*?\\((.*?)\\)").matcher(comment);
		matcher.find();
		return matcher.group(1);
	}


	/* === PRIVATE METHODS */




}
