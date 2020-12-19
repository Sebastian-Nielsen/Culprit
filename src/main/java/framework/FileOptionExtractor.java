package framework;

import java.io.File;
import java.io.IOException;
import java.util.List;

public interface FileOptionExtractor {
	/**
	 * Extracts all FileOptions in {@code file}
	 * @param file
	 * @return list of FileOptionImpl objects
	 * @throws IOException
	 */
	List<FileOptionImpl> extractFileOptionsFrom(File file) throws IOException;
}
