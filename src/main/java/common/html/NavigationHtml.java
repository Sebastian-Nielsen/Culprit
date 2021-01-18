package common.html;

import one.util.streamex.StreamEx;
import org.apache.commons.io.comparator.NameFileComparator;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static common.html.HTML.Attribute.CLASS;
import static common.html.HTML.Attribute.HREF;
import static common.html.HTML.Tag.*;
import static common.preparatorFacade.Deployer.getDeployEquivalentOf;
import static framework.utils.FileUtils.Filename.relativeFilePathBetween;
import static framework.utils.FileUtils.Filename.relativePath;
import static framework.utils.FileUtils.Lister.*;
import static framework.utils.FileUtils.Lister.RECURSION.NONRECURSIVE;
import static org.apache.commons.io.FilenameUtils.removeExtension;

/**
 * Responsible for efficiently storing NavigationHtml
 */
public class NavigationHtml {
	private static final Map<String, String> DirPathToNavHtmlOfFilesInTheDir = new HashMap<>();
	private static final int NUMBER_OF_PARENTS_TO_INCLUDE_IN_NAV_HTML = 3;
	private final File contentRootFolder;
	private final File deployRootFolder;

	public NavigationHtml(File contentRootFolder, File deployRootFolder) {
		this.contentRootFolder = contentRootFolder;
		this.deployRootFolder = deployRootFolder;
	}


	/**
	 * Extracts {@code File}s and generates navigation html on the basis of the dirs
	 */
	public void generateNavHtmlForAllFilesInDeploy() throws Exception {
		generateNavHtmlFor(deployRootFolder, deployRootFolder);
	}


	/* === GETTERS */

	/**
	 * @param contentFile file or folder
	 * @return Navigation html of the specified {@code File}
	 */
	public String getNavHtmlOf(File contentFile) {
		File deployFile = getDeployEquivalentOf(contentFile, contentRootFolder, deployRootFolder);

		File parent = deployFile.getParentFile();
		String parentPath = relativePath(parent, deployRootFolder);

//		System.out.println();
//		System.out.println();
//		System.out.println(":!:");
//		System.out.println(deployRootFolder.toURI().relativize(parent.toURI()).getPath());
//		System.out.println();
//		System.out.println(parent.toURI().relativize(deployRootFolder.toURI()).getPath());
//		System.out.println();
//		System.out.println();
//		System.out.println("contentRootFolder");
//		System.out.println(deployRootFolder.toString());
//		System.out.println();
//		System.out.println("parent:");
//		System.out.println(parent.toString());
//		System.out.println();
//		System.out.println("parentPath:");
//		System.out.println(parentPath);
//		for (String file : DirPathToNavHtmlOfFilesInTheDir.keySet())
//			System.out.println(file);
//		System.out.println();
//		System.out.println();
//		System.out.println();
//		System.out.println();
		return DirPathToNavHtmlOfFilesInTheDir.get(parentPath);
	}




	/* === PRIVATE METHODS */

	private void generateNavHtmlFor(File rootDir, File originalDir) throws Exception {

		File dirToMark = rootDir;

		HtmlBuilder navHtmlBuilder = generateNavHtmlForAllFilesInDeploy( rootDir, dirToMark, originalDir, NUMBER_OF_PARENTS_TO_INCLUDE_IN_NAV_HTML );
		storeDirToNavHtml(rootDir, navHtmlBuilder.toString());

		for (File subDir : listDirsFrom(rootDir, NONRECURSIVE))
			generateNavHtmlFor(subDir, originalDir);

	}

	private HtmlBuilder generateNavHtmlForAllFilesInDeploy(File rootDir, File dirToMark, File originalDir, int numOfParentsToInclude) throws Exception {
		HtmlBuilder builder;

		if (numOfParentsToInclude > 0) {
			// Ask for the solution to the parent of rootDir
			builder = generateNavHtmlForAllFilesInDeploy(rootDir.getParentFile(), rootDir, originalDir, numOfParentsToInclude-1);
		} else {
			// Base case, there is no more parents to include, so just generate for this rootDir
			builder = new HtmlBuilder();
		}
		buildOlTagOn(builder, rootDir, dirToMark, originalDir);

		return builder;
	}

	private File[] sortByFileName(File[] files) {
		Arrays.sort(files, NameFileComparator.NAME_COMPARATOR);
		return files;
	}

	private void buildOlTagOn(HtmlBuilder builder, File rootDir, File dirToMark, File originalDir) throws IOException {
		builder.open(OL);

		buildLiTagsForDirs(builder, rootDir, originalDir, dirToMark);

		buildLiTagsForNonDirs(builder, rootDir, originalDir);

		builder.close(OL);
	}

	private void buildLiTagsForDirs(HtmlBuilder builder, File rootDir, File originalDir, File dirToMark) throws IOException {
		File[] dirs = sortByFileName(listDirsFrom(rootDir, NONRECURSIVE));
		for (File file : dirs) {

			List<String> classValues;
			if (isEqual(file, dirToMark))
				classValues = List.of("dir", "marked");
			else
				classValues = List.of("dir");

			buildLiTagFor(builder, file, originalDir, classValues);
		}
	}

	private void buildLiTagsForNonDirs(HtmlBuilder builder, File rootDir, File originalDir) throws IOException {

		File[] nonDirs = sortByFileName(listNonDirsFrom(rootDir, NONRECURSIVE));
		for (File file : nonDirs)
			buildLiTagFor(builder, file, originalDir, List.of("file"));
	}

	private boolean isEqual(File fileA, File fileB) {
		return fileA.toString().equals(fileB.toString());
	}

	private void buildLiTagFor(HtmlBuilder builder, File file, File originalDir, List<String> listOfClassValues) {

		String classValues = String.join(" ", listOfClassValues);

		builder	.open(LI, Map.of(CLASS, classValues))
					.open(A, Map.of(HREF, relativeFilePathBetween(originalDir, file)))
						.setText(removeExtension(file.getName()))
					.close(A)
				.close(LI);
	}

	private void storeDirToNavHtml(File rootDir, String navHtml) {
		assert rootDir.isDirectory(); // TODO remove when tested

		// TODO shouldn't include if (rootDir.getFileName() == 'index.html')

		String dirPath = relativePath(rootDir, deployRootFolder);
		DirPathToNavHtmlOfFilesInTheDir.put(dirPath, navHtml);
	}


	/**
	 * E.g.
	 * "/"                  -> dirLevel=1
	 * "/content"           -> dirLevel=2     Note: (filePath is absolute not relative to root as seen here)
	 * "/content/fileC.md"  -> dirLevel=3
	 */
//	private static final Map<String, Integer> filePathToDirLevel = new HashMap<>();


	//	/**
//	 * parentNumber is number of times to call `.getParent()` on the given {@code file}.
//	 * Precondition: parentNum > 1
//	 */
//	private File getNthParent(File file, int parentNum) {
//		File parent = file;
//		for (int i = 0; i < parentNum; i++) {
//			parent = file.getParentFile();
//		}
//		return parent;
//	}
}
