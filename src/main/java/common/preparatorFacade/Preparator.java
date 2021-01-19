package common.preparatorFacade;

import framework.CulpritFactory.PreparatorFactory;
import framework.DeployFileHierarchy;
import framework.PreparatorFacade;

import java.io.File;
import java.io.IOException;

import static framework.utils.FileUtils.Filename.changeFileExt;

public class Preparator implements PreparatorFacade {

	private final FileOptionPreparator fileOptionPreparator;
	private final Deployer deployer;
	private final boolean shouldAddDefaultIndexes;
	private final boolean shouldAddIdToContentFilesWithoutOne;
	private final DeployFileHierarchy deployFileHierchy;

	/* ============================================= */

	public Preparator(PreparatorFactory factory) {
		this.deployFileHierchy = factory.getDeployHierarchy();

		this.shouldAddDefaultIndexes             = factory.addDefaultIndexes();             // TODO extract into object with (¤)
		this.shouldAddIdToContentFilesWithoutOne = factory.addIdToContentFilesWithoutOne(); // TODO extract into object with (¤)
		this.fileOptionPreparator                = factory.createFileOptionPreparator(this);

		this.deployer = new Deployer(factory.getContentHierarchy(), deployFileHierchy);
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
		DefaultIndexPreparator.addDefaultIndexesRecursivelyTo(deployFileHierchy.getDeployRootDir());
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
