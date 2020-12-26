package unitTests;

import common.CompilerImpl;
import framework.Compiler;
import framework.FileOption;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import static framework.utils.FileUtils.filesToTheirContent;
import static java.util.Arrays.asList;
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
	public void shouldCompileFilesWhenListOfFileOptionsContainsDLINKS() throws IOException {
		// Fixture
		final String CONTENT_ROOT_PATH = "compilerTest_testFiles/D_LINKS/input";
		final File contentRootFolder = getResourceFile(CONTENT_ROOT_PATH);
		final File fileA = getResourceFile(CONTENT_ROOT_PATH + "/A.md");
		final File fileB = getResourceFile(CONTENT_ROOT_PATH + "/B.md");
		final File fileC = getResourceFile(CONTENT_ROOT_PATH + "/nested/C.md");
		final File fileD = getResourceFile(CONTENT_ROOT_PATH + "/nested/x2nested/D.md");

		final List<FileOption>  fileOptionsOfFileA,fileOptionsOfFileB,
								fileOptionsOfFileC,fileOptionsOfFileD;

		fileOptionsOfFileA = asList(
				new FileOption("[ID]: <> (1bafbfc7-11d8-4e26-9edc-4ff3092d37a7)"),
				new FileOption("[D_LINKS]: <> (true)")
		);
		fileOptionsOfFileB = asList(
				new FileOption("[ID]: <> (2d424a8f-6fe8-455d-81de-6be20691cf32)"),
				new FileOption("[D_LINKS]: <> (true)")
		);
		fileOptionsOfFileC = asList(
				new FileOption("[ID]: <> (3ad5a033-98e0-4842-b89e-555641d90f5c)")
		);
		fileOptionsOfFileD = asList(
				new FileOption("[ID]: <> (4f198863-f01a-428e-8343-6060330501df)"),
				new FileOption("[D_LINKS]: <> (true)")
		);

//		final Map<File, Map<FileOption.KEY, String>> fileToListOfFileOptions = Map.of(
//				fileA, Map.of(),
//				fileB, fileOptionsOfFileB,
//				fileC, fileOptionsOfFileC,
//				fileD, fileOptionsOfFileD
//		);

		compiler = new CompilerImpl(contentRootFolder);
		// Exercise
//		Map<File, String> fileToCompiledContent = compiler.compile(fileToListOfFileOptions);
		// Verify
		Map<File, String> actual, expected;
		expected = filesToTheirContent(contentRootFolder);
		actual   = fileToCompiledContent;
		assertIdenticalMaps(actual, expected);
	}

	private void assertIdenticalMaps(Map<File, String> actual,
	                                 Map<File, String> expected) {

		assertIdenticalLengthedKeysets(actual, expected);
		//getRelativePath()
		for (File file : expected.keySet())
			System.out.println("2<" + file);
		for (File file :   actual.keySet()) {
			System.out.println("3>" + file);
		}
		for (String file :   actual.values()) {
			System.out.println("4>" + file);
		}

		System.out.println(String.join("\n", expected.get(0)));
		System.out.println(String.join("\n",   actual.get(0)));

//			assertThat(actual.values(),
//					is(expected.values()));

//		for (List<String> contentAsLines: actual.values())
//			assertThat(contentAsLinesact, )


	}

	private void assertIdenticalLengthedKeysets(Map<File, String> actual,
	                                            Map<File, String> expected) {
//		assertEquals(actual.keySet().size(), expected.keySet().size());
	}

}
