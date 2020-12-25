package framework;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;

public interface Compiler {
	/**
	 * A prescan has to be carried out before compile.
	 */
	public void prescan();

	/**
	 * Compiles all files in *deployment*.
	 * Precondition: `prescan` must've been run prior.
	 * @return a map of each {@code File} in *deployment* to their
	 * respective compiled content.
	 */
	public Map<File, String> compile(Map<File, List<FileOption>> fileToListOfFileOption) throws IOException;
}
