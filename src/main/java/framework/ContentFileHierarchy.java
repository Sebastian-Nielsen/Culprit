package framework;

import framework.utils.FileUtils;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.File;
import java.io.IOException;
import java.util.*;

import static framework.utils.FileUtils.Filename.fileExtOf;
import static framework.utils.FileUtils.Lister.*;
import static framework.utils.FileUtils.Lister.RECURSION.NONRECURSIVELY;
import static org.apache.commons.io.FilenameUtils.getExtension;

public class ContentFileHierarchy extends FileHierarchy {

	/**
	 * A map from folders to their config.json file; note: not all folders have a config.json file
	 */
	private final Map<File, @Nullable File> dirToConfigCache = new HashMap<>();

	/**
	 * <em>Content</em> files are {@code File}s that have one of the file extensions listed in {@code fileExtFilter}
	 */
	private final Set<String> fileExtsOfContentFiles = new HashSet<>(List.of("md"));

	/* ===================================================== */

	public ContentFileHierarchy(@NotNull String pathToRootOfHierarchy) {
		super(pathToRootOfHierarchy);
	}

	public ContentFileHierarchy(@NotNull File rootOfHierarchy) {
		super(rootOfHierarchy);
	}

	@Override
	public boolean isEssential(@NotNull File file) {
//		 fileExtFilter.contains(fileExtOf(file))
		return fileExtsOfContentFiles.contains(getExtension(file.toString()));
	}

	/**
	 *
	 * @param dir
	 */
	public boolean isTopicDir(File dir) throws Exception {
		return doesConfigFileExistIn(dir);
	}

	/* ===================================================== */

	/* === LISTERS */

	public boolean doesConfigFileExistIn(File dir) throws Exception {
		if (dirToConfigCache.containsKey(dir))
			return dirToConfigCache.get(dir) != null;

		File configFile = getConfigFileOf(dir);
		dirToConfigCache.put(dir, configFile);

		boolean doesConfigFileExist = configFile != null;
		return  doesConfigFileExist;
	}

	public @Nullable File getConfigFileOf(File dir) throws IOException {
		 return streamNonDirsFrom(dir, NONRECURSIVELY)
				 .filter(this::isConfigFile)
				 .findAny().orElse(null);


	}

	public boolean isConfigFile(File file) {
		return file.toString().endsWith("config.json");
	}

	/* === PRIVATE METHODS */

//	/**
//	 * A <em>content</em> file is any file in the <em>content</em> folder that has any of
//	 * file extensions defined in {@link ContentFileHierarchy#CONTENT_FILE_EXTENTIONS}
//	 * @return whether the file is a <em>content</em> file
//	 */
//	private boolean hasValidContentFileExt(File file) {
//		return CONTENT_FILE_EXTENTIONS.contains(getExtension(file.toString()));
//	}


	/* ===================================================== */

	/* === GETTERS */


}
