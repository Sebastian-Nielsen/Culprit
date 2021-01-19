package unitTests.FileUtilsSuiteClasses;


import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Objects;

import static framework.utils.FileUtils.Lister.RECURSION.RECURSIVELY;
import static framework.utils.FileUtils.Lister.listFilesAndDirsFrom;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsInRelativeOrder;
import static org.junit.jupiter.api.Assertions.assertArrayEquals;


public class FileUtils_listAllFilesFrom {

	private final String ROOT_FILENAME = "basicFileHierarchy/root";
	private final String ROOT_ABS_PATH = getAbsResourcePath(ROOT_FILENAME);
	private final File   ROOT_FOLDER   = new File(ROOT_ABS_PATH);

	@Test
	public void shouldRetrieveAllFiles() throws IOException {
		// Exercise
		File[] actualFiles = listFilesAndDirsFrom(ROOT_FOLDER, RECURSIVELY);
		// Verify
		File[] expectedFiles = new File[]{
			new File(ROOT_ABS_PATH + "/A"),             // 1
			new File(ROOT_ABS_PATH + "/lvlB"),          // 2
			new File(ROOT_ABS_PATH + "/lvlB/B"),        // 3
			new File(ROOT_ABS_PATH + "/lvlB/lvlC1"),    // 4
			new File(ROOT_ABS_PATH + "/lvlB/lvlC1/C1"), // 5
			new File(ROOT_ABS_PATH + "/lvlB/lvlC2"),    // 6
			new File(ROOT_ABS_PATH + "/lvlB/lvlC2/C2")  // 7
		};
		assertArrayEquals(expectedFiles, actualFiles);
	}

	@Test
	public void shouldRetrieveFilesDepthFirst() throws IOException {
		// Exercise
		File[] actualFiles = listFilesAndDirsFrom(ROOT_FOLDER, RECURSIVELY);
		// Verify
		File[] expectedRelativeOrderOfFolders = new File[]{
				new File(ROOT_ABS_PATH + "/lvlB"),
				new File(ROOT_ABS_PATH + "/lvlB/lvlC1"),
				new File(ROOT_ABS_PATH + "/lvlB/lvlC2")
		};
		assertDepthFirstOrder(actualFiles, expectedRelativeOrderOfFolders);
	}







	/* PRIVATE METHODS */

	private void assertDepthFirstOrder(File[] actualFiles, File[] expectedFiles) {
		assertThat(List.of(actualFiles), containsInRelativeOrder(expectedFiles));
	}

	@NotNull
	private String getAbsResourcePath(String name) {
		return Objects.requireNonNull(getClass().getClassLoader().getResource(name)).getFile();
	}

}

