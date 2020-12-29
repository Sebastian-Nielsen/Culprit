package unitTests;

import common.PrecompilerImpl;
import framework.Precompiler;

import static common.fileOption.FileOption.KEY.D_LINKS;
import static common.fileOption.FileOption.KEY.ID;

import common.fileOption.FileOptionContainer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import java.io.File;
import java.io.IOException;
import java.util.Collection;
import java.util.Map;
import static java.util.AbstractMap.SimpleEntry;

import static java.util.Map.Entry;
import static common.fileOption.FileOption.KEY;

import static framework.utils.FileUtils.filesToTheirContent;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static testHelper.TestHelper.getResourceFile;

/**
 * Test list:
 * - 'D_LINK' test with some fileOptions and files.
 */
public class PrecompilerTest {
	private Precompiler precompiler;

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

		final Map<File, FileOptionContainer> fileToFOContainer;
		
		fileToFOContainer =
			Map.of(
				fileA,  new FileOptionContainer(
							entry(ID, "11111111-1111-1111-1111-111111111111"),
							entry(D_LINKS, "true")),

				fileB,  new FileOptionContainer(
							entry(ID, "22222222-2222-2222-2222-222222222222"),
							entry(D_LINKS, "true")),

				fileC,  new FileOptionContainer(
							entry(ID, "33333333-3333-3333-3333-333333333333"),
							entry(D_LINKS, "true")),

				fileD,  new FileOptionContainer(
							entry(ID, "44444444-4444-4444-4444-444444444444"),
							entry(D_LINKS, "true"))
			);

		precompiler = new PrecompilerImpl(INPUT_ROOT_FOLDER);

		// Exercise
		Map<File, String> fileToCompiledContent = precompiler.compileAllFiles(fileToFOContainer);

		// Verify
		Map<File, String> expected = filesToTheirContent(EXPECTED_ROOT_FOLDER);
		Map<File, String> actual   = fileToCompiledContent;
		assertIdenticalMaps(actual, expected);
	}
	
	private Entry<KEY, String> entry(KEY key, String value) {
		return new SimpleEntry<>(key, value);
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
