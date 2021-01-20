package framework;

import framework.utils.FileUtils;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static framework.utils.FileUtils.Filename.fileExtOf;
import static framework.utils.FileUtils.Lister.*;
import static org.apache.commons.io.FilenameUtils.getExtension;

public class ContentFileHierarchy extends FileHierarchy {

	/**
	 * <em>Content</em> files are {@code File}s that have one of the file extensions listed in {@code fileExtFilter}
	 */
	private static final Set<String> fileExtsOfContentFiles = new HashSet<>(List.of("md"));

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

	/* ===================================================== */

	/* === LISTERS */




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
