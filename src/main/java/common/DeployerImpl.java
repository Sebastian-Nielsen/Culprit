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
	private final UUIDGenerator uuidGenerator;

//	/**
//	 * @param relativeContentPath Relative path to the content root folder; e.g. "src/content"
//	 * @param relativeDeployPath  Relative path to the deploy  root folder; e.g  "src/ioFiles.deployment"
//	 * @param uuidGenerator
//	 */
//	public DeployerImpl(String relativeContentPath, String relativeDeployPath, framework.UUIDGenerator uuidGenerator) {
//		this.contentRootFolder = new File(CWD + "/" + relativeContentPath);
//		this.deployRootFolder  = new File(CWD + "/" + relativeDeployPath);
//		UUIDGenerator = uuidGenerator;
//	}

	public DeployerImpl(String contentRootString, String deployRootString, UUIDGenerator uuidGenerator) {
		this.contentRootFolder = new File(contentRootString);
		this.deployRootFolder  = new File(deployRootString );
		this.uuidGenerator = uuidGenerator;
	}

	public DeployerImpl(File contentRootFolder, File deployRootFolder, UUIDGenerator uuidGenerator) {
		this.contentRootFolder = contentRootFolder;
		this.deployRootFolder  = deployRootFolder;
		this.uuidGenerator = uuidGenerator;
	}

	public DeployerImpl(File contentRootFolder, File deployRootFolder) {
		this.contentRootFolder = contentRootFolder;
		this.deployRootFolder  = deployRootFolder;
		this.uuidGenerator = UUIDGeneratorImpl.getInstance();
	}

	public DeployerImpl() {
		this.contentRootFolder = new File(CWD + "/" + "content");
		this.deployRootFolder  = new File(CWD + "/" + "deployment");
		this.uuidGenerator = UUIDGeneratorImpl.getInstance();
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
	public void addDefaultIndexes() {
		addDefaultIndexesRecursivelyTo(deployRootFolder);
	}

	@Override
	public void addIdToContentFilesWithoutOne() throws Exception {

		Map<File, FileOptionContainer> fileToFOContainer = extractFOContainerFromEachFileIn(contentRootFolder);

		Set<File> files = fileToFOContainer.keySet();
		for (File file : files) {

			boolean foContainerHasIDKey = fileToFOContainer.get(file).containsKey(ID);
			if (!foContainerHasIDKey)

				addIdTo(file);


		}

	}


	/* === PRIVATE METHODS */

	private void addIdTo(File file) throws IOException {
		new FileOptionInserter().addIdTo(file);
	}

	private Map<File, FileOptionContainer> extractFOContainerFromEachFileIn(File folder) throws Exception {
		return FileOptionExtractorImpl.getInstance()
				.extractFOContainerFromEachFileIn(folder);
	}

	private void addDefaultIndexesRecursivelyTo(File folder) {

		for (File file : folder.listFiles())

			if (file.isDirectory())
				addDefaultIndexesRecursivelyTo(file);
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
