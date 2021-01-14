package common;

import common.fileOption.FileOptionContainer;
import common.fileOption.FileOptionExtractorImpl;
import common.fileOption.FileOptionInserter;
import common.html.preparatorClasses.DefaultIndexPreparator;
import common.html.preparatorClasses.Deployer;
import common.html.preparatorClasses.FileOptionPreparator;
import framework.PreparatorFacade;
import framework.singleClasses.CompilerFacade;

import java.io.File;
import java.io.IOException;
import java.util.Map;

public class Preparator implements PreparatorFacade {

	private final File contentRootFolder;
	private final File deployRootFolder;
	private final FileOptionPreparator fileOptionPreparator;
	private final Deployer deployer;

	/* ============================================= */

//	/**
//	 * @param relativeContentPath Relative path to the content root folder; e.g. "src/content"
//	 * @param relativeDeployPath  Relative path to the deploy  root folder; e.g  "src/ioFiles.deployment"
//	 */
//	public Preparator(String relativeContentPath, String relativeDeployPath, FileOptionInserter fileOptionInserter) {
//		this.contentRootFolder = new File(CWD + '/' + relativeContentPath);
//		this.deployRootFolder  = new File(CWD + '/' + relativeDeployPath);
//		this.fileOptionPreparator = new FileOptionPreparator(contentRootFolder, fileOptionInserter);
//		this.deployer = new Deployer(contentRootFolder, deployRootFolder);
//	}
	public Preparator(File contentRootFolder, File deployRootFolder, FileOptionInserter fileOptionInserter, CompilerFacade.Builder builder) {
		this.contentRootFolder = contentRootFolder;
		this.deployRootFolder  = deployRootFolder;
		this.fileOptionPreparator = new FileOptionPreparator(contentRootFolder, fileOptionInserter, this);
		this.deployer = new Deployer(contentRootFolder, deployRootFolder);
	}
	public Preparator(File contentRootFolder, File deployRootFolder, CompilerFacade.Builder builder) {
		this(contentRootFolder, deployRootFolder, new FileOptionInserter(), builder);
	}

	/* ============================================= */

	@Override
	public void prepare() throws Exception {
		deploy();

		if (addDefaultIndexes)
			addDefaultIndexes();

		if (addIdToContentFilesWithoutOne)
			addIdToContentFilesWithoutOne();
	}

	/* ============================================= */

	@Override
 	public void deploy() throws IOException {
		deployer.deploy();
	}

	@Override
	public File getDeployEquivalentOf(File contentFile) {
		return deployer.getDeployEquivalentOf(contentFile);
	}

	/* ============================================= */

	@Override
	public void addDefaultIndexes() throws Exception {
		DefaultIndexPreparator.addDefaultIndexesRecursivelyTo(deployRootFolder);
	}

	/* ============================================= */

	@Override
	public void addRequiredFileOptionsToFilesWithoutOne() throws Exception {
		addIdToContentFilesWithoutOne();
	}

	@Override
	public void addIdToContentFilesWithoutOne() throws Exception {
		fileOptionPreparator.addIdToContentFilesWithoutOne();
	}

	/* ============================================= */

	@Override
	public Map<File, FileOptionContainer> extractFOContainerFromEachContentFile() throws Exception {

		return FileOptionExtractorImpl.getInstance()
				.extractFOContainerFromEachFileIn(contentRootFolder);

	}

}
