package framework;

import java.io.File;
import java.util.Map;

/**
 * Markdown to HTML Compiler
 * Adapter for commonmark
 */
public interface Compiler {

	/**
	 * An enum for each html-template that it's possible
	 * to insert the html (from the compiled .md) into.
	 */
	public enum HtmlTemplate {
		NONE,
		DEFAULT_PAGE
	}

	/**
	 * @param markdown markdown to compile
	 * @param template see {@link HtmlTemplate}
	 * @return the compiled html of the {@code markdown} contained within the specified html-{@code template}
	 */
	public String compile(String markdown, HtmlTemplate template);

	/**
	 * Compiles from .md to .html for all {@code File}s in the supplied map.
	 * @param fileToContent a map from {@code File}s to their associated content
	 * @return a map from {@code File}s to their associated compiled content
	 */
	public Map<File, String> compileAllFiles(Map<File, String> fileToContent);

}
