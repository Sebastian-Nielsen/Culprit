package common;

import common.fileOption.FileOptionContainer;
import framework.Compiler;
import org.jetbrains.annotations.Unmodifiable;

import java.io.File;
import java.nio.file.Paths;
import java.util.Map;

/**
 * Immutable container for all data that the {@link Compiler} needs in
 * order to compile a given .md {@code File}.
 */
public class CompilerDataContainer {

	private final @Unmodifiable Map<String, File> idToContentFileWithHtmlExt;
	private final @Unmodifiable Map<File, FileOptionContainer> fileToFOContainer;

	public CompilerDataContainer(Map<String, File> idToContentFileWithHtmlExt,
	                             Map<File, FileOptionContainer> fileToFOContainer) {
		this.idToContentFileWithHtmlExt = idToContentFileWithHtmlExt;
		this.fileToFOContainer = fileToFOContainer;
	}


	/* === Getters */

	public FileOptionContainer getFOContainerOf(File file) {
		return fileToFOContainer.get(file);
	}

//	public String getRelativeDeployPathBy(String id, File fromFile) {
//		File toFile = new File(idToContentFileWithHtmlExt.get(id));
//
//		return Paths.get("" + fromFile).relativize(Paths.get("" + toFile)).toString();
//	}

	public File getFileOfId(String id) {
		return idToContentFileWithHtmlExt.get(id);
	}

}
