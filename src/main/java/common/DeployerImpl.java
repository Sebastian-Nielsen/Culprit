package common;

import framework.Deployer;
import framework.UUIDGenerator;
import framework.UUIDGeneratorImpl;

import java.io.File;
import java.io.IOException;
import java.util.stream.Stream;

import static framework.Constants.Constants.CWD;
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

	public DeployerImpl(File contentRootFolder, File deployRootFolder) {
		this.contentRootFolder = contentRootFolder;
		this.deployRootFolder  = deployRootFolder;
		this.uuidGenerator = new UUIDGeneratorImpl();
	}

	public DeployerImpl(File contentRootFolder, File deployRootFolder, UUIDGenerator uuidGenerator) {
		this.contentRootFolder = contentRootFolder;
		this.deployRootFolder  = deployRootFolder;
		this.uuidGenerator = uuidGenerator;
	}

	public DeployerImpl() {
		this.contentRootFolder = new File(CWD + "/" + "content");
		this.deployRootFolder  = new File(CWD + "/" + "deployment");
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
	public void addIdToContentFilesWithoutOne() {
		return;
	}


	/* === PRIVATE METHODS */

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
