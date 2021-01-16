package framework.compilerFacade;

import common.html.HtmlTemplateStrategy;

/**
 * Markdown to HTML Compiler
 * Adapter for commonmark
 */
public interface Compiler {

	/**
	 * Compile the given markdown to html
	 * @return the compiled html of the {@code markdown}
	 */
	String compile(String markdown);

}

