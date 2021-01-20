package framework;

import framework.utils.FileUtils;
import framework.utils.FileUtils.Lister.*;

import java.io.File;
import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static framework.utils.FileUtils.Lister.streamFilesAndDirsFrom;
import static framework.utils.FileUtils.Lister.streamNonDirsFrom;

public class DeployFileHierarchy extends FileHierarchy {

	/**
	 * <em>Deploy</em> files are {@code File}s that have one of the file extensions listed in {@code fileExtFilter}
	 */
	public Set<String> fileExtFilter;

	public DeployFileHierarchy(String pathToRootOfHierarchy) {
		super(pathToRootOfHierarchy);
	}

	public DeployFileHierarchy(File rootOfHierarchy) {
		super(rootOfHierarchy);
	}

	@Override
	public Set<String> initFileExtFilter() {
		return new HashSet<>(List.of("html"));
	}

	/* ===================================================== */

	/* === LISTERS */

//	public File[] listNonDirs(RECURSION isRecursive) throws IOException {
//		return streamNonDirsFrom(deployRootDir, isRecursive)
//				.filter(file -> file.isDirectory() || hasValidContentFileExt(file))
//				.toArray(File[]::new);
//	}
//
//	public File[] listFilesAndDirs(RECURSION isRecursive) throws IOException {
//		return streamFilesAndDirsFrom(deployRootDir, isRecursive)
//				.filter(file -> file.isDirectory() || hasValidContentFileExt(file))
//				.toArray(File[]::new);  // TODO: Implement cache for speedup
//	}
//
//	public File[] listJsonConfigFiles(RECURSION isRecursive) {
//		return null;
//	}

	/* ===================================================== */

	/* === PRIVATE METHODS */

}
