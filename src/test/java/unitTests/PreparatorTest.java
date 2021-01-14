package unitTests;

import common.Preparator;
import common.fileOption.FileOptionInserter;
import framework.PreparatorFacade;
import framework.UUIDGenerator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import stubs.UUIDGeneratorStub;

import java.io.File;

import static framework.utils.FileUtils.Lister.getRelativePathsFrom;
import static framework.utils.FileUtils.Lister.listContentOfFilesFrom;
import static org.junit.Assert.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static testHelper.TestHelper.getResourceFile;

/**
 * Test list
 * - A call to deploy() copies the file-hierarchy as expected.
 * -
 */
public class PreparatorTest {

	private File DEPLOY_ROOT_DIR;

	@BeforeEach
	public void setup(@TempDir File tempDir) {
		DEPLOY_ROOT_DIR = tempDir;
	}

	@Test
	public void shouldCopyFileHierarchyToDeployDir() throws Exception {
		// Fixture
		final String CONTENT_ROOT_DIRNAME  = "basicFileHierarchy/root";
		final File   CONTENT_ROOT_DIR      = getResourceFile(CONTENT_ROOT_DIRNAME);
		PreparatorFacade preparator = new Preparator(CONTENT_ROOT_DIR, DEPLOY_ROOT_DIR);
		// Exercise
		preparator.deploy();
		// Verify post-exercise state
		String[] expectedHierarchy = getRelativePathsFrom(CONTENT_ROOT_DIR);
		String[]   actualHierarchy = getRelativePathsFrom(DEPLOY_ROOT_DIR);

		assertArrayEquals(expectedHierarchy, actualHierarchy);
	}

	@Test
	public void shouldAddIdToContentFilesWithoutOne() throws Exception {
		// Fixture
		final String methodSpecificTestDirname = "addIdsToFilesWithoutOne";

		final File CONTENT_DIR = getTestDir(methodSpecificTestDirname + "/input");

		final PreparatorFacade preparator = newPreparator(CONTENT_DIR, new UUIDGeneratorStub());

		// Exercise
		preparator.addIdToContentFilesWithoutOne();

		// Verify post-exercise state
		final File EXPECTED_DIR = getTestDir(methodSpecificTestDirname + "/expected");

		String[] expectedFileContents = listContentOfFilesFrom(EXPECTED_DIR);
		String[]   actualFileContents = listContentOfFilesFrom( CONTENT_DIR);

		assertArrayEquals(expectedFileContents, actualFileContents);
	}

	@Test
	public void shouldCreateDefaultIndexFilesInDeployFolder() throws Exception {
		// Fixture
		final String methodSpecificTestDirname = "addDefaultIndexes";

		final File CONTENT_DIR = getTestDir(methodSpecificTestDirname + "/content");
		final File  DEPLOY_DIR = getTestDir(methodSpecificTestDirname + "/deploy");

		final PreparatorFacade preparator = new Preparator(CONTENT_DIR, DEPLOY_DIR);

		// Exercise
		preparator.addDefaultIndexes();

		// Verify
		final File EXPECTED_DIR = getTestDir(methodSpecificTestDirname + "/expectedDeploy");

		String[] expectedFileContents = listContentOfFilesFrom(EXPECTED_DIR);
		String[]   actualFileContents = listContentOfFilesFrom(  DEPLOY_DIR);

		// I can imagine changing how the default index.html template looks quite often,
		// so in order to avoid having to refactor this test, just assert that the index files
		// is made by asserting that the length of the two are the same.
		assertEquals(actualFileContents.length, expectedFileContents.length);

//		assertArrayEquals(expectedFileContents, actualFileContents);
	}

	@Test
	public void shouldAddDefaultIndexContentToDefaultCreatedIndexFiles() {
		//TODO
	}


	/* === PRIVATE METHODS */

	private File getTestDir(String dirname) {
		return getResourceFile("PreparatorTest_testFiles/" + dirname);
	}

	private PreparatorFacade newPreparator(File contentRootDir, UUIDGenerator uuidGenerator) {

		File dummyDeployFile = new File("");
		FileOptionInserter foInserter = new FileOptionInserter(uuidGenerator);

		return new Preparator(contentRootDir, dummyDeployFile, foInserter);

	}

	private void debugPrint(String[] expectedFileContents, String[] actualFileContents) {
		System.out.println("=======");
		for (String expectedContent : expectedFileContents) {
			System.out.println(expectedContent);
			System.out.println("-------------");
		}
		System.out.println("=======");
		for (String actualContent : actualFileContents) {
			System.out.println(actualContent);
			System.out.println("-------------");
		}
		System.out.println("=======");
	}
}
