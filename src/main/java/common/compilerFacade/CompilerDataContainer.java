package common.compilerFacade;

import common.fileOption.FileOptionContainer;
import one.util.streamex.EntryStream;
import one.util.streamex.StreamEx;
import org.jetbrains.annotations.Unmodifiable;

import java.io.File;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;

/**
 * Immutable container for all data that the {@link CompilerFacade} needs in
 * order to compile a given .md {@code File}.
 */
public class CompilerDataContainer {

	private final @Unmodifiable Map<String, File> idToContentFile;
	private @Unmodifiable Map<String, FileOptionContainer> pathToFOContainer;

	public CompilerDataContainer(Map<String, File> idToContentFile, Map<String, FileOptionContainer> pathToFOContainer) {
		this.idToContentFile = idToContentFile;
		this.pathToFOContainer = pathToFOContainer;
	}


	/* === Getters */

	public FileOptionContainer getFOContainerOf(File file) {
		return pathToFOContainer.get(file.toString());
	}

	public File getFileOfId(String id) {
		return idToContentFile.get(id);
	}

}
