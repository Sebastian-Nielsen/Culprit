package common;

import common.fileOption.FileOptionContainer;
import common.fileOption.FileOptionExtractorImpl;
import common.preparatorClasses.DefaultIndexPreparator;
import common.preparatorClasses.Deployer;
import common.preparatorClasses.FileOptionPreparator;
import framework.CulpritFactory.PreparatorFactory;
import framework.PreparatorFacade;

import java.io.File;
import java.io.IOException;
import java.util.Map;

public class Preparator implements PreparatorFacade {

	private final File contentRootFolder;
	private final File deployRootFolder;
	private final FileOptionPreparator fileOptionPreparator;
	private final Deployer deployer;
	private final boolean shouldAddDefaultIndexes;
	private final boolean shouldAddIdToContentFilesWithoutOne;

	/* ============================================= */

	public Preparator(PreparatorFactory factory) {
		this.contentRootFolder = factory.getContentRootFolder();
		this.deployRootFolder  = factory.getDeployRootFolder();
		this.shouldAddDefaultIndexes = factory.addDefaultIndexes();  // TODO extract into object with (¤)
		this.shouldAddIdToContentFilesWithoutOne = factory.addIdToContentFilesWithoutOne(); // TODO extract into object with (¤)
		this.fileOptionPreparator = factory.createFileOptionPreparator(this);
		this.deployer = new Deployer(contentRootFolder, deployRootFolder);
	}

	/* ============================================= */

	@Override
	public void prepare() throws Exception {
		deploy();

		if (shouldAddDefaultIndexes)
			addDefaultIndexes();

		if (shouldAddIdToContentFilesWithoutOne)
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
