package stubs;

import framework.FileOption;
import framework.Validator;

public class ValidatorStub implements Validator {

	/**
	 * This method is stubbed to not check if the KEY is valid.
	 * (Why? so that we don't have to rewrite the testcases whenever
	 * a KEY in FileOption.Key is changed)
	 */
	@Override
	public boolean isFileOption(String line) {
		if (line == null)
			return false;

		boolean isValidMdComment = line.matches(" {0,3}\\[.*?]:\s*<>\s+\\(.*?\\)\s*");
		if (!isValidMdComment)
			return false;

		// Don't check if the KEY is valid, so that we don't have to
		// refactor our test cases whenever we alter FileOption.KEY

		return true;
	}
}
