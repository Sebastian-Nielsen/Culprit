package framework;

import common.fileOption.FileOptionContainer;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.IOException;
import java.util.Map;

public interface Precompiler {

	/**
	 * Compiles all files in *deployment*.
	 * Precondition: {@code fileToFOContainer} must've been preprocessed prior.
	 * @return a map of each {@code File} in *deployment* to their
	 * respective compiled content.
	 */
	Map<File, String> compileAllFiles(Map<File, FileOptionContainer> fileToFOContainer) throws IOException;

	String compileSingleFile(File fileToCompile, @NotNull FileOptionContainer foContainer) throws IOException;
}
