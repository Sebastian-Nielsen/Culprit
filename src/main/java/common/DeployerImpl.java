package common;

import common.fileOption.FileOptionExtractorImpl;
import common.fileOption.FileOptionInserter;
import common.other.UUIDGeneratorImpl;
import framework.Deployer;
import framework.UUIDGenerator;
import common.fileOption.FileOptionContainer;

import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.Set;
import java.util.stream.Stream;

import static framework.Constants.Constants.CWD;
import static common.fileOption.FileOption.KEY.ID;
import static framework.utils.FileUtils.*;
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
		for (File contentFile : listAllFilesFrom(contentRootFolder))
			createDeployFileFrom(contentFile);

	}

	@Override
	public File getDeployEquivalentOf(File contentFile) {
		return new File(getAbsDeployPath(contentFile));
	}

	@Override
	public void addDefaultIndexes() throws IOException {
		addDefaultIndexesRecursivelyTo(deployRootFolder);
	}

	@Override
	public void addIdToContentFilesWithoutOne() throws Exception {

		Map<File, FileOptionContainer> fileToFOContainer = extractFOContainerFromEachFileIn(contentRootFolder);

		Set<File> files = fileToFOContainer.keySet();
		for (File file : files) {

			boolean foContainerHasIdKey = fileToFOContainer.get(file).containsKey(ID);
			if (!foContainerHasIdKey)

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

	private void addDefaultIndexesRecursivelyTo(File folder) throws IOException {

		boolean hasSeenIndexFile = false;

		for (File file : folder.listFiles()) {

			if (file.isDirectory())
				addDefaultIndexesRecursivelyTo(file);

			if (isIndexFile(file))
				hasSeenIndexFile = true;
		}

		if (!hasSeenIndexFile)
			new File(folder + "/index.html").createNewFile();
	}

	private boolean isIndexFile(File file) {
		return file.toString().equals("index.html");
	}


	private void addDefaultIndexTo(File folder) throws IOException {

		Stream<File> files = allNonDirFilesFrom(folder);

		// if an 'index.html' doesn't already exist; create one.
		boolean doesIndexFileExist = files.anyMatch(isEqual("index.html"));
		if (!doesIndexFileExist)
			createDefaultIndexIn(folder);

	}

	private void createDefaultIndexIn(File folder) throws IOException {
		new File(folder + "/index.html").createNewFile();
		// Write default
		// TODO
	}

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
