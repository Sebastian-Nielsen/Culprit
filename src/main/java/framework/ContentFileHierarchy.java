package framework;

import framework.utils.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static framework.utils.FileUtils.Lister.*;
import static org.apache.commons.io.FilenameUtils.getExtension;

public class ContentFileHierarchy {

	private @NotNull final String contentRootPath;
	private @NotNull final File   contentRootDir;

	private static final Set<String> EXT_FILTER = new HashSet<>(List.of("md"));

	/* ===================================================== */

	public ContentFileHierarchy(@NotNull String pathToRootOfHierarchy) {
		this.contentRootPath = pathToRootOfHierarchy;
		this.contentRootDir = new File(pathToRootOfHierarchy);
	}

	public ContentFileHierarchy(@NotNull File rootOfHierarchy) {
		this.contentRootPath = rootOfHierarchy.toString();
		this.contentRootDir = rootOfHierarchy;
	}

	/* ===================================================== */

	/* === LISTERS */

	public File[] listNonDirs(FileUtils.Lister.RECURSION isRecursive, String mdFilesOnly) throws IOException {
		return listNonDirsFrom(contentRootDir, isRecursive, mdFilesOnly);
	}

	public File[] listNonDirs(FileUtils.Lister.RECURSION isRecursive) throws IOException {
		return listNonDirsFrom(contentRootDir, isRecursive);
	}

	public File[] listFilesAndDirs(FileUtils.Lister.RECURSION isRecursive) throws IOException {
		return streamFilesAndDirsFrom(contentRootDir, isRecursive)
//				.filter(file -> file.isDirectory() || EXT_FILTER.contains(getExtension(file.toString())))
				.toArray(File[]::new);  // TODO: Implement cache for speedup
	}

	/* ===================================================== */

	/* === GETTERS */

	public File getContentRootDir() {
		return contentRootDir;
	}

}
