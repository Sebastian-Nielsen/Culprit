package unitTests;

import framework.Deployer;
import framework.DeployerImpl;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.File;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Paths;
import java.util.Objects;

import static framework.utils.FileUtils.getRelativePathsFrom;
import static net.bytebuddy.matcher.ElementMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Test list
 * - A call to deploy() copies the file-hierarchy as expected.
 * -
 */
public class DeployerImplTest {

	private final String CONTENT_ROOT_FILENAME = "basicFileHierarchy/root";
//	private final String  DEPLOY_ROOT_FILENAME = "deployment";

	private final String CONTENT_ABS_PATH = getAbsResourcePath(CONTENT_ROOT_FILENAME);
//	private final String  DEPLOY_ABS_PATH = getAbsResourcePath( DEPLOY_ROOT_FILENAME);

	private final File CONTENT_ROOT_FOLDER = new File(CONTENT_ABS_PATH);
//	private final File  DEPLOY_ROOT_FOLDER = new File(DEPLOY_ABS_PATH);


	@Test
	public void shouldCopyFileHierarchyToDeployDir(@TempDir File deployFolder) throws Exception {
		// Fixture
		Deployer deployer = new DeployerImpl(CONTENT_ROOT_FOLDER, deployFolder);
		// Exercise
		deployer.deploy();
		// Verify
		String[] expectedHierarchy = getRelativePathsFrom(CONTENT_ROOT_FOLDER, CONTENT_ROOT_FOLDER.toString());
		String[]   actualHierarchy = getRelativePathsFrom(deployFolder, deployFolder.toString());

		assertArrayEquals(actualHierarchy, expectedHierarchy);
	}
//
//	@Test
//	public void shouldCopyFileHierarchy() throws IOException {
//		// Exercise
//		new DeployerImpl(CONTENT_ROOT_FOLDER, DEPLOY_ROOT_FOLDER).deploy();
//		System.out.println("=0000000000000");
//
//	    File child = temporaryFolder.newFolder("grandparent", "parent", "child");
//
//		System.out.println("printing a File: " + child);
//
//		String[] test = getRelativePathsRecursivelyFrom(child, "");
//		System.out.println("--------------");
//		for (String f : test)
//			System.out.println(f);
//
//		System.out.println("--------WE DID IT------");
//
//		// Verify
//		System.out.println(CONTENT_ABS_PATH);
//		System.out.println(DEPLOY_ABS_PATH);
//		String[] expectedFileHierarchy = getRelativePathsRecursivelyFrom(CONTENT_ROOT_FOLDER, CONTENT_ABS_PATH);
//		String[]   actualFileHierarchy = getRelativePathsRecursivelyFrom( DEPLOY_ROOT_FOLDER,  DEPLOY_ABS_PATH);
//
//		System.out.println("--------------");
//		for (String f : expectedFileHierarchy)
//		System.out.println("--------------");
//		for (String f : actualFileHierarchy)
//			System.out.println(f);
//		System.out.println("--------------");
//
//		assertArrayEquals(actualFileHierarchy, expectedFileHierarchy);
//	}
//
//
//

	/* PRIVATE METHODS */

	@NotNull
	private String getAbsResourcePath(String name) {
		try {
			URL url = Objects.requireNonNull(getClass().getClassLoader().getResource(name));
			File file = Paths.get(url.toURI()).toFile();
			return file.getAbsolutePath();
		} catch(URISyntaxException e) {
			throw new RuntimeException(e);
		}
	}

}
