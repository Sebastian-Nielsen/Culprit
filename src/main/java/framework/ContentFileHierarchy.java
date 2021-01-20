package framework;

import framework.utils.FileUtils;
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

	/**
	 * Content files are {@code File}s that have one of the file extensions listed in
	 */
	public static final Set<String> CONTENT_FILE_EXTENTIONS = new HashSet<>(List.of("md"));

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

	public File[] listNonDirs(FileUtils.Lister.RECURSION isRecursive) throws IOException {
		return streamNonDirsFrom(contentRootDir, isRecursive)
				.filter(file -> file.isDirectory() || hasValidContentFileExt(file))
				.toArray(File[]::new);
	}

	public File[] listFilesAndDirs(FileUtils.Lister.RECURSION isRecursive) throws IOException {
		return streamFilesAndDirsFrom(contentRootDir, isRecursive)
				.filter(file -> file.isDirectory() || hasValidContentFileExt(file))
				.toArray(File[]::new);  // TODO: Implement cache for speedup
	}


	/* === PRIVATE METHODS */

	/**
	 * A <em>content</em> file is any file in the <em>content</em> folder that has any of
	 * file extensions defined in {@link ContentFileHierarchy#CONTENT_FILE_EXTENTIONS}
	 * @return whether the file is a <em>content</em> file
	 */
	private boolean hasValidContentFileExt(File file) {
		return CONTENT_FILE_EXTENTIONS.contains(getExtension(file.toString()));
	}


	/* ===================================================== */
	/* === GETTERS */

	public File getContentRootDir() {
		return contentRootDir;
	}

}
