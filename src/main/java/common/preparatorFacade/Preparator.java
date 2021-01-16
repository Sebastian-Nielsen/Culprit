package common.preparatorFacade;

import common.preparatorFacade.DefaultIndexPreparator;
import common.preparatorFacade.Deployer;
import common.preparatorFacade.FileOptionPreparator;
import framework.CulpritFactory.PreparatorFactory;
import framework.PreparatorFacade;

import java.io.File;
import java.io.IOException;

import static framework.utils.FileUtils.Filename.changeFileExt;

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

	@Override
	public void cleanDeployDir(File folder) {

	}

}
