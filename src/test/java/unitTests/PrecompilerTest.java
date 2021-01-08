package unitTests;

import common.PrecompilerImpl;
import common.fileOption.FileOptionContainer;
import framework.Precompiler;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.util.Collection;
import java.util.Map;

import static common.fileOption.FileOption.KEY;
import static common.fileOption.FileOption.KEY.D_LINKS;
import static common.fileOption.FileOption.KEY.ID;
import static framework.utils.FileUtils.Lister.filesToTheirContent;
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

		final String A = "/A";
		final String B = "/B";
		final String C = "/nested/C";
		final String D = "/nested/x2nested/D";
		final String E = "/nested_v2/E";

		final File inputFileA = getResourceFile(INPUT_ROOT_PATH + A + ".md");
		final File inputFileB = getResourceFile(INPUT_ROOT_PATH + B + ".md");
		final File inputFileC = getResourceFile(INPUT_ROOT_PATH + C + ".md");
		final File inputFileD = getResourceFile(INPUT_ROOT_PATH + D + ".md");
		final File inputFileE = getResourceFile(INPUT_ROOT_PATH + E + ".md");

		final File expectedFileA = getResourceFile(EXPECTED_ROOT_PATH + A + ".extIsIgnored");
		final File expectedFileB = getResourceFile(EXPECTED_ROOT_PATH + B + ".extIsIgnored");
		final File expectedFileC = getResourceFile(EXPECTED_ROOT_PATH + C + ".extIsIgnored");
		final File expectedFileD = getResourceFile(EXPECTED_ROOT_PATH + D + ".extIsIgnored");
		final File expectedFileE = getResourceFile(EXPECTED_ROOT_PATH + E + ".extIsIgnored");

		final Map<File, FileOptionContainer> fileToFOContainer;
		fileToFOContainer =
				Map.of(
						inputFileA, new FileOptionContainer(
									entry(ID, "11111111-1111-1111-1111-111111111111"),
									entry(D_LINKS, "true")),

						inputFileB, new FileOptionContainer(
									entry(ID, "22222222-2222-2222-2222-222222222222"),
									entry(D_LINKS, "true")),

						inputFileC, new FileOptionContainer(
									entry(ID, "33333333-3333-3333-3333-333333333333"),
									entry(D_LINKS, "true")),

						inputFileD, new FileOptionContainer(
									entry(ID, "44444444-4444-4444-4444-444444444444"),
									entry(D_LINKS, "true")),

						inputFileE, new FileOptionContainer(
									entry(ID, "55555555-5555-5555-5555-555555555555"),
									entry(D_LINKS, "true"))
				);

		precompiler = new PrecompilerImpl(INPUT_ROOT_FOLDER);

		// Exercise
		Map<File, String> fileToCompiledContent = precompiler.compileAllFiles(fileToFOContainer);

		// Verify
		assertThat(fileToCompiledContent.get(inputFileA), is(contentOf(expectedFileA)));
		assertThat(fileToCompiledContent.get(inputFileB), is(contentOf(expectedFileB)));
		assertThat(fileToCompiledContent.get(inputFileC), is(contentOf(expectedFileC)));
		assertThat(fileToCompiledContent.get(inputFileD), is(contentOf(expectedFileD)));
		assertThat(fileToCompiledContent.get(inputFileE), is(contentOf(expectedFileE)));

//		System.out.println("-----------------");
//		System.out.println(fileToCompiledContent.get(inputFileE));
//		System.out.println("-----------------");
//		System.out.println(contentOf(expectedFileE));
//		System.out.println("-----------------");

	}



	/* === PRIVATE METHODS */

	private Entry<KEY, String> entry(KEY key, String value) {
		return new SimpleEntry<>(key, value);
	}

}
