package framework;

import framework.utils.FileUtils;
import framework.utils.FileUtils.Lister.*;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.IOException;

import static framework.utils.FileUtils.Filename.relativePath;
import static framework.utils.FileUtils.Lister.*;

/**
 * Abstraction for an entire File-Hierarchy whose main responsibility is to organize all folders and files
 * that reside in the given File-Hierarchy.
 * <p>
 * Most importantly, it organizes the files into two categories:
 * essential and non-essential files (see {@link #isEssential(File)} for the difference).
 * The two categories might be further subcategorized.
 * <p>
 * Additionally, it stores everything related to the FileHierarchy in question, like its {@code rootDir}.
 */
public abstract class FileHierarchy {

	protected String rootDirPath;
	protected File   rootDir;

	public FileHierarchy(@NotNull File rootOfHierarchy) {
		this.rootDirPath = rootOfHierarchy.toString();
		this.rootDir     = rootOfHierarchy;
	}

	public FileHierarchy(@NotNull String pathToRootOfHierarchy) {
		this(new File(pathToRootOfHierarchy));
	}

	/**
	 * Contains the boolean logic for whether the given file is an essential file of the hierarchy.<p>
	 * The boolean answer to whether a file is essential amounts to asking:
	 * <pre>
	 * "Is the intend of the <em>concrete File-Hierarchy</em> to structure these sort of files in a hierarchy,
	 * or is this file more like a helper-/config-file kind of deal."
	 * </pre>
	 * Hence, non-essential files could be config files; image files; excel documents; etc.
	 * @param file a non-dir {@code File}
	 */
	protected abstract boolean isEssential(@NotNull File file);

	/* ===================================================== */

	/* === LISTERS */

	public File[] listNonDirs(RECURSION isRecursive) throws IOException {
		return streamNonDirsFrom(rootDir, isRecursive)
				.filter(file -> isEssential(file))
				.toArray(File[]::new);
	}
	public File[] listNonDirsFrom(File folder, RECURSION isRecursive) throws IOException {
		return streamNonDirsFrom(folder, isRecursive)
				.filter(file -> isEssential(file))
				.toArray(File[]::new);
	}

	public File[] listFilesAndDirs(RECURSION isRecursive) throws IOException {
		return streamFilesAndDirsFrom(rootDir, isRecursive)
				.filter(file -> file.isDirectory() || (file.isFile() && isEssential(file)) )
				.toArray(File[]::new);  // TODO: Implement cache for speedup
	}

	/**
	 * precondition: {@code folder} is either the {@link FileHierarchy#rootDir} or a subdir of the FileHierarchy instance
	 * @param folder the root or a subdir of the {@link FileHierarchy#rootDir} of this {@code FileHierarchy} instance
	 * @param isRecursive whether to recursively search for dirs in subdirs
	 */
	public File[] listDirsFrom(File folder, RECURSION isRecursive) throws IOException {
		return streamDirsFrom(folder, isRecursive)
				.toArray(File[]::new);  // TODO: Implement cache for speedup
	}

	/* ===================================================== */

	/**
	 * See {@link FileUtils.Filename#relativePath},
	 * though where the basePath is {@code rootDirPath} of this FileHierarchy instance
	 */
	public String relativePathToRoot(String path) {
		return relativePath(rootDirPath, path);
	}

	/* ===================================================== */

	/* === GETTERS */

	public String getRootPath() {
		return rootDirPath;
	}
	public File getRootDir() {
		return rootDir;
	}

	/* ===================================================== */

}
