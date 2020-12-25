package unitTests;

import framework.FileOption;
import framework.FileOptionExtractor;
import common.FileOptionExtractorImpl;
import framework.Validator;
import framework.ValidatorImpl;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import stubs.FileHandlerStub;

import java.io.File;
import java.io.IOException;
import java.util.List;

import static constants.FileOptionConstants.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Test list:
 */
public class FileOptionExtractorTest {

	private @NotNull FileOptionExtractor extractor;
	private @NotNull final Validator validator = ValidatorImpl.getInstance();

	private String EXPECTED_KEY1;
	private String EXPECTED_VAL1;
	private String EXPECTED_KEY2;
	private String EXPECTED_VAL2;

	@BeforeEach
	public void setup() {
		EXPECTED_KEY1 = ARBITRARY_KEY_1;
		EXPECTED_VAL1 = ARBITRARY_VAL_1;
		String ARBITRARY_FILEOPTION_1 = FILEOPTION_TEMPLATE.formatted(ARBITRARY_KEY_1, ARBITRARY_VAL_1);

		EXPECTED_KEY2 = ARBITRARY_KEY_2;
		EXPECTED_VAL2 = ARBITRARY_VAL_2;
		String ARBITRARY_FILEOPTION_2 = FILEOPTION_TEMPLATE.formatted(ARBITRARY_KEY_2, ARBITRARY_VAL_2);

		assertValidFileOptions(ARBITRARY_FILEOPTION_1, ARBITRARY_FILEOPTION_1);

		String[] linesToExtractFrom = new String[]{
				ARBITRARY_FILEOPTION_1,  // VALID                      - Line 1
				ARBITRARY_FILEOPTION_2,  // VALID                      - Line 2
				"Since this is not a fileOption, the extractor" +   // - Line 3
						" should ignore everything below this line",
				ARBITRARY_FILEOPTION_3, // INVALID ∵ bad position      - Line 4
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
		final List<FileOption> fileOptions;
		fileOptions = extractFileOptions();
		// Verify
		assertEquals(fileOptions.size(), 2);
	}

	@Test
	public void shouldExtractFileOption1AsTheFirst() throws IOException {
		// Exercise
		final List<FileOption> fileOptions;
		fileOptions = extractFileOptions();
		// Verify
		FileOption fileOptions1 = getFirstOf(fileOptions);
		assertFileOptionHas(fileOptions1, EXPECTED_KEY1, EXPECTED_VAL1);
	}

	@Test
	public void shouldExtractFileOption2AsTheSecond() throws IOException {
		// Exercise
		final List<FileOption> fileOptions;
		fileOptions = extractFileOptions();
		// Verify
		FileOption fileOptions2 = getSecondOf(fileOptions);
		assertFileOptionHas(fileOptions2, EXPECTED_KEY2, EXPECTED_VAL2);
	}


	/* === PRIVATE METHODS */

	private FileOption getFirstOf(List<FileOption> fileOptions) {
		return fileOptions.get(0);
	}

	private FileOption getSecondOf(List<FileOption> fileOptions) {
		return fileOptions.get(1);
	}

	private void assertFileOptionHas(FileOption fileOption, String expectedKey, String expectedVal) {
		assertThat(fileOption.getKey(), is(expectedKey));
		assertThat(fileOption.getVal(), is(expectedVal));
	}

	private List<FileOption> extractFileOptions() throws IOException {
		File dummyFile = null;
		// The file is not used in `extractFileOptionsFrom` since the fileHandler 
		// is stubbed to return some hard-coded text. Hence why we use a dummy file.
		return extractor.extractFileOptionsFrom(dummyFile);
	}

	@NotNull
	private FileOptionExtractor newFileOptionExtractor(String[] linesToExtractFrom) {
		return new FileOptionExtractorImpl(
				new FileHandlerStub(linesToExtractFrom),
				validator
				);
	}

}
