package framework;

import java.io.File;
import java.util.Map;

/**
 * Markdown to HTML Compiler
 * Adapter for commonmark
 */
public interface Compiler {

	public String compile(String markdown);

	/**
	 * Compiles from .md to .html for all {@code File}s in the supplied map.
	 * @param fileToContent a map from {@code File}s to their associated content
	 * @return a map from {@code File}s to their associated compiled content
	 */
	public Map<File, String> compileAllFiles(Map<File, String> fileToContent);

}
