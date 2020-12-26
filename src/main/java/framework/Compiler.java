package framework;

import java.io.File;
import java.io.IOException;
import java.util.Map;

public interface Compiler {
	/**
	 *
//	 */
//	void preprocess(Map<File, Map<String, String>> fileToKeyToVal);

	/**
	 * Compiles all files in *deployment*.
	 * Precondition: `prescan` must've been run prior.
	 * @return a map of each {@code File} in *deployment* to their
	 * respective compiled content.
	 * @param fileToKeyToVal
	 */
	public Map<File, String> compile(Map<File, Map<FileOption.KEY, String>> fileToKeyToVal) throws IOException;
}
