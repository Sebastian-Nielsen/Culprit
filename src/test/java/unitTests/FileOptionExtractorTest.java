package unitTests;

import framework.FileOptionExtractor;
import framework.FileOptionExtractorImpl;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import stubs.FileHandlerStub;

import java.io.File;
import java.io.IOException;
import java.util.List;

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
						" ignore everything below this line",
				"[key3]: <> (val3)"    // INVALID fileOption              - Line 4
		};

		extractor = createFileOptionExtractor(linesToExtractFrom);
	}

	@NotNull
	private FileOptionExtractor createFileOptionExtractor(String[] linesToExtractFrom) {
		return new FileOptionExtractorImpl(new FileHandlerStub(linesToExtractFrom));
	}

	@Test
	public void shouldExtractTwoFileOptions() throws IOException {
		// Exercise
		List<String> fileOptions = extractFileOptions();
		// Verify
		assertTrue(fileOptions.contains(FILEOPTION1));
		assertTrue(fileOptions.contains(FILEOPTION2));
		assertEquals(fileOptions.size(), 2);
	}

	private List<String> extractFileOptions() throws IOException {
		File dummy = null;
		return extractor.extractFileOptionsFrom(dummy);
	}

}