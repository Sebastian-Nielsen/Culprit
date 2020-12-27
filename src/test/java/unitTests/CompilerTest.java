package unitTests;

import common.CompilerImpl;
import framework.Compiler;
import framework.FileOption;

import static framework.FileOption.KEY.D_LINKS;
import static framework.FileOption.KEY.ID;

import framework.FileOptionContainer;
import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import java.io.File;
import java.io.IOException;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import static framework.utils.FileUtils.filesToTheirContent;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static testHelper.TestHelper.getResourceFile;

/**
 * Test list:
 * - 'D_LINK' test with some fileOptions and files.
 */
public class CompilerTest {
	private Compiler compiler;

	@BeforeEach
	public void setup() {
	}

	@Test
	public void shouldCompileDLINKSInFiles() throws IOException {
		// Fixture
		final String EXPECTED_ROOT_PATH = "compilerTest_testFiles/D_LINKS/expected";
		final String    INPUT_ROOT_PATH = "compilerTest_testFiles/D_LINKS/input";
		final File EXPECTED_ROOT_FOLDER = getResourceFile(EXPECTED_ROOT_PATH);
		final File    INPUT_ROOT_FOLDER = getResourceFile(INPUT_ROOT_PATH);
		final File fileA = getResourceFile(INPUT_ROOT_PATH + "/A.md");
		final File fileB = getResourceFile(INPUT_ROOT_PATH + "/B.md");
		final File fileC = getResourceFile(INPUT_ROOT_PATH + "/nested/C.md");
		final File fileD = getResourceFile(INPUT_ROOT_PATH + "/nested/x2nested/D.md");

		final Map<File, FileOptionContainer> fileToFileOptionContainer;
		fileToFileOptionContainer =
			Map.of(
				fileA,  new FileOptionContainer(){
						ID, "11111111-1111-1111-1111-1111111111111",
						D_LINKS, "true"},

				fileB,  Map.of(
						ID, "22222222-2222-2222-2222-2222222222222",
						D_LINKS, "true"),

				fileC,  Map.of(
						ID, "33333333-3333-3333-3333-3333333333333",
						D_LINKS, "true"),

				fileD,  Map.of(
						ID, "44444444-4444-4444-4444-4444444444444",
						D_LINKS, "true")
			);

		compiler = new CompilerImpl(INPUT_ROOT_FOLDER);

		// Exercise
		compiler.preprocess(fileToFileOptionContainer);
		Map<File, String> fileToCompiledContent = compiler.compile(fileToFileOptionContainer);

		// Verify
		Map<File, String> expected = filesToTheirContent(EXPECTED_ROOT_FOLDER);
		Map<File, String> actual   = fileToCompiledContent;
		assertIdenticalMaps(actual, expected);
	}


	/* === PRIVATE METHODS */

	private void assertIdenticalMaps(Map<File, String> actual,
	                                 Map<File, String> expected) {

		assertIdenticalLengths(actual, expected);

		Collection<String> expectedCompiledContent = expected.values();
		Collection<String>   actualCompiledContent =   actual.values();

		assertContainsSameElements(actualCompiledContent, expectedCompiledContent);
	}

	private void assertContainsSameElements(Collection<String> actualCompiledContent, Collection<String> expectedCompiledContent) {
		assertTrue(
				  actualCompiledContent.containsAll(expectedCompiledContent)  &&
				expectedCompiledContent.containsAll(  actualCompiledContent)
		);
	}

	private void assertIdenticalLengths(Map<File, String> actual,
	                                    Map<File, String> expected) {
		assertEquals(actual.size(), expected.size());
	}

}
