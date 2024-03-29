package common.html;

import common.preparatorFacade.Deployer;
import framework.ContentFileHierarchy;
import org.apache.commons.io.comparator.NameFileComparator;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import static common.html.HTML.Attribute.*;
import static common.html.HTML.Tag.*;
import static framework.utils.FileUtils.Filename.*;
import static framework.utils.FileUtils.Lister.RECURSION.NONRECURSIVELY;
import static org.apache.commons.io.FilenameUtils.removeExtension;

/**
 * Responsible for generating and storing navigation-html for each <em>content file</em> in {@link ContentFileHierarchy}.
 * The navigation-html of a <em>content file</em> can be retrieved either using the <em>content file</em> or
 * the <em>deploy file</em> equivalent of it (see {@link Deployer#getDeployEquivalentOf}).
 */
public class navHtmlGenerator implements NavigationHtmlGenerator {
	private final Map<String, String> relDirPathToNavHtml = new HashMap<>();
	private final int numberOfParentsToInclude;
	private @NotNull final File contentRootDir;
	private @NotNull final ContentFileHierarchy contentHierarchy;
	private @NotNull final String contentRootPath;

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
	public navHtmlGenerator(@NotNull ContentFileHierarchy contentHierarchy, int maxNumberOfParentsToInclude) {

		if (maxNumberOfParentsToInclude <= 0)
			throw new IllegalArgumentException("Number of parents to include must be greater than 0");

		this.numberOfParentsToInclude = maxNumberOfParentsToInclude;
		this.contentHierarchy = contentHierarchy;
		this.contentRootDir   = contentHierarchy.getRootDir();
		this.contentRootPath  = contentHierarchy.getRootPath();
	}

	public navHtmlGenerator(@NotNull ContentFileHierarchy deployHiearchy) {
		this(deployHiearchy, 3);
	}

	/* ===================================================== */

	/**
	 * Extracts {@code File}s and generates navigation html on the basis of the dirs
	 */
	@Override
	public void generateNavHtmlForAllFiles() throws Exception {
		main(contentRootDir);
	}


	/* ===================================================== */

	/* === STORING AND RETRIEVAL */

	/**
	 * @return Navigation html of the specified {@code File}
	 * @param relFilePath relative file path to the file for which to get the nav html of
	 */
	@Override
	public @NotNull String getNavHtmlOf(String relFilePath) {

		File parentDir = getParentFile(relFilePath);

		String relParentDirPath = relativePath(contentRootDir, parentDir);

		return relDirPathToNavHtml.get(relParentDirPath);
	}

	private File getParentFile(String relFilePath) {

		String absFilePath = contentRootPath + "/" + changeFileExt(relFilePath, "md");

		File   file        = new File(absFilePath);
		return file.getParentFile();
	}

	private void storeDirToNavHtml(File rootDir, String navHtml) {
		assert rootDir.isDirectory(); // TODO remove when tested

		String relDirPathWithoutExt = removeExtension(relativePath(contentRootDir, rootDir));

		relDirPathToNavHtml.put(relDirPathWithoutExt, navHtml);
	}


	/* ===================================================== */

	/* === PRIVATE METHODS - Main Recursion Logic */

	private void main(File dir) throws Exception {

		File dirToMark   = dir; // Keep track of: Parent files of any content file should be marked with a class attribute
		File originalDir = dir; // Keep track of: The dir of what content file we generating for; useful for reltiving href paths

		storeDirToNavHtml(
				dir,
				generateNavHtmlStringForDir( dir, dirToMark, originalDir, numberOfParentsToInclude )
		);

		for (File subDir : contentHierarchy.listDirsFrom(dir, NONRECURSIVELY))
			main(subDir);
	}


	private String generateNavHtmlStringForDir(File rootDir, File dirToMark, File originalDir, int numOfParentsToInclude) throws Exception {
		return wrapInDivContainer(
				recursivelyBuildHtmlBuilderForDir(rootDir, dirToMark, originalDir, numOfParentsToInclude)
		);
	}

	private HtmlBuilder recursivelyBuildHtmlBuilderForDir(File rootDir, File dirToMark, File originalDir, int numOfParentsToInclude) throws Exception {
		HtmlBuilder builder;

		if (numOfParentsToInclude > 0 && !isTopicDir(rootDir)) {
			// Ask for the solution to the parent of rootDir
			builder = recursivelyBuildHtmlBuilderForDir(rootDir.getParentFile(), rootDir, originalDir, numOfParentsToInclude-1);
		} else {
			// Base case, there is no more parents to include, so just generate for this rootDir
			builder = new HtmlBuilder();
		}
		buildOlTag(builder, rootDir, dirToMark, originalDir);

		return builder;
	}

	/* ===================================================== */

	/* PRIVATE METHODS - HtmlBuilder related */

	private void buildOlTag(HtmlBuilder builder, File rootDir, File dirToMark, File originalDir) throws IOException {
		builder.open(OL);

		buildLiTagsForDirs(builder, rootDir, originalDir, dirToMark);

		buildLiTagForNonDirs(builder, rootDir, originalDir);

		builder.close(OL);
	}

			/* ======================== */

	private void buildLiTagsForDirs(HtmlBuilder builder, File rootDir, File originalDir, File dirToMark) throws IOException {

		File[] dirs = sortByFileName(contentHierarchy.listDirsFrom(rootDir, NONRECURSIVELY));
		for (File dir : dirs) {

			String classValues;
			if (isEqual(dir, dirToMark))
				classValues = "dir isParentDir";
			else
				classValues = "dir";

			buildLiTagForDir(builder, dir, originalDir, classValues);
		}
	}

	private void buildLiTagForNonDirs(HtmlBuilder builder, File rootDir, File originalDir) throws IOException {

		File[] nonDirs = sortByFileName(contentHierarchy.listNonDirsFrom(rootDir, NONRECURSIVELY));
		for (File contentFile : nonDirs) {

			File deployFile = new File(changeFileExt(contentFile, "html"));

			String classValues = "nonDir";

			buildLiTagForNonDir(builder, deployFile, originalDir, classValues);
		}
	}

			/* ======================== */

	private void buildLiTagForDir(HtmlBuilder builder, File dir, File originalDir, String classValues) {
		String relPath = relativizePathBetween(originalDir, dir);

		String hrefValue = relPath.equals("") ? "." : relPath;

		buildLiTagFor(builder, dir, hrefValue, classValues);
	}

	private void buildLiTagForNonDir(HtmlBuilder builder, File nonDir, File originalDir, String classValues) {
		String hrefValue = relativizePathBetween(originalDir, nonDir);

		buildLiTagFor(builder, nonDir, hrefValue, classValues);
	}

			/* ======================== */

	private void buildLiTagFor(HtmlBuilder builder, File file, String hrefValue, String classValues) {

		builder	.open(LI, Map.of(CLASS, classValues))
					.open(A, Map.of(HREF, hrefValue))
						.setText(removeExtension(file.getName()))
					.close(A)
				.close(LI);
	}

	private String wrapInDivContainer(HtmlBuilder builder) {
		HtmlBuilder htmlBuilder = new HtmlBuilder();

		htmlBuilder .open(DIV, Map.of(ID, "container"))
						.insert(builder.toString())
					.close(DIV);

		return htmlBuilder.toString();
	}


	/* ===================================================== */

	/* === PRIVATE METHODS - Miscellaneous */

	private boolean isEqual(File fileA, File fileB) {
		return fileA.toString().equals(fileB.toString());
	}

	private File[] sortByFileName(File[] files) {
		Arrays.sort(files, NameFileComparator.NAME_COMPARATOR);
		return files;
	}

	private boolean isTopicDir(File dir) throws Exception {
		return contentHierarchy.isTopicDir(dir);
	}

}
