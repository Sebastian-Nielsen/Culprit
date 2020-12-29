package unitTests;

import common.DeployerImpl;
import framework.Deployer;
import framework.UUIDGenerator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import stubs.UUIDGeneratorStub;

import java.io.File;

import static framework.utils.FileUtils.getRelativePathsFrom;
import static framework.utils.FileUtils.listContentOfFilesFrom;
import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static testHelper.TestHelper.getResourceFile;

/**
 * Test list
 * - A call to deploy() copies the file-hierarchy as expected.
 * -
 */
public class DeployerImplTest {

	private File DEPLOY_ROOT_FOLDER;

	@BeforeEach
	public void setup(@TempDir File tempDir) {
		DEPLOY_ROOT_FOLDER = tempDir;
	}

	@Test
	public void shouldCopyFileHierarchyToDeployDir() throws Exception {
		// Fixture
		final String CONTENT_ROOT_DIRNAME  = "basicFileHierarchy/root";
		final File   CONTENT_ROOT_DIR      = getResourceFile(CONTENT_ROOT_DIRNAME);
		Deployer deployer = new DeployerImpl(CONTENT_ROOT_DIR, DEPLOY_ROOT_FOLDER);
		// Exercise
		deployer.deploy();
		// Verify post-exercise state
		String[] expectedHierarchy = getRelativePathsFrom(CONTENT_ROOT_DIR);
		String[]   actualHierarchy = getRelativePathsFrom(DEPLOY_ROOT_FOLDER);

		assertArrayEquals(actualHierarchy, expectedHierarchy);
	}

	@Test
	public void shouldAddIdToContentFilesWithoutOne() throws Exception {
		// Fixture
		final File CONTENT_ROOT_DIR = getResourceFile("DeployerTest_testFiles/input");

		Deployer deployer = newDeployer(CONTENT_ROOT_DIR, new UUIDGeneratorStub());

		// Exercise
		deployer.addIdToContentFilesWithoutOne();

		// Verify post-exercise state
		File EXPECTED_DIR = getResourceFile("DeployerTest_testFiles/expected");
		String[] expectedFileContents = listContentOfFilesFrom(EXPECTED_DIR);
		String[]   actualFileContents = listContentOfFilesFrom(CONTENT_ROOT_DIR);

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


	/* === PRIVATE METHODS */
	private Deployer newDeployer(File contentRootDir, UUIDGenerator uuidGenerator) {
		File dummyDeployFile = new File("");
		return new DeployerImpl(contentRootDir, dummyDeployFile, uuidGenerator);
	}
}
