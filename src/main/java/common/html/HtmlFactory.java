package common.html;

import common.fileOption.FileOptionContainer;
import common.html.HtmlBuilder;

import java.io.File;
import java.io.IOException;
import java.util.Map;

import static common.fileOption.FileOption.KEY.KATEX;
import static common.html.HTML.Attribute.*;
import static common.html.HTML.Tag.*;
import static common.html.HTML.Tag.LINK;
import static common.html.HTML.Tag.STYLE;
import static framework.utils.FileUtils.Filename.changeFileExt;
import static framework.utils.FileUtils.Lister.RECURSION.NONRECURSIVE;
import static framework.utils.FileUtils.Lister.listDirsFrom;
import static framework.utils.FileUtils.Lister.listNonDirsFrom;

public class HtmlFactory {


	/**
	 * For each {@code File} (both dirs and files) in the specified folder,
	 * create a li {@code Tag} and add it to the ol {@code Tag} to return.
	 * @param folder folder from which to list the files from.
	 * @return an ol {@code Tag}
	 */
	public String createNavigationHtml(File folder) throws IOException {
		HtmlBuilder builder = new HtmlBuilder();  // TODO cohesify this method

		// Generate OL with LI tags for each {@code dir} in folder
		builder.open(OL, Map.of(CLASS, "folders"));
		for (File dir : listDirsFrom(folder, NONRECURSIVE))
			builder .open(LI)
						.open(A, Map.of(HREF, "./" + dir.getName()))
							.setText(dir.getName())
						.close(A)
					.close(LI);
		builder.close(OL);

		// Generate OL with LI tags for each {@code File} (nonDir) in folder
		builder.open(OL, Map.of(CLASS, "files"));
		for (File nonDir : listNonDirsFrom(folder, NONRECURSIVE))
			builder .open(LI)
						.open(A, Map.of(HREF, "./" + changeFileExt(nonDir.getName(), "html")))
							.setText(nonDir.getName())
						.close(A)
					.close(LI);
		builder.close(OL);

		return builder.toString();
	}




	public String createKatexHtml(FileOptionContainer foContainer) {

		boolean shouldUseKatexBuilder = foContainer.getOrDefault(KATEX, KATEX.defaultVal).equals("true");
		if (!shouldUseKatexBuilder)
			return "";

		else
			return new HtmlBuilder()
					.openSingle(LINK,
							Map.of(
									HREF, "https://cdn.jsdelivr.net/npm/katex@0.12.0/dist/katex.min.css",
									REL, "stylesheet",
									CROSSORIGIN, "anonymous"
							)
					)
					.open(SCRIPT,
							Map.of(
									DEFER, "",
									SRC, "https://cdn.jsdelivr.net/npm/katex@0.12.0/dist/katex.min.js",
									CROSSORIGIN, "anonymous"
							)
					).close(SCRIPT)
					.open(SCRIPT,
							Map.of(
									DEFER, "",
									SRC, "https://cdn.jsdelivr.net/npm/katex@0.12.0/dist/contrib/auto-render.min.js",
									CROSSORIGIN, "anonymous",
									ONLOAD, "renderMathInElement(document.body);"
							)
					).close(SCRIPT)
					.open(STYLE, Map.of(REL, "stylesheet"))
						.insert(".katex { font-size: 1em !important; }")
					.close(STYLE)
					.open(SCRIPT)
						.insert("""
								    document.addEventListener("DOMContentLoaded", function() {
								        renderMathInElement(document.body, {
								            delimiters: [
								                  {left: "$$", right: "$$", display: true},
								                  {left: "$", right: "$", display: false},
								              ],
								          macros: {
								              "\\\\RR": "\\\\mathbb{R}"
								          }
								        });
								    });
								""")
					.close(SCRIPT)
			.toString();
	}




}
