package framework;

import java.io.File;
import java.util.Map;

import common.fileOption.FileOptionContainer;
import common.html.HtmlTemplateStrategy;

/**
 * Markdown to HTML Compiler
 * Adapter for commonmark
 */
public interface Compiler {

	/**
	 * Compile the specified {@code markdown} to html and return it the result but inserted into {@code HtmlTemplate}
	 * @param markdown markdown to compile
	 * @param template see {@link HtmlTemplateStrategy}
	 * @return the compiled html of the {@code markdown} contained within the specified html-{@code template}
	 */
	String compile(String markdown);

	/**
	 * Compiles from .md to .html for all {@code File}s in the supplied map.
	 * @param fileToContent a map from {@code File}s to their associated content
	 * @return a map from {@code File}s to their associated compiled content
	 */
//	Map<File, String> compileAllFiles(Map<File, String> fileToContent, Map<File, FileOptionContainer> fileToFOContainer) throws Exception;

}
