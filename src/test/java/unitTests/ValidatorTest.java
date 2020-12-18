package unitTests;

import framework.Validator;
import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ValidatorTest {
	private final String THREE_SPACES = " ".repeat(3);
	private final String FOUR_SPACES = " ".repeat(4);

	@Test
	public void shouldBeValidFileOption() {
		assertTrue(Validator.isFileOption("[keyA]:<> (valueA)  "));
		assertTrue(Validator.isFileOption("[keyA]: <> (valueA) "));
		assertTrue(Validator.isFileOption("[keyA]: <>  (valueA)"));
		assertTrue(Validator.isFileOption(THREE_SPACES + "[keyA]: <> (valueA)"));
	}

	@Test
	public void shouldBeInvalidFileOption() {
		assertFalse(Validator.isFileOption("[keyA]: <>(valueA)"),
				"There has to be at least one space between: '<>' and '(...)' ");
		assertFalse(Validator.isFileOption(FOUR_SPACES + "[keyA]: <>(valueA)"),
				"Maximum four spaces prior to the start of the comment is allowed");
	}

}
