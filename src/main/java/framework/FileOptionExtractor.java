package framework;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import static framework.FileOption.KEY;

public interface FileOptionExtractor {

	/**
	 * Extracts all FileOptions in {@code file}
	 * @return a {@code FileOptionContainer} containing all the FileOption
	 * (key, value) pairs present in {@code file}
	 */
	FileOptionContainer extractFileOptions() throws IOException;

}
