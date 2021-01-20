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

public class ContentFileHierarchy extends FileHierarchy {

	/**
	 * <em>Content</em> files are {@code File}s that have one of the file extensions listed in {@code fileExtFilter}
	 */
	public Set<String> fileExtFilter;

	/* ===================================================== */

	public ContentFileHierarchy(String pathToRootOfHierarchy) {
		super(pathToRootOfHierarchy);
	}

	public ContentFileHierarchy(File rootOfHierarchy) {
		super(rootOfHierarchy);
	}

	@Override
	public Set<String> initFileExtFilter() {
		return new HashSet<>(List.of("md"));
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
