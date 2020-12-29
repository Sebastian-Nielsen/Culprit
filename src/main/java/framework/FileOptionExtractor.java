package framework;

import framework.singleClasses.FileOptionContainer;

import java.io.File;
import java.io.IOException;
import java.util.Map;

public interface FileOptionExtractor {

	/**
	 * Extracts all FileOptions in {@code file}
	 * @return a {@code FileOptionContainer} containing all the FileOption
	 * (key, value) pairs present in {@code file}
	 * @param fileHandler
	 */
	FileOptionContainer extractFOContainer(FileHandler fileHandler) throws IOException;

	FileOptionContainer extractFOContainer(FileHandler fileHandler, Validator validator) throws IOException;


	public Map<File, FileOptionContainer> extractFOContainerFromEachFileIn(File folder) throws Exception;
	public Map<File, FileOptionContainer> extractFOContainerFromEachFileIn(File folder, Validator validator) throws Exception;
}
