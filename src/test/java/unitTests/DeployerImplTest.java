package unitTests;

import common.DeployerImpl;
import framework.Deployer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.File;
import java.io.IOException;

import static framework.utils.FileUtils.getRelativePathsFrom;
import static framework.utils.FileUtils.listContentOfAllFilesFrom;
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
		final String CONTENT_ROOT_DIRNAME  = "basicFileHierarchy/root";
		final File   CONTENT_ROOT_DIR      = getResourceFile(CONTENT_ROOT_DIRNAME);
		Deployer deployer = new DeployerImpl(CONTENT_ROOT_DIR, DEPLOY_ROOT_FOLDER, new DeployerImpl);
		// Exercise
		deployer.addIdToContentFilesWithoutOne();
		// Verify post-exercise state
		String[] expectedFileContents = getRelativePathsFrom(CONTENT_ROOT_DIR);
		String[]   actualFileContents = listContentOfAllFilesFrom(CONTENT_ROOT_DIR);


	}

}
