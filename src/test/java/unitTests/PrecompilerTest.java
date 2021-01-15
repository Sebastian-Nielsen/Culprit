package unitTests;

import common.CompilerDataContainer;
import common.DataExtractor;
import common.PrecompilerImpl;
import common.fileOption.FileOptionContainer;
import framework.Precompiler;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.util.Map;

import static common.fileOption.FileOption.KEY;
import static common.fileOption.FileOption.KEY.D_LINKS;
import static common.fileOption.FileOption.KEY.ID;
import static framework.utils.FileUtils.Retriever.contentOf;
import static java.util.AbstractMap.SimpleEntry;
import static java.util.Map.Entry;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static testHelper.TestHelper.getResourceFile;

/**
 * Test list:
 * - 'D_LINK' test with some fileOptions and files.
 *
 * To improve 'defect localization', we have one assertion in each test method
 */
public class PrecompilerTest {
	
	private Precompiler precompiler;
	
	private final String EXPECTED_ROOT_PATH = "precompilerTest_testFiles/D_LINKS/expected";;
	private final String    INPUT_ROOT_PATH = "precompilerTest_testFiles/D_LINKS/input";

	private final String A = "/A";
	private final String B = "/B";
	private final String C = "/nested/C";
	private final String D = "/nested/x2nested/D";
	private final String E = "/nested_v2/E";

	private final File INPUT_FILE_A = getResourceFile(INPUT_ROOT_PATH + A + ".md");
	private final File INPUT_FILE_B = getResourceFile(INPUT_ROOT_PATH + B + ".md");
	private final File INPUT_FILE_C = getResourceFile(INPUT_ROOT_PATH + C + ".md");
	private final File INPUT_FILE_D = getResourceFile(INPUT_ROOT_PATH + D + ".md");
	private final File INPUT_FILE_E = getResourceFile(INPUT_ROOT_PATH + E + ".md");

	private final File EXPECTED_FILE_A = getResourceFile(EXPECTED_ROOT_PATH + A + ".extIsIgnored");
	private final File EXPECTED_FILE_B = getResourceFile(EXPECTED_ROOT_PATH + B + ".extIsIgnored");
	private final File EXPECTED_FILE_C = getResourceFile(EXPECTED_ROOT_PATH + C + ".extIsIgnored");
	private final File EXPECTED_FILE_D = getResourceFile(EXPECTED_ROOT_PATH + D + ".extIsIgnored");
	private final File EXPECTED_FILE_E = getResourceFile(EXPECTED_ROOT_PATH + E + ".extIsIgnored");
	
	@BeforeEach
	public void setup() throws Exception {
		Map<File, FileOptionContainer> fileToFOContainer;
		fileToFOContainer = Map.of(
				INPUT_FILE_A, new FileOptionContainer(
						entry(ID, "11111111-1111-1111-1111-111111111111"),
						entry(D_LINKS, "true")),

				INPUT_FILE_B, new FileOptionContainer(
						entry(ID, "22222222-2222-2222-2222-222222222222"),
						entry(D_LINKS, "true")),

				INPUT_FILE_C, new FileOptionContainer(
						entry(ID, "33333333-3333-3333-3333-333333333333"),
						entry(D_LINKS, "true")),

				INPUT_FILE_D, new FileOptionContainer(
						entry(ID, "44444444-4444-4444-4444-444444444444"),
						entry(D_LINKS, "true")),

				INPUT_FILE_E, new FileOptionContainer(
						entry(ID, "55555555-5555-5555-5555-555555555555"),
						entry(D_LINKS, "true"))
		);

		precompiler = new PrecompilerImpl(  newCompilerDataContainer(fileToFOContainer)  );
	}

	private CompilerDataContainer newCompilerDataContainer(Map<File, FileOptionContainer> fileToFOContainer) throws Exception {
		DataExtractor dataExtractor = new DataExtractor(
				getResourceFile(INPUT_ROOT_PATH),
				getResourceFile(EXPECTED_ROOT_PATH)
		);

		return new CompilerDataContainer(
				dataExtractor.extractIdToContentFile(),
				fileToFOContainer
		);
	}

	@Test
	public void shouldCompileDLinksInFileA() throws IOException {
		// Exercise
		String actualCompiledContent = precompiler.compile(INPUT_FILE_A);
		// Verify
		assertThat(actualCompiledContent, is(contentOf(EXPECTED_FILE_A)));
	}
	@Test
	public void shouldCompileDLinksInFileB() throws IOException {
		// Exercise 
		String actualCompiledContent = precompiler.compile(INPUT_FILE_B);
		// Verify
		assertThat(actualCompiledContent, is(contentOf(EXPECTED_FILE_B)));
	}
	@Test
	public void shouldCompileDLinksInFileC() throws IOException {
		// Exercise 
		String actualCompiledContent = precompiler.compile(INPUT_FILE_C);
		// Verify
		assertThat(actualCompiledContent, is(contentOf(EXPECTED_FILE_C)));
	}
	@Test
	public void shouldCompileDLinksInFileD() throws IOException {
		// Exercise 
		String actualCompiledContent = precompiler.compile(INPUT_FILE_D);
		// Verify
		assertThat(actualCompiledContent, is(contentOf(EXPECTED_FILE_D)));
	}
	@Test
	public void shouldCompileDLinksInFileE() throws IOException {
		// Exercise 
		String actualCompiledContent = precompiler.compile(INPUT_FILE_E);
		// Verify
		assertThat(actualCompiledContent, is(contentOf(EXPECTED_FILE_E)));
	}








	/* === PRIVATE METHODS */

	private Entry<KEY, String> entry(KEY key, String value) {
		return new SimpleEntry<>(key, value);
	}

}
