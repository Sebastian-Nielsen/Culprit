package framework;

import java.io.File;
import java.io.IOException;

import static framework.Constants.Constants.CWD;
import static framework.utils.FileUtils.*;

public class DeployerImpl implements Deployer {

	private final File contentRootFolder;
	private final File deployRootFolder;

	/**
	 * @param relativeContentPath Relative path to the content root folder; e.g. "src/content"
	 * @param relativeDeployPath  Relative path to the deploy  root folder; e.g  "src/ioFiles.deployment"
	 */
	public DeployerImpl(String relativeContentPath, String relativeDeployPath) {
		this.contentRootFolder = new File(CWD + "/" + relativeContentPath);
		this.deployRootFolder  = new File(CWD + "/" + relativeDeployPath);
	}

	public DeployerImpl(File contentRootFolder, File deployRootFolder) {
		this.contentRootFolder = contentRootFolder;
		this.deployRootFolder  = deployRootFolder;
	}

	public DeployerImpl() {
		this.contentRootFolder = new File(CWD + "src/content");
		this.deployRootFolder  = new File(CWD + "src/ioFiles.deployment");
	}


	@Override
 	public void deploy() throws IOException {

		for (File contentFile : listAllFilesOf(contentRootFolder))

			createDeployFileFrom(contentFile);
	}

	@Override
	public void addDefaultIndexesTo() {

	}


	/* === PRIVATE METHODS */

	private String getRelativeDeployPath(File contentFile) {
		String relativePath = getRelativePath(contentFile, contentRootFolder.toURI());

		if (relativePath.endsWith(".md"))
			return changeFileExt(relativePath, "html");
		return relativePath;
	}

	private String getAbsDeployPath(File contentFile) {
		return deployRootFolder + "/" + getRelativeDeployPath(contentFile);
	}

	private void createDeployFileFrom(File contentFile) throws IOException {
		File deployFile = new File(getAbsDeployPath(contentFile));

		if (contentFile.isFile())
			deployFile.createNewFile();
		else
			deployFile.mkdirs();
	}
}
