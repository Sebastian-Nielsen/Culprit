package framework;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import static framework.FileOption.KEY;

public interface FileOptionExtractor {
	Map<KEY, String> extractKeyToValMap() throws IOException;

	/**
	 * Extracts all FileOptions in {@code file}
	 * @return list of FileOption objects
	 */
	FileOptionContainer extractFileOptions() throws IOException;
}
