package unitTests;

import framework.Deployer;
import common.DeployerImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.File;

import static framework.utils.FileUtils.getRelativePathsFrom;
import static net.bytebuddy.matcher.ElementMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static testHelper.TestHelper.getResourceFile;

/**
 * Test list
 * - A call to deploy() copies the file-hierarchy as expected.
 * -
 */
public class DeployerImplTest {

	private final String CONTENT_ROOT_DIRNAME = "basicFileHierarchy/root";
	private final File   CONTENT_ROOT_DIR      = getResourceFile(CONTENT_ROOT_DIRNAME);

	@Test
	public void shouldCopyFileHierarchyToDeployDir(@TempDir File deployFolder) throws Exception {
		// Fixture
		Deployer deployer = new DeployerImpl(CONTENT_ROOT_DIR, deployFolder);
		// Exercise
		deployer.deploy();
		// Verify post-exercise state
		String[] expectedHierarchy = getRelativePathsFrom(CONTENT_ROOT_DIR);
		String[]   actualHierarchy = getRelativePathsFrom(deployFolder);

		assertArrayEquals(actualHierarchy, expectedHierarchy);
	}

}
