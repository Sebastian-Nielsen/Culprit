package common;

import common.fileOption.FileOptionContainer;
import framework.Compiler;
import org.jetbrains.annotations.Unmodifiable;

import java.io.File;
import java.util.Collections;
import java.util.Map;

/**
 * Immutable container for all data that the {@link Compiler} needs in
 * order to compile a given .md {@code File}.
 */
public class CompilerDataContainer {

	private final @Unmodifiable Map<String, String> idToRelativeDeployPath;
	private final @Unmodifiable Map<File, FileOptionContainer> fileToFOContainer;

	public CompilerDataContainer(Map<String, String> idToRelativeDeployPath, Map<File, FileOptionContainer> fileToFOContainer) {
		this.idToRelativeDeployPath = idToRelativeDeployPath;
		this.fileToFOContainer      = fileToFOContainer;
	}


	/* === Getters */

	public FileOptionContainer getFOContainerOf(File file) {
		return fileToFOContainer.get(file);
	}

	public String getRelativeDeployPathBy(String id) {
		return idToRelativeDeployPath.get(id);
	}

}
