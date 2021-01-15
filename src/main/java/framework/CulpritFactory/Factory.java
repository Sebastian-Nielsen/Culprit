package framework.CulpritFactory;

import java.io.File;

public abstract class Factory {

	protected final File contentRootFolder;
	protected final File deployRootFolder;

	public Factory(File contentRootFolder, File deployRootFolder) {
		this.contentRootFolder = contentRootFolder;
		this.deployRootFolder  = deployRootFolder;
	}

	public File getContentRootFolder() {
		return contentRootFolder;
	}

	public File getDeployRootFolder() {
		return deployRootFolder;
	}
}
