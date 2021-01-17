package common.html;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import static common.html.HTML.Attribute.HREF;
import static common.html.HTML.Tag.*;
import static framework.Constants.Constants.CWD;
import static framework.Constants.Constants.ROOT_FILE;
import static framework.utils.FileUtils.Filename.changeFileExt;
import static framework.utils.FileUtils.Lister.RECURSION.NONRECURSIVE;
import static framework.utils.FileUtils.Lister.listFilesAndDirsFrom;
import static org.apache.commons.io.FilenameUtils.removeExtension;

/**
 * Responsible for efficiently storing NavigationHtml
 */
public class NavigationHtml {
	private static final NavigationHtml instance = new NavigationHtml();

	/**
	 * E.g.
	 * "/"                  -> dirLevel=1
	 * "/content"           -> dirLevel=2     Note: (filePath is absolute not relative to root as seen here)
	 * "/content/fileC.md"  -> dirLevel=3
	 */
	private static final Map<String, Integer> filePathToDirLevel = new HashMap<>();

	private static final Map<Integer, String> dirLevelToNavigationHtml = new HashMap<>();

	private NavigationHtml() {}

	/**
	 * Generates the navigation html on the basis of the files in the {@code deployRootFolder}
	 */
	public void generateNavHtmlFor(File deployRootFolder) throws Exception {
		generateNavHtmlForDir(deployRootFolder, new HtmlBuilder(), 1);
	}

	private void generateNavHtmlForDir(File dir, HtmlBuilder builder, int dirLevel) throws Exception {
		builder.open(OL);
		HtmlBuilder builderSnapshot = builder.clone();

		for (File file : listFilesAndDirsFrom(dir, NONRECURSIVE)) {

			filePathToDirLevel.put(file.toString(), dirLevel);

			if (file.isFile())
				generateNavHtmlForNonDir(file, builder);

			else
				generateNavHtmlForDir(file, builder, dirLevel+1);

		}

		builderSnapshot.close(OL);

		dirLevelToNavigationHtml.put(dirLevel, builderSnapshot.toString());
	}

	private HtmlBuilder generateNavHtmlForNonDir(File file, HtmlBuilder builder) {
		return builder
				.open(LI)
					.open(A, Map.of(HREF, "./" + file.getName()))
						.setText(removeExtension(file.getName()))
					.close(A)
				.close(LI);
	}

	public static NavigationHtml getInstance() {
		return instance;
	}

	/**
	 * @param file file or folder
	 * @return Navigation html of the specified {@code File}
	 */
	public String getNavHtmlOf(File file) {
		int dirLvlOfFile = getDirLvlOf(file);
		return dirLevelToNavigationHtml.get(dirLvlOfFile);
	}

	private int getDirLvlOf(File file) {
		return filePathToDirLevel.get(file.toString());
	}

}
