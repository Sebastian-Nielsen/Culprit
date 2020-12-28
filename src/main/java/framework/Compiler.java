package framework;

import java.io.File;
import java.io.IOException;
import java.util.Map;

public interface Compiler {
	/**
	 *
//	 */
	public void preprocess(Map<File, FileOptionContainer> fileToFOContainer);

	/**
	 * Compiles all files in *deployment*.
	 * Precondition: `prescan` must've been run prior.
	 * @return a map of each {@code File} in *deployment* to their
	 * respective compiled content.
	 * @param fileToFOContainer .
	 */
	public Map<File, String> compile(Map<File, FileOptionContainer> fileToFOContainer) throws IOException;
}
