package framework;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import static framework.FileOption.KEY;

public interface FileOptionExtractor {
	Map<KEY, String> extractKeyToValMapFrom(File file) throws IOException;

	/**
	 * Extracts all FileOptions in {@code file}
	 * @return list of FileOption objects
	 */
	@Deprecated
	List<FileOption> extractFileOptions() throws IOException;
}
