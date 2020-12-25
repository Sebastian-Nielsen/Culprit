package unitTests;

import framework.FileOption;
import framework.Validator;
import framework.ValidatorImpl;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.Test;

import static constants.Constants.*;
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
				getArbitraryValidKey(),
				getArbitraryValidVal()
		);

		return validator.isFileOption(formattedLine);
	}

	@NotNull
	private String getArbitraryValidVal() {
		return "9cdd0616-7940-4a73-89fa-862627fc8bd5";
	}

	@NotNull
	private FileOption.KEY getArbitraryValidKey() {
		return FileOption.KEY.ID;
	}


}
