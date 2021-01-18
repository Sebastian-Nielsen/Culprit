package common.html;

import org.apache.commons.io.comparator.NameFileComparator;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import static common.html.HTML.Attribute.CLASS;
import static common.html.HTML.Attribute.HREF;
import static common.html.HTML.Tag.*;
import static common.preparatorFacade.Deployer.getDeployEquivalentOf;
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
		generateNavHtmlFor(deployRootFolder);
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

	/**
	 *
	 */
	private void generateNavHtmlFor(File rootDir) throws Exception {

		HtmlBuilder navHtmlBuilder = generateNavHtmlForAllFilesInDeploy( rootDir, NUMBER_OF_PARENTS_TO_INCLUDE_IN_NAV_HTML );
		storeDirToNavHtml(rootDir, navHtmlBuilder.toString());

		for (File subDir : listDirsFrom(rootDir, NONRECURSIVE))
			generateNavHtmlFor(subDir);

	}


	private File[] sortByFileName(File[] files) {
		Arrays.sort(files, NameFileComparator.NAME_COMPARATOR);
		return files;
	}

	private void buildOlTagOn(HtmlBuilder builder, File rootDir) throws IOException {
		builder.open(OL);

		File[] dirs = sortByFileName(listDirsFrom(rootDir, NONRECURSIVE));
		for (File file : dirs)
			buildLiTagFor(builder, file);

		File[] nonDirs = sortByFileName(listNonDirsFrom(rootDir, NONRECURSIVE));
		for (File file : nonDirs)
			buildLiTagFor(builder, file);

		builder.close(OL);
	}

	private void buildLiTagFor(HtmlBuilder builder, File file) {
		String liClassValue = file.isFile() ? "file" : "folder";
		builder
				.open(LI, Map.of(CLASS, liClassValue))
					.open(A, Map.of(HREF, "./" + file.getName()))
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


	private HtmlBuilder generateNavHtmlForAllFilesInDeploy(File rootDir, int numOfParentsToInclude) throws Exception {
		HtmlBuilder builder;

		if (numOfParentsToInclude > 0) {
			// Ask for the solution to the parent of rootDir
			builder = generateNavHtmlForAllFilesInDeploy(rootDir.getParentFile(), numOfParentsToInclude-1);
		} else {
			// Base case, there is no more parents to include, so just generate for this rootDir
			builder = new HtmlBuilder();
		}
		buildOlTagOn(builder, rootDir);

		return builder;
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
