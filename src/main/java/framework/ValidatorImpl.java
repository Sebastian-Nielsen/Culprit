package framework;

import static framework.FileOption.getKeyOf;
import static framework.FileOption.getValOf;

public class ValidatorImpl implements Validator {
	private static final Validator instance = new ValidatorImpl();

	private ValidatorImpl() {}

	/**
	 * FileOption format: `[key]:<> (value)`
	 * @return True if {@code line} complies to the FileOption format, false otherwise.
	 */
	@Override
	public boolean isFileOption(String line) {
		if (line == null)
			return false;

		boolean isValidMdCommentFormat = line.matches(" {0,3}\\[.*?]:\s*<>\s+\\(.*?\\)\s*");
		if (!isValidMdCommentFormat)
			return false;

		boolean hasValidKey = FileOption.KEY.contains("" + getKeyOf(line));
		if (!hasValidKey)
			return false;

		boolean hasValidValue = getKeyOf(line).isValidValue(getValOf(line));
		if (!hasValidValue)
			return false;

		return true;
	}


	/* GETTERS & SETTERS */

	public static Validator getInstance() {
		return instance;
	}
}
