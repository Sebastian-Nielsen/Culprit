package framework.CulpritFactory;

import framework.ContentFileHierarchy;
import framework.DeployFileHierarchy;

import java.io.File;

public abstract class Factory {

	protected final ContentFileHierarchy contentHierarchy;
	protected final DeployFileHierarchy deployHierarchy;

	public Factory(ContentFileHierarchy contentHierarchy,
	               DeployFileHierarchy deployHierarchy) {
		this.contentHierarchy = contentHierarchy;
		this.deployHierarchy = deployHierarchy;
	}

	public ContentFileHierarchy getContentHierarchy() {
		return contentHierarchy;
	}

	public DeployFileHierarchy getDeployHierarchy() {
		return deployHierarchy;
	}
}
