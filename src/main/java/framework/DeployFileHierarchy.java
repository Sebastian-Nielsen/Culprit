package framework;

import framework.utils.FileUtils;
import framework.utils.FileUtils.Lister.*;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static framework.utils.FileUtils.Lister.streamFilesAndDirsFrom;
import static framework.utils.FileUtils.Lister.streamNonDirsFrom;
import static org.apache.commons.io.FilenameUtils.getExtension;

@NotNull public class DeployFileHierarchy extends FileHierarchy {

	/**
	 * <em>Deploy</em> files are {@code File}s that have one of the file extensions listed in {@code fileExtFilter}
	 */
	private static final Set<String> fileExtsOfDeployFiles = new HashSet<>(List.of("html"));

	public DeployFileHierarchy(@NotNull String pathToRootOfHierarchy) {
		super(pathToRootOfHierarchy);
	}

	public DeployFileHierarchy(@NotNull File rootOfHierarchy) {
		super(rootOfHierarchy);
	}

	@Override
	public boolean isEssential(@NotNull File file) {

		boolean hasFileValidFileExt = fileExtsOfDeployFiles.contains(getExtension(file.toString()));
		boolean isIndexFile         = file.toString().equals("index.html");

		return hasFileValidFileExt && !isIndexFile;
	}

	/* ===================================================== */

	/* === LISTERS */

	/* ===================================================== */

	/* === PRIVATE METHODS */

}
