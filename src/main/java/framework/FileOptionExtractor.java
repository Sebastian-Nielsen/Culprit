package framework;

import java.io.File;
import java.io.IOException;
import java.util.List;

public interface FileOptionExtractor {
	/**
	 * Extracts all FileOptions in {@code file}
	 * @param file file to extract {@code FileOption}s from
	 * @return list of FileOption objects
	 */
	List<FileOption> extractFileOptionsFrom(File file) throws IOException;
}
