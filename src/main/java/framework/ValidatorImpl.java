package framework;

import static framework.FileOption.getKeyOf;

public class ValidatorImpl implements Validator {
	private static final Validator instance = new ValidatorImpl();

	private ValidatorImpl() {}

	/**
	 * FileOption format:  (== markdown comment)
	 *      `[whatever]:<> (whatever2)`
	 * @return True if {@code line} complies to the FileOption format, false otherwise.
	 */
	@Override
	public boolean isFileOption(String line) {
		if (line == null)
			return false;

		boolean isValidMdComment = line.matches(" {0,3}\\[.*?]:\s*<>\s+\\(.*?\\)\s*");
		if (!isValidMdComment)
			return false;

		boolean hasValidKey = FileOption.KEY.contains(FileOption.getKeyOf(line));
		if (!hasValidKey)
			return false;

		return true;
	}


	/* GETTERS & SETTERS */

	public static Validator getInstance() {
		return instance;
	}
}
