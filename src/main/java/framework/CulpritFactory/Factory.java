package framework.CulpritFactory;

import framework.ContentFileHierarchy;
import framework.DeployFileHierarchy;

import java.io.File;

import static framework.utils.FileUtils.Filename.areDistinctFilePaths;

public abstract class Factory {

	protected final ContentFileHierarchy contentHierarchy;
	protected final DeployFileHierarchy deployHierarchy;

	public Factory(ContentFileHierarchy contentHierarchy,
	               DeployFileHierarchy deployHierarchy) {

		if (!areDistinctFilePaths(contentHierarchy.getRootPath(), deployHierarchy.getRootPath()))
			throw new RuntimeException("Content- and Deployrootfolder are not distinct");

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
