package unitTests.FileUtilsSuiteClasses;

import org.junit.jupiter.api.Test;

import java.io.File;

import static framework.utils.FileUtils.Lister.getRelativePathsFrom;
import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static testHelper.TestHelper.getResourceFile;

public class FileUtils_getRelativePathsRecursivelyFrom {

	private final String ROOT_DIRNAME = "basicFileHierarchy/root";
	private final File   ROOT_DIR     = getResourceFile(ROOT_DIRNAME);


	@Test
	public void shouldRetrieveAllRelativePaths() throws Exception {
		// Exercise
		String[] actualPaths = getRelativePathsFrom(ROOT_DIR);

		// Verify
		String[] expectedPaths = new String[]{
			"A",              // 1
			"lvlB/",          // 2
			"lvlB/B",         // 3
			"lvlB/lvlC1/",    // 4
			"lvlB/lvlC1/C1",  // 5
			"lvlB/lvlC2/",    // 6
			"lvlB/lvlC2/C2"   // 7
		};

		assertArrayEquals(actualPaths, expectedPaths);
	}



}
