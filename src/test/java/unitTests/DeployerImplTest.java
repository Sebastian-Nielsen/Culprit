package unitTests;

import common.DeployerImpl;
import common.fileOption.FileOptionInserter;
import framework.Deployer;
import framework.UUIDGenerator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import stubs.UUIDGeneratorStub;

import java.io.File;
import java.io.IOException;

import static framework.utils.FileUtils.*;
import static org.junit.Assert.assertThat;
import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static testHelper.TestHelper.getResourceFile;

/**
 * Test list
 * - A call to deploy() copies the file-hierarchy as expected.
 * -
 */
public class DeployerImplTest {

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
		Deployer deployer = new DeployerImpl(CONTENT_ROOT_DIR, DEPLOY_ROOT_DIR);
		// Exercise
		deployer.deploy();
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

		final Deployer deployer = newDeployer(CONTENT_DIR, new UUIDGeneratorStub());

		// Exercise
		deployer.addIdToContentFilesWithoutOne();

		// Verify post-exercise state
		final File EXPECTED_DIR = getTestDir(methodSpecificTestDirname + "/expected");

		String[] expectedFileContents = listContentOfFilesFrom(EXPECTED_DIR);
		String[]   actualFileContents = listContentOfFilesFrom( CONTENT_DIR);

		assertArrayEquals(expectedFileContents, actualFileContents);
	}

	@Test
	public void shouldCreateDefaultIndexFilesInDeployFolder() throws IOException {
		// Fixture
		final String methodSpecificTestDirname = "addDefaultIndexes";

		final File CONTENT_DIR = getTestDir(methodSpecificTestDirname + "/content");
		final File  DEPLOY_DIR = getTestDir(methodSpecificTestDirname + "/deploy");

		final Deployer deployer = new DeployerImpl(CONTENT_DIR, DEPLOY_DIR);

		// Exercise
		deployer.addDefaultIndexes();

		// Verify
		final File EXPECTED_DIR = getTestDir(methodSpecificTestDirname + "/expectedDeploy");

		String[]   actualFileContents = listContentOfFilesFrom(DEPLOY_DIR);
		String[] expectedFileContents = listContentOfFilesFrom(EXPECTED_DIR);

		assertArrayEquals(expectedFileContents, actualFileContents);
	}

	@Test
	public void shouldAddDefaultIndexContentToDefaultCreatedIndexFiles() {
		//TODO
	}


	/* === PRIVATE METHODS */

	private File getTestDir(String dirname) {
		return getResourceFile(
				"DeployerTest_testFiles/" + dirname
		);
	}

	private Deployer newDeployer(File contentRootDir, UUIDGenerator uuidGenerator) {

		File dummyDeployFile = new File("");
		FileOptionInserter foInserter = new FileOptionInserter(uuidGenerator);

		return new DeployerImpl(contentRootDir, dummyDeployFile, foInserter);

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
