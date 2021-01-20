package common.preparatorFacade;

import framework.ContentFileHierarchy;
import framework.DeployFileHierarchy;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.IOException;

import static framework.utils.FileUtils.Filename.changeFileExt;
import static framework.utils.FileUtils.Filename.relativePath;
import static framework.utils.FileUtils.Lister.RECURSION.RECURSIVELY;

/**
 * Creates the *deploy* hierarchy/skeleton with empty .html files
 * based on the *content* files-hierarchy. More specifically,
 * <pre>
 *      +--------------- For all files in *content*, do: --------------+
 *      | Given e.g.                                                   |
 *      |       File("C:/.../content/{relativePath}/test.md")          |
 *      | Then create                                                  |
 *      |       File("C:/.../deployment/{relativePath}/test.html")     |
 *      +--------------------------------------------------------------+
 * </pre>
 */
public class Deployer {

	private @NotNull final ContentFileHierarchy contentHierarchy;
	private @NotNull final DeployFileHierarchy deployHierarchy;

	public Deployer(@NotNull ContentFileHierarchy contentHierarchy, @NotNull DeployFileHierarchy deployHierarchy) {
		this.contentHierarchy = contentHierarchy;
		this.deployHierarchy  = deployHierarchy;
	}

	public void deploy() throws IOException {

		for (File contentFile : contentHierarchy.listFilesAndDirs(RECURSIVELY))
			createDeployFileFrom(contentFile);

	}

	/**
	 * Returns the deploy-file equivalent of the content-file
	 * <pre>
	 * +------------------------------------------------------------+
	 * | E.g. the delpoy-file equivalent of the content file        |
	 * |        File("C:/.../content/{relativePath}/test.md")       |
	 * | is                                                         |
	 * |        File("C:/.../deployment/{relativePath}/test.html")  |
	 * +------------------------------------------------------------+
	 * </pre>
	 */
	public static File getDeployEquivalentOf(File contentFile, File contentRootFolder, File deployRootFolder) {
		return new File(getAbsDeployPath(contentFile, contentRootFolder, deployRootFolder));
	}







	/* === PRIVATE METHODS */

	private void createDeployFileFrom(File contentFile) throws IOException {

		File deployFile = getDeployEquivalentOf(contentFile, contentHierarchy.getRootDir(), deployHierarchy.getRootDir());

		if (contentFile.isFile())
			deployFile.createNewFile();
		else
			deployFile.mkdirs();

	}

	private static String getAbsDeployPath(File contentFile, File contentRootFolder, File deployRootFolder) {
		return deployRootFolder + "/" + getRelativeDeployPath(contentFile, contentRootFolder);
	}

	private static String getRelativeDeployPath(File contentFile, File contentRootFolder) {

		String relativePath = relativePath(contentFile, contentRootFolder);

		if (relativePath.endsWith(".md"))
			relativePath = changeFileExt(relativePath, "html");

		return relativePath;

	}

}
