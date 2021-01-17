package common.html.htmlTemplatesStrategy;

import common.compilerFacade.CompilerDataContainer;
import common.html.HTML;
import common.html.htmlBuilderStrategy.HtmlBuilder;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.IOException;
import java.util.Map;

import static common.html.HTML.Attribute.*;
import static common.html.HTML.Tag.*;
import static common.html.HTML.Tag.LINK;
import static common.html.HTML.Tag.TITLE;
import static framework.Constants.Constants.CWD_NAME;
import static framework.utils.FileUtils.Lister.RECURSION.NONRECURSIVE;
import static framework.utils.FileUtils.Lister.listDirsFrom;
import static framework.utils.FileUtils.Lister.listNonDirsFrom;


public class Helper {


	public static String buildDefaultPageHtmlTemplateUsing(File contentFile, String articleTag,
	                                                       CompilerDataContainer dataContainer) throws Exception {
		return new DefaultPageHtmlTemplate()
				.buildUsing(contentFile, articleTag, dataContainer.getFOContainerOf(contentFile));
	}


		/**
	 * For each {@code File} (both dirs and files) in the specified folder,
	 * create a li {@code Tag} and add it to the ol {@code Tag} to return.
	 * @param folder folder from which to list the files from.
	 * @return an ol {@code Tag}
	 */
	public static HtmlBuilder generateOlTagListingOfFilesIn(File folder) throws IOException {
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


	public static HtmlBuilder defaultHeadTags =
			new HtmlBuilder()
					.open(TITLE).setText("index").close(TITLE)
					.openSingle(META, Map.of(CHARSET, "utf-8"))
					.openSingle(LINK, defaultCssAttributes("global.css"));



	public static @NotNull Map<HTML.Attribute, String> defaultCssAttributes(String hrefVal) {
		return Map.of(
//				HREF, "/" + hrefVal,
				HREF, "/%s/%s".formatted(CWD_NAME, hrefVal),
				REL,  "stylesheet"
		);
	}

	public static @NotNull Map<HTML.Attribute, String> defaultScriptAttributes(String srcVal,
	                                                                           Map<HTML.Attribute, String> attrs) {
		return combineMaps(attrs,
				Map.of(
//						SRC, "/" + srcVal,
						SRC, "/%s/%s".formatted(CWD_NAME, srcVal),
						REL, "stylesheet"
				)
		);
	}

	private static Map<HTML.Attribute, String> combineMaps(Map<HTML.Attribute, String> mapA,
	                                                       Map<HTML.Attribute, String> mapB) {
		mapA.putAll(mapB);
		return mapA;
	}

}
