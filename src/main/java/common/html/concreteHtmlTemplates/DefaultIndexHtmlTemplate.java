package common.html.concreteHtmlTemplates;

import common.html.HtmlBuilder;
import common.html.HtmlTemplateStrategy;
import common.html.TemplateParameters;

import java.io.File;
import java.io.IOException;
import java.util.Map;

import static common.html.HTML.Attribute.HREF;
import static common.html.HTML.Tag.*;
import static common.html.concreteHtmlTemplates.Helper.defaultHead;
import static framework.utils.FileUtils.Lister.RECURSION.NONRECURSIVE;
import static framework.utils.FileUtils.Lister.listDirsFrom;
import static framework.utils.FileUtils.Lister.listNonDirsFrom;

public class DefaultIndexHtmlTemplate implements HtmlTemplateStrategy {


	/**
	 * The default html of an index file is dynamic:
	 * for each file in the dir of the index file in question,
	 * insert a {@code li} element pointing to that file.
	 * @return {@code toString} of {@code ArticleTag}
	 */
	@Override
	public String buildUsing(TemplateParameters parameters) throws Exception {
		return new HtmlBuilder()
				.open(HTML)
					.insertBuilder(defaultHead)
					.open(BODY)
						.insertBuilder(generateOlTagListingOfFilesIn(parameters.folder))
					.close(BODY)
				.close(HTML)
				.toString();
	}

	/* === PRIVATE METHODS */

	/**
	 * For each {@code File} (both dirs and files) in the specified folder,
	 * create a li {@code Tag} and add it to the ol {@code Tag} to return.
	 * @param folder folder from which to list the files from.
	 * @return an ol {@code Tag}
	 */
	private static HtmlBuilder generateOlTagListingOfFilesIn(File folder) throws IOException {
		HtmlBuilder builder = new HtmlBuilder();  // TODO cohesify this method

		// Generate OL with LI tags for each {@code dir} in folder
		builder.open(OL);
		for (File dir : listDirsFrom(folder, NONRECURSIVE))
			builder .open(LI)
						.open(A, Map.of(HREF, "./" + dir.getName()))
							.setText(dir.getName())
						.close(A)
					.close(LI);

		builder.close(OL);

		// Generate OL with LI tags for each {@code File} (nonDir) in folder
		builder.open(OL);
		for (File nonDir : listNonDirsFrom(folder, NONRECURSIVE))
			builder .open(LI)
						.open(A, Map.of(HREF, "./" + nonDir.getName()))
							.setText(nonDir.getName())
						.close(A)
					.close(LI);

		builder.close(OL);

		return builder;
	}














		/**
		 *     <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/katex@0.12.0/dist/katex.min.css" integrity="sha384-AfEj0r4/OFrOo5t7NnNe46zW/tFgW6x/bCJG8FqQCEo3+Aro6EYUG4+cU+KJWu/X" crossorigin="anonymous">
		 *
		 *     <!-- The loading of KaTeX is deferred to speed up page rendering -->
		 *     <script defer src="https://cdn.jsdelivr.net/npm/katex@0.12.0/dist/katex.min.js" integrity="sha384-g7c+Jr9ZivxKLnZTDUhnkOnsh30B4H0rpLUpJ4jAIKs4fnJI+sEnkvrMWph2EDg4" crossorigin="anonymous"></script>
		 *
		 *     <!-- To automatically render math in text elements, include the auto-render extension: -->
		 *     <script defer src="https://cdn.jsdelivr.net/npm/katex@0.12.0/dist/contrib/auto-render.min.js" integrity="sha384-mll67QQFJfxn0IYznZYonOWZ644AWYC+Pt2cHqMaRhXVrursRwvLnLaebdGIlYNa" crossorigin="anonymous"
		 *         onload="renderMathInElement(document.body);"></script>
		 */


	/* === Default Tags */
//
//	public static enum BuilderOption {
//
//	}



//	public static @NotNull HtmlBuilder defaultLatexHead() {
//    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/katex@0.12.0/dist/katex.min.css" crossorigin="anonymous">
//
//    <!-- The loading of KaTeX is deferred to speed up page rendering -->
//    <script defer src="https://cdn.jsdelivr.net/npm/katex@0.12.0/dist/katex.min.js" crossorigin="anonymous"></script>
//
//    <!-- To automatically render math in text elements, include the auto-render extension: -->
//    <script defer src="https://cdn.jsdelivr.net/npm/katex@0.12.0/dist/contrib/auto-render.min.js" crossorigin="anonymous"
//        onload="renderMathInElement(document.body);"></script>
//	}

	/* ============================================================ */



	/* ============================================================ */



}
