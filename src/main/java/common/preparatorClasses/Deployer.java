package common.preparatorClasses;

import java.io.File;
import java.io.IOException;

import static framework.utils.FileUtils.Filename.changeFileExt;
import static framework.utils.FileUtils.Filename.getRelativePath;
import static framework.utils.FileUtils.Lister.RECURSION.RECURSIVE;
import static framework.utils.FileUtils.Lister.listFilesAndDirsFrom;

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

	private final File contentRootFolder;
	private final File deployRootFolder;

	public Deployer(File contentRootFolder, File deployRootFolder) {
		this.contentRootFolder = contentRootFolder;
		this.deployRootFolder  = deployRootFolder;
	}

	public void deploy() throws IOException {

		for (File contentFile : listFilesAndDirsFrom(contentRootFolder, RECURSIVE))
			createDeployFileFrom(contentFile);

	}

	public File getDeployEquivalentOf(File contentFile) {
		return new File(getAbsDeployPath(contentFile));
	}


	/* === PRIVATE METHODS */

	private void createDeployFileFrom(File contentFile) throws IOException {

		File deployFile = getDeployEquivalentOf(contentFile);

		if (contentFile.isFile())
			deployFile.createNewFile();
		else
			deployFile.mkdirs();

	}

	private String getAbsDeployPath(File contentFile) {
		return deployRootFolder + "/" + getRelativeDeployPath(contentFile);
	}

	private String getRelativeDeployPath(File contentFile) {

		String relativePath = getRelativePath(contentFile, contentRootFolder.toURI());

		if (relativePath.endsWith(".md"))
			relativePath = changeFileExt(relativePath, "html");

		return relativePath;

	}

}
