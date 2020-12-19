package unitTests;

import framework.FileOptionImpl;
import framework.FileOptionExtractor;
import common.FileOptionExtractorImpl;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
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
public class FileOptionImplExtractorTest {

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


	@Test
	public void shouldExtractTwoFileOptions() throws IOException {
		// Exercise
		final List<FileOptionImpl> fileOptionImpls = extractFileOptions();
		// Verify
		assertEquals(fileOptionImpls.size(), 2);
		// === FileOption1
		FileOptionImpl fileOptionImpl1 = fileOptionImpls.get(0);
		String expectedKey = "key1";
		String expectedVal = "val1";
		assertFileOptionHas(fileOptionImpl1, expectedKey, expectedVal);
		// === FileOption2
		FileOptionImpl fileOptionImpl2 = fileOptionImpls.get(1);
		String expectedKey2 = "key2";
		String expectedVal2 = "val2";
		assertFileOptionHas(fileOptionImpl2, expectedKey2, expectedVal2);
	}



	/* === PRIVATE METHODS */

	private void assertFileOptionHas(FileOptionImpl fileOptionImpl, String expectedKey, String expectedVal) {
		assertThat(fileOptionImpl.getKey(), is(expectedKey));
		assertThat(fileOptionImpl.getVal(), is(expectedVal));
	}

	private List<FileOptionImpl> extractFileOptions() throws IOException {
		File dummy = null;
		return extractor.extractFileOptionsFrom(dummy);
	}

	@NotNull
	private FileOptionExtractor newFileOptionExtractor(String[] linesToExtractFrom) {
		return new FileOptionExtractorImpl(new FileHandlerStub(linesToExtractFrom));
	}
}
