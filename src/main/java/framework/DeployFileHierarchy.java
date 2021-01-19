package framework;

import java.io.File;

public class DeployFileHierarchy {
	
	private final File deployRootDir;
	private final String deployRootPath;

	public DeployFileHierarchy(String pathToRootOfHierarchy) {
		this.deployRootPath =          pathToRootOfHierarchy;
		this.deployRootDir  = new File(pathToRootOfHierarchy);
	}

	public DeployFileHierarchy(File rootOfHierarchy) {
		this.deployRootPath = rootOfHierarchy.toString();
		this.deployRootDir  = rootOfHierarchy;
	}

	public File getDeployRootDir() {
		return deployRootDir;
	}
}
