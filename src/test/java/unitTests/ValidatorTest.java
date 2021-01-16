package unitTests;

import framework.other.Validator;
import common.other.ValidatorImpl;
import org.junit.jupiter.api.Test;

import static constants.Constants.*;
import static constants.FileOptionConstants.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ValidatorTest {
	private final Validator validator = ValidatorImpl.getInstance();

	@Test
	public void shouldBeValidFileOptionFormat() {
		// Where '%s' is a placeholder for a key or value.
		assertTrue(isFileOption("[%s]:<> (%s)  "));
		assertTrue(isFileOption("[%s]: <> (%s) "));
		assertTrue(isFileOption("[%s]: <>  (%s)"));
		assertTrue(isFileOption(THREE_SPACES + "[%s]: <> (%s)"));
	}

	@Test
	public void shouldBeInvalidFileOption() {
		// Where '%s' is a placeholder for a key or  value.
		assertFalse(isFileOption(FOUR_SPACES + "[%s]: <>(%s)"),
				"Max three spaces before the first bracket of the comment is allowed");
		assertFalse(isFileOption("[%s]: <>(%s)"),
				"There has to be at least one space between: '<>' and '(...)' ");
	}


	/* === PRIVATE METHODS */

	private boolean isFileOption(String line) {
		String formattedLine = line.formatted(
				ARBITRARY_KEY_1,
				ARBITRARY_VAL_1
		);
		return validator.isFileOption(formattedLine);
	}
}
