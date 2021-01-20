package framework;

import framework.utils.FileUtils.Lister.*;
import org.jetbrains.annotations.NotNull;

import static framework.utils.FileUtils.Filename.*;
import static framework.utils.FileUtils.Lister.*;

import java.io.File;
import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public abstract class FileHierarchy {

	protected String rootDirPath;
	protected File   rootDir;

	/**
	 * Set of files are {@code File}s that have one of the file extensions listed in
	 */
	protected @NotNull Set<String> fileExtFilter;

	public FileHierarchy(@NotNull File rootOfHierarchy) {
		this.rootDirPath = rootOfHierarchy.toString();
		this.rootDir     = rootOfHierarchy;
		fileExtFilter = initFileExtFilter();
	}

	public FileHierarchy(@NotNull String pathToRootOfHierarchy) {
		this(new File(pathToRootOfHierarchy));
	}

	protected abstract Set<String> initFileExtFilter();

	/* ===================================================== */

	/* === LISTERS */

	public File[] listNonDirs(RECURSION isRecursive) throws IOException {
		return streamNonDirsFrom(rootDir, isRecursive)
				.filter(file -> file.isDirectory() || fileExtFilter.contains(fileExtOf(file)))
				.toArray(File[]::new);
	}

	public File[] listFilesAndDirs(RECURSION isRecursive) throws IOException {
		return streamFilesAndDirsFrom(rootDir, isRecursive)
				.filter(file -> file.isDirectory() || fileExtFilter.contains(fileExtOf(file)))
				.toArray(File[]::new);  // TODO: Implement cache for speedup
	}

	/**
	 * precondition: {@code folder} is either the {@link FileHierarchy#rootDir} or a subdir of the FileHierarchy instance
	 * @param folder the root or a subdir of the {@link FileHierarchy#rootDir} of this {@code FileHierarchy} instance
	 * @param isRecursive whether to recursively search for dirs in subdirs
	 */
	public File[] listDirsFrom(File folder, RECURSION isRecursive) throws IOException {
		return streamDirsFrom(folder, isRecursive)
				.filter(file -> file.isDirectory() || fileExtFilter.contains(fileExtOf(file)))
				.toArray(File[]::new);  // TODO: Implement cache for speedup
	}

	/* ===================================================== */

	/* === GETTERS */

	public File getRootDir() {
		return rootDir;
	}

	/* ===================================================== */

}
