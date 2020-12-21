package unitTests;

import framework.FileOption;
import framework.FileOption;
import framework.FileOptionExtractor;
import common.FileOptionExtractorImpl;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import stubs.FileHandlerStub;

import java.io.File;
import java.io.IOException;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Test list:
 */
public class FileOptionExtractorTest {

	private @NotNull FileOptionExtractor extractor;

	private @NotNull final String FILEOPTION1 = "[key1]: <> (val1)";
	private @NotNull final String FILEOPTION2 = "[key2]: <> (val2)";

	@BeforeEach
	public void setup() {

		String[] linesToExtractFrom = new String[]{
				FILEOPTION1,           // Valid fileOption                - Line 1
				FILEOPTION2,           // Valid fileOption                - Line 2
				"Since this is not a fileOption, the extractor" +   //    - Line 3
						" should ignore everything below this line",
				"[key3]: <> (val3)"    // INVALID fileOption              - Line 4
		};

		extractor = newFileOptionExtractor(linesToExtractFrom);
	}


	@Test   // TODO FIX THIS SHIT
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
		FileOption FileOption1 = getFirstOf(fileOptions);
		String expectedKey = "key1";
		String expectedVal = "val1";
		assertFileOptionHas(FileOption1, expectedKey, expectedVal);
	}

	@Test
	public void shouldExtractFileOption2AsTheSecond() throws IOException {
		// Exercise
		final List<FileOption> fileOptions;
		fileOptions = extractFileOptions();
		// Verify
		FileOption FileOption2 = getSecondOf(fileOptions);
		String expectedKey = "key2";
		String expectedVal = "val2";
		assertFileOptionHas(FileOption2, expectedKey, expectedVal);
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
		return new FileOptionExtractorImpl(new FileHandlerStub(linesToExtractFrom));
	}
}
