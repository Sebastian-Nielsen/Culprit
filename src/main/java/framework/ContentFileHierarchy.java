package framework;

import framework.utils.FileUtils;

import java.io.File;
import java.io.IOException;

import static framework.utils.FileUtils.Lister.listNonDirsFrom;

public class ContentFileHierarchy {

	private final String contentRootPath;
	private final File   contentRootDir;

	public ContentFileHierarchy(String pathToRootOfHierarchy) {
		this.contentRootPath = pathToRootOfHierarchy;
		this.contentRootDir = new File(pathToRootOfHierarchy);
	}

	public ContentFileHierarchy(File rootOfHierarchy) {
		this.contentRootPath = rootOfHierarchy.toString();
		this.contentRootDir = rootOfHierarchy;
	}

	public File getContentRootDir() {
		return contentRootDir;
	}

	public File[] listNonDirs(FileUtils.Lister.RECURSION isRecursive, String mdFilesOnly) throws IOException {
		return listNonDirsFrom(contentRootDir, isRecursive, mdFilesOnly);
	}
	public File[] listNonDirs(FileUtils.Lister.RECURSION isRecursive) throws IOException {
		return listNonDirsFrom(contentRootDir, isRecursive);
	}
}
