package unitTests;

import framework.*;
import common.FileOptionExtractorImpl;
import framework.singleClasses.FileOptionContainer;
import framework.singleClasses.ValidatorImpl;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static constants.FileOptionConstants.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static framework.singleClasses.FileOption.KEY;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Test list:
 */
public class FileOptionExtractorTest {

	private @NotNull FileOptionExtractor extractor;
	private @NotNull final Validator validator = ValidatorImpl.getInstance();

	private KEY    EXPECTED_KEY1;
	private String EXPECTED_VAL1;
	private KEY    EXPECTED_KEY2;
	private String EXPECTED_VAL2;

	@BeforeEach
	public void setup() {
		EXPECTED_KEY1 = ARBITRARY_KEY_1;
		EXPECTED_VAL1 = ARBITRARY_VAL_1;
		String ARBITRARY_FILEOPTION_1 = FILEOPTION_TEMPLATE.formatted(ARBITRARY_KEY_1, ARBITRARY_VAL_1);

		EXPECTED_KEY2 = ARBITRARY_KEY_2;
		EXPECTED_VAL2 = ARBITRARY_VAL_2;
		String ARBITRARY_FILEOPTION_2 = FILEOPTION_TEMPLATE.formatted(ARBITRARY_KEY_2, ARBITRARY_VAL_2);

		// ==== For this test to make sense:
		assertValidFileOptions(ARBITRARY_FILEOPTION_1, ARBITRARY_FILEOPTION_1);
		assertNotEquals(ARBITRARY_KEY_1, ARBITRARY_KEY_2);
		// ====

		String[] linesToExtractFrom = new String[]{
				ARBITRARY_FILEOPTION_1,  // VALID                      - Line 1
				ARBITRARY_FILEOPTION_2,  // VALID                      - Line 2
				"Since this is not a fileOption, the extractor" +   // - Line 3
						" should ignore everything below this line",
				ARBITRARY_FILEOPTION_2, // INVALID ∵ bad position      - Line 4
		};

		extractor = newFileOptionExtractor(linesToExtractFrom);
	}

	private void assertValidFileOptions(String fileoption1, String fileoption2) {
		assertTrue(validator.isFileOption(fileoption1), "Invalid FileOption");
		assertTrue(validator.isFileOption(fileoption2), "Invalid FileOption");
	}


	@Test
	public void shouldExtractTwoFileOptions() throws IOException {
		// Exercise
		FileOptionContainer fileOptions = extractor.extractFOContainer();
		// Verify
		assertEquals(fileOptions.size(), 2);
	}

	@Test
	public void shouldExtractFileOption1() throws IOException {
		// Exercise
		FileOptionContainer fileOptions = extractor.extractFOContainer();
		// Verify
		assertThat(fileOptions.get(EXPECTED_KEY1), is(EXPECTED_VAL1));
	}

	@Test
	public void shouldExtractFileOption2() throws IOException {
		// Exercise
		FileOptionContainer fileOptions = extractor.extractFOContainer();
		// Verify
		assertThat(fileOptions.get(EXPECTED_KEY2), is(EXPECTED_VAL2));
	}


	/* === PRIVATE METHODS */

	@NotNull
	private FileOptionExtractor newFileOptionExtractor(String[] linesToExtractFrom) {
		return new FileOptionExtractorImpl(
				validator
				);
	}

}
