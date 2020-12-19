package framework;

public class Validator {
	/**
	 * FileOptionImpl format:  (== markdown comment)
	 *      `[whatever]:<> (whatever2)`
	 * @return True if {@code line} complies to the FileOptionImpl format, false otherwise.
	 */
	public static boolean isFileOption(String line) {
		if (line == null)
			return false;
		return line.matches(" {0,3}\\[.*?]:\s*<>\s+\\(.*?\\)\s*");
	}



}
