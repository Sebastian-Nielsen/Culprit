package framework;

import java.util.regex.Pattern;

import static java.util.regex.Pattern.CASE_INSENSITIVE;

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
public interface FileOption {
	/**
	 * The set of valid {@code FileOption} keys.
	 */
	enum KEY {
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

		public static boolean contains(String key) {
			for (KEY k : KEY.values())
				if (k.name().equals(key))
					return true;
			return false;
		}

	}

	static boolean isFileOption(String line) {
		throw new RuntimeException("Not implemented");
	}

	String getVal();

}
