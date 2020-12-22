package unitTests;

import org.junit.jupiter.api.Test;

import static framework.FileOption.isFileOption;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class FileOptionTest {
	private final String THREE_SPACES = " ".repeat(3);
	private final String FOUR_SPACES = " ".repeat(4);

	@Test
	public void shouldBeValidFileOption() {
		assertTrue(isFileOption("[keyA]:<> (valueA)  "));
		assertTrue(isFileOption("[keyA]: <> (valueA) "));
		assertTrue(isFileOption("[keyA]: <>  (valueA)"));
		assertTrue(isFileOption(THREE_SPACES + "[keyA]: <> (valueA)"));
	}

	@Test
	public void shouldBeInvalidFileOption() {
		assertFalse(isFileOption("[keyA]: <>(valueA)"),
				"There has to be at least one space between: '<>' and '(...)' ");
		assertFalse(isFileOption(FOUR_SPACES + "[keyA]: <>(valueA)"),
				"Maximum four spaces prior to the start of the comment is allowed");
	}
}
