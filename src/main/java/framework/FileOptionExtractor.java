package framework;

import java.io.File;
import java.io.IOException;
import java.util.List;

public interface FileOptionExtractor {
	/**
	 * Extracts all FileOptions in {@code file}
	 * @param file
	 * @return list of FileOption objects
	 * @throws IOException
	 */
	List<FileOption> extractFileOptionsFrom(File file) throws IOException;
}
