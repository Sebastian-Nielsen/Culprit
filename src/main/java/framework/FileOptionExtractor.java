package framework;

import framework.singleClasses.FileOptionContainer;

import java.io.IOException;

public interface FileOptionExtractor {

	/**
	 * Extracts all FileOptions in {@code file}
	 * @return a {@code FileOptionContainer} containing all the FileOption
	 * (key, value) pairs present in {@code file}
	 */
	FileOptionContainer extractFOContainer() throws IOException;

}
