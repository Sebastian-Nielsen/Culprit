package common;

import common.fileOption.FileOptionExtractorImpl;
import common.fileOption.FileOptionInserter;
import common.html.HtmlBuilder;
import framework.Deployer;
import common.fileOption.FileOptionContainer;

import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.Set;

import static framework.Constants.Constants.CWD;
import static common.fileOption.FileOption.KEY.ID;
import static framework.utils.FileUtils.Filename.changeFileExt;
import static framework.utils.FileUtils.Filename.getRelativePath;
import static framework.utils.FileUtils.Lister.*;
import static framework.utils.FileUtils.Lister.RECURSION.NONRECURSIVE;
import static framework.utils.FileUtils.Lister.RECURSION.RECURSIVE;
import static framework.utils.FileUtils.Modifier.writeStringTo;
import static java.util.function.Predicate.isEqual;

public class DeployerImpl implements Deployer {

	private final File contentRootFolder;
	private final File deployRootFolder;
	private final FileOptionInserter fileOptionInserter;

	/**
	 * @param relativeContentPath Relative path to the content root folder; e.g. "src/content"
	 * @param relativeDeployPath  Relative path to the deploy  root folder; e.g  "src/ioFiles.deployment"
	 */
	public DeployerImpl(String relativeContentPath, String relativeDeployPath, FileOptionInserter fileOptionInserter) {
		this.contentRootFolder = new File(CWD + '/' + relativeContentPath);
		this.deployRootFolder  = new File(CWD + '/' + relativeDeployPath);
		this.fileOptionInserter = fileOptionInserter;
	}

	public DeployerImpl(File contentRootFolder, File deployRootFolder, FileOptionInserter fileOptionInserter) {
		this.contentRootFolder = contentRootFolder;
		this.deployRootFolder  = deployRootFolder;
		this.fileOptionInserter = fileOptionInserter;
	}

	public DeployerImpl(File contentRootFolder, File deployRootFolder) {
		this.contentRootFolder = contentRootFolder;
		this.deployRootFolder  = deployRootFolder;
		this.fileOptionInserter = new FileOptionInserter();
	}

	@Override
 	public void deploy() throws IOException {

		for (File contentFile : listFilesAndDirsFrom(contentRootFolder, RECURSIVE))
			createDeployFileFrom(contentFile);

	}

	@Override
	public File getDeployEquivalentOf(File contentFile) {
		return new File(getAbsDeployPath(contentFile));
	}

	@Override
	public void addDefaultIndexes() throws Exception {
		addDefaultIndexesRecursivelyTo(deployRootFolder);
	}

	@Override
	public void addIdToContentFilesWithoutOne() throws Exception {

		Map<File, FileOptionContainer> fileToFOContainer = extractFOContainerFromEachFileIn(contentRootFolder);

		Set<File> files = fileToFOContainer.keySet();
		for (File file : files) {

			boolean foContainerOfFileHasIdKey = fileToFOContainer.get(file).containsKey(ID);
			if (!foContainerOfFileHasIdKey)

				addIdTo(file);

		}

	}


	/* === PRIVATE METHODS */

	private void addIdTo(File file) throws IOException {
		fileOptionInserter.addIdTo(file);
	}

	private Map<File, FileOptionContainer> extractFOContainerFromEachFileIn(File folder) throws Exception {

		return FileOptionExtractorImpl.getInstance()
				.extractFOContainerFromEachFileIn(folder);

	}


	/* ===================================================== */


	private void addDefaultIndexesRecursivelyTo(File folder) throws Exception {

		for (File dir : listDirsFrom(folder, NONRECURSIVE))

			addDefaultIndexesRecursivelyTo(dir);

		addDefaultIndexTo(folder);

	}

	/**
	 * Add a default index `index.html` to {@code folder}
	 * if one doesn't already exist.
	 */
	private void addDefaultIndexTo(File folder) throws Exception {

		boolean doesIndexFileExist = streamNonDirsFrom(folder, NONRECURSIVE).anyMatch(isEqual("index.html"));
		if (!doesIndexFileExist)
			createDefaultIndexIn(folder);

	}

	/**
	 * Creates a default-index.html and writes the default-index.html content to it.
	 * @param folder folder in which to create the default-index
	 */
	private void createDefaultIndexIn(File folder) throws IOException {
		String defaultIndexHtml = HtmlBuilder.buildDefaultIndexHtml(folder);

		File indexFile = new File(folder + "/index.html");

		indexFile.createNewFile();

		writeStringTo(indexFile, defaultIndexHtml);
	}


	/* ===================================================== */


	private String getRelativeDeployPath(File contentFile) {

		String relativePath = getRelativePath(contentFile, contentRootFolder.toURI());

		if (relativePath.endsWith(".md"))
			relativePath = changeFileExt(relativePath, "html");

		return relativePath;

	}

	private String getAbsDeployPath(File contentFile) {
		return deployRootFolder + "/" + getRelativeDeployPath(contentFile);
	}

	private void createDeployFileFrom(File contentFile) throws IOException {

		File deployFile = getDeployEquivalentOf(contentFile);

		if (contentFile.isFile())
			deployFile.createNewFile();
		else
			deployFile.mkdirs();

	}


}
