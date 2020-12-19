package unitTests.FileUtilsSuiteClasses;

import framework.utils.FileUtils;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Objects;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsInRelativeOrder;
import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static testHelper.TestHelper.getResourceFile;

public class FileUtils_getRelativePathsRecursivelyFrom {

	private final String ROOT_DIRNAME = "basicFileHierarchy/root";
	private final File   ROOT_DIR     = getResourceFile(ROOT_DIRNAME);


	@Test
	public void shouldRetrieveAllRelativePaths() throws Exception {
		// Exercise
		String[] actualPaths = FileUtils.getRelativePathsFrom(ROOT_DIR);
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
