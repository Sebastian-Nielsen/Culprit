package framework.other;

import common.fileOption.FileOptionContainer;
import framework.ContentFileHierarchy;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.Map;

public interface FileOptionExtractor {

	/**
	 * Extracts all FileOptions in {@code file}
	 * @return a {@code FileOptionContainer} containing all the FileOption
	 * (key, value) pairs present in {@code file}
	 * @param validator validator that determines what a valid fileOption format is
	 */
	FileOptionContainer extractFOContainer(FileHandler fileHandler, Validator validator) throws IOException;
	FileOptionContainer extractFOContainer(FileHandler fileHandler) throws IOException;

	/**
	 * Extracts a {@code FileOptionContainer} for each {@code File} in the specified {@code folder}
	 * @param validator validator that determines what a valid fileOption format is
	 * @return map of {@code File}s to their respective {@code FileOptionContainer}
	 */
	public Map<File, FileOptionContainer> extractContentToFOContainer(ContentFileHierarchy contentHierarchy, Validator validator) throws Exception;
	public Map<File, FileOptionContainer> extractContentToFOContainer(ContentFileHierarchy contentHierarchy) throws Exception;
}
