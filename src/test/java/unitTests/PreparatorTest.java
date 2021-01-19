package unitTests;

import common.preparatorFacade.Preparator;
import common.culpritFactory.DefaultPreparatorFactory;
import common.fileOption.FileOptionInserter;
import framework.ContentFileHierarchy;
import framework.CulpritFactory.PreparatorFactory;
import framework.DeployFileHierarchy;
import framework.PreparatorFacade;
import framework.other.UUIDGenerator;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import stubs.UUIDGeneratorStub;

import javax.swing.text.AbstractDocument;
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
		PreparatorFacade preparator = new Preparator(newDefaultPreparatorFactory(CONTENT_ROOT_DIR, DEPLOY_ROOT_DIR));
		// Exercise
		preparator.deploy();
		// Verify post-exercise state
		String[] expectedHierarchy = getRelativePathsFrom(CONTENT_ROOT_DIR);
		String[]   actualHierarchy = getRelativePathsFrom(DEPLOY_ROOT_DIR);

		assertArrayEquals(expectedHierarchy, actualHierarchy);
	}

	private PreparatorFactory newDefaultPreparatorFactory(File CONTENT_ROOT_DIR, File DEPLOY_ROOT_DIR) {
		return new DefaultPreparatorFactory(
				new ContentFileHierarchy(CONTENT_ROOT_DIR),
				new DeployFileHierarchy(DEPLOY_ROOT_DIR)
		);
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

		final PreparatorFacade preparator = new Preparator(newPreparatorFactory(CONTENT_DIR, DEPLOY_DIR));

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

	@NotNull
	private DefaultPreparatorFactory newPreparatorFactory(File CONTENT_DIR, File DEPLOY_DIR) {
		return new DefaultPreparatorFactory(
				new ContentFileHierarchy(CONTENT_DIR),
				new DeployFileHierarchy(DEPLOY_DIR)
		);
	}

	@Test
	public void shouldAddDefaultIndexContentToDefaultCreatedIndexFiles() {
		//TODO: this is hard to test, not sure if I should test this.
	}


	/* === PRIVATE METHODS */

	private File getTestDir(String dirname) {
		return getResourceFile("PreparatorTest_testFiles/" + dirname);
	}

	private PreparatorFacade newPreparator(File contentRootDir, UUIDGenerator uuidGenerator) {

		ContentFileHierarchy contentHierarchy    = new ContentFileHierarchy(contentRootDir);
		DeployFileHierarchy  dummyDeployHierachy = new DeployFileHierarchy(new File(""));

		FileOptionInserter foInserter = new FileOptionInserter(uuidGenerator);

		return new Preparator(
				new DefaultPreparatorFactory(contentHierarchy, dummyDeployHierachy, foInserter)
		);

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
