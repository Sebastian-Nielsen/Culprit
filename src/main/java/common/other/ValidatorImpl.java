package common.other;

import common.fileOption.FileOption;
import framework.other.Validator;

import static common.fileOption.FileOption.parseKeyOf;
import static common.fileOption.FileOption.parseValOf;


public class ValidatorImpl implements Validator {
	private static final Validator instance = new ValidatorImpl();

	public static class REGEXES {

		public static final String MD_COMMENT_FORMAT = " {0,3}\\[.*?]:\s*<>\s+\\(.*?\\)\s*";
		public static final String UUID = "[a-f0-9]{8}-[a-f0-9]{4}-[a-f0-9]{4}-[a-f0-9]{4}-[a-f0-9]{12}";
	}

	private ValidatorImpl() {}

	/**
	 * FileOption format: `[key]:<> (value)`
	 * @return True if {@code line} complies to the FileOption format, false otherwise.
	 */
	@Override
	public boolean isFileOption(String line) {
		if (line == null)
			return false;

		boolean isValidMdCommentFormat = line.matches(REGEXES.MD_COMMENT_FORMAT);
		if (!isValidMdCommentFormat)
			return false;

		boolean hasValidKey = FileOption.KEY.contains("" + parseKeyOf(line));
		if (!hasValidKey)
			return false;

		boolean hasValidValue = parseKeyOf(line).isValidValue(parseValOf(line));
		if (!hasValidValue)
			return false;

		return true;
	}


	/* GETTERS & SETTERS */

	public static Validator getInstance() {
		return instance;
	}
}
