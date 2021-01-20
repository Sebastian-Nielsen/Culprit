package common.html;

import common.preparatorFacade.Deployer;
import framework.ContentFileHierarchy;
import framework.DeployFileHierarchy;
import framework.FileHierarchy;
import org.apache.commons.io.comparator.NameFileComparator;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static common.html.HTML.Attribute.CLASS;
import static common.html.HTML.Attribute.HREF;
import static common.html.HTML.Tag.*;
import static framework.utils.FileUtils.Filename.relativeFilePathBetween;
import static framework.utils.FileUtils.Filename.relativePath;
import static framework.utils.FileUtils.Lister.*;
import static framework.utils.FileUtils.Lister.RECURSION.NONRECURSIVELY;
import static org.apache.commons.io.FilenameUtils.removeExtension;

/**
 * Responsible for generating and storing navigation-html for each <em>content file</em> in {@link ContentFileHierarchy}.
 * The navigation-html of a <em>content file</em> can be retrieved either using the <em>content file</em> or
 * the <em>deploy file</em> equivalent of it (see {@link Deployer#getDeployEquivalentOf}).
 */
public class navHtmlGenerator implements NavigationHtmlGenerator {
	private final Map<String, String> dirPathToNavHtmlOfFilesInTheDir = new HashMap<>();
	private final int numberOfParentsToInclude;
	private @NotNull final File deployRootFolder;
	private @NotNull final DeployFileHierarchy deployHierarchy;

	/**
	 * @param maxNumberOfParentsToInclude a positive number that for each file/folder in a file-hierarchy specifies
	 *                                    how many of its parent at max should be included in the generated
	 *                                    navigation-html for the given file/folder.
	 *                                    {@code maxNumberOfParentsToInclude}= ...
	 *                                    <ul>
	 *                                    <li>{@code 0}: <em>one ol</em> consisting of the files in the current dir</li>
	 *                                    <li>{@code 1}: <em>two ol</em> one as above, the other consisting of all files and
	 *                                                   dirs in the parent dir of the current dir</li>
	 *                                    <li>{@code 2}: <em>three ol</em>, ...</li>
	 *                                    </ul>
	 */
	public navHtmlGenerator(@NotNull DeployFileHierarchy deployHierarchy, int maxNumberOfParentsToInclude) {

		if (maxNumberOfParentsToInclude <= 0)
			throw new IllegalArgumentException("Number of parents to include must be greater than 0");

		this.numberOfParentsToInclude = maxNumberOfParentsToInclude;
		this.deployHierarchy = deployHierarchy;
		this.deployRootFolder = deployHierarchy.getRootDir();
	}

	public navHtmlGenerator(@NotNull DeployFileHierarchy deployHiearchy) {
		this(deployHiearchy, 3);
	}


	/**
	 * Extracts {@code File}s and generates navigation html on the basis of the dirs
	 */
	@Override
	public void generateNavHtmlForAllFiles() throws Exception {
		generateNavHtmlFor(deployRootFolder);
	}


	/* === GETTERS */

	/**
	 * @param deployFile file or folder
	 * @return Navigation html of the specified {@code File}
	 */
	@Override
	public String getNavHtmlOf(File deployFile) {

		File parent = deployFile.getParentFile();

		String parentPath = relativePath(parent, deployRootFolder);

		return dirPathToNavHtmlOfFilesInTheDir.get(parentPath);
	}




	/* === PRIVATE METHODS */

	private void generateNavHtmlFor(File rootDir) throws Exception {

		File dirToMark   = rootDir;
		File originalDir = rootDir;

		HtmlBuilder navHtmlBuilder = generateNavHtmlForAllFilesInDeploy( rootDir, dirToMark, originalDir, numberOfParentsToInclude);
		storeDirToNavHtml(rootDir, navHtmlBuilder.toString());

		for (File subDir : deployHierarchy.listDirsFrom(rootDir, NONRECURSIVELY))
			generateNavHtmlFor(subDir);

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
		buildOlTag(builder, rootDir, dirToMark, originalDir);

		return builder;
	}

	private File[] sortByFileName(File[] files) {
		Arrays.sort(files, NameFileComparator.NAME_COMPARATOR);
		return files;
	}

	private void buildOlTag(HtmlBuilder builder, File rootDir, File dirToMark, File originalDir) throws IOException {
		builder.open(OL);

		buildLiTagsForDirs(builder, rootDir, originalDir, dirToMark);

		buildLiTagsForNonDirs(builder, rootDir, originalDir);

		builder.close(OL);
	}

	private void buildLiTagsForDirs(HtmlBuilder builder, File rootDir, File originalDir, File dirToMark) throws IOException {

		File[] dirs = sortByFileName(deployHierarchy.listDirsFrom(rootDir, NONRECURSIVELY));
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

		File[] nonDirs = sortByFileName(listNonDirsFrom(rootDir, NONRECURSIVELY));
		for (File file : nonDirs)

			buildLiTagFor(builder, file, originalDir, List.of("file"));

	}

	private boolean isEqual(File fileA, File fileB) {
		return fileA.toString().equals(fileB.toString());
	}

	private void buildLiTagFor(HtmlBuilder builder, File file, File originalDir, List<String> listOfClassValues) {

		String classValues = String.join(" ", listOfClassValues);
//		System.out.println();
//		System.out.println();
//		System.out.println();
//		System.out.println("original");
//		System.out.println(originalDir);
//		System.out.println();
//		System.out.println("file");        relativePathToHiearchyRoot
//		System.out.println(file);  e.g.    contentHiearchy.relativePathToRoot(contentFile);
//		System.out.println();
//		System.out.println(relativeFilePathBetween(originalDir, file));
//		System.out.println();
//		System.out.println();
		builder	.open(LI, Map.of(CLASS, classValues))
					.open(A, Map.of(HREF, relativeFilePathBetween(originalDir, file)))
						.setText(removeExtension(file.getName()))
					.close(A)
				.close(LI);
	}

	// Should take relative filePath as argument (instead of rootDir)
	private void storeDirToNavHtml(File rootDir, String navHtml) {
		assert rootDir.isDirectory(); // TODO remove when tested

		String dirPath = relativePath(rootDir, deployRootFolder);
		dirPathToNavHtmlOfFilesInTheDir.put(dirPath, navHtml);
	}

	/**
	 * Precondition: dirPath have a fileExt
	 */
	private String stripFileExt(String dirPath) {
		int indexOfLastDot = dirPath.lastIndexOf(".");
		assert indexOfLastDot != -1;
		return dirPath.substring(0, indexOfLastDot);
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