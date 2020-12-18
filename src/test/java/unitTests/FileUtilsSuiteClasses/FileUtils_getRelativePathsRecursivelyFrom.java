package unitTests.FileUtilsSuiteClasses;

import framework.utils.FileUtils;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Arrays;
import java.util.Objects;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsInRelativeOrder;
import static org.junit.jupiter.api.Assertions.assertArrayEquals;

public class FileUtils_getRelativePathsRecursivelyFrom {

	private final String ROOT_FILENAME = "basicFileHierarchy/root";
	private final String ROOT_ABS_PATH = getAbsResourcePath(ROOT_FILENAME);
	private final File   ROOT_FOLDER   = new File(ROOT_ABS_PATH);


	@Test
	public void shouldRetrieveAllRelativePaths() throws IOException, URISyntaxException {
		// Exercise
		String[] actualPaths = FileUtils.getRelativePathsFrom(ROOT_FOLDER, ROOT_ABS_PATH);
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



	/* PRIVATE METHODS */

	@NotNull
	private String getAbsResourcePath(String name) {
		return Objects.requireNonNull(getClass().getClassLoader().getResource(name)).getFile();
	}

}
