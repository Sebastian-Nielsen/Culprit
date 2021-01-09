package common.html;

import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.IOException;
import java.util.Map;

import static common.html.HTML.Attribute.STYLE;
import static common.html.HTML.Tag.*;
import static common.html.HTML.Attribute.*;
import static common.html.HTML.Attribute;

import static common.html.HTML.Tag.LINK;
import static common.html.HTML.Tag.TITLE;
import static framework.Constants.Constants.CWD_NAME;
import static framework.utils.FileUtils.Lister.*;
import static framework.utils.FileUtils.Lister.RECURSION.NONRECURSIVE;

public class HtmlBuilder {

	/**
	 * The default html of an index file is dynamic:
	 * for each file in the dir of the index file in question,
	 * insert a {@code li} element pointing to that file.
	 * @param folder the folder that the generation of the dynamic html is based on
	 * @return {@code toString} of {@code HtmlTag}
	 */
	public static String buildDefaultIndexHtml(File folder) throws IOException {
		return new Builder()
				.open(HTML)
					.insertBuilder(defaultHead())
					.open(BODY)
						.insertBuilder(generateOlTagListingOfFilesIn(folder))
					.close(BODY)
				.close(HTML)
				.toString();


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

	}


	public static String buildDefaultPageHtmlFrom(String articleContent) {
		// TODO: Enhance this to depend on the fileoption specified in the file.
		return new Builder()
				.open(HTML)
					.insertBuilder(defaultHead())
					.open(BODY)
						.open(MAIN)
							.open(ASIDE, Map.of(ID,  "left-aside")).close(ASIDE)
							.open(ASIDE, Map.of(ID, "right-aside")).close(ASIDE)
							.open(ARTICLE)
								.insertRaw(articleContent)
							.close(ARTICLE)
						.close(MAIN)
					.close(BODY)
				.close(HTML)
				.toString();
	}


	/* === Default Tags */
//
//	public static enum BuilderOption {
//
//	}

	public static @NotNull Builder defaultHead() {
		return new Builder()
				.open(HEAD)
					.open(TITLE).setText("index").close(TITLE)
					.openSingle(META, Map.of(CHARSET,"utf-8"))
					.openSingle(LINK, defaultCssAttributes("global.css"))
				.close(HEAD);
	}

//	public static @NotNull Builder defaultLatexHead() {
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

	public static Map<Attribute, String> defaultCssAttributes(String hrefVal) {
		return Map.of(
				HREF, "/%s/%s".formatted(CWD_NAME, hrefVal),
				REL,  "stylesheet"
		);
	}

	/* ============================================================ */

	/* === PRIVATE METHODS */

	/**
	 * For each {@code File} (both dirs and files) in the specified folder,
	 * create a li {@code Tag} and add it to the ol {@code Tag} to return.
	 * @param folder folder from which to list the files from.
	 * @return an ol {@code Tag}
	 */
	private static Builder generateOlTagListingOfFilesIn(File folder) throws IOException {
		Builder builder = new Builder();  // TODO cohesify this method

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


}
